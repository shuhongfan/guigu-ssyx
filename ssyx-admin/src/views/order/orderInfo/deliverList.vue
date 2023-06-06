<template>
  <div class="app-container">

    <el-card class="operate-container" shadow="never">
      <el-form inline>

        <el-form-item label="下单时间">
          <el-date-picker
            v-model="searchObj.date"
            type="date"
            placeholder="选择开始日期"
            value-format="yyyy-MM-dd" />
        </el-form-item>

        <el-form-item label="仓库">
          <el-select v-model="searchObj.wareId" placeholder="请选择" @change="fetchLeaderList">
            <el-option
              v-for="ware in wareList"
              :key="ware.id"
              :label="ware.name"
              :value="ware.id"/>
          </el-select>
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
        <el-button type="default" @click="resetSearch">清空</el-button>
      </el-form>
    </el-card>

    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" size="mini" style="margin: 0 10px;">打印</el-button>
    </el-card>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据正在加载......"
      border
      fit
      highlight-current-row>

      <el-table-column
        type="index"
        label="序号"
        width="50"
        align="center">
      </el-table-column>

      <el-table-column prop="leaderName" label="团长" width="90"/>
      <el-table-column prop="leaderPhone" label="团长电话" width="100"/>
      <el-table-column prop="takeName" label="提货点" width="130"/>
      <el-table-column prop="skuNum" label="商品数量" width="80"/>
      <el-table-column prop="driverName" label="司机" width="90"/>
      <el-table-column prop="driverPhone" label="司机电话" width="100"/>
      <el-table-column prop="deliverDate" label="配送日期" width="100"/>
      <el-table-column prop="createTime" label="配送时间"/>
      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
          <p>{{ scope.row.deliverStatus == 0 ? '未发货' : scope.row.deliverStatus == 1 ? '已发货' : '已收货'}}</p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="fetchReceiveList(scope.row.leaderId)">发货明细</el-button>
          <el-button size="mini" type="text" v-if="scope.row.deliverStatus == 0" @click="send(scope.row.leaderId)">发货</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="发货清单" :visible.sync="receiveDialogVisible" width="40%">
      <el-table
        v-loading="listLoading"
        :data="receiveList"
        element-loading-text="数据正在加载......"
        border
        fit
        highlight-current-row>

        <el-table-column
          type="index"
          label="序号"
          width="50"
          align="center">
        </el-table-column>

        <el-table-column prop="skuName" label="商品"/>
        <el-table-column prop="skuNum" label="商品数量" width="130" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="receiveDialogVisible = false" size="small">取 消</el-button>
      </span>
    </el-dialog>

    <el-dialog title="发货" :visible.sync="sendDialogVisible" width="40%">
      <el-form ref="flashPromotionForm" label-width="150px" size="small">
        <el-form-item label="司机：">
          <el-select v-model="driver" placeholder="请选择">
            <el-option
              v-for="driver in driverList"
              :key="driver.id"
              :label="driver.name"
              :value="driver"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <template slot-scope="scope">
            <el-button @click="sendDialogVisible = false" size="small">取 消</el-button>
            <el-button type="primary" @click="sendSave()" size="small">确 定</el-button>
          </template>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import api from '@/api/order/orderInfo'
import driverApi from '@/api/user/driver'
import wareApi from '@/api/sys/ware'
export default {
  data() {
    return {
      listLoading: false, // 数据是否正在加载
      list: null, // banner列表
      searchObj: {}, // 查询表单对象

      wareList: [],
      leaderList: [],

      receiveDialogVisible: false,
      receiveList: [],

      sendDialogVisible: false,
      leaderId: null,
      driverList: [],
      driver: null,
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    console.log('list created......')
    this.fetchWareList();
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    console.log('list mounted......')
  },

  methods: {

    // 加载banner列表数据
    fetchData() {
      api.findReceiveList(this.searchObj.wareId, this.searchObj.date).then(
        response => {
          this.list = response.data

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    fetchWareList() {
      wareApi.findAllList().then(response => {
        this.wareList = response.data
      })
    },

    fetchReceiveList(leaderid) {
      this.receiveDialogVisible = true
      api.findLeaderReceiveList(leaderid, this.searchObj.date).then(
        response => {
          this.receiveList = response.data

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    send(leaderId) {
      this.sendDialogVisible = true
      this.leaderId = leaderId;

      driverApi.findDriver(this.searchObj.wareId).then(response => {
        this.driverList = response.data
      })
    },

    sendSave() {
      var orderDeliverVo = {
        deliverDate: this.searchObj.date,
        leaderId: this.leaderId,
        driverId: this.driver.id,
        driverName: this.driver.name,
        driverPhone: this.driver.phone
      }
      debugger
      api.deliver(orderDeliverVo).then(response => {
        this.$message({
          type: 'success',
          message: '发货成功!'
        })

        this.fetchData()
      })
    },

    // 重置查询表单
    resetData() {
      console.log('重置查询表单')
      this.searchObj = {}
      this.fetchData()
    }
  }
}
</script>
