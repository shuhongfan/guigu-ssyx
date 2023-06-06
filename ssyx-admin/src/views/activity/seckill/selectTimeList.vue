<template>
  <div class="app-container">

    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" size="mini" @click="$router.back()">返回</el-button>
    </el-card>

    <!-- banner列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;">

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="场次名称" />
      <el-table-column prop="startTime" label="每日开始时间" />
      <el-table-column prop="endTime" label="每日结束时间" />
      <el-table-column label="启用状态" width="200" align="center">
        <template slot-scope="scope">
          <el-switch
            @change="updateStatus(scope.row)"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.status">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="showRelation(scope.row)">商品列表 </el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import api from '@/api/activity/seckillTime'

export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null // banner列表
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    console.log('list created......')
    this.fetchData()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    console.log('list mounted......')
  },

  methods: {
    // 加载banner列表数据
    fetchData() {
      api.getList().then(
        response => {
          debugger
          this.list = response.data

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    showRelation(row) {
      this.$router.push({ path: '/activity/seckill/seckillSkuList', query: {
          seckillId: this.$route.query.seckillId,
          seckillTimeId: row.id
      }})
    }
  }
}
</script>

