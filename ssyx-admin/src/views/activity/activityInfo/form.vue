<template>
  <div class="app-container">
    <el-form label-width="120px">

      <el-form-item label="活动名称">
        <el-input v-model="activityInfo.activityName"/>
      </el-form-item>
      <el-form-item label="活动类型">
        <el-radio-group v-model="activityInfo.activityType">
          <el-radio label="FULL_REDUCTION">满减</el-radio>
          <el-radio label="FULL_DISCOUNT">满量打折</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="活动时间">
        <el-date-picker
          v-model="activityInfo.startTime"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd" />
        至
        <el-date-picker
          v-model="activityInfo.endTime"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item label="活动描述">
        <el-input v-model="activityInfo.activityDesc" :rows="3" type="textarea"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import api from '@/api/activity/activityInfo'

const defaultForm = {
  id: '',
  activityName: '',
  activityType: 'FULL_REDUCTION',
  activityDesc: '',
  startTime: '',
  endTime: ''
}

export default {
  data() {
    return {
      activityInfo: defaultForm,
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
        this.activityInfo = { ...defaultForm }
      }
    },

    saveOrUpdate() {
      this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.activityInfo.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      api.save(this.activityInfo).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/activity/activityInfo/list' })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      api.updateById(this.activityInfo).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/activity/activityInfo/list' })
        }
      })
    },

    back() {
      this.$router.push({ path: '/activity/activityInfo/list' })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        this.activityInfo = response.data
      })
    }
  }
}
</script>
