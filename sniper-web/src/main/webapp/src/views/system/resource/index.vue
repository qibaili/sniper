<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            v-if="checkPermission('system:resource:list')"
            v-waves
            type="primary"
            icon="el-icon-refresh"
            @click="onSubmit()"
          >刷新</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="checkPermission('system:resource:add')"
            type="success"
            icon="el-icon-circle-plus-outline"
            @click="showResourceDialog('create')"
          >新建资源</el-button>
        </el-form-item>
        <el-form-item>
          <el-tooltip content="展开/折叠节点" placement="top">
            <el-switch
              v-if="checkPermission('system:resource:list')"
              v-model="switchValue"
              class="demo"
              active-text="开"
              inactive-text="关"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="100"
              inactive-value="0"
              @change="switchChange(switchValue)"
            />
          </el-tooltip>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-bottom:10px">
      <el-alert :title="warnTitle" :description="warnDescription" type="error" close-text="知道了" />
    </div>
    <el-table
      ref="tree"
      :data="tableData"
      border
      stripe
      highlight-current-row
      header-row-class-name="table-header"
      style="width: 100%"
      row-key="id"
      :expand-row-keys="expands"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="name" label="名称" fixed="left" />
      <el-table-column prop="permission" label="权限编码" />
      <el-table-column align="center" prop="type" label="类型">
        <template slot-scope="{row}">
          <el-tag v-if="row.type==0">{{ "页面" }}</el-tag>
          <el-tag v-if="row.type==1" type="success">{{ "按钮" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sort" label="排序" />
      <el-table-column align="center" prop="action" label="操作" width="220" fixed="right">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission('system:resource:update')"
            type="text"
            size="mini"
            style="color:green"
            icon="el-icon-edit-outline"
            @click="showResourceDialog('update', row)"
          >修改</el-button>
          <el-button
            v-if="row.type==='0' && checkPermission('system:resource:add')"
            type="text"
            size="mini"
            style="color:orange"
            icon="el-icon-circle-plus-outline"
            @click="showResourceDialog('createLower',row)"
          >添加下级</el-button>
          <el-button
            v-if="checkPermission('system:resource:delete')"
            type="text"
            size="mini"
            style="color:red"
            icon="el-icon-delete"
            @click="handleDelate(row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <resource-dialog ref="editResource" />
  </div>
</template>

<script>
import { getList, deleteResource } from '@/api/system/resource'
// 新建、修改资源对话框
import ResourceDialog from './resourceDialog'
export default {
  components: { ResourceDialog },
  data() {
    return {
      switchValue: '0',
      expands: [],
      tableData: [],
      warnTitle: '权限编码说明',
      warnDescription:
        '上级资源权限编码不能设置为下级的通配符！如:“用户管理”菜单权限编码不能为“system:user”，否则 Shiro 会解析为“system:user:*”从而匹配上“用户管理”的所有权限'
    }
  },

  created() {
    if (this.checkPermission('system:resource:list')) {
      this.onSubmit()
    }
  },

  methods: {
    onSubmit() {
      getList().then(response => {
        if (response.success) {
          this.tableData = response.data
        }
      })
    },

    showResourceDialog(editType, row) {
      this.$refs.editResource.showDialog(editType, row)
    },

    handleDelate(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteResource(id).then(response => {
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
            this.onSubmit()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

    switchChange(switchValue) {
      if (switchValue === '100') {
        for (let i = 0; i < this.tableData.length; i++) {
          this.expands.push(this.tableData[i].id)
        }
      }
      if (switchValue === '0') {
        for (let i = 0; i < this.tableData.length; i++) {
          this.$refs.tree.toggleRowExpansion(this.tableData[i], false)
        }
      }
    }
  }
}
</script>

<style >
.demo .el-switch__label {
  position: absolute;
  display: none;
  color: #fff;
}
/*打开时文字位置设置*/
.demo .el-switch__label--right {
  z-index: 1;
  right: -15px;
}
/*关闭时文字位置设置*/
.demo .el-switch__label--left {
  z-index: 1;
  left: 22px;
}
/*显示文字*/
.demo .el-switch__label.is-active {
  display: block;
}
.demo.el-switch .el-switch__core,
.el-switch .el-switch__label {
  width: 50px !important;
}
</style>
