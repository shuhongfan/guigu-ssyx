package com.shf.ssyx.search.service.impl;

import com.shf.ssyx.activity.client.ActivityFeignClient;
import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.common.auth.AuthContextHolder;
import com.shf.ssyx.enums.SkuType;
import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.model.search.SkuEs;
import com.shf.ssyx.search.repository.SkuRepository;
import com.shf.ssyx.search.service.SkuService;
import com.shf.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 上架
     * @param skuId
     */
    @Override
    public void upperSku(Long skuId) {
//        1. 通过远程调用 根据SKUID获取相关信息

//        查询sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }

//        查询分类
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());

//        2. 获取数据封装SkuES对象
        SkuEs skuEs = new SkuEs();

//        封装分类
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }

//        封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) { // 普通商品
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品

        }

//        3.调用方法添加ES
        skuRepository.save(skuEs);
    }

    /**
     * 下架
     * @param skuId
     */
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    /**
     * 获取爆款商品
     * @return
     */
    @Override
    public List<SkuEs> findHotSkuList() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<SkuEs> pageModel = skuRepository.findByOrderByHotScoreDesc(pageable);
        List<SkuEs> skuEsList = pageModel.getContent();
        return skuEsList;
    }

    /**
     * 查询分类商品
     * @param pageabel
     * @param skuEsQueryVo
     * @return
     */
    @Override
    public Page<SkuEs> search(PageRequest pageabel, SkuEsQueryVo skuEsQueryVo) {
//        1. 向SkuEsQueryVo 设置 wareId  设置当前登录用户的仓库Id
        Long wareId = AuthContextHolder.getWareId();
        skuEsQueryVo.setWareId(wareId);

        Page<SkuEs> pageModel = null;
//        2. 调用SkuRepository方法，根据SpringData命名规则定义方法，进行条件查询
        String keyword = skuEsQueryVo.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
//        判断keyWord是否为空，如果为空，根据仓库id+分类id查询
            pageModel = skuRepository.finByCategoryIdAndWareId(
                    skuEsQueryVo.getCategoryId(),
                    skuEsQueryVo.getWareId(),
                    pageabel
            );
        } else {
//        如果keyword不为空，根据仓库id+keyword查询
            pageModel = skuRepository.findByKeywordAndWareId(
                    skuEsQueryVo.getKeyword(),
                    skuEsQueryVo.getWareId()
            );
        }

//        3.查询商品参加优惠活动
        List<SkuEs> skuEsList = pageModel.getContent();
        if (!CollectionUtils.isEmpty(skuEsList)) {
//            遍历skuEsList，得到所有SKUID
            List<Long> skuIds = skuEsList.stream().map(SkuEs::getId).collect(Collectors.toList());

//            根据skuId列表远程调用，调用service-activity里面的接口得到数据
            Map<Long, List<String>> skuIdToRuleListMap = activityFeignClient.findActivity(skuIds);

//            封装获取数据到skuEs里面
            if (skuIdToRuleListMap != null) {
                skuEsList.forEach(skuEs -> skuEs.setRuleList(skuIdToRuleListMap.get(skuEs.getId())));
            }

        }

        return pageModel;
    }

    /**
     * 更新商品热度
     * @param skuId
     */
    @Override
    public void incrHotScore(Long skuId) {
        String key = "hotScore";

//        redis保存数据，每次+1
        Double hotScore = redisTemplate.opsForZSet().incrementScore(key, "skuId:" + skuId, 1);

//        规则
        if (hotScore % 10 == 0) {
//            更新es
            Optional<SkuEs> optional = skuRepository.findById(skuId);
            SkuEs skuEs = optional.get();
            skuEs.setHotScore(Math.round(hotScore));
            skuRepository.save(skuEs);
        }
    }
}
