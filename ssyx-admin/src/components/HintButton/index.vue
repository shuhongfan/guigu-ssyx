<template>
  <el-tooltip :content="title" placement="top-start">
    <el-button v-bind="$attrs" v-on="$listeners"/>
  </el-tooltip>
</template>

<script type="text/ecmascript-6">

  export default {
    name: 'HintButton',
    props: {
      title: String
    },

    mounted () {
      // console.log('mounted()', this.$attrs)
      // console.log('mounted()', this.$listeners)
    }
  }
</script>