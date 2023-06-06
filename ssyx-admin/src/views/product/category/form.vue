<template>
  <div class="app-container">
    <el-form ref="categoryForm" :model="category" :rules="validateRules" label-width="120px">

      <el-form-item label="分类名称" prop="name">
        <el-input v-model="category.name"/>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="category.sort"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import api from '@/api/product/category'

const isNum = (rule, value, callback) => {
  const age= /^[0-9]*$/
  if (!age.test(value)) {
    callback(new Error('只能为数字'))
  }else{
    callback()
  }
}

const defaultForm = {
  id: '',
  name: '',
  status: '',
  sort: ''
}

export default {
  data() {
    return {
      category: defaultForm,
      saveBtnDisabled: false,

      validateRules: {
        name: [{ required: true, trigger: 'blur', message: '必须输入' }],
        sort: [
          { required: true, decimal:2, trigger: 'blur', message: '必须输入' },
          { validator: isNum, trigger: 'blur' }
        ]
      }
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
        this.category = { ...defaultForm }
      }
    },

    saveOrUpdate() {
      this.$refs.categoryForm.validate(valid => {
        if (valid) {
          this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.category.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增
    saveData() {
      api.save(this.category).then(response => {
        // debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/category/list' })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      api.updateById(this.category).then(response => {
        debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/category/list' })
        }
      })
    },

    back() {
      this.$router.push({ path: '/product/category/list' })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        // debugger
        this.category = response.data
      })
    }
  }
}
</script>
