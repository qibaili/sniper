<template>
  <el-dialog
    :title="dialogTitile"
    :visible.sync="dialogVisible"
    width="450px"
    center
    :close-on-click-modal="false"
  >
    <el-form class="small-space" :model="form" label-position="right" label-width="80px">
      <el-form-item label="名称" required>
        <el-input v-model="form.name" type="text" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button v-if="editType=='create'" type="success" @click="createRole()">创 建</el-button>
      <el-button v-else type="primary" @click="updateRole()">修 改</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { createRole, updateRole } from '@/api/system/role'

export default {
  data() {
    return {
      dialogVisible: false,
      editType: '',
      dialogTitile: '',
      form: {
        id: '',
        name: ''
      }
    }
  },

  methods: {
    showDialog(type, row) {
      this.editType = type
      if (type === 'create') {
        this.dialogVisible = true
        this.dialogTitile = '新建角色'
        this.form.name = ''
      } else if (type === 'update') {
        this.dialogVisible = true
        this.dialogTitile = '修改角色'
        this.form = Object.assign({}, row)
      }
    },

    updateRole() {
      const id = this.form.id
      const data = {
        name: this.form.name
      }
      updateRole(id, data).then(response => {
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
    },

    createRole() {
      const data = {
        name: this.form.name
      }
      createRole(data).then(response => {
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
