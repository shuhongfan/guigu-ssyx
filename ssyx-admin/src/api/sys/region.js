import request from '@/utils/request'

const api_name = '/admin/sys/region'

export default {

  findRegionByKeyword(keyword) {
    return request({
      url: `${api_name}/findRegionByKeyword/${keyword}`,
      method: 'get'
    })
  },

  findByParentId(parentId) {
    return request({
      url: `${api_name}/findByParentId/${parentId}`,
      method: 'get'
    })
  }
}
