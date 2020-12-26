<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>成绩管理</el-breadcrumb-item>
      <el-breadcrumb-item>成绩查询</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input
            placeholder="请输入学生ID或姓名"
            v-model="queryInfo.studentName"
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
        <el-col :span="3">
          <el-select
            v-model="queryInfo.classRankId"
            clearable
            placeholder="请选择班级"
          >
            <el-option
              v-for="item in classRankList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-input
            placeholder="请输入分数"
            v-model.number="queryInfo.score"
            clearable
          >
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button class="custom-button" type="primary" @click="getScoreList"
            >搜索</el-button
          >
        </el-col>
        <el-col :span="2">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加学生成绩</el-button
          >
        </el-col>
      </el-row>
      <!-- 数据列表 -->
      <el-table :data="scoreList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="学生ID" prop="studentId"></el-table-column>
        <el-table-column label="学生姓名" prop="studentName"></el-table-column>
        <el-table-column label="考试科目" prop="subjectName">
          <template v-slot="scope">
            <el-tag type="success">{{ scope.row.subjectName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="考试分数" prop="score">
          <template v-slot="scope">
            <div style="color: red;" v-if="scope.row.score >= 60">
              <b>{{ scope.row.score }}</b>
            </div>
            <div style="color: green;" v-else>
              <b>{{ scope.row.score }}</b>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="班级名称"
          prop="classRankName"
        ></el-table-column>
        <el-table-column
          label="考试名称"
          prop="examinationName"
        ></el-table-column>
        <el-table-column label="操作" width="120px">
          <template v-slot="scope">
            <el-tooltip
              effect="dark"
              content="修改分数"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                v-model="scope.row.id"
                @click="showEditDialog(scope.row.id)"
                >修改分数</el-button
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
    <!-- 添加的对话框 -->
    <el-dialog
      title="录入学生成绩"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogclose"
    >
      <el-form
        :model="addScoreForm"
        :rules="addScoreFormRules"
        ref="addScoreFormRef"
        label-width="100px"
      >
        <el-form-item label="学生ID" prop="studentId">
          <el-input v-model.number="addScoreForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="考试ID" prop="examId">
          <el-input v-model.number="addScoreForm.examId"></el-input>
        </el-form-item>
        <el-form-item label="分数" prop="score">
          <el-input v-model.number="addScoreForm.score"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addScore"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 修改的对话框 -->
    <el-dialog
      title="修改学生成绩"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogclose"
    >
      <el-form
        :model="editScoreForm"
        :rules="editScoreFormRules"
        ref="editScoreFormRef"
        label-width="100px"
      >
        <el-form-item label="学生ID">
          <el-input v-model="editScoreForm.studentId" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="editScoreForm.studentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="考试科目">
          <el-input v-model="editScoreForm.subjectName" disabled></el-input>
        </el-form-item>
        <el-form-item label="班级名称">
          <el-input v-model="editScoreForm.classRankName" disabled></el-input>
        </el-form-item>
        <el-form-item label="考试名称">
          <el-input v-model="editScoreForm.examinationName" disabled></el-input>
        </el-form-item>
        <el-form-item label="分数" prop="score">
          <el-input v-model.number="editScoreForm.score"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="updateScore"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    // 自定义总分数验证规则
    var checkNumber = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入id'))
      }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字'))
      } else if (value <= 0) {
        callback(new Error('不能小于0'))
      } else {
        callback()
      }
    }
    return {
      // 分数列表
      scoreList: [],
      // 科目列表
      subjectList: [],
      // 班级列表
      classRankList: [],
      queryInfo: {
        studentName: '',
        subjectId: '',
        classRankId: '',
        score: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addScoreForm: {},
      editScoreForm: {},
      addScoreFormRules: {
        studentId: [
          { required: true, message: '请输入学生id', trigger: 'blur' },
          { validator: checkNumber, trigger: 'blur' }
        ],
        examId: [
          { required: true, message: '请输入考试id', trigger: 'blur' },
          { validator: checkNumber, trigger: 'blur' }
        ],
        score: [
          { required: true, message: '请输入考试分数', trigger: 'blur' },
          { validator: checkNumber, trigger: 'blur' }
        ]
      },
      editScoreFormRules: {
        score: [
          { required: true, message: '请输入考试分数', trigger: 'blur' },
          { validator: checkNumber, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getScoreList()
    this.getSubjectList()
    this.getClassRankList()
  },
  methods: {
    async getScoreList() {
      // console.log(this.queryInfo)
      if (
        this.queryInfo.score !== '' &&
        !Number.isInteger(this.queryInfo.score)
      ) {
        return this.$message.error('分数只能是数字')
      }
      const { data: res } = await this.$http.get('/api/score/query', {
        params: this.queryInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.scoreList = res.data.scores.data
      this.total = res.data.scores.total
    },
    // 查询科目列表
    async getSubjectList() {
      const { data: res } = await this.$http.get('/api/subject/query')
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.subjectList = res.data.subjects
    },
    // 查询班级列表
    async getClassRankList() {
      // console.log(gId)
      const { data: res } = await this.$http.get('/api/classRank/query', {
        params: { pageNum: 1, pageSize: 1000 }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.classRankList = res.data.classRanks.data
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
      this.getScoreList()
    },
    // 关闭添加对话框事件
    addDialogclose() {
      this.$refs.addScoreFormRef.resetFields()
    },
    // 关闭修改对话框事件
    editDialogclose() {
      this.$refs.editScoreFormRef.resetFields()
    },
    // 添加成绩
    addScore() {
      this.$refs.addScoreFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          `/api/score/add?studentId=${this.addScoreForm.studentId}
          &examId=${this.addScoreForm.examId}
          &score=${this.addScoreForm.score}`
        )
        if (res.code !== 200) {
          return this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新列表
        this.getScoreList()
      })
    },
    // 修改按钮
    updateScore() {
      this.$refs.editScoreFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          `/api/score/update?id=${this.editScoreForm.id}
          &score=${this.editScoreForm.score}`
        )
        if (res.code !== 200) {
          return this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.editDialogVisible = false
        // 刷新列表
        this.getScoreList()
      })
    },
    // 展示修改对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      const { data: res } = await this.$http.get('/api/score/queryById', {
        params: { id: id }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.editScoreForm = res.data.score
    }
  }
}
</script>

<style lang="less" scoped></style>
