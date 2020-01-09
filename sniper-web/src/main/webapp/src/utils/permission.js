import store from '@/store'

export function checkPermission(permission) {
  const myPermissions = store.getters.permissions
  return myPermissions.indexOf(permission) > -1
}
