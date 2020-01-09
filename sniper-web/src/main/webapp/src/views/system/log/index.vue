<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="searchForm" :model="searchForm" :inline="true">
        <el-form-item label="用户名:" prop="username">
          <el-input v-model="searchForm.username" clearable />
        </el-form-item>
        <el-form-item label="操作名称:" prop="action">
          <el-input v-model="searchForm.action" clearable />
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
      <el-table-column align="center" prop="username" label="用户名" width="100" fixed="left" />
      <el-table-column align="center" prop="action" label="操作名称" width="180" />
      <el-table-column align="center" prop="httpMethod" label="Method" width="100" />
      <el-table-column align="center" prop="createTime" label="操作时间" width="180" />
      <el-table-column align="center" prop="ip" label="IP地址" width="150" />
      <el-table-column align="center" prop="params" label="参数" width="350" />
      <el-table-column align="center" prop="uri" label="URI" />
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
  </div>
</template>

<script>
import { getList } from '@/api/system/log'

export default {
  data() {
    return {
      searchForm: {
        username: '',
        action: '',
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
