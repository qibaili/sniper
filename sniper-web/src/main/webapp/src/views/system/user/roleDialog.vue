<template>
  <el-dialog
    title="关联角色"
    :visible.sync="dialogVisible"
    width="450px"
    center
    :close-on-click-modal="false"
    :destroy-on-close="true"
  >
    <el-table
      ref="tableData"
      :data="tableData"
      max-height="350"
      stripe
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" />
      <el-table-column property="name" label="角色" />
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="assignRole()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getList, getListByUserId } from '@/api/system/role'
import { assignRole } from '@/api/system/user'

export default {
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      userId: '',
      multipleSelection: []
    }
  },
  methods: {
    showDialog(userId) {
      this.dialogVisible = true
      this.userId = userId
      this.getList(userId)
    },
    getList(userId) {
      const data = {
        page: 1,
        pageSize: 1000
      }
      // 查询系统所有角色列表
      getList(data).then(response => {
        if (response.success) {
          this.tableData = response.data.content
          this.getRoleListByUserId(this.tableData)
        }
      })
    },
    getRoleListByUserId(tableData) {
      const roleIds = []
      // 根据 userId 查询角色列表
      getListByUserId(this.userId).then(response => {
        if (response.success) {
          const ids = []
          const roleSelection = response.data
          if (roleSelection) {
            roleSelection.forEach(e => {
              ids.push(e.id)
            })
          }

          if (ids.length > 0) {
            ids.forEach(e => {
              for (let i = 0; i < tableData.length; i++) {
                if (e === tableData[i].id) {
                  roleIds.push(tableData[i])
                  continue
                }
              }
            })
            this.toggleSelection(roleIds)
          }
        }
      })
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.tableData.toggleRowSelection(row)
        })
      } else {
        this.$refs.tableData.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    assignRole() {
      const roleIds = []
      if (this.multipleSelection) {
        for (let i = 0; i < this.multipleSelection.length; i++) {
          roleIds.push(this.multipleSelection[i].id)
        }
      }
      assignRole(this.userId, roleIds).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            duration: 2 * 1000,
            message: response.message
          })
          this.dialogVisible = false
          this.$parent.onSubmit()
        }
      })
    }
  }
}
</script>
