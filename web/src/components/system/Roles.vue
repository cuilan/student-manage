<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>查询角色</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <!-- 添加按钮 -->
      <el-row>
        <el-col>
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加角色</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="roleList" border stripe>
        <el-table-column type="expand">
          <template v-slot="scope">
            <el-row
              :class="['bdbottom', i1 === 0 ? 'bdtop' : '', 'vcenter']"
              v-for="(item1, i1) in scope.row.sysMenus"
              :key="item1.id"
            >
              <!-- 一级菜单 -->
              <el-col :span="5">
                <el-tag
                  closable
                  @close="removeRoleMenuRelation(scope.row, item1.id)"
                  >{{ item1.name }}</el-tag
                >
                <i class="el-icon-caret-right"></i>
              </el-col>
              <!-- 二级菜单 -->
              <el-col :span="19">
                <el-row
                  :class="[i2 === 0 ? '' : 'bdtop']"
                  v-for="(item2, i2) in item1.sysMenus"
                  :key="item2.id"
                >
                  <el-col>
                    <el-tag
                      type="success"
                      closable
                      @close="removeRoleMenuRelation(scope.row, item2.id)"
                      >{{ item2.name }}</el-tag
                    >
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>
        <el-table-column label="ID" type="index"></el-table-column>
        <el-table-column label="菜单名称" prop="name"> </el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="是否可用">
          <!-- 作用域插槽 -->
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.visible"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="updateStatus(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="340px">
          <template v-slot="scope">
            <el-tooltip
              effect="dark"
              content="修改角色"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                v-model="scope.row.id"
                @click="showEditDialog(scope.row.id)"
                >修改角色</el-button
              >
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="删除角色"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-model="scope.row.id"
                @click="removeRoleById(scope.row.id)"
                >删除角色</el-button
              >
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="分配菜单"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                v-model="scope.row.id"
                @click="showSetMenuDialog(scope.row)"
                >分配菜单</el-button
              >
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 添加角色的对话框 -->
      <el-dialog
        title="添加角色"
        :visible.sync="addDialogVisible"
        width="50%"
        @close="addDialogclose"
      >
        <el-form
          :model="addSysRoleForm"
          :rules="addSysRoleFormRules"
          ref="addSysRoleFormRef"
          label-width="100px"
        >
          <el-form-item label="名称" prop="name">
            <el-input v-model="addSysRoleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="addSysRoleForm.description"></el-input>
          </el-form-item>
        </el-form>
        <!-- 按钮区域 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button class="custom-button" type="primary" @click="addSysRole"
            >确 定</el-button
          >
        </span>
      </el-dialog>
      <!-- 修改角色的对话框 -->
      <el-dialog
        title="添加角色"
        :visible.sync="editDialogVisible"
        width="50%"
        @close="editDialogclose"
      >
        <el-form
          :model="editSysRoleForm"
          :rules="addSysRoleFormRules"
          ref="editSysRoleFormRef"
          label-width="100px"
        >
          <el-form-item label="名称" prop="name">
            <el-input v-model="editSysRoleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="editSysRoleForm.description"></el-input>
          </el-form-item>
        </el-form>
        <!-- 按钮区域 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button class="custom-button" type="primary" @click="updateSysRole"
            >确 定</el-button
          >
        </span>
      </el-dialog>
      <!-- 分配菜单对话框 -->
      <el-dialog
        title="提示"
        :visible.sync="showSetMenuDialogVisible"
        width="30%"
        @close="setMenuDialogClosed"
      >
        <!--
          分配菜单的树形结构
          :data 绑定的数据
          :props 树结构的展示名称及子项的名称
          show-checkbox 复选框
          node-key 选中复选框后，绑定唯一属性，一般为id
          default-expand-all 默认展开
          default-checked-keys 默认勾选的节点数组
        -->
        <el-tree
          :data="menuList"
          :props="treeProps"
          show-checkbox
          node-key="id"
          default-expand-all
          :default-checked-keys="defaultKeys"
          ref="treeRef"
        ></el-tree>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showSetMenuDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="allotMenus">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 角色集合
      roleList: [],
      // 控制添加对话框的显示
      addDialogVisible: false,
      // 控制修改对话框的显示
      editDialogVisible: false,
      // 控制设置菜单对话框的显示
      showSetMenuDialogVisible: false,
      // 添加系统角色form
      addSysRoleForm: {
        name: '',
        description: ''
      },
      addSysRoleFormRules: {
        name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
        description: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      },
      editSysRoleForm: {},
      // 所有菜单的数据
      menuList: [],
      // 树形控件的绑定对象
      treeProps: {
        label: 'name',
        children: 'sysMenus'
      },
      // 树形结构默认选中的节点id数组
      defaultKeys: [],
      // 被操作的角色id
      roleId: ''
    }
  },
  created() {
    this.getRoleList()
  },
  methods: {
    async getRoleList() {
      const { data: res } = await this.$http.get('/api/sysRole/all')
      if (res.code !== 200) {
        return this.$message.console.error(res.message)
      }
      // console.log(res.data.roles)
      this.roleList = res.data.roles
    },
    // 关闭添加对话框事件
    addDialogclose() {
      this.$refs.addSysRoleFormRef.resetFields()
    },
    // 关闭修改对话框事件
    editDialogclose() {
      this.$refs.editSysRoleFormRef.resetFields()
    },
    // 添加系统角色
    addSysRole() {
      this.$refs.addSysRoleFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/sysRole/add',
          this.addSysRoleForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新列表
        this.getRoleList()
      })
    },
    // 展示修改对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      // console.log(id)
      const { data: res } = await this.$http.get('/api/sysRole/id', {
        params: { id: id }
      })
      // console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      // console.log(res.data.sysRole)
      this.editSysRoleForm = res.data.sysRole
    },
    updateSysRole() {
      this.$refs.editSysRoleFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/sysRole/update',
          this.editSysRoleForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('更新成功!')
        this.editDialogVisible = false
        // 刷新列表
        this.getRoleList()
      })
    },
    // 修改可用状态
    async updateStatus(roleInfo) {
      // console.log(roleInfo)
      const { data: res } = await this.$http.post('/api/sysRole/update', {
        id: roleInfo.id,
        visible: roleInfo.visible
      })
      if (res.code !== 200) {
        roleInfo.visible = !roleInfo.visible
        return this.$message.error(res.message)
      }
      return this.$message.success('状态更新成功!')
    },
    // 根据id删除
    async removeRoleById(id) {
      // 弹框提示
      const confirmResult = await this.$confirm(
        '此操作将永久删除该角色, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 如果点击确认按钮，返回的是字符串confirm
      // 如果点击取消按钮，返回的是字符串cancel
      // console.log(confirmResult)
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消删除')
        return
      }
      const { data: res } = await this.$http.post(
        '/api/sysRole/delete?sysRoleId=' + id
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('删除成功')
      // 刷新列表
      this.getRoleList()
    },
    // 根据roleId和menuId删除菜单与角色的关系
    async removeRoleMenuRelation(role, menuId) {
      const confirmResult = await this.$confirm(
        '确认删除该菜单与角色的关联关系?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      // console.log(roleId, menuId)
      const { data: res } = await this.$http.post(
        `/api/sysRole/deleteSysMenu?sysRoleId=${role.id}&sysMenuId=${menuId}`
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      role.sysMenus = res.data.newMenus
    },
    // 展示分配菜单对话框
    async showSetMenuDialog(role) {
      this.roleId = role.id
      // 获取所有菜单数据
      const { data: res } = await this.$http.get('/api/sysMenu/all')
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      // console.log(res.data.menus)
      this.menuList = res.data.menus
      // 递归获取当前角色节点的菜单id数组
      this.getHasMenuKeys(role, this.defaultKeys)
      // 展示对话框
      this.showSetMenuDialogVisible = true
    },
    /**
     * 通过递归获取当前角色下已有的菜单id数组，并赋值给 defaultKeys
     * @param node 当前节点
     * @param arr 数组
     */
    getHasMenuKeys(node, arr) {
      // node如果是role(有description说明是role)，并且菜单为空，则跳过
      if (!node.sysMenus && node.description) {
        return
      }
      // 二级子菜单，则放入该节点的id
      if (!node.sysMenus) {
        // console.log(node.name)
        return arr.push(node.id)
      }
      // 一级菜单，包含子节点，则递归调用
      node.sysMenus.forEach(item => this.getHasMenuKeys(item, arr))
    },
    // 监听分配菜单对话框的关闭事件
    setMenuDialogClosed() {
      this.defaultKeys = []
    },
    // 点击树形结构确定按钮，发起请求
    async allotMenus() {
      // ...为展开运算符，获取选中的id，半选中的id
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      // console.log(keys)
      // 英文逗号拼接
      const idStr = keys.join(',')
      const { data: res } = await this.$http.post(
        `/api/sysRole/addSysMenu?roleId=${this.roleId}&menuIds=${idStr}`
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.getRoleList()
      // 最后设置该对话框不可见
      this.showSetMenuDialogVisible = false
    }
  }
}
</script>

<style lang="less" scoped>
/* 设置tag间隔 */
.el-tag {
  margin: 7px;
}
/* 设置tag顶部边框线 */
.bdtop {
  border-top: 1px solid #eee;
}
/* 设置tag底部边框线 */
.bdbottom {
  border-bottom: 1px solid #eee;
}
/* tag标签纵向居中对齐 */
.vcenter {
  display: flex;
  align-items: center;
}
</style>
