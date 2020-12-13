
// 导入vue组件
import Vue from 'vue'
// 导入App.vue
import App from './App.vue'
// 导入路由
import router from './router'
// 导入element.js组件
import './plugins/element.js'
// 导入全局样式表
import './assets/css/global.css'
// 导入axios请求包
import axios from 'axios'

// const devHost = 'http://localhost:8080'
const prodHost = 'http://10.10.5.100:8080'

axios.defaults.baseURL = prodHost

// axios 请求拦截器
axios.interceptors.request.use(config => {
  // console.log(config)
  // 为所有请求添加请求头token
  config.headers.token = window.sessionStorage.getItem('token')
  return config
})

// 每个vue对象都可以通过属性访问$http
Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  // 将App实例挂载到路由
  router,
  render: h => h(App)
}).$mount('#app')
