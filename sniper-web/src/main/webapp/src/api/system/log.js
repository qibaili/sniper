import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/system/log',
    method: 'get',
    params: data
  })
}
