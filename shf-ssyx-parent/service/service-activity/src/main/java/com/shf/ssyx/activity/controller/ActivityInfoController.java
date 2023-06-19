package com.shf.ssyx.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.activity.mapper.ActivityRuleMapper;
import com.shf.ssyx.activity.mapper.ActivitySkuMapper;
import com.shf.ssyx.activity.service.ActivityInfoService;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.activity.ActivityInfo;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.activity.ActivityRuleVo;
import com.shf.ssyx.vo.order.CartInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
@Api(tags = "活动接口")
@RestController
@RequestMapping("/admin/activity/activityInfo")
public class ActivityInfoController {
    @Autowired
    private ActivityInfoService activityInfoService;

    @ApiOperation("活动列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit) {
        Page<ActivityInfo> pageParam = new Page<>(page, limit);
        IPage<ActivityInfo> pageModel = activityInfoService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取活动")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.ok(activityInfo);
    }


    @ApiOperation(value = "新增活动")
    @PostMapping("save")
    public Result save(@RequestBody ActivityInfo activityInfo) {
        activityInfo.setCreateTime(new Date());
        activityInfoService.save(activityInfo);
        return Result.ok();
    }

    @ApiOperation(value = "修改活动")
    @PutMapping("update")
    public Result updateById(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.updateById(activityInfo);
        return Result.ok();
    }

    @ApiOperation(value = "删除活动")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        activityInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除活动")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        activityInfoService.removeByIds(idList);
        return Result.ok();
    }

    //    营销活动相关接口
    @ApiOperation("根据活动id获取活动规则数据")
    @GetMapping("findActivityRuleList/{id}")
    public Result findActivityRuleList(@PathVariable Long id) {
        Map<String, Object> activityRuleMap = activityInfoService.findActivityRuleList(id);
        return Result.ok(activityRuleMap);
    }

    @ApiOperation("在活动里面添加规则数据")
    @PostMapping("saveActivityRule")
    public Result saveActivityRule(@RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveActivityRule(activityRuleVo);
        return Result.ok();
    }

    @ApiOperation("根据关键词查询匹配SKU信息")
    @GetMapping("findSkuInfoByKeyword/{keyword}")
    public Result findSkuInfoByKeyword(@PathVariable String keyword) {
        List<SkuInfo> list = activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.ok(list);
    }
}

