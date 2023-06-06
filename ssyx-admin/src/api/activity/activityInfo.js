import request from '@/utils/request'

const api_name = '/admin/activity/activityInfo'

export default {

  getPageList(page, limit) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get'
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },

  save(role) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: role
    })
  },

  updateById(role) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: role
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  removeRows(idList) {
    return request({
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: idList
    })
  },

  findActivityRuleList(id) {
    return request({
      url: `${api_name}/findActivityRuleList/${id}`,
      method: 'get'
    })
  },
  saveActivityRule(rule) {
    return request({
      url: `${api_name}/saveActivityRule`,
      method: 'post',
      data: rule
    })
  },

  findSkuInfoByKeyword(keyword) {
    return request({
      url: `${api_name}/findSkuInfoByKeyword/${keyword}`,
      method: 'get'
    })
  }
}
