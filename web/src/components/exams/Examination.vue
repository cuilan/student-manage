<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <!-- 数据列表 -->
      <el-table :data="examList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="考试名称" prop="name"></el-table-column>
        <el-table-column
          label="总分数"
          prop="totalScore"
          width="80px"
        ></el-table-column>
        <el-table-column label="考试地点" prop="address"></el-table-column>
        <el-table-column label="是否已结束" width="100px">
          <template v-slot="scope">
            <el-tag v-if="scope.row.finished === true" type="success"
              >完成</el-tag
            >
            <el-tag v-else type="danger">未完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="考试科目"
          prop="subjectName"
          width="100px"
        ></el-table-column>
        <el-table-column label="班级名称" prop="classRankName">
        </el-table-column>
        <el-table-column label="开始时间">
          <template v-slot="scope">
            {{ scope.row.startDateTime | dateToStr }}
          </template></el-table-column
        >
        <el-table-column label="结束时间">
          <template v-slot="scope">
            {{ scope.row.endDateTime | dateToStr }}
          </template></el-table-column
        >
        <el-table-column label="备注" prop="remark"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 考试信息列表
      examList: [],
      // 获取列表的参数
      queryInfo: {
        name: '',
        startTime: '',
        endTime: '',
        subjectId: '',
        classRankId: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0
    }
  },
  created() {
    this.getExamList()
  },
  methods: {
    // 查询列表
    async getExamList() {
      // console.log(this.queryInfo)
      const { data: res } = await this.$http.get('/api/exam/query', {
        params: this.queryInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.examList = res.data.exams.data
      this.total = res.data.exams.total
    }
  }
}
</script>

<style lang="less" scoped></style>
