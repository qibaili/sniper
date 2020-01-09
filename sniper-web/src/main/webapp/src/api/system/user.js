import request from '@/utils/request'
import qs from 'qs'

export function getInfo() {
  return request({
    url: '/current',
    method: 'post'
  })
}

export function getList(data) {
  return request({
    url: '/system/user',
    method: 'get',
    params: data
  })
}

export function createUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function updateUser(id, data) {
  return request({
    url: `/system/user/${id}`,
    method: 'put',
    data: qs.stringify(data)
  })
}

export function changeStatus(id) {
  return request({
    url: `/system/user/status/${id}`,
    method: 'put'
  })
}

export function assignRole(userId, roleIds) {
  return request({
    url: `/system/user/role/${userId}`,
    method: 'put',
    data: qs.stringify({ roleIds: roleIds }, { indices: false })
  })
}

export function resetPassword(userId) {
  return request({
    url: `/system/user/resetpassword/${userId}`,
    method: 'put'
  })
}

export function getListByRoleId(roleId) {
  return request({
    url: `/system/user/${roleId}`,
    method: 'get'
  })
}

export function changePassword(userId, data) {
  return request({
    url: `/system/user/changepassword/${userId}`,
    method: 'put',
    data: qs.stringify(data)
  })
}
