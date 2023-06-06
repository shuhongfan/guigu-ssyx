<template>
  <div class="app-container">
    <el-form label-width="120px" size="small">

      <div style="background-color:#E0E0E0;width: 100%;padding: 0 10px;margin: 10px 0;"><h3>基本信息</h3></div>
      <el-form-item label="sku名称">
        <el-input v-model="skuInfo.skuName" />
      </el-form-item>
      <el-form-item label="商品分类" prop="tmId">
        <el-select v-model="skuInfo.categoryId" placeholder="请选择" >
          <el-option
            v-for="category in categoryList"
            :key="category.id"
            :label="category.name"
            :value="category.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="SKU编号：">
        <el-input v-model="skuInfo.skuCode" ></el-input>
      </el-form-item>
      <el-form-item label="商品售价：">
        <el-input v-model="skuInfo.price" ></el-input>
      </el-form-item>
      <el-form-item label="市场价：">
        <el-input v-model="skuInfo.marketPrice" ></el-input>
      </el-form-item>
      <el-form-item label="是否新人专享:">
        <el-radio-group v-model="skuInfo.isNewPerson">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="skuInfo.sort" ></el-input>
      </el-form-item>

      <div style="background-color:#E0E0E0;width: 100%;padding: 0 10px;margin: 10px 0;"><h3>平台属性</h3></div>
      <el-form-item label="属性分组" prop="tmId">
        <el-select v-model="skuInfo.attrGroupId" placeholder="请选择"  @change="selectAttrChange">
          <el-option
            v-for="attrGroup in attrGroupList"
            :key="attrGroup.id"
            :label="attrGroup.name"
            :value="attrGroup.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="平台属性：">
        <el-card shadow="never" class="cardBg">
          <div :key="item.id" v-for="(item,index) in attrList" :class="{littleMarginTop:index!==0}">
            <div class="paramInputLabel">{{item.name}}:</div>
            <el-select v-if="item.inputType===1" class="paramInput" v-model="attrList[index].value" style="width: 300px">
              <el-option
                v-for="item in getParamSelectList(item.selectList)"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
            <el-input v-else class="paramInput" v-model="attrList[index].value" style="width: 300px"></el-input>
          </div>
        </el-card>
      </el-form-item>

      <div style="background-color:#E0E0E0;width: 100%;padding: 0 10px;margin: 10px 0;"><h3>商品图片</h3></div>
      <el-form-item label="图片上传" >
        <el-upload
          :on-success="onUploadSuccess"
          :before-upload="beforeUpload"
          :on-preview="onUploadPreview"
          :on-remove="onUploadRemove"
          :multiple="true"
          :action="BASE_API+'/admin/product/fileUpload'"
          class="upload-demo"
          list-type="picture-card"
          :file-list="fileList">
          <i class="el-icon-plus"/>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png/gif文件，且不超过2MB</div>
        </el-upload>
      </el-form-item>

      <div style="background-color:#E0E0E0;width: 100%;padding: 0 10px;margin: 10px 0;"><h3>商品库存</h3></div>
      <el-form-item label="商品库存：">
        <el-input v-model="skuInfo.stock" ></el-input>
      </el-form-item>
      <el-form-item label="商品预警库存：">
        <el-input v-model="skuInfo.lowStock" ></el-input>
      </el-form-item>
      <el-form-item label="限购个数：">
        <el-input v-model="skuInfo.perLimit" ></el-input>
      </el-form-item>

<!--      <div style="background-color:#E0E0E0;width: 100%;padding: 0 10px;margin: 10px 0;"><h3>商品详情</h3></div>-->
<!--      <el-form-item label="商品详情">-->
<!--        <el-input v-model="skuInfo.skuDetail.detailHtml" type="textarea" :rows="10"></el-input>-->
<!--      </el-form-item>-->
      <div style="background-color:#E0E0E0;width: 100%;padding: 0 10px;margin: 10px 0;"><h3>商品海报</h3></div>
      <el-form-item label="上传海报" >
        <el-upload
          :on-success="onUploadPosterSuccess"
          :before-upload="beforeUpload"
          :on-preview="onUploadPreview"
          :on-remove="onUploadPosterRemove"
          :multiple="true"
          :action="BASE_API+'/admin/product/fileUpload'"
          class="upload-demo"
          list-type="picture-card"
          :file-list="filePosterList">
          <i class="el-icon-plus"/>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png/gif文件，且不超过2MB</div>
        </el-upload>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        <el-button @click="back">返回</el-button>
      </el-form-item>
    </el-form>

    <!--图片预览-->
    <el-dialog :visible.sync="dialogImageVisible">
      <img :src="dialogImageUrl" width="100%" alt="">
    </el-dialog>
  </div>
</template>

<script>

import api from '@/api/product/skuInfo'
import categoryApi from '@/api/product/category'
import attrGroupApi from '@/api/product/attrGroup'
import attrApi from '@/api/product/attr'

const defaultForm = {
  id: '',
  categoryId: '',
  attrGroupId: '',
  skuType: 0,
  skuName: '',
  perLimit: 1,
  isNewPerson: 0,
  sort: '',
  imgUrl: '',
  skuCode: '',
  price: '',
  marketPrice: '',
  stock: '',
  lowStock: '',
  skuAttrValueList: [],
  skuPosterList: [],
  skuImagesList: []
}

