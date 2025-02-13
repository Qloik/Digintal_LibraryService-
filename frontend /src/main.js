import { createApp, VueElement } from 'vue'
import ElementPlus from 'element-plus'
import router from './router'
import 'element-plus/dist/index.css'
import App from './App.vue'
import './all.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from 'axios'

axios.defaults.baseURL = '/api'

const app = createApp(App)

app.use(ElementPlus)
app.use(router)
app.mount('#app')
app.config.globalProperties.$axios=axios;  //配置axios的全局引用

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 每次请求前 有token就带上
axios.interceptors.request.use( config => {
  const token = localStorage.getItem("token");
  if(token !== null && token !== ""){
      config.headers.token = token;
  }
  return config
})


