<template>
  <div class="app-container">
    <el-form label-width="120px">

      <el-form-item label="组名">
        <el-input v-model="attrGroup.name"/>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="attrGroup.sort"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="attrGroup.remark"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import api from '@/api/product/attrGroup'

const defaultForm = {
  id: '',
  name: '',
  sort: '',
  remark: ''
}

export default {
  data() {
    return {
      attrGroup: defaultForm,
      saveBtnDisabled: false
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
      // debugger
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.fetchDataById(id)
      } else {
        // 对象拓展运算符：拷贝对象，而不是赋值对象的引用
        this.attrGroup = { ...defaultForm }
      }
    },

    saveOrUpdate() {
      this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.attrGroup.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      api.save(this.attrGroup).then(response => {
        // debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/attrGroup/list' })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      api.updateById(this.attrGroup).then(response => {
        debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/attrGroup/list' })
        }
      })
    },

    back() {
      this.$router.push({ path: '/product/attrGroup/list' })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        // debugger
        this.attrGroup = response.data
      })
    }
  }
}
</script>