export default {
  data() {
    return {
      // 接口API地址
      BASE_API: 'http://localhost:8203',

      skuInfo: defaultForm,
      saveBtnDisabled: false,

      categoryList: [],
      attrGroupList: [],
      attrList: [],
      fileList: [],
      filePosterList: [],

      // 图片预览区域url
      dialogImageUrl: '',
      // 图片预览对话框
      dialogImageVisible: false
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
        this.skuInfo = { ...defaultForm }
      }

      this.fetchCategoryList()
      this.fetchAttrGroupList()
    },

    fetchCategoryList() {
      categoryApi.findAllList().then(response => {
        this.categoryList = response.data
      })
    },

    fetchAttrGroupList() {
      attrGroupApi.findAllList().then(response => {
        this.attrGroupList = response.data
      })
    },

    fetchAttrList(attrGroupId) {
      attrApi.getList(attrGroupId).then(response => {
        this.attrList = response.data

        // 修改属性回显
        debugger
        if (this.skuInfo.skuAttrValueList.length > 0) {
          this.attrList.forEach(attrItem => {
            this.skuInfo.skuAttrValueList.forEach(valueItem => {
              if (attrItem.id === valueItem.attrId) {
                attrItem.value = valueItem.attrValue
              }
            })
          })
        }
      })
    },

    getParamSelectList(selectList) {
      return selectList.split(',')
    },

    selectAttrChange(attrGroupId) {
      this.fetchAttrList(attrGroupId)
    },

    saveOrUpdate() {
      // this.saveBtnDisabled = true // 防止表单重复提交
      if (!this.skuInfo.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      const attrListArray = []
      this.attrList.forEach(item => {
        attrListArray.push({
          attrId: item.id,
          attrName : item.name,
          attrValue : item.value
        })
      })
      this.skuInfo.skuAttrValueList = attrListArray
      if (this.skuInfo.skuImagesList.length > 0) {
        this.skuInfo.imgUrl = this.skuInfo.skuImagesList[0].imgUrl
      }
      api.save(this.skuInfo).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/skuInfo/list' })
          this.saveBtnDisabled = false
        }
      })
    },

    // 根据id更新记录
    updateData() {
      debugger
      const attrListArray = []
      this.attrList.forEach(item => {
        attrListArray.push({
          attrId: item.id,
          attrName : item.name,
          attrValue : item.value
        })
      })
      this.skuInfo.skuAttrValueList = attrListArray
      if (this.skuInfo.skuImagesList.length > 0) {
        this.skuInfo.imgUrl = this.skuInfo.skuImagesList[0].imgUrl
      }
      api.updateById(this.skuInfo).then(response => {
        if (response.code) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/product/skuInfo/list' })
        }
      })
    },

    back() {
      this.$router.push({ path: '/product/skuInfo/list' })
    },

    // 根据id查询记录
    fetchDataById(id) {
      api.getById(id).then(response => {
        debugger
        this.skuInfo = response.data

        // 平台属性回显
        this.fetchAttrList(this.skuInfo.attrGroupId)

        // 图片回显
        this.skuInfo.skuImagesList.forEach(item => {
          let obj = new Object()
          obj.url = item.imgUrl
          this.fileList.push(obj)
        })

        this.skuInfo.skuPosterList.forEach(item => {
          let obj = new Object()
          obj.url = item.imgUrl
          this.filePosterList.push(obj)
        })
      })
    },

    // 文件上传限制条件
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isGIF = file.type === 'image/gif'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error('上传头像图片只能是 JPG、PNG 或 GIF 格式!')
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
        return false
      }
      return true
    },

    // 上传图片成功的回调
    onUploadSuccess(res, file) {
      // 填充上传文件列表
      this.skuInfo.skuImagesList.push({
        imgName: file.name,
        imgUrl: file.response.data
      })
    },

    // 上传的文件预览
    onUploadPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogImageVisible = true
    },

    // 删除上传的文件
    onUploadRemove(file, fileList) {
      var list = []
      this.skuInfo.skuImagesList.forEach(item => {
        if(file.url != item.imgUrl) {
          list.push(item)
        }
      })
      this.skuInfo.skuImagesList = list
    },

    //-----------------
    // 上传图片成功的回调
    onUploadPosterSuccess(res, file) {
      debugger
      // 填充上传文件列表
      this.skuInfo.skuPosterList.push({
        imgName: file.name,
        imgUrl: file.response.data
      })
    },

    // 删除上传的文件
    onUploadPosterRemove(file, fileList) {
      var list = []
      this.skuInfo.skuPosterList.forEach(item => {
        if(file.url != item.imgUrl) {
          list.push(item)
        }
      })
      this.skuInfo.skuPosterList = list
    }

  }
}
</script>
<style scoped>

  .littleMarginTop {
    margin-top: 10px;
  }

  .paramInput {
    width: 250px;
  }

  .paramInputLabel {
    display: inline-block;
    width: 100px;
    text-align: right;
    padding-right: 10px
  }

  .cardBg {
    background: #F8F9FC;
  }
</style>
