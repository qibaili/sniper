import request from '@/utils/request'
import qs from 'qs'

export function getList(data) {
  return request({
    url: '/system/role',
    method: 'get',
    params: data
  })
}

export function getListByUserId(userId) {
  return request({
    url: `/system/role/${userId}`,
    method: 'get'
  })
}

export function createRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function updateRole(id, data) {
  return request({
    url: `/system/role/${id}`,
    method: 'put',
    data: qs.stringify(data)
  })
}

export function assignPermissions(id, resourceIds) {
  return request({
    url: `/system/role/resource/${id}`,
    method: 'put',
    data: qs.stringify({ resourceIds: resourceIds }, { indices: false })
  })
}
