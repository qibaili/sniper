<template>
  <el-dialog
    title="查看用户"
    :visible.sync="dialogVisible"
    width="450px"
    center
    :close-on-click-modal="false"
    :destroy-on-close="true"
  >
    <el-table ref="tableData" :data="tableData" max-height="350" stripe border>
      <el-table-column property="username" label="用户名" />
      <el-table-column property="name" label="姓名" />
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getListByRoleId } from '@/api/system/user'

export default {
  data() {
    return {
      dialogVisible: false,
      tableData: []
    }
  },

  methods: {
    showDialog(roleId) {
      this.dialogVisible = true
      this.getList(roleId)
    },

    getList(roleId) {
      getListByRoleId(roleId).then(response => {
        if (response.success) {
          this.tableData = response.data
        }
      })
    }
  }
}
</script>
