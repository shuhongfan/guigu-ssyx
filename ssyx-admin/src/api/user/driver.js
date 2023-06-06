import request from '@/utils/request'

const api_name = '/admin/user/driver'

export default {
  findDriver(wareId) {
    return request({
      url: `${api_name}/findDriver/${wareId}`,
      method: 'get'
    })
  }
}
