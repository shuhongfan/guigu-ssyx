import request from '@/utils/request'

const api_name = '/admin/order/orderInfo'

export default {

  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj // url查询字符串或表单键值对
    })
  },

  show(orderId) {
    return request({
      url: `${api_name}/get/${orderId}`,
      method: 'get'
    })
  },

  deliver(orderDeliverVo) {
    return request({
      url: `${api_name}/deliver`,
      method: 'post',
      data: orderDeliverVo
    })
  },

  findReceiveList(wareId, date) {
    return request({
      url: `${api_name}/findReceiveList/${wareId}/${date}`,
      method: 'get'
    })
  },

  findLeaderReceiveList(leaderId, date) {
    return request({
      url: `${api_name}/findLeaderReceiveList/${leaderId}/${date}`,
      method: 'get'
    })
  }
}
