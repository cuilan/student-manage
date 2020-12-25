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
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input
            placeholder="请输入考试名称"
            v-model="queryInfo.name"
            clearable
          >
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryInfo.finished"
            placeholder="是否已结束"
            clearable
          >
            <el-option label="未结束" value="false"></el-option>
            <el-option label="已结束" value="true"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-input
            placeholder="请输入班级名称"
            v-model="queryInfo.classRankName"
            clearable
          >
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryInfo.subjectId"
            clearable
            placeholder="请选择科目"
          >
            <el-option
              v-for="item in subjectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-date-picker
            type="datetime"
            placeholder="起始日期时间"
            v-model="queryInfo.startTime"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
        <el-col :span="4">
          <el-date-picker
            type="datetime"
            placeholder="结束日期时间"
            v-model="queryInfo.endTime"
            value-format="timestamp"
          ></el-date-picker>
        </el-col>
        <el-col :span="2">
          <el-button class="custom-button" type="primary" @click="getExamList"
            >搜索</el-button
          >
        </el-col>
        <el-col :span="2">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加考试</el-button
          >
        </el-col>
      </el-row>
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
        <el-table-column label="操作" width="120px">
          <template v-slot="scope">
            <el-tooltip
              effect="dark"
              content="修改考试"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                v-model="scope.row.id"
                @click="showEditDialog(scope.row.id)"
                ></el-button
              >
            </el-tooltip>
            <el-tooltip
              effect="dark"
              content="删除考试"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-model="scope.row.id"
                @click="removeExamById(scope.row.id)"
                ></el-button
              >
            </el-tooltip>
          </template>
        </el-table-column>
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
      // 考试信息列表
      examList: [],
      // 获取列表的参数
      queryInfo: {
        name: '',
        finished: '',
        startTime: '',
        endTime: '',
        subjectId: '',
        classRankName: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      // 科目列表
      subjectList: []
    }
  },
  created() {
    this.getExamList()
    this.getSubjectList()
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
    },
    // 查询科目列表
    async getSubjectList() {
      const { data: res } = await this.$http.get('/api/subject/query')
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.subjectList = res.data.subjects
    },
    // 监听 pageSize 改变事件
    handleSizeChange(newSize) {
      // console.log(newSize)
      // 设置为选中的分页大小
      this.queryInfo.pageSize = newSize
      this.getExamList()
    },
    // 监听 pageNum 改变事件
    handleCurrentChange(newPage) {
      // console.log(newPage)
      // 设置为选中的页码
      this.queryInfo.pageNum = newPage
      this.getExamList()
    }
  }
}
</script>

<style lang="less" scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
</style>
