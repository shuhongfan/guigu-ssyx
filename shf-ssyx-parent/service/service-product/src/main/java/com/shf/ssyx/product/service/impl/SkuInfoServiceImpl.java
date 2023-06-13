package com.shf.ssyx.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
}
