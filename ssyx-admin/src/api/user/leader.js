import request from '@/utils/request'

const api_name = '/admin/user/leader'

export default {

  getPageCheckList(page, limit) {
    return request({
      url: `${api_name}/checkList/${page}/${limit}`,
      method: 'get'
    })
  },

  getPageList(page, limit) {
    return request({
      url: `${api_name}/list/${page}/${limit}`,
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

  check(id, status) {
    return request({
      url: `${api_name}/check/${id}/${status}`,
      method: 'post'
    })
  },
}
