package com.shf.ssyx.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.common.constant.RedisConst;
import com.shf.ssyx.common.exception.SsyxException;
import com.shf.ssyx.common.result.ResultCodeEnum;
import com.shf.ssyx.model.product.SkuAttrValue;
import com.shf.ssyx.model.product.SkuImage;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.model.product.SkuPoster;
import com.shf.ssyx.mq.config.MQConfig;
import com.shf.ssyx.mq.constant.MqConst;
import com.shf.ssyx.mq.service.RabbitService;
import com.shf.ssyx.product.mapper.SkuInfoMapper;
import com.shf.ssyx.product.service.SkuAttrValueService;
import com.shf.ssyx.product.service.SkuImageService;
import com.shf.ssyx.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.product.service.SkuPosterService;
import com.shf.ssyx.vo.product.SkuInfoQueryVo;
import com.shf.ssyx.vo.product.SkuInfoVo;
import com.shf.ssyx.vo.product.SkuStockLockVo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    /**
     * Sku图片
     */
    @Autowired
    private SkuImageService skuImageService;

    /**
     * SKU平台属性
     */
    @Autowired
    private SkuAttrValueService skuAttrValueService;

    /**
     * SKU海报
     */
    @Autowired
    private SkuPosterService skuPosterService;

    /**
     * MQ发送消息
     */
    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * SKU列表
     * @param pageParam
     * @param skuInfoQueryVo
     * @return
     */
    @Override
    public IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();

        String keyword = skuInfoQueryVo.getKeyword();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String skuType = skuInfoQueryVo.getSkuType();

        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }

        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    /**
     * 添加商品SKU信息
     * @param skuInfoVo
     */
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
//        1. 添加SKU基本信息
//        SkuInfoVo --- SkuInfo
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);

//        2. 保存SKU海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            skuPosterList.forEach(skuPoster -> skuPoster.setSkuId(skuInfo.getId()));
            skuPosterService.saveBatch(skuPosterList);
        }

//        3.保存SKU图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            skuImagesList.forEach(skuImage -> skuImage.setSkuId(skuImage.getSkuId()));
            skuImageService.saveBatch(skuImagesList);
        }

//        4. 保存SKU平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuImagesList.forEach(skuImage -> skuImage.setSkuId(skuImage.getSkuId()));
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    /**
     * 获取SKU信息
     * @param id
     * @return
     */
    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();

//        根据id查询sku基本信息
        SkuInfo skuInfo = baseMapper.selectById(id);
        BeanUtils.copyProperties(skuInfo, skuInfoVo);

//        根据id查询商品图片列表
        List<SkuImage> skuImageList = skuImageService.getImageListBySkuId(id);
        skuInfoVo.setSkuImagesList(skuImageList);

//        根据id查询商品海报列表
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListBySkuId(id);
        skuInfoVo.setSkuPosterList(skuPosterList);

//        根据id查询商品属性信息列表
        List<SkuAttrValue> skuAttrValueList=skuAttrValueService.getAttrValueListBySkuId(id);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);

        return skuInfoVo;
    }

    /**
     * 修改SKU
     * @param skuInfoVo
     */
    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
//        修改SKU基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);

        Long skuId = skuInfoVo.getId();
//        海报信息 删除后重新添加
        LambdaQueryWrapper<SkuPoster> skuPosterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuPosterLambdaQueryWrapper.eq(SkuPoster::getSkuId, skuId);
        skuPosterService.remove(skuPosterLambdaQueryWrapper);

        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            skuPosterList.forEach(skuPoster -> skuPoster.setSkuId(skuId));
            skuPosterService.saveBatch(skuPosterList);
        }

//        商品图片
        LambdaQueryWrapper<SkuImage> skuImageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuImageLambdaQueryWrapper.eq(SkuImage::getSkuId, skuId);
        skuImageService.remove(skuImageLambdaQueryWrapper);

        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            skuPosterList.forEach(skuPoster -> skuPoster.setSkuId(skuId));
            skuImageService.saveBatch(skuImagesList);
        }

