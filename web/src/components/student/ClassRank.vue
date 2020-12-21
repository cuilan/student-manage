<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
      <el-breadcrumb-item>班级管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-select
            v-model="queryInfo.gradeId"
            clearable
            @clear="getClassRankList()"
            placeholder="请选择"
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
        <el-col :span="6">
          <el-input
            placeholder="请输入班级名称"
            v-model="queryInfo.name"
            clearable
            @clear="getClassRankList()"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getClassRankList()"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加班级</el-button
          >
        </el-col>
      </el-row>
      <!-- 数据列表 -->
      <el-table :data="classRankList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="班级名称" prop="name"></el-table-column>
        <el-table-column label="班级人数" prop="studentNum"></el-table-column>
        <el-table-column label="所在年级" prop="gradeName"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template v-slot="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              v-model="scope.row.id"
              @click="showEditDialog(scope.row.id)"
              >修改班级</el-button
            >
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              v-model="scope.row.id"
              @click="removeClassRankById(scope.row.id)"
              >删除班级</el-button
            >
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
    <!-- 添加对话框 -->
    <el-dialog
      title="添加班级"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogclose"
    >
      <el-form
        :model="addClassRankForm"
        :rules="addClassRankFormRules"
        ref="addClassRankFormRef"
        label-width="100px"
      >
        <el-form-item label="班级名称" prop="name">
          <el-input v-model="addClassRankForm.name"></el-input>
        </el-form-item>
        <el-form-item label="所属年级" prop="gradeId">
          <el-select
            v-model="addClassRankForm.gradeId"
            placeholder="请选择年级"
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
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addClassRank"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 修改对话框 -->
    <el-dialog
      title="修改班级"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogclose"
    >
      <el-form
        :model="editClassRankForm"
        :rules="addClassRankFormRules"
        ref="editClassRankFormRef"
        label-width="100px"
      >
        <el-form-item label="班级名称" prop="name">
          <el-input v-model="editClassRankForm.name"></el-input>
        </el-form-item>
        <el-form-item label="所属年级" prop="gradeId">
          <el-select
            v-model="editClassRankForm.gradeId"
            placeholder="请选择年级"
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
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="updateClassRank"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      classRankList: [],
      // 下拉年级选中的id
      gradeId: '',
      // 所有年级列表
      gradeList: [],
      // 添加对话框中的年级下拉框
      addGradeList: [],
      // 获取列表的参数
      queryInfo: {
        name: '',
        gradeId: '',
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      // 添加班级对话框展示
      addDialogVisible: false,
      // 修改班级对话框展示
      editDialogVisible: false,
      // 添加班级表单数据
      addClassRankForm: {
        name: '',
        gradeId: ''
      },
      addClassRankFormRules: {
        name: [{ required: true, message: '请输入班级名称', trigger: 'blur' }],
        gradeId: [
          { required: true, message: '请选择所属年级', trigger: 'change' }
        ]
      },
      // 修改年级表单数据
      editClassRankForm: {
        id: 0,
        name: '',
        gradeId: ''
      }
    }
  },
  created() {
    this.getClassRankList()
    this.getGradeList()
  },
  methods: {
    // 查询年级列表
    async getClassRankList() {
      // console.log(this.queryInfo)
      const { data: res } = await this.$http.get('/api/classRank/query', {
        params: this.queryInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.classRankList = res.data.classRanks.data
      this.total = res.data.classRanks.total
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
    // 添加班级
    addClassRank() {
      this.$refs.addClassRankFormRef.validate(async valid => {
        if (!valid) {
          return valid
        }
        const { data: res } = await this.$http.post(
          '/api/classRank/add',
          this.addClassRankForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新列表
        this.getClassRankList()
      })
    },
    // 添加对话框关闭事件
    addDialogclose() {
      this.$refs.addClassRankFormRef.resetFields()
    },
    // 展示修改对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      const { data: res } = await this.$http.get('/api/classRank/queryById', {
        params: { id: id }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.editClassRankForm = res.data.classRank
    },
    // 修改对话框关闭事件
    editDialogclose() {
      this.$refs.editClassRankFormRef.resetFields()
    },
    updateClassRank() {
      this.$refs.editClassRankFormRef.validate(async valid => {
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/classRank/update',
          this.editClassRankForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('更新成功!')
        this.editDialogVisible = false
        // 刷新列表
        this.getClassRankList()
      })
    },
    async removeClassRankById(id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该班级, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消删除')
        return
      }
      const { data: res } = await this.$http.post('/api/classRank/delete?id=' + id)
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('删除成功')
      // 刷新列表
      this.getGradeList()
    }
  }
}
</script>

<style lang="less" scoped></style>
