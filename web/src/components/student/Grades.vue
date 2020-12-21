<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
      <el-breadcrumb-item>年级管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            placeholder="请输入年级名称"
            v-model="gradeInfo.name"
            clearable
            @clear="getGradeList()"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getGradeList()"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加年级</el-button
          >
        </el-col>
      </el-row>
      <!-- 数据列表 -->
      <el-table :data="gradeList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="年级名称" prop="name"></el-table-column>
        <el-table-column
          label="创建时间"
          prop="createDateTime"
        ></el-table-column>
        <el-table-column
          label="修改时间"
          prop="upateDateTime"
        ></el-table-column>
        <el-table-column label="操作" width="300px">
          <template v-slot="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              v-model="scope.row.id"
              @click="showEditDialog(scope.row.id)"
              >修改年级名称</el-button
            >
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              v-model="scope.row.id"
              @click="removeGradeById(scope.row.id)"
              >删除年级</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加对话框 -->
    <el-dialog
      title="添加年级"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogclose"
    >
      <el-form
        :model="addGradeForm"
        :rules="addGradeFormRules"
        ref="addGradeFormRef"
        label-width="100px"
      >
        <el-form-item label="年级名称" prop="name">
          <el-input v-model="addGradeForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addGrade"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 修改对话框 -->
    <el-dialog
      title="修改年级"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogclose"
    >
      <el-form
        :model="editGradeForm"
        :rules="addGradeFormRules"
        ref="editGradeFormRef"
        label-width="100px"
      >
        <el-form-item label="年级名称" prop="name">
          <el-input v-model="editGradeForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="updateGrade"
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
      gradeInfo: {
        name: ''
      },
      gradeList: [],
      // 添加班级对话框展示
      addDialogVisible: false,
      // 修改班级对话框展示
      editDialogVisible: false,
      // 添加年级表单数据
      addGradeForm: {
        name: ''
      },
      addGradeFormRules: {
        name: [{ required: true, message: '请输入年级名称', trigger: 'blur' }]
      },
      // 修改年级表单数据
      editGradeForm: {
        id: 0,
        name: ''
      }
    }
  },
  created() {
    this.getGradeList()
  },
  methods: {
    // 查询年级列表
    async getGradeList() {
      const { data: res } = await this.$http.get('/api/grade/query', {
        params: this.gradeInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.gradeList = res.data.grades
    },
    // 添加年级
    addGrade() {
      this.$refs.addGradeFormRef.validate(async valid => {
        if (!valid) {
          return valid
        }
        const { data: res } = await this.$http.post(
          '/api/grade/add',
          this.addGradeForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新列表
        this.getGradeList()
      })
    },
    // 添加对话框关闭事件
    addDialogclose() {
      this.$refs.addGradeFormRef.resetFields()
    },
    // 展示修改对话框
    async showEditDialog(id) {
      this.editDialogVisible = true
      const { data: res } = await this.$http.get('/api/grade/queryById', {
        params: { id: id }
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.editGradeForm = res.data.grade
    },
    // 修改对话框关闭事件
    editDialogclose() {
      this.$refs.editGradeFormRef.resetFields()
    },
    updateGrade() {
      this.$refs.editGradeFormRef.validate(async valid => {
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/grade/update',
          this.editGradeForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('更新成功!')
        this.editDialogVisible = false
        // 刷新列表
        this.getGradeList()
      })
    },
    async removeGradeById(id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该年级, 是否继续?',
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
      const { data: res } = await this.$http.post(
        '/api/grade/delete?id=' + id
      )
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