//        商品属性
        LambdaQueryWrapper<SkuAttrValue> skuAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuAttrValueLambdaQueryWrapper.eq(SkuAttrValue::getSkuId, skuId);
        skuAttrValueService.remove(skuAttrValueLambdaQueryWrapper);

        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuAttrValueList.forEach(skuAttrValue -> skuAttrValue.setSkuId(skuId));
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }

    /**
     * 商品审核
     * @param skuId
     * @param status
     */
    @Override
    public void check(long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    /**
     * 商品上下架
     * @param skuId
     * @param status
     */
    @Override
    public void publish(Long skuId, Integer status) {
        if (status == 1) { // 上架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);

//            TODO 整合MQ把数据同步到ES里面
            rabbitService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_UPPER,
                    skuId);
        } else { // 下架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
//            TODO 整合MQ把数据同步到ES里面
            rabbitService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_LEADER_LOWER,
                    skuId
            );
        }
    }

    /**
     * 新人专享
     * @param skuId
     * @param status
     */
    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }

    /**
     * 根据skuId列表得到sku信息列表
     * @param skuIdList
     * @return
     */
    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        List<SkuInfo> skuInfoList = baseMapper.selectBatchIds(skuIdList);
        return skuInfoList;
    }

    /**
     * 根据关键词匹配SKU列表
     * @param keyword
     * @return
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoList = baseMapper.selectList(
                new LambdaQueryWrapper<SkuInfo>()
                        .like(SkuInfo::getSkuName, keyword)
        );
        return skuInfoList;
    }

    /**
     * 获取新人专享商品
     * @return
     */
    @Override
    public List<SkuInfo> findNewPersonSkuInfoList() {
//        条件1： is_new_person=1
//        条件2：publish_status=1
//        条件3：其实其中三个
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuInfo::getIsNewPerson, 1);
        wrapper.eq(SkuInfo::getPublishStatus, 1);
        wrapper.orderByDesc(SkuInfo::getStock);

        Page<SkuInfo> pageParam = new Page<>(1, 3);
        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        List<SkuInfo> records = skuInfoPage.getRecords();
        return records;
    }

    /**
     * 根据skuId获取SKu信息
     * @param skuId
     * @return
     */
    @Override
    public SkuInfoVo getSkuInfoVo(Long skuId) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
//        skuId查询skuInfo
        SkuInfo skuInfo = baseMapper.selectById(skuId);

//        skuId查询sku图片
        List<SkuImage> skuImages = skuImageService.getImageListBySkuId(skuId);

//        skuId查询sku海报
        List<SkuPoster> skuPosters = skuPosterService.getPosterListBySkuId(skuId);

//        SkuId查询sku属性
        List<SkuAttrValue> skuAttrValues = skuAttrValueService.getAttrValueListBySkuId(skuId);

//        封装到SkuInfoVo对象
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImages);
        skuInfoVo.setSkuPosterList(skuPosters);
        skuInfoVo.setSkuAttrValueList(skuAttrValues);

        return skuInfoVo;
    }

    /**
     * 验证和锁定库存
     * @param skuStockLockVoList
     * @param orderNo
     * @return
     */
    @Override
    public Boolean checkAndLock(List<SkuStockLockVo> skuStockLockVoList, String orderNo) {
//        1.判断skuStockLockVoList集合是否为空
        if (CollectionUtils.isEmpty(skuStockLockVoList)) {
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }

//        2.遍历skuStockLockVoList得到每个商品，验证库存并锁定库存，具备原子性
        skuStockLockVoList.forEach(skuStockLockVo -> {
            checkLock(skuStockLockVo);
        });

//        3.只要有一个商品锁定失败，所有锁定成功的都解锁
        boolean flag = skuStockLockVoList.stream().anyMatch(SkuStockLockVo::getIsLock);
        if (flag) {
//            所有锁定成功的商品都解锁
            skuStockLockVoList.stream().filter(SkuStockLockVo::getIsLock)
                    .forEach(skuStockLockVo -> {
                        baseMapper.unlockStock(skuStockLockVo.getSkuId(), skuStockLockVo.getSkuNum());
                    });
//            返回失败信息
            return false;
        }

//        4.如果所有商品都锁定成功了，redis缓存相关数据，为了方便后面解锁和减轻库存
        redisTemplate.opsForValue().set(RedisConst.SROCK_INFO + orderNo, skuStockLockVoList);
        return true;
    }

    /**
     * 2.遍历skuStockLockVoList得到每个商品，验证库存并锁定库存，具备原子性
     * @param skuStockLockVo
     */
    private void checkLock(SkuStockLockVo skuStockLockVo) {
        Long skuId = skuStockLockVo.getSkuId();
        Integer skuNum = skuStockLockVo.getSkuNum();
//        获取锁
        RLock rLock = redissonClient.getFairLock(RedisConst.SKUKEY_PREFIX + skuId);

//        加锁
        rLock.lock();

        try {
//        验证库存
            SkuInfo skuInfo = baseMapper.checkStock(skuId, skuNum);

//        判断有没有满足条件商品，设置isLock值false，返回
            if (skuInfo == null) {
                skuStockLockVo.setIsLock(false);
                return;
            }
//            有满足条件商品
//            锁定库存
            Integer rows = baseMapper.lockStock(skuId, skuNum);
            if (rows == 1) {
                skuStockLockVo.setIsLock(true);
            }
        } finally {
            rLock.unlock();
        }
    }
}
