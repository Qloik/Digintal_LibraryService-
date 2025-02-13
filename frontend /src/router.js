import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import Help from './views/help.vue'
import Welcome from './views/Welcome.vue'
import Single from './views/single.vue'
import Double from './views/double.vue'
import Multi from './views/multi.vue'
import Notice from './views/Notice.vue'
import Bookdetail from './views/bookdetail.vue'
import Bookdetailnew from './views/bookdetail_new.vue'

import show from './views/show.vue'

import personalpage from './views/personalpage.vue'
import myInf from './views/myInf.vue'
import post from './views/post.vue'
import review from './views/review.vue'
import myBorrow from './views/myBorrow.vue'
import myCheck from './views/mycheckbook.vue'
import payFor from './views/payFor.vue'
import placeAppointed from './views/placeAppointed.vue'
import Notice2 from './views/Notice2.vue'

import zhuye from './views/zhuye.vue'
import Siping from './views/bookDistribute/Siping.vue'
import Jiading from './views/bookDistribute/Jiading.vue'
import bookCheck from './views/bookDistribute/bookCheck.vue'
import login from './views/login.vue'
import register from './views/register.vue'


import manager from './views/Manager.vue'
import M_book from './views/Manager_Book.vue'
import M_notice from './views/Manager_Notice.vue'
import M_review from './views/Manager_Review.vue'
import M_post from './views/Manager_Post.vue'

import postDetail from './views/postdetail.vue'
import forum from './views/PostList.vue'

import postlist from './views/PostList.vue'

import homepage from './views/HomePage.vue'

import R_login from './views/ReceptionService/ReceptionLogin.vue'
import Reception from './views/ReceptionService/Reception.vue'
import R_broken from './views/ReceptionService/R_Broken.vue'
import R_Reserve from './views/ReceptionService/R_Reserve.vue'
import R_Finish from './views/ReceptionService/R_Finish.vue'
import R_Blacklist from './views/ReceptionService/R_Blacklist.vue'


const routes = [
  {
    path: '/',
    redirect: '/show',
  },

  {
    path: '/show',
    component: show,
  },

  {
    path: '/rlogin',
    component: R_login,
  },

  {
    path: '/reception',
    component: Reception,
    redirect: '/Broken',
    children: [
      { path: '/Broken', component: R_broken },
      { path: '/Reserve', component: R_Reserve },
      { path: '/Blacklist', component: R_Blacklist },
      { path: '/Finish', component: R_Finish },
    ]
  },

  {
    path: '/HomePage',
    component: homepage,
  },


  {
    path: '/login',
    component: login,
  },

  {
    path: '/register',
    component: register,
  },


  {
    path: '/zhuye',
    component: zhuye,
    redirect: '/book',
    children: [
      // { path: '/book', component: Bookdetail },
      { path: '/book', component: Bookdetailnew },
      { path: '/forum', component: forum },
      { path: '/postlist', component: postlist },
      {
        path: '/Siping',
        component: Siping
      },
      {
        path: '/Jiading',
        component: Jiading
      },
      {
        path: '/bookCheck',
        component: bookCheck
      },
      {
        path: '/postdetail',
        component: postDetail,
      },
      {
        path: '/home',
        component: Home,
        redirect: '/welcome',
        children: [
          { path: '/help', component: Help },
          { path: '/welcome', component: Welcome },
          { path: '/single', component: Single },
          { path: '/double', component: Double },
          { path: '/multi', component: Multi },
        ]
      },
      {
        path: '/personalpage',
        component: personalpage,
        redirect: '/myInf',
        children: [
          { path: '/myInf', component: myInf },
          { path: '/post', component: post },
          { path: '/review', component: review },
          { path: '/myBorrow', component: myBorrow },
          { path: '/myCheck', component: myCheck },
          { path: '/payFor', component: payFor },
          { path: '/placeAppointed', component: placeAppointed },
          { path: '/notice2', component: Notice2 }
        ]
      },
      {
        path: '/notice',
        component: Notice,
      },

      {
        path: '/Manager',
        component: manager,
        redirect: '/BookManage',
        children: [
          { path: '/BookManage', component: M_book },
          { path: '/NoticeManage', component: M_notice },
          { path: '/ReviewManage', component: M_review },
          { path: '/PostManage', component: M_post },
        ]
      }
    ]
  },




]

const router = createRouter({
  routes,
  history: createWebHistory(process.env.BASE_URL),
  scrollBehavior() {
    return { top: 0 }
  }
})


// // 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to 将要访问的路径
//   // from 从哪个路径跳转
//   // next 放行或跳转

//   if (from.path === '/show') next()
//   if (to.path === '/login' || to.path === '/register' || to.path === '/show' || to.path === '/loginAndRegister') return next();

import axios from "axios";
import { ElMessage } from 'element-plus'

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 从哪个路径跳转
  // next 放行或跳转

  // 获取userid
  const useridStr = window.localStorage.getItem('userid');
  if (from.path === '/show') next()
  if (to.path === '/rlogin') return next();
  if (to.path === '/login' || to.path === '/register' || to.path === '/show' || to.path === '/loginAndRegister') return next();

  console.log('userid:',useridStr)

  if (!useridStr) return next('/login')

  const token = localStorage.getItem("token");
  if (token !== null && token !== "") {//是否带有token
    axios.get("/book-info/get").then(//去后端判断这个token是否正确
      response => {
        window.sessionStorage.setItem('userid', localStorage.getItem("userid"))
        next()
      },
      error => {
        ElMessage({
          message: 'token已过期，请重新登录',
          type: 'warning',
        })
        console.log('请求失败了', error.message)
        next("/login")
      })
  } else {
    next("/login")
  }
})

export default router
