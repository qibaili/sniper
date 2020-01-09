import request from '@/utils/request'
import qs from 'qs'

export function getList() {
  return request({
    url: '/system/resource',
    method: 'get'
  })
}

export function getResourceByRoleId(roleId) {
  return request({
    url: `/system/resource/${roleId}`,
    method: 'get'
  })
}

export function createResource(data) {
  return request({
    url: '/system/resource',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function updateResource(id, data) {
  return request({
    url: `/system/resource/${id}`,
    method: 'put',
    data: qs.stringify(data)
  })
}

export function deleteResource(id) {
  return request({
    url: `/system/resource/${id}`,
    method: 'delete'
  })
}
