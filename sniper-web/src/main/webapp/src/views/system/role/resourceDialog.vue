<template>
  <el-dialog
    title="分配权限"
    :visible.sync="dialogVisible"
    width="450px"
    center
    :close-on-click-modal="false"
  >
    <el-button-group>
      <el-button type="success" @click="setCheckedKeys()">全选</el-button>
      <el-button type="warning" @click="resetChecked()">清除</el-button>
    </el-button-group>
    <el-tree
      ref="tree"
      style="margin-top:15px"
      :data="data"
      :props="defaultProps"
      node-key="id"
      :render-content="renderContent"
      show-checkbox
    />
    <div style="margin-top:15px">说明：标红权限为对应菜单内必选权限</div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="assignResource()">修 改</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getList, getResourceByRoleId } from '@/api/system/resource'
import { assignPermissions } from '@/api/system/role'
export default {
  data() {
    return {
      dialogVisible: false,
      roleId: '',
      data: [],
      treeMap: {},
      defaultProps: {
        label: 'name'
      }
    }
  },

  methods: {
    showDialog(roleId) {
      this.dialogVisible = true
      this.roleId = roleId
      this.getList(roleId)
    },

    getList(roleId) {
      getList().then(response => {
        if (response.success) {
          const rows = response.data
          this.data = rows
          this.treeMap = new Map()
          rows.forEach(row => {
            this.treeMap.set(row.id, row)
          })
          this.getResourceByRoleId(roleId)
        }
      })
    },

    getResourceByRoleId(roleId) {
      getResourceByRoleId(roleId).then(response => {
        if (response.success) {
          for (let i = 0; i < response.data.length; i++) {
            const item = response.data[i]
            this.$refs.tree.setChecked(item.id, true, false)
          }
        }
      })
    },

    assignResource() {
      const checkedKeys = this.$refs.tree.getCheckedKeys()
      const halfCheckedKeys = this.$refs.tree.getHalfCheckedKeys()
      const data = checkedKeys.concat(halfCheckedKeys)
      assignPermissions(this.roleId, data).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            duration: 2 * 1000,
            message: response.message
          })
          this.dialogVisible = false
        }
      })
    },

    setCheckedKeys() {
      this.$refs.tree.setCheckedKeys([...this.treeMap.keys()])
    },

    resetChecked() {
      this.$refs.tree.setCheckedKeys([])
    },

    renderContent(h, { node, data }) {
      if (data.required === '1') {
        return (
          <span>
            <span style='color:red'>{node.label}</span>
          </span>
        )
      } else {
        return (
          <span>
            <span style='color:blue'>{node.label}</span>
          </span>
        )
      }
    }
  }
}
</script>

<style>
</style>
