<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="searchForm" :model="searchForm" :inline="true">
        <el-form-item label="名称:" prop="name">
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
            v-if="checkPermission('system:role:add')"
            type="success"
            icon="el-icon-circle-plus-outline"
            @click="showRoleDialog('create')"
          >新建角色</el-button>
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
      <el-table-column align="center" prop="name" label="名 称" fixed="left" />
      <el-table-column align="center" prop="createPerson" label="创建人" />
      <el-table-column align="center" prop="createTime" label="创建时间" />
      <el-table-column align="center" prop="updatePerson" label="修改人" />
      <el-table-column align="center" prop="updateTime" label="修改时间" />
      <el-table-column align="center" prop="action" label="操 作" width="300" fixed="right">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission('system:role:update')"
            type="text"
            size="mini"
            style="color:green"
            icon="el-icon-edit-outline"
            @click="showRoleDialog('update',row)"
          >修改</el-button>
          <el-button
            v-if="checkPermission('system:role:update')"
            type="text"
            size="mini"
            style="color:orange"
            icon="el-icon-setting"
            @click="showResourceDialog(row.id)"
          >分配权限</el-button>
          <el-button
            v-if="checkPermission('system:role:list')"
            type="text"
            size="mini"
            style="color:blue"
            icon="el-icon-notebook-2"
            @click="showUserDialog(row.id)"
          >查看用户</el-button>
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
    <resource-dialog ref="editResource" />
  </div>
</template>

<script>
import { getList } from '@/api/system/role'
// 查看角色包含的用户对话框
import UserDialog from './userDialog'
// 新建、修改角色对话框
import RoleDialog from './roleDialog'
// 分配权限对话框
import ResourceDialog from './resourceDialog'

export default {
  components: { UserDialog, RoleDialog, ResourceDialog },
  data() {
    return {
      searchForm: {
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
    if (this.checkPermission('system:role:list')) {
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

    showUserDialog(id) {
      this.$refs.editUser.showDialog(id)
    },

    showRoleDialog(type, row) {
      this.$refs.editRole.showDialog(type, row)
    },

    showResourceDialog(id) {
      this.$refs.editResource.showDialog(id)
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
