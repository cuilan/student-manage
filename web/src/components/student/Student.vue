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
      <el-row :gutter="24">
        <el-col :span="3">
          <el-input
            placeholder="请输入学生名称"
            v-model="queryInfo.name"
            clearable
          >
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-select v-model="queryInfo.sex" placeholder="请选择性别" clearable>
            <el-option label="男" value="MALE"></el-option>
            <el-option label="女" value="FEMALE"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryInfo.bloodType"
            placeholder="请选择血型"
            clearable
          >
            <el-option label="A型" value="A"></el-option>
            <el-option label="B型" value="B"></el-option>
            <el-option label="AB型" value="AB"></el-option>
            <el-option label="O型" value="O"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-input
            placeholder="请输入民族"
            v-model="queryInfo.nation"
            clearable
          >
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryInfo.gradeId"
            clearable
            placeholder="请选择年级"
            @change="getClassRankList(queryInfo.gradeId)"
          >
            <el-option
              v-for="item in gradeList"
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
        <el-col :span="2">
          <el-button
            class="custom-button"
            type="primary"
            @click="getStudentList"
            >搜索</el-button
          >
        </el-col>
        <el-col :span="2">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >录入学生信息</el-button
          >
        </el-col>
      </el-row>
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
        <el-table-column label="操作" width="180px">
          <template v-slot="scope">
            <el-tooltip
              effect="dark"
              content="修改学生信息"
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
              content="删除学生信息"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-model="scope.row.id"
                @click="removeStudentById(scope.row.id)"
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
      title="录入学生信息"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogclose"
    >
      <el-form
        :model="addStudentForm"
        :rules="addStudentFormRules"
        ref="addStudentFormRef"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addStudentForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="addStudentForm.sex" placeholder="请选择性别">
            <el-option label="男" value="MALE"></el-option>
            <el-option label="女" value="FEMALE"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="addStudentForm.age"></el-input>
        </el-form-item>
        <el-form-item label="血型" prop="bloodType">
          <el-select
            v-model="addStudentForm.bloodType"
            placeholder="请选择血型"
          >
            <el-option label="A型" value="A"></el-option>
            <el-option label="B型" value="B"></el-option>
            <el-option label="AB型" value="AB"></el-option>
            <el-option label="O型" value="O"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-input v-model="addStudentForm.nation"></el-input>
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="addStudentForm.address"></el-input>
        </el-form-item>
        <el-form-item label="所属年级" prop="gradeId">
          <el-select
            v-model="addStudentForm.gradeId"
            placeholder="请选择年级"
            @change="getClassRankList(addStudentForm.gradeId)"
          >
            <el-option
              v-for="item in addGradeList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属班级" prop="classRankId">
          <el-select
            v-model="addStudentForm.classRankId"
            placeholder="请选择班级"
          >
            <el-option
              v-for="item in addClassRankList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addStudent"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 修改的对话框 -->
    <el-dialog
      title="修改学生信息"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogclose"
    >
      <el-form
        :model="editStudentForm"
        :rules="editStudentFormRules"
        ref="editStudentFormRef"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editStudentForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="editStudentForm.sex" placeholder="请选择性别">
            <el-option label="男" value="MALE"></el-option>
            <el-option label="女" value="FEMALE"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="editStudentForm.age"></el-input>
        </el-form-item>
        <el-form-item label="血型" prop="bloodType">
          <el-select
            v-model="editStudentForm.bloodType"
            placeholder="请选择血型"
          >
            <el-option label="A型" value="A"></el-option>
            <el-option label="B型" value="B"></el-option>
            <el-option label="AB型" value="AB"></el-option>
            <el-option label="O型" value="O"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-input v-model="editStudentForm.nation"></el-input>
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="editStudentForm.address"></el-input>
        </el-form-item>
        <el-form-item label="所属年级" prop="gradeId">
          <el-input v-model="editStudentForm.gradeName" disabled></el-input>
        </el-form-item>
        <el-form-item label="所属班级" prop="classRankId">
          <el-input v-model="editStudentForm.classRankName" disabled></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="updateStudent"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    // 自定义年龄验证规则
    var checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入年龄'))
      }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      studentList: [],
      // 获取列表的参数
      queryInfo: {
        name: '',
        sex: '',
        bloodType: '',
        nation: '',
        gradeId: '',
        classRankId: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      // 下拉年级选中的id
      gradeId: '',
      // 所有年级列表
      gradeList: [],
      // 添加对话框中的年级下拉框
      addGradeList: [],
      // 所有班级列表
      classRankList: [],
      // 对话框中根据年级id动态加载班级列表
      addClassRankList: [],
      // 控制添加对话框的显示
      addDialogVisible: false,
      // 控制修改对话框的显示
      editDialogVisible: false,
      // 添加学生表单数据
      addStudentForm: {},
      // 修改学生表单数据
      editStudentForm: {},
      addStudentFormRules: {
        name: [{ required: true, message: '请输入学生姓名', trigger: 'blur' }],
        sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
        age: [
          { required: true, message: '请输入年龄', trigger: 'change' },
          // 自定义验证规则
          { validator: checkAge, trigger: 'blur' }
        ],
        bloodType: [
          { required: true, message: '请选择血型', trigger: 'change' }
        ],
        nation: [{ required: true, message: '请输入民族', trigger: 'blur' }],
        address: [
          { required: true, message: '请输入家庭住址', trigger: 'blur' }
        ],
        gradeId: [{ required: true, message: '请选择年级', trigger: 'change' }],
        classRankId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ]
      },
      editStudentFormRules: {
        name: [{ required: true, message: '请输入学生姓名', trigger: 'blur' }],
        sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
        age: [
          { required: true, message: '请输入年龄', trigger: 'change' },
          // 自定义验证规则
          { validator: checkAge, trigger: 'blur' }
        ],
        bloodType: [
          { required: true, message: '请选择血型', trigger: 'change' }
        ],
        nation: [{ required: true, message: '请输入民族', trigger: 'blur' }],
        address: [
          { required: true, message: '请输入家庭住址', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getStudentList()
    this.getGradeList()
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
    },
    // 查询年级列表
    async getGradeList() {
      const { data: res } = await this.$http.get('/api/grade/query', {
        params: this.gradeInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.gradeList = res.data.grades
      this.addGradeList = res.data.grades
    },
    // 查询班级列表
    async getClassRankList(gId) {
      // console.log(gId)
      const { data: res } = await this.$http.get('/api/classRank/query', {
        params: { gradeId: gId, pageNum: 1, pageSize: 1000 }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.classRankList = res.data.classRanks.data
      this.addClassRankList = res.data.classRanks.data
    },
    // 点击确定，添加学生
    addStudent() {
      this.$refs.addStudentFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/student/add',
          this.addStudentForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新学生列表
        this.getStudentList()
      })
    },
    // 关闭添加对话框事件
    addDialogclose() {
      this.$refs.addStudentFormRef.resetFields()
    },
    // 关闭修改对话框事件
    editDialogclose() {
      this.$refs.editStudentFormRef.resetFields()
    },
    // 展示修改对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      const { data: res } = await this.$http.get('/api/student/queryById', {
        params: { id: id }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.editStudentForm = res.data.student
    },
    // 修改学生信息
    updateStudent() {
      this.$refs.editStudentFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/student/update',
          this.editStudentForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('更新成功!')
        this.editDialogVisible = false
        // 刷新列表
        this.getStudentList()
      })
    },
    // 根据id删除学生
    async removeStudentById(id) {
      // console.log(id)
      // 弹框提示
      const confirmResult = await this.$confirm(
        '此操作将永久删除该学生, 是否继续?',
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
        '/api/student/delete?id=' + id
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('删除成功')
      // 刷新列表
      this.getStudentList()
    }
  }
}
</script>

<style lang="less" scoped></style>
