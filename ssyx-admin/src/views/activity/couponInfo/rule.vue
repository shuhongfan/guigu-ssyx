<template>
  <div class="app-container">

    <h4>优惠券信息</h4>
    <table class="table table-striped table-condenseda table-bordered" width="100%">
      <tbody>
      <tr>
        <th width="15%">优惠券名称</th>
        <td width="35%"><b style="font-size: 14px">{{ couponInfo.couponName }}</b></td>
        <th width="15%">优惠券类型</th>
        <td width="35%">
          {{ couponInfo.couponTypeString }}
        </td>
      </tr>
      <tr>
        <th>发行数量</th>
        <td>{{ couponInfo.publishCount }}</td>
        <th>每人限领次数</th>
        <td>{{ couponInfo.perLimit }}</td>
      </tr>
      <tr>
        <th>领取时间</th>
        <td>{{ couponInfo.startTime }}至{{ couponInfo.endTime }}</td>
        <th>过期时间</th>
        <td>{{ couponInfo.expireTime }}</td>
      </tr>
      </tbody>
    </table>

    <h4>添加规则</h4>
    <el-form label-width="140px" style="background: #f9f9f9;padding-top: 15px; padding-bottom: 1px;">
      <el-form-item v-if="couponInfo.couponType == 'CASH'" label="现金券金额（元）" style="width: 50%;">
        <el-input v-model="couponInfo.amount"/>
      </el-form-item>

      <el-form-item v-if="couponInfo.couponType == 'FULL_REDUCTION'" label="满减金额（元）" style="width: 50%;">
        <el-input v-model="couponInfo.conditionAmount"/>
      </el-form-item>

      <el-form-item v-if="couponInfo.couponType == 'FULL_REDUCTION'" label="优惠金额（元）" style="width: 50%;">
        <el-input v-model="couponInfo.amount"/>
      </el-form-item>
    </el-form>

    <h4>优惠券范围选择</h4>
    <el-form label-width="0px" style="background: #f9f9f9;padding-top: 15px;padding-left: 15px; padding-bottom: 1px;">
      <el-form-item>
        <el-radio-group v-model="couponInfo.rangeType">
          <el-radio label="ALL">通用</el-radio>
          <el-radio label="SKU">SKU</el-radio>
          <el-radio label="CATEGORY">分类</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <div v-if="couponInfo.rangeType == 'SKU'">
      <el-dialog title="添加范围" :visible.sync="dialogSkduRangVisible" width="490px">
        <div style="margin-top: 20px;">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
              <el-autocomplete
                v-model="keyword"
                :fetch-suggestions="querySkuSearch"
                :trigger-on-focus="false"
                class="inline-input"
                placeholder="请输入关键字，选择活动商品"
                value-key="skuName"
                style="width: 400px;"
                @select="selectSkuData"
              />
            </el-form-item>

            <el-form-item>
              <el-button type="" @click="dialogSkduRangVisible = false">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <h4>
        优惠券范围列表&nbsp;&nbsp;&nbsp;
        <el-button type="" size="mini" @click="dialogSkduRangVisible = true">添加优惠券范围</el-button>
      </h4>
      <el-table
        v-loading="listLoading"
        :data="skuInfoList"
        border
        fit
        highlight-current-row>

        <el-table-column
          label="序号"
          width="70"
          align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>

        <el-table-column prop="id" label="sku ID" width="100"/>
        <el-table-column prop="skuName" label="sku名称" width="120"/>
        <el-table-column prop="skuCode" label="sku编号" />

        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="removeSkuDataById(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div v-if="couponInfo.rangeType == 'CATEGORY'">
      <el-dialog title="添加范围" :visible.sync="dialogCateRangVisible" width="490px">
        <div style="margin-top: 20px;">
          <el-form :inline="true" class="demo-form-inline">
            <el-select placeholder="请选择" style="width: 300px" @change="selectCategory">
              <el-option
                v-for="category in selectCategoryList"
                :key="category.id"
                :label="category.name"
                :value="category.id"/>
            </el-select>

            <el-form-item>
              <el-button type="" @click="dialogCateRangVisible = false">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <h4>
        优惠券范围列表&nbsp;&nbsp;&nbsp;
        <el-button type="" size="mini" @click="dialogCateRangVisible = true">添加优惠券范围</el-button>
      </h4>
      <el-table
        v-loading="listLoading"
        :data="categoryList"
        border
        fit
        highlight-current-row>

        <el-table-column
          label="序号"
          width="70"
          align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>

        <el-table-column prop="id" label="分类ID" width="100"/>
        <el-table-column prop="name" label="名称" />

        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="removeCateDataById(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div v-if="couponInfo.rangeType == 'ALL'">
      <span>通用</span>
    </div>

    <div style="margin-top: 15px;">
      <el-form label-width="0px">
        <el-form-item>
          <el-button type="primary" @click="save">保存</el-button>
          <el-button @click="back">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

