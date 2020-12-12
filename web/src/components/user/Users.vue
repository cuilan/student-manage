<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统用户管理</el-breadcrumb-item>
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
          <el-button class="custom-button" type="primary">添加用户</el-button>
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
              ></el-button>
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="设置用户菜单"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                v-model="scope.row.id"
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 获取用户列表的参数
      queryInfo: {
        username: '',
        pageNum: 1,
        pageSize: 10
      },
      userList: [],
      total: 0
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
    }
  }
}
</script>

<style lang="less" scoped></style>
