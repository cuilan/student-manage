<template>
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header>
      <div>
        <img src="../assets/logo.png" alt="" />
        <span>中小学管理系统</span>
      </div>
      <div>
        <el-avatar size="large" :src="this.avatar"></el-avatar>
        <el-button type="info" @click="logout">退出</el-button>
      </div>
    </el-header>
    <!-- 页面主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '13%'">
        <!-- 展开关闭 -->
        <div class="toggle-button" @click="toggleCollapse">
          <i
            :class="
              isCollapse ? 'el-icon-d-arrow-right' : 'el-icon-d-arrow-left'
            "
          ></i>
        </div>
        <!--
          侧边栏菜单
          unique-opened 只允许单个菜单展开
          collapse 折叠
          collapse-transition 关闭折叠动画
          router 开启路由链接
          default-active 动态激活被选中的地址
        -->
        <el-menu
          background-color="#314156"
          text-color="#fff"
          active-text-color="#40b983"
          :unique-opened="true"
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :default-active="activePath"
        >
          <div v-for="item in menuList" :key="item.id">
            <!-- 一级菜单 包含子菜单-->
            <el-submenu
              :index="item.id + ''"
              v-if="item.subMenus && item.subMenus.length > 0"
            >
              <!-- 一级菜单模板区 -->
              <template slot="title">
                <!-- 图标 -->
                <i :class="item.icon"></i>
                <!-- 文本 -->
                <span>{{ item.name }}</span>
              </template>
              <!-- 二级菜单 -->
              <el-menu-item
                :index="subItem.url"
                v-for="subItem in item.subMenus"
                :key="subItem.id"
                @click="saveNavState(subItem.url)"
              >
                <template slot="title">
                  <i :class="subItem.icon"></i>
                  <span>{{ subItem.name }}</span>
                </template>
              </el-menu-item>
            </el-submenu>
            <!-- 没有子菜单 -->
            <el-menu-item
              :index="item.url"
              v-else
              @click="saveNavState(item.url)"
            >
              <i :class="item.icon"></i>
              <span>{{ item.name }}</span>
            </el-menu-item>
          </div>
        </el-menu>
      </el-aside>
      <!-- 右侧主体内容 -->
      <el-main>
        <!-- 路由占位符 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  // 页面初始化时调用
  created() {
    // 获取所有菜单
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('activePath')
    this.avatar = window.sessionStorage.getItem('avatar')
  },
  data() {
    return {
      // 左侧菜单数据
      menuList: [],
      // 是否折叠
      isCollapse: false,
      // 被激活的链接地址
      activePath: '',
      // 头像小图标
      avatar: ''
    }
  },
  methods: {
    logout() {
      // 清除token
      window.sessionStorage.clear()
      // 页面重定向到登录页
      this.$router.push('/login')
    },
    async getMenuList() {
      const { data: res } = await this.$http.get('/api/sysMenu/all')
      // console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.message)
      }
      this.menuList = res.data.menus
    },
    // 点击菜单按钮，折叠展开
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    // 保存链接的激活状态
    saveNavState(activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
  width: 100%;
}
.el-header {
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  padding-left: 2%;
  align-items: center;
  color: #314156;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    > img {
      width: 10%;
      height: 10%;
    }
    > span {
      margin-left: 10px;
    }
    > .el-avatar {
      border: 1px solid #eff2f5;
      margin-right: 10px;
    }
    > .el-button {
      background-color: #314156;
      border-color: #314156;
    }
    > .el-button:hover {
      background-color: #45515f;
      border-color: #45515f;
    }
  }
}
.el-aside {
  background-color: #314156;
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: #eff2f5;
}
.toggle-button {
  background-color: #40b983;
  line-height: 24px;
  color: #ffffff;
  text-align: center;
  cursor: pointer;
}
</style>
