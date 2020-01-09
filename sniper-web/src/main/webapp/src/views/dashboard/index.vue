<template>
  <div class="app-container">
    <aside>欢迎您, {{ name }}。</aside>
    <aside>当前角色：{{ roles }}</aside>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['name', 'roles'])
  }
}
</script>
