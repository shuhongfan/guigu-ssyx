<template>
  <div class="app-container">
    <h4>订单信息</h4>
    <table class="table table-striped table-condenseda table-bordered" width="100%">
      <tbody>
      <tr>
        <th width="15%">订单单号</th>
        <td width="35%"><b style="font-size: 14px">{{ orderInfo.orderNo }}</b> </td>
        <th width="15%">下单时间</th>
        <td width="35%">{{ orderInfo.createTime }}</td>
      </tr>
      <tr>
        <th>支付时间</th>
        <td>{{ orderInfo.paymentTime }}</td>
        <th>支付方式</th>
        <td>微信</td>
      </tr>
      <tr>
        <th>订单状态</th>
        <td >{{ orderInfo.param.orderStatusName }}</td>
        <th></th>
        <td></td>
      </tr>
      </tbody>
    </table>

    <h4>收货人信息</h4>
    <table class="table table-striped table-condenseda table-bordered" width="100%">
      <tbody>
      <tr>
        <th width="15%">姓名</th>
        <td width="35%">{{ orderInfo.receiverName }}</td>
        <th width="15%">电话</th>
        <td width="35%">{{ orderInfo.receiverPhone }}</td>
      </tr>
      <tr>
        <th>团长名称</th>
        <td>{{ orderInfo.leaderName }}</td>
        <th>提货点</th>
        <td>{{ orderInfo.takeName }}</td>
      </tr>
      <tr>
        <th>收货地址</th>
        <td colspan="3">{{ orderInfo.receiverAddress }}</td>
      </tr>
      </tbody>
    </table>

    <h4>购物项信息</h4>
    <el-table
      v-loading="listLoading"
      :data="orderInfo.orderItemList"
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

      <el-table-column label="商品图片" width="250" align="center">
        <template slot-scope="scope">
          <img :src="scope.row.imgUrl" alt="scope.row.title" width="80px">
        </template>
      </el-table-column>

      <el-table-column prop="skuName" label="商品名称"/>
      <el-table-column prop="skuPrice" label="价格" width="150"/>
      <el-table-column prop="skuNum" label="数量" width="100"/>
    </el-table>

    <h4>支付信息</h4>
    <table class="table table-striped table-condenseda table-bordered" width="100%">
      <tbody>
      <tr>
        <th width="15%">商品总金额</th>
        <td width="35%">{{ orderInfo.originalTotalAmount }}</td>
        <th width="15%">返现</th>
        <td width="35%">{{ orderInfo.activityAmount }}</td>
      </tr>
      <tr>
        <th>使用优惠券</th>
        <td>{{ orderInfo.couponAmount }}</td>
        <th>实际支付</th>
        <td>{{ orderInfo.totalAmount }}</td>
      </tr>
      </tbody>
    </table>

    <br/>
    <el-row>
      <el-button @click="back">返回</el-button>
    </el-row>
  </div>
</template>

<script>
import api from '@/api/order/orderInfo'
export default {
  data() {
    return {
      orderInfo: null
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    console.log('list created......')
    this.init()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    console.log('list mounted......')
  },

  methods: {
    init() {
      const id = this.$route.params.id
      api.show(id).then(response => {
        debugger
        this.orderInfo = response.data
      })
    },

    back() {
      window.history.go(-1)
    }
  }
}
</script>
