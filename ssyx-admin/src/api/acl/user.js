import request from '@/utils/request'

const api_name = '/admin/acl/user'

/*
登陆
*/
export function login({ username, password }) {
  return request({
    url: '/admin/acl/index/login',
    method: 'post',
    data: { username, password }
  })
}

/*
获取用户信息(根据token)
*/
export function getInfo() {
  return request({
    url: '/admin/acl/index/info',
    method: 'get'
  })
}

/*
登出
*/
export function logout() {
  return request({
    url: '/admin/acl/index/logout',
    method: 'post'
  })
}

/*
获取当前用户的菜单权限列表
*/
export function getMenu() {
  return request('/admin/acl/index/menu')
}


/*
获取后台用户分页列表(带搜索)
*/
export function getPageList(page, limit, searchObj) {
  return request({
    url: `${api_name}/${page}/${limit}`,
    method: 'get',
    params: searchObj
  })
}

/*
根据ID获取某个后台用户
*/
export function getById(id) {
  return request({
    url: `${api_name}/get/${id}`,
    method: 'get'
  })
}

/*
保存一个新的后台用户
*/
export function add(user) {
  return request({
    url: `${api_name}/save`,
    method: 'post',
    data: user
  })
}

/*
更新一个后台用户
*/
export function update(user) {
  return request({
    url: `${api_name}/update`,
    method: 'put',
    data: user
  })
}

/*
获取某个用户的所有角色
*/
export function getRoles(adminId) {
  return request({
    url: `${api_name}/toAssign/${adminId}`,
    method: 'get'
  })
}

/*
给某个用户分配角色
roleId的结构: 字符串, 'rId1,rId2,rId3'
*/
export function assignRoles(adminId, roleId) {
  return request({
    url: `${api_name}/doAssign`,
    method: 'post',
    params: {
      adminId,
      roleId
    }
  })
}

/*
删除某个用户
*/
export function removeById(id) {
  return request({
    url: `${api_name}/remove/${id}`,
    method: 'delete'
  })
}

/*
批量删除多个用户
ids的结构: ids是包含n个id的数组
*/
export function removeUsers(ids) {
  return request({
    url: `${api_name}/batchRemove`,
    method: 'delete',
    data: ids
  })
}
