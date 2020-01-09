<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="searchForm" :model="searchForm" :inline="true">
        <el-form-item label="账号:" prop="username">
          <el-input v-model="searchForm.username" clearable />
        </el-form-item>
        <el-form-item label="姓名:" prop="name">
          <el-input v-model="searchForm.name" clearable />
        </el-form-item>
        <el-form-item>
          <el-button v-waves type="primary" icon="el-icon-search" @click="onSubmit()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            v-waves
            icon="el-icon-refresh"
            type="info"
            plain
            @click="resetForm('searchForm')"
          >重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="checkPermission('system:user:add')"
            type="success"
            icon="el-icon-circle-plus-outline"
            @click="showUserDialog('create')"
          >新建用户</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      height="660"
      :data="tableData"
      border
      stripe
      highlight-current-row
      header-row-class-name="table-header"
      style="width: 100%"
    >
      <el-table-column align="center" prop="username" label="用户名" width="180" fixed="left" />
      <el-table-column align="center" prop="name" label="姓名" width="180" />
      <el-table-column align="center" prop="status" label="状态" width="100">
        <template slot-scope="{row}">
          <el-tag :type="row.status==1?'success':'danger'">{{ row.status==1?"正常":"禁用" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="email" label="邮箱" width="200" />
      <el-table-column align="center" prop="telephone" label="电话" width="200" />
      <el-table-column align="center" prop="createPerson" label="创建人" width="200" />
      <el-table-column align="center" prop="createTime" label="创建时间" width="200" />
      <el-table-column align="center" prop="updatePerson" label="修改人" width="200" />
      <el-table-column align="center" prop="updateTime" label="修改时间" width="200" />
      <el-table-column align="center" prop="action" label="操作" width="300" fixed="right">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission('system:user:update')"
            type="text"
            size="mini"
            style="color:green"
            icon="el-icon-edit-outline"
            @click="showUserDialog('update',row)"
          >修改</el-button>
          <el-button
            v-if="checkPermission('system:user:update')"
            type="text"
            size="mini"
            style="color:orange"
            icon="el-icon-setting"
            @click="showRoleDialog(row.id)"
          >分配角色</el-button>
          <el-button
            v-if="checkPermission('system:user:update')"
            type="text"
            size="mini"
            :style="{color:row.status==1?'red':''}"
            :icon="row.status==1?'el-icon-open':'el-icon-turn-off'"
            @click="changStatus(row.id)"
          >{{ row.status==1?"禁用":"启用" }}</el-button>
          <el-button
            v-if="checkPermission('system:user:update')"
            type="text"
            size="mini"
            style="color:blue"
            icon="el-icon-refresh"
            @click="resetPassword(row.id)"
          >重置密码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="searchForm.page"
      :page-sizes="[15, 20, 30, 40]"
      :page-size="searchForm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalElements"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <user-dialog ref="editUser" />
    <role-dialog ref="editRole" />
  </div>
</template>

<script>
import { getList, changeStatus, resetPassword } from '@/api/system/user'
// 新建、修改用户对话框
import UserDialog from './userDialog'
// 用户分配角色对话框
import RoleDialog from './roleDialog'

export default {
  components: { UserDialog, RoleDialog },
  data() {
    return {
      searchForm: {
        username: '',
        name: '',
        page: 1,
        pageSize: 15
      },
      loading: false,
      tableData: [],
      // 总条目数
      totalElements: 0
    }
  },

  created() {
    if (this.checkPermission('system:user:list')) {
      this.onSubmit()
    }
  },

  methods: {
    onSubmit() {
      getList(this.searchForm)
        .then(response => {
          if (response.success) {
            this.loading = false
            this.tableData = response.data.content
            this.totalElements = response.data.totalElements
            this.totalPages = response.data.totalPages
          }
        })
        .catch(() => {
          this.loading = false
        })
    },

    showUserDialog(type, row) {
      this.$refs.editUser.showDialog(type, row)
    },

    showRoleDialog(id) {
      this.$refs.editRole.showDialog(id)
    },

    changStatus(id) {
      changeStatus(id).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            duration: 2 * 1000,
            message: response.message
          })
          this.onSubmit()
        }
      })
    },

    resetPassword(id) {
      resetPassword(id).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            duration: 2 * 1000,
            message: response.message
          })
          this.onSubmit()
        }
      })
    },

    handleSizeChange(val) {
      this.searchForm.pageSize = val
      this.onSubmit()
    },

    handleCurrentChange(val) {
      this.searchForm.page = val
      this.onSubmit()
    },

    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
