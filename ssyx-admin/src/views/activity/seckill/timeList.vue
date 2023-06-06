<template>
  <div class="app-container">

    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button class="btn-add" size="mini" @click="add()" :disabled="$hasBP('btn.all')  === false">添加</el-button>
      <el-button class="btn-add" size="mini" @click="$router.back()" style="margin: 0 10px;">返回</el-button>
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
          <el-button size="mini" type="text" @click="edit(scope.row.id)" :disabled="$hasBP('btn.all')  === false">编辑 </el-button>
          <el-button size="mini" type="text" @click="removeDataById(scope.row.id)" :disabled="$hasBP('btn.all')  === false">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="添加活动" :visible.sync="dialogVisible" width="40%">
      <el-form ref="flashPromotionForm" label-width="150px" size="small">
        <el-form-item label="秒杀时间段名称：">
          <el-input v-model="seckillTime.name" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="每日开始时间：">
          <el-time-picker
            v-model="seckillTime.startTime"
            placeholder="请选择时间"
            value-format="HH:mm:ss" format="HH:mm:ss">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="每日结束时间：">
          <el-time-picker
            v-model="seckillTime.endTime"
            placeholder="请选择时间"
            value-format="HH:mm:ss" format="HH:mm:ss">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="是否启用">
          <el-radio-group v-model="seckillTime.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">不启用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import api from '@/api/activity/seckillTime'

const defaultForm = {
  id: '',
  name: '',
  startTime: '',
  endTime: '',
  status: 1
}
export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // banner列表

      dialogVisible: false,
      saveBtnDisabled: false,
      seckillTime: defaultForm
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

    add() {
      this.dialogVisible = true
      this.seckill = Object.assign({}, defaultForm)
    },

    edit(id) {
      this.dialogVisible = true
      this.fetchDataById(id)
    },

    saveOrUpdate() {
      this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.seckill.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      api.save(this.seckillTime).then(response => {
        // debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.dialogVisible = false
          this.fetchData(1)
        }
      })
    },

    // 根据id更新记录
    updateData() {
      api.updateById(this.seckillTime).then(response => {
        debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.dialogVisible = false
          this.fetchData(1)
        }
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        this.seckillTime = response.data
      })
    },

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

    // 根据id删除数据
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return api.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.code) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    updateStatus(row) {
      this.$confirm('是否要修改该状态?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.updateStatus(row.id, row.status).then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消修改'
        })
        this.getList()
      })
    }
  }
}
</script>

