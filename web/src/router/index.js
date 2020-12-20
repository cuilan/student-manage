import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import Users from '../components/system/Users.vue'
import Menus from '../components/system/Menus.vue'
import Roles from '../components/system/Roles.vue'
import Grades from '../components/student/Grades.vue'
import ClassRank from '../components/student/ClassRank.vue'
import StudentQuery from '../components/student/StudentQuery.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/users', component: Users },
        { path: '/menus', component: Menus },
        { path: '/roles', component: Roles },
        { path: '/grades', component: Grades },
        { path: '/class-rank', component: ClassRank },
        { path: '/student-query', component: StudentQuery }
      ]
    }
  ]
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 表示访问的路径
  // from 表示从哪个路径跳转而来
  // next 是一个函数，表示放行。 next() 表示放行，next('/login') 表示强制跳转
  if (to.path === '/login') {
    return next()
  }

  // 获取token，并判断
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) {
    return next('/login')
  }
  // 放行
  next()
})

export default router