import api from '@/api/activity/couponInfo'
import activityInfoApi from '@/api/activity/activityInfo'
import categoryApi from '@/api/product/category'

export default {
  data() {
    return {
      listLoading: false, // 数据是否正在加载

      couponInfo: {},
      saveBtnDisabled: false,

      // 优惠券范围：SPU
      dialogSkduRangVisible: false,
      keyword: '',
      skuInfoList: [],

      // 优惠券范围：三级分类
      dialogCateRangVisible: false,
      categoryList: [],
      selectCategoryList: []
    }
  },

  // 监听器
  watch: {
    $route(to, from) {
      console.log('路由变化......')
      console.log(to)
      console.log(from)
      this.init()
    }
  },

  // 生命周期方法（在路由切换，组件不变的情况下不会被调用）
  created() {
    console.log('form created ......')
    this.init()
  },

  methods: {

    // 表单初始化
    init() {
      const couponId = this.$route.params.id
      // 获取优惠券信息
      this.fetchDataById(couponId)

      // 获取优惠券规则与范围信息
      this.fetchRuleDataById(couponId)

      this.fetchCategoryList()
    },

    fetchCategoryList() {
      categoryApi.findAllList().then(response => {
        this.selectCategoryList = response.data
      })
    },

    // 提交保存数据
    save() {
      let rangeDesc = '可购买'
      const couponRangeList = []
      if (this.couponInfo.rangeType === 'SKU') {
        this.skuInfoList.forEach(function(item) {
          rangeDesc += '：'
          couponRangeList.push({
            rangeType: 'SKU',
            rangeId: item.id
          })
          rangeDesc += item.spuName + ','
        })
      }
      if (this.couponInfo.rangeType === 'CATEGORY') {
        rangeDesc += '分类：'
        this.categoryList.forEach(function(item) {
          couponRangeList.push({
            rangeType: 'CATEGORY',
            rangeId: item.id
          })
          rangeDesc += item.name + ','
        })
      }
      if (this.couponInfo.rangeType === 'ALL') {
        rangeDesc +=  '全场通用,'
      }
      if (rangeDesc.length > 3) {
        rangeDesc = rangeDesc.substring(0, rangeDesc.length - 1)
      } else {
        rangeDesc = this.couponInfo.couponName
      }
      const ruleData = {
        couponId: this.couponInfo.id,
        rangeType: this.couponInfo.rangeType,
        amount: this.couponInfo.amount,
        conditionAmount: this.couponInfo.conditionAmount,
        couponRangeList: couponRangeList,
        rangeDesc: rangeDesc
      }

      api.saveCouponRule(ruleData).then(response => {
        this.$message.success(response.message)
        this.$router.push({ path: '/activity/couponInfo/list' })
      })
    },

    removeDataById(index) {
      this.activityRuleList.splice(index, 1)
    },

    back() {
      // this.$router.push({ path: '/activity/couponInfo/list' })
      window.history.go(-1)
    },

    fetchRuleDataById(id) {
      api.findCouponRuleList(id).then(response => {

        this.skuInfoList = response.data.skuInfoList || []
        this.categoryList = response.data.categoryList || []
        this.trademarkList = response.data.trademarkList || []
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        //
        this.couponInfo = response.data
      })
    },

    querySkuSearch(queryString, cb) {
      console.log(queryString)
      console.log(cb)

      activityInfoApi.findSkuInfoByKeyword(queryString).then(response => {
        // 调用 callback 返回建议列表的数据
        cb(response.data)
      })
    },

    selectSkuData(item) {

      this.skuInfoList.push(item)
      this.dialogSkduRangVisible = false
    },

    removeSkuDataById(index) {
      this.skuInfoList.splice(index, 1)
    },

    selectCategory(categoryId) {
      console.log(categoryId)
      categoryApi.getById(categoryId)
          .then(response => {
              this.categoryList.push({
                id: categoryId,
                name: response.data.name
              })
          })
      this.dialogCateRangVisible = false
    },

    removeCateDataById(index) {
      this.categoryList.splice(index, 1)
    }
  }
}
</script>
<style>
  .app-container h4 {
    color: #606266;
  }
</style>
