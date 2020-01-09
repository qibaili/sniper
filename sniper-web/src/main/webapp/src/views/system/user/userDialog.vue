<template>
  <el-dialog
    :title="dialogTitile"
    :visible.sync="dialogVisible"
    width="450px"
    center
    :close-on-click-modal="false"
  >
    <el-form class="small-space" :model="form" label-position="right" label-width="80px">
      <el-form-item label="用户名" required>
        <el-input v-if="editType=='create'" v-model="form.username" type="text" />
        <el-input v-else v-model="form.username" disabled type="text" />
      </el-form-item>
      <el-form-item label="姓名" required>
        <el-input v-if="editType=='create'" v-model="form.name" type="text" />
        <el-input v-else v-model="form.name" disabled type="text" />
      </el-form-item>
      <el-form-item v-if="editType=='create'" label="密码" required>
        <el-input v-model="form.password" type="password" show-password />
      </el-form-item>
      <el-form-item label="邮箱" required>
        <el-input v-model="form.email" type="text" />
      </el-form-item>
      <el-form-item label="电话" required>
        <el-input v-model="form.telephone" type="text" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button v-if="editType=='create'" type="success" @click="createUser()">创 建</el-button>
      <el-button v-else type="primary" @click="updateUser()">修 改</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { updateUser, createUser } from '@/api/system/user'

export default {
  data() {
    return {
      dialogVisible: false,
      editType: '',
      dialogTitile: '',
      form: {
        id: '',
        username: '',
        name: '',
        password: '',
        email: '',
        telephone: ''
      }
    }
  },

  methods: {
    showDialog(type, row) {
      this.editType = type
      if (type === 'create') {
        this.dialogVisible = true
        this.dialogTitile = '新建用户'
        this.form.username = ''
        this.form.name = ''
        this.form.password = ''
        this.form.userId = ''
        this.form.email = ''
        this.form.telephone = ''
      } else if (type === 'update') {
        this.dialogVisible = true
        this.dialogTitile = '修改用户'
        this.form = Object.assign({}, row)
      }
    },

    updateUser() {
      const id = this.form.id
      const data = {
        email: this.form.email,
        telephone: this.form.telephone
      }
      updateUser(id, data).then(response => {
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

    createUser() {
      const data = {
        username: this.form.username,
        name: this.form.name,
        password: this.form.password,
        email: this.form.email,
        telephone: this.form.telephone
      }
      createUser(data).then(response => {
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
