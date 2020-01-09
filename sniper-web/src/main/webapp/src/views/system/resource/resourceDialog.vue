<template>
  <el-dialog
    :title="dialogTitile"
    :visible.sync="dialogVisible"
    width="450px"
    center
    :close-on-click-modal="false"
  >
    <el-form class="small-space" :model="form" label-position="right" label-width="80px">
      <el-form-item v-if="editType=='createLower'" label="父级ID" required>
        <el-input v-model="form.parentId" disabled />
      </el-form-item>
      <el-form-item label="名称" required>
        <el-input v-if="editType=='update'" v-model="form.name" disabled />
        <el-input v-else v-model="form.name" />
      </el-form-item>
      <el-form-item label="权限编码" required>
        <el-input v-if="editType=='update'" v-model="form.permission" disabled />
        <el-input v-else v-model="form.permission" />
      </el-form-item>
      <el-form-item label="排序" required>
        <el-input-number v-model="form.sort" :min="1" />
      </el-form-item>
      <el-form-item label="类型" required>
        <el-select v-model="form.type" placeholder="请选择">
          <el-option
            v-for="item in typeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否必选" required>
        <el-select v-model="form.required" placeholder="请选择">
          <el-option
            v-for="item in requiredOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button v-if="editType=='create'" type="success" @click="createResource()">创 建</el-button>
      <el-button
        v-if="editType=='createLower'"
        type="success"
        @click="createResource('lowerResource')"
      >创 建</el-button>
      <el-button v-if="editType=='update'" type="primary" @click="updateResource()">修 改</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { createResource, updateResource } from '@/api/system/resource'

export default {
  data() {
    return {
      dialogVisible: false,
      editType: '',
      dialogTitile: '',
      form: {
        id: '',
        parentId: '',
        name: '',
        permission: '',
        sort: '',
        required: '',
        type: ''
      },
      requiredOptions: [
        {
          value: '1',
          label: '是'
        },
        {
          value: '0',
          label: '否'
        }
      ],
      typeOptions: [
        {
          value: '0',
          label: '页面'
        },
        {
          value: '1',
          label: '按钮'
        }
      ]
    }
  },

  methods: {
    showDialog(editType, row) {
      this.editType = editType
      if (editType === 'create') {
        this.dialogVisible = true
        this.dialogTitile = '新建资源'
        this.form.name = ''
        this.form.permission = ''
        this.form.type = ''
        this.form.sort = ''
        this.form.required = ''
      } else if (editType === 'update') {
        this.dialogVisible = true
        this.dialogTitile = '修改资源'
        this.form = Object.assign({}, row)
      } else if (editType === 'createLower') {
        this.dialogVisible = true
        this.dialogTitile = '添加下级'
        this.form.parentId = row.id
        this.form.name = ''
        this.form.permission = ''
        this.form.type = ''
        this.form.sort = ''
        this.form.required = ''
      }
    },

    updateResource() {
      const id = this.form.id
      const data = {
        required: this.form.required,
        type: this.form.type,
        sort: this.form.sort
      }
      updateResource(id, data).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            duration: 2 * 1000,
            message: response.message
          })
          this.dialogVisible = false
          this.$parent.getList()
        }
      })
    },

    createResource(editType) {
      let data = {}
      if (editType) {
        data = {
          parentId: this.form.parentId,
          name: this.form.name,
          permission: this.form.permission,
          required: this.form.required,
          type: this.form.type,
          sort: this.form.sort
        }
      } else {
        data = {
          name: this.form.name,
          permission: this.form.permission,
          required: this.form.required,
          type: this.form.type,
          sort: this.form.sort
        }
      }
      createResource(data).then(response => {
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
