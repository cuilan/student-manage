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
              ></el-button>
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
              ></el-button>
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
      title="录入考试信息"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogclose"
    >
      <el-form
        :model="addExamForm"
        :rules="addExamFormRules"
        ref="addExamFormRef"
        label-width="100px"
      >
        <el-form-item label="考试名称" prop="name">
          <el-input v-model="addExamForm.name"></el-input>
        </el-form-item>
        <el-form-item label="考试科目" prop="subjectId">
          <el-select
            v-model="addExamForm.subjectId"
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
        </el-form-item>
        <el-form-item label="所属班级" prop="classRankId">
          <el-select v-model="addExamForm.classRankId" placeholder="请选择班级">
            <el-option
              v-for="item in classRankList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            type="datetime"
            placeholder="起始日期时间"
            v-model="addExamForm.startTime"
            value-format="timestamp"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            type="datetime"
            placeholder="起始日期时间"
            v-model="addExamForm.endTime"
            value-format="timestamp"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="总分数" prop="totalScore">
          <el-input v-model.number="addExamForm.totalScore"></el-input>
        </el-form-item>
        <el-form-item label="考试地点" prop="address">
          <el-input v-model="addExamForm.address"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addExamForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addExam"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 修改的对话框 -->
    <el-dialog
      title="录入考试信息"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogclose"
    >
      <el-form
        :model="editExamForm"
        :rules="addExamFormRules"
        ref="editExamFormRef"
        label-width="100px"
      >
        <el-form-item label="考试名称" prop="name">
          <el-input v-model="editExamForm.name"></el-input>
        </el-form-item>
        <el-form-item label="考试科目" prop="subjectId">
          <el-select
            v-model="editExamForm.subjectId"
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
        </el-form-item>
        <el-form-item label="所属班级" prop="classRankId">
          <el-select
            v-model="editExamForm.classRankId"
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
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            type="datetime"
            placeholder="起始日期时间"
            v-model="editExamForm.startTime"
            value-format="timestamp"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            type="datetime"
            placeholder="起始日期时间"
            v-model="editExamForm.endTime"
            value-format="timestamp"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="总分数" prop="totalScore">
          <el-input v-model.number="editExamForm.totalScore"></el-input>
        </el-form-item>
        <el-form-item label="考试地点" prop="address">
          <el-input v-model="editExamForm.address"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="editExamForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="updateExam"
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
    var checkTotalScore = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入考试总分数'))
      }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字'))
      } else if (value <= 0) {
        callback(new Error('总分数不能小于0'))
      } else {
        callback()
      }
    }
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
      subjectList: [],
      // 所有班级列表
      classRankList: [],
      // 控制添加对话框的显示
      addDialogVisible: false,
      editDialogVisible: false,
      addExamForm: {},
      editExamForm: {},
      addExamFormRules: {
        name: [{ required: true, message: '请输入考试名称', trigger: 'blur' }],
        subjectId: [
          { required: true, message: '请选择科目', trigger: 'change' }
        ],
        classRankId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ],
        startTime: [
          { required: true, message: '请选择开始日期时间', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '请选择结束日期时间', trigger: 'blur' }
        ],
        totalScore: [
          { required: true, message: '请输入考试总分数', trigger: 'change' },
          // 自定义验证规则
          { validator: checkTotalScore, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入考试地点', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getExamList()
    this.getSubjectList()
    this.getClassRankList()
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
      this.getExamList()
    },
    // 关闭添加对话框事件
    addDialogclose() {
      this.$refs.addExamFormRef.resetFields()
    },
    // 关闭修改对话框事件
    editDialogclose() {
      this.$refs.editExamFormRef.resetFields()
    },
    // 添加考试
    addExam() {
      this.$refs.addExamFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/exam/add',
          this.addExamForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新列表
        this.getExamList()
      })
    },
    // 展示修改对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      const { data: res } = await this.$http.get('/api/exam/queryById', {
        params: { id: id }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.editExamForm = res.data.exam
    },
    // 修改信息
    updateExam() {
      this.$refs.editExamFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/exam/update',
          this.editExamForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('更新成功!')
        this.editDialogVisible = false
        // 刷新列表
        this.getExamList()
      })
    },
    // 根据id删除
    async removeExamById(id) {
      // console.log(id)
      // 弹框提示
      const confirmResult = await this.$confirm(
        '此操作将永久删除该考试, 是否继续?',
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
      const { data: res } = await this.$http.post('/api/exam/delete?id=' + id)
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('删除成功')
      // 刷新列表
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
