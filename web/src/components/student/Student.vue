<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
      <el-breadcrumb-item>学生信息管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <!-- 数据列表 -->
      <el-table :data="studentList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="学生姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="sex">
          <template v-slot="scope">
            <p v-if="scope.row.sex === 'MALE'">男</p>
            <p v-else>女</p>
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="血型">
          <template v-slot="scope">
            <p v-if="scope.row.bloodType === 'A'">A型</p>
            <p v-else-if="scope.row.bloodType === 'B'">B型</p>
            <p v-else-if="scope.row.bloodType === 'AB'">AB型</p>
            <p v-else-if="scope.row.bloodType === 'O'">O型</p>
          </template>
        </el-table-column>
        <el-table-column label="民族" prop="nation"></el-table-column>
        <el-table-column label="家庭住址" prop="address"></el-table-column>
        <el-table-column label="所在年级" prop="gradeName"></el-table-column>
        <el-table-column
          label="所在班级"
          prop="classRankName"
        ></el-table-column>
      </el-table>
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
      studentList: [],
      // 获取列表的参数
      queryInfo: {
        name: '',
        gradeId: '',
        classRankId: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0
    }
  },
  created() {
    this.getStudentList()
  },
  methods: {
    // 查询学生列表
    async getStudentList() {
      const { data: res } = await this.$http.get('/api/student/query', {
        params: this.queryInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.studentList = res.data.students.data
      this.total = res.data.students.total
    },
    // 监听 pageSize 改变事件
    handleSizeChange(newSize) {
      // 设置为选中的分页大小
      this.queryInfo.pageSize = newSize
      this.getStudentList()
    },
    // 监听 pageNum 改变事件
    handleCurrentChange(newPage) {
      // 设置为选中的页码
      this.queryInfo.pageNum = newPage
      this.getStudentList()
    }
  }
}
</script>

<style lang="less" scoped></style>
