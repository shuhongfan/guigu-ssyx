import request from '@/utils/request'

const api_name = '/admin/activity/couponInfo'

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

  findCouponRuleList(id) {
    return request({
      url: `${api_name}/findCouponRuleList/${id}`,
      method: 'get'
    })
  },
  saveCouponRule(rule) {
    return request({
      url: `${api_name}/saveCouponRule`,
      method: 'post',
      data: rule
    })
  },

  findCouponByKeyword(keyword) {
    return request({
      url: `${api_name}/findCouponByKeyword/${keyword}`,
      method: 'get'
    })
  }
}
