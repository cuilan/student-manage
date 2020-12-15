<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>查询菜单</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 添加按钮 -->
      <el-row>
        <el-col>
          <el-button class="custom-button" type="primary"
            >添加一级菜单</el-button
          >
        </el-col>
      </el-row>
      <el-table
        :data="menuList"
        border
        stripe
        row-key="id"
        default-expand-all
        :tree-props="{ children: 'sysMenus', hasChildren: 'parentId !== 0' }"
      >
        <el-table-column label="ID" prop="id"></el-table-column>
        <el-table-column label="菜单名称">
          <template v-slot="scope">
            <span v-if="scope.row.parentId === 0"
              ><b>{{ scope.row.name }}</b></span
            >
            <span v-else>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="url" prop="url"></el-table-column>
        <el-table-column label="icon" prop="icon"></el-table-column>
        <el-table-column label="父ID" prop="parentId"></el-table-column>
        <el-table-column label="菜单等级">
          <template v-slot="scope">
            <el-tag v-if="scope.row.parentId === 0" type="success"
              >一级菜单</el-tag
            >
            <el-tag v-else type="info">二级菜单</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="优先级" prop="priority"></el-table-column>
        <el-table-column label="是否可用">
          <!-- 作用域插槽 -->
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.visible"
              active-color="#13ce66"
              inactive-color="#ff4949"
            >
            </el-switch>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 菜单集合
      menuList: []
    }
  },
  created() {
    this.getMenuList()
  },
  methods: {
    async getMenuList() {
      const { data: res } = await this.$http.get('/api/sysMenu/all')
      if (res.code !== 200) {
        return this.$message.console.error(res.message)
      }
      // console.log(res.data.menus)
      this.menuList = res.data.menus
    }
  }
}
</script>

<style lang="less" scoped></style>
