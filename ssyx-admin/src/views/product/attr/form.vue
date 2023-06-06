<template>
  <div class="app-container">
    <el-form label-width="120px">

      <el-form-item label="属性名称">
        <el-input v-model="attr.name"/>
      </el-form-item>
      <el-form-item label="属性录入方式:">
        <el-radio-group v-model="attr.inputType">
          <el-radio :label="0">手动录入</el-radio>
          <el-radio :label="1">列表中选择</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="属性值可选值:">
        <el-input v-model="attr.selectList" :autosize="true" type="textarea"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import api from '@/api/product/attr'

const defaultForm = {
  id: '',
  name: '',
  inputType: '',
  selectList: '',
  attrGroupId: ''
}

export default {
  data() {
    return {
      attr: defaultForm,
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
        this.attr = { ...defaultForm }
        this.attr.attrGroupId = this.$route.query.attrGroupId
      }
    },

    saveOrUpdate() {
      this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.attr.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      api.save(this.attr).then(response => {
        // debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/attr/list/' + this.attr.attrGroupId })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      api.updateById(this.attr).then(response => {
        debugger
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/attr/list/' + this.attr.attrGroupId })
        }
      })
    },

    back() {
      this.$router.push({ path: '/product/attr/list/' + this.attr.attrGroupId })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        // debugger
        this.attr = response.data
      })
    }
  }
}
</script>
