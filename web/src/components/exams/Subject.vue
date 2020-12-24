<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>科目管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索输入框 添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            placeholder="请输入科目名称"
            v-model="queryInfo.name"
            clearable
            @clear="getSubjectList()"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getSubjectList()"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button
            class="custom-button"
            type="primary"
            @click="addDialogVisible = true"
            >添加科目</el-button
          >
        </el-col>
      </el-row>
      <!-- 数据列表 -->
      <el-table :data="subjectList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="科目名称" prop="name"></el-table-column>
        <el-table-column label="科目备注" prop="remark"></el-table-column>
        <el-table-column label="是否需要考试">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.exam"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="updateSubject(scope.row, true)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="是否开卷">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.open"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="updateSubject(scope.row, false)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template v-slot="scope">
            <el-tooltip
              effect="dark"
              content="删除科目"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                v-model="scope.row.id"
                @click="removeSubjectById(scope.row.id)"
                >删除科目</el-button
              >
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加的对话框 -->
    <el-dialog
      title="添加科目"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogclose"
    >
      <el-form
        :model="addSubjectForm"
        :rules="addSubjectFormRules"
        ref="addSubjectFormRef"
        label-width="100px"
      >
        <el-form-item label="科目名称" prop="name">
          <el-input v-model="addSubjectForm.name"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="addSubjectForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <!-- 按钮区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button class="custom-button" type="primary" @click="addSubject"
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
      queryInfo: {
        name: ''
      },
      subjectInfo: {
        name: '',
        exam: '',
        open: ''
      },
      subjectList: [],
      addDialogVisible: false,
      addSubjectForm: {
        name: '',
        remark: ''
      },
      addSubjectFormRules: {
        name: [{ required: true, message: '请输入科目名称', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getSubjectList()
  },
  methods: {
    // 查询科目列表
    async getSubjectList() {
      const { data: res } = await this.$http.get('/api/subject/query', {
        params: this.queryInfo
      })
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.subjectList = res.data.subjects
    },
    /**
     * 更新科目
     * @param subjectInfo 科目数据
     * @param examOrOpen 更新是否考试或是否开卷，true-考试，false-开卷
     */
    async updateSubject(subjectInfo, examOrOpen) {
      // console.log(subjectInfo)
      const { data: res } = await this.$http.post('/api/subject/update', {
        id: subjectInfo.id,
        exam: subjectInfo.exam,
        open: subjectInfo.open
      })
      if (res.code !== 200) {
        if (examOrOpen) {
          subjectInfo.exam = !subjectInfo.exam
        } else {
          subjectInfo.open = !subjectInfo.open
        }
        return this.$message.error(res.message)
      }
      return this.$message.success('状态更新成功!')
    },
    addSubject() {
      this.$refs.addSubjectFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return valid
        }
        // 验证成功，发起请求
        const { data: res } = await this.$http.post(
          '/api/subject/add',
          this.addSubjectForm
        )
        if (res.code !== 200) {
          this.$message.error(res.message)
        }
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        // 刷新列表
        this.getSubjectList()
      })
    },
    // 关闭添加对话框事件
    addDialogclose() {
      this.$refs.addSubjectFormRef.resetFields()
    },
    // 根据id删除科目
    async removeSubjectById(id) {
      // console.log(id)
      // 弹框提示
      const confirmResult = await this.$confirm(
        '此操作将永久删除该科目, 是否继续?',
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
        '/api/subject/delete?id=' + id
      )
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.$message.success('删除成功')
      // 刷新列表
      this.getSubjectList()
    }
  }
}
</script>

<style lang="less" scoped></style>
