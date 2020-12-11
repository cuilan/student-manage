
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

const devHost = 'http://localhost:8888'
// const prodHost = 'http://127.0.0.1'

axios.defaults.baseURL = devHost

// 每个vue对象都可以通过属性访问$http
Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  // 将App实例挂载到路由
  router,
  render: h => h(App)
}).$mount('#app')
