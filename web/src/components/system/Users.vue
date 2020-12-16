<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>查询用户</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            placeholder="请输入用户名称"
            v-model="queryInfo.username"
            clearable
            @clear="getUserList()"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getUserList()"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加用户</el-button
          >
        </el-col>
      </el-row>
      <!-- 数据列表 -->
      <el-table :data="userList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="头像" prop="portrait">
          <template v-slot="scope">
            <el-avatar size="large" :src="scope.row.portrait"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
          label="最后登录IP"
          prop="lastLoginIp"
        ></el-table-column>
        <el-table-column
          label="最后登录时间"
          prop="lastLoginDateTime"
        ></el-table-column>
        <el-table-column label="是否可用">
          <!-- 作用域插槽 -->
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="updateStatus(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template v-slot="scope">
            <el-tooltip
              effect="dark"
              content="修改用户"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                v-model="scope.row.id"
                @click="showEditDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="删除用户"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-model="scope.row.id"
                @click="removeUserById(scope.row.id)"
              ></el-button>
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="设置角色"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                v-model="scope.row.id"
                @click="showRole(scope.row)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!--
          分页区
          @size-change 切换分页大小触发此函数
          @current-change 切换当前选中页码触发此函数
          :current-page 当前页
          :page-sizes 定义可选分页大小
          :page-size 当前分页大小
          layout 显示项
          :total 总大小
      -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[10, 20, 30]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
    <!-- 添加用户的对话框 -->
    <el-dialog
      title="添加用户"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogclose"
    >
      <el-form
        :model="addSysUserForm"
        :rules="addSysUserFormRules"
        ref="addSysUserFormRef"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addSysUserForm.username"></el-input>
        </el-form-item>
        <el-form-item label="初始密码" prop="password">
          <el-input v-model="addSysUserForm.password"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="addSysUserForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="portrait">
          <el-input v-model="addSysUserForm.portrait"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addSysUser"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 修改用户的对话框 -->
    <el-dialog
      title="修改用户"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="updateDialogclose"
    >
      <el-form
        :model="editSysUserForm"
        :rules="addSysUserFormRules"
        ref="editSysUserFormRef"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editSysUserForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="初始密码" prop="password">
          <el-input v-model="editSysUserForm.password"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="editSysUserForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="portrait">
          <el-input v-model="editSysUserForm.portrait"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSysUser">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 分配角色对话框 -->
    <el-dialog
      title="分配角色"
      :visible.sync="showRoleDialogVisible"
      width="30%"
      @close="setRoleDialogclosed"
    >
      <div>
        <p>
          当前用户：<b>{{ userInfo.username }}</b>
        </p>
        <p>
          当前角色：<b>{{ userInfo.roleName }}</b>
        </p>
        <p>
          角色描述：<b>{{ userInfo.description }}</b>
        </p>
        <p>
          分配新角色：
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.description"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleIdAndUserId">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    // 自定义电话号码验证规则
    var checkPhone = (rule, value, callback) => {
      const regPhone = /^1[3|4|5|7|8][0-9]{9}$/
      if (regPhone.test(value)) {
        // 合法电话号码
        return callback()
      }
      callback(new Error('请输入合法的电话号码'))
    }
    return {
      // 获取用户列表的参数
      queryInfo: {
        username: '',
        pageNum: 1,
        pageSize: 10
      },
      // 分页查询的多个结果集
      userList: [],
      total: 0,
      isAdmin: false,
      // 控制添加用户对话框的显示
      addDialogVisible: false,
      // 控制修改用户对话框的显示
      editDialogVisible: false,
      // 添加系统用户表单数据
      addSysUserForm: {
        username: '',
        password: '',
        phone: '',
        portrait: ''
      },
      // 添加用户表单验证对象
      addSysUserFormRules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入电话号码', trigger: 'blur' },
          { min: 6, max: 11, message: '电话号码为 11 位', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' }
        ]
      },
      // 根据id查询的结果，修改
      editSysUserForm: {
        id: 0,
        username: '',
        password: '',
        phone: '',
        portrait: ''
      },
      // 修改验证对象
      editSysUserFormRules: {},
      // 控制分配角色对话框的显示
      showRoleDialogVisible: false,
      userInfo: {},
      // 所有角色数据列表
      roleList: [],
      // 下拉角色列表选中的角色id值
      selectedRoleId: ''
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    async getUserList() {
      const { data: res } = await this.$http.get('/api/sysUser/query', {
        params: this.queryInfo
      })
      // console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.userList = res.data.sysUsers.data
      this.total = res.data.sysUsers.total
      this.isAdmin = res.data.isAdmin
    },
    // 修改用户信息，并发起更新请求
    async updateStatus(userInfo) {
      // console.log(userInfo)
      const { data: res } = await this.$http.post('/api/sysUser/update', {
        id: userInfo.id,
        status: userInfo.status
      })
      if (res.code !== 200) {
        userInfo.status = !userInfo.status
        return this.$message.error(res.message)
      }
      return this.$message.success('状态更新成功!')
    },
    // 监听 pageSize 改变事件
    handleSizeChange(newSize) {
      // console.log(newSize)
      // 设置为选中的分页大小
      this.queryInfo.pageSize = newSize
      this.getUserList()
    },
    // 监听 pageNum 改变事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      // 设置为选中的页码
      this.queryInfo.pageNum = newPage
      this.getUserList()
    },
    // 关闭添加用户对话框事件
    addDialogclose() {
      this.$refs.addSysUserFormRef.resetFields()
    },
    // 关闭修改用户对话框事件
    updateDialogclose() {
      this.$refs.editSysUserFormRef.resetFields()
    },
    // 点击确定，添加用户预验证函数
    addSysUser() {
      this.$refs.addSysUserFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/sysUser/add',
          this.addSysUserForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新用户列表
        this.getUserList()
      })
    },
    // 点击确定，修改用户预验证函数
    updateSysUser() {
      this.$refs.editSysUserFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/sysUser/update',
          this.editSysUserForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('更新成功!')
        this.editDialogVisible = false
        // 刷新用户列表
        this.getUserList()
      })
    },
    // 展示修改用户对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      // console.log(id)
      const { data: res } = await this.$http.get('/api/sysUser/query', {
        params: { id: id }
      })
      // console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      // console.log(res.data.sysUser)
      this.editSysUserForm = res.data.sysUser
    },
    // 根据id删除用户
    async removeUserById(id) {
      // console.log(id)
      // 弹框提示
      const confirmResult = await this.$confirm(
        '此操作将永久删除该用户, 是否继续?',
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
        '/api/sysUser/delete?sysUserId=' + id
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('删除成功')
      // 刷新用户列表
      this.getUserList()
    },
    // 点击分配角色按钮事件
    async showRole(sysUser) {
      this.userInfo = sysUser
      // 获取所有角色列表
      const { data: res } = await this.$http.get('/api/sysRole/all')
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.roleList = res.data.roles
      // 展示分配角色对话框
      this.showRoleDialogVisible = true
    },
    // 保存角色与用户的关联关系
    async saveRoleIdAndUserId() {
      if (!this.selectedRoleId) {
        return this.$message.error('请选择要分配的角色')
      }
      // console.log(this.selectedRoleId)
      // console.log(this.userInfo.id)
      const { data: res } = await this.$http.post(
        `/api/sysRole/addSysUser?roleId=${this.selectedRoleId}&userId=${this.userInfo.id}`
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('更新角色成功')
      this.getUserList()
      // 关闭分配角色对话框
      this.showRoleDialogVisible = false
    },
    // 监听分配角色对话框关闭事件，重置复选框中的数据
    setRoleDialogclosed() {
      this.selectedRoleId = ''
      this.userInfo = {}
    }
  }
}
</script>

<style lang="less" scoped></style>
