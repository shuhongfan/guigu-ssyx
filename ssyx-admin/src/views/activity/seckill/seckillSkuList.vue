<template>
  <div class="app-container">

    <!-- 工具条 -->
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button size="mini" class="btn-add" @click="addSku()" :disabled="$hasBP('btn.all')  === false">添加</el-button>
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
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="skuInfo.skuName" label="商品名称" />
      <el-table-column prop="skuInfo.skuCode" label="商品编号" />
      <el-table-column prop="skuInfo.price" label="商品价格" />
      <el-table-column prop="skuInfo.stock" label="剩余数量" />
      <el-table-column prop="seckillPrice" label="秒杀价格" />
      <el-table-column prop="seckillStock" label="秒杀数量" />
      <el-table-column prop="seckillLimit" label="每人限购数量" />
      <el-table-column prop="seckillSort" label="排序" />
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleUpdate(scope.row)" :disabled="$hasBP('btn.all')  === false">修改</el-button>
          <el-button type="text" @click="removeDataById(scope.row.id)" :disabled="$hasBP('btn.all')  === false">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData"
      @size-change="changeSize"
    />

    <el-dialog title="添加商品" :visible.sync="selectDialogVisible" width="40%">
<!--      <el-input style="width: 250px;margin-bottom: 20px"  size="small" placeholder="商品名称搜索">-->
<!--        <el-button slot="append" icon="el-icon-search" @click="handleSelectSearch()"></el-button>-->
<!--      </el-input>-->
      <el-table :data="dialogData.list" @selection-change="handleDialogSelectionChange" border>
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column label="商品名称" align="center">
          <template slot-scope="scope">{{scope.row.skuName}}</template>
        </el-table-column>
        <el-table-column label="货号" width="160" align="center">
          <template slot-scope="scope">{{scope.row.skuCode}}</template>
        </el-table-column>
        <el-table-column label="价格" width="120" align="center">
          <template slot-scope="scope">￥{{scope.row.price}}</template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          background
          @size-change="handleDialogSizeChange"
          @current-change="handleDialogCurrentChange"
          layout="prev, pager, next"
          :current-page.sync="dialogData.page"
          :page-size="dialogData.limit"
          :page-sizes="[5,10,15]"
          :total="dialogData.total">
        </el-pagination>
      </div>
      <div style="clear: both;"></div>
      <div slot="footer">
        <el-button  size="small" @click="selectDialogVisible = false">取 消</el-button>
        <el-button  size="small" type="primary" @click="handleSelectDialogConfirm()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑秒杀商品信息" :visible.sync="editDialogVisible" width="40%">
      <el-form  ref="flashProductRelationForm" label-width="150px" size="small">
        <el-form-item label="商品名称：">
          <span>{{ seckillSku.skuInfo.skuName }}</span>
        </el-form-item>
        <el-form-item label="sku编号：">
          <span>{{ seckillSku.skuInfo.skuCode }}</span>
        </el-form-item>
        <el-form-item label="sku价格：">
          <span>￥{{ seckillSku.skuInfo.price }}</span>
        </el-form-item>
        <el-form-item label="秒杀价格：">
          <el-input v-model="seckillSku.seckillPrice" class="input-width">
            <template slot="prepend">￥</template>
          </el-input>
        </el-form-item>
        <el-form-item label="剩余数量：">
          <span>{{ seckillSku.skuInfo.stock }}</span>
        </el-form-item>
        <el-form-item label="秒杀数量：">
          <el-input v-model="seckillSku.seckillStock" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="限购数量：">
          <el-input v-model="seckillSku.seckillLimit" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="排序：">
          <el-input v-model="seckillSku.seckillSort" class="input-width"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="updateData()" size="small">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import api from '@/api/activity/seckillSku'
import skuApi from '@/api/product/skuInfo'

const defaultForm = {
  id: '',
  seckillId: '',
  seckillTimeId: '',
  skuId: '',
  seckillPrice: '',
  seckillStock: '',
  seckillLimit: '',
  seckillSort: '',
  skuName: '',
  imgUrl: '',
  skuInfo: {
    skuName: '',
    skuCode: '',
    price: '',
    stock: ''
  }
}
export default {
  data() {
    return {
      seckillId: null,
      seckillTimeId: null,

      listLoading: true, // 数据是否正在加载
      list: null, // banner列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询表单对象

      dialogVisible: false,
      selectDialogVisible: false,
      dialogData: {
        list: null,
        total: null,
        page: 1,
        limit: 5,
        multipleSelection: []
      },

      editDialogVisible: false,
      seckillSku: defaultForm
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    this.seckillId = this.$route.query.seckillId
    this.seckillTimeId = this.$route.query.seckillTimeId

    console.log('list created......')
    this.fetchData()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    console.log('list mounted......')
  },

  methods: {
    // 加载banner列表数据
    fetchData(page = 1) {
      console.log('翻页。。。' + page)
      // 异步获取远程数据（ajax）
      this.page = page

      this.searchObj.seckillId = this.seckillId
      this.searchObj.seckillTimeId = this.seckillTimeId
      api.getPageList(this.page, this.limit, this.searchObj).then(
        response => {
          this.list = response.data.records
          this.total = response.data.total

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    addSku() {
      this.selectDialogVisible = true
      this.getDialogList()
    },

    getDialogList() {
      skuApi.getPageList(this.dialogData.page, this.dialogData.limit, []).then(
        response => {
          debugger
          this.dialogData.list = response.data.records
          this.dialogData.total = response.data.total

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    handleDialogSizeChange(val) {
      debugger
      this.dialogData.pageNum = 1
      this.dialogData.pageSize = val
      this.getDialogList()
    },
    handleDialogCurrentChange(val) {
      debugger
      this.dialogData.pageNum = val
      this.getDialogList()
    },
    handleDialogSelectionChange(val) {
      this.dialogData.multipleSelection = val
    },
    handleSelectDialogConfirm() {
      if (this.dialogData.multipleSelection < 1) {
        this.$message({
          message: '请选择一条记录',
          type: 'warning',
          duration: 1000
        })
        return
      }
      let selectSkuInfos = []
      for (let i = 0; i < this.dialogData.multipleSelection.length; i++) {
        selectSkuInfos.push({
          skuId: this.dialogData.multipleSelection[i].id,
          seckillId: this.seckillId,
          seckillTimeId: this.seckillTimeId,
          skuName: this.dialogData.multipleSelection[i].skuName,
          imgUrl: this.dialogData.multipleSelection[i].imgUrl
        })
      }
      this.$confirm('使用要进行添加操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.save(selectSkuInfos).then(response => {
          if (response.code) {
            this.$message({
              type: 'success',
              message: response.message
            })
            this.selectDialogVisible = false
            this.fetchData(1)
          }
        })
      })
    },

    handleUpdate(row) {
      this.editDialogVisible = true
      this.seckillSku = Object.assign({}, row)
    },

    updateData() {
      api.updateById(this.seckillSku).then(response => {
        debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.editDialogVisible = false
          this.fetchData(1)
        }
      })
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
    }
  }
}
</script>
<style scoped>
  .operate-container{
    margin-top: 0;
  }
  .input-width{
    width: 200px;
  }
</style>
