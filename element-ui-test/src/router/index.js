import Vue from 'vue'
import Router from 'vue-router'
import Home from "../components/Home";
import Login from "../Login";
import Welcome from "../components/Welcome";
import Setting from "../components/Setting";
import Patients from "../components/Patients";
import PatientDetails from "../components/PatientDetails";
import HeartPredict from "../components/HeartPredict";

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect:'/welcome',
      children: [
        {path: '/welcome', component: Welcome},
        {path: '/setting', component: Setting},
        {path: '/patients', component: Patients},
        {path: '/patientDetails/:patId', component: PatientDetails},
        {path:'/heartPredict',component:HeartPredict},
      ]
    },
    {path: '/', redirect:'/login'},
    {path: '/login', name: 'Login', component: Login},

  ]
})

//挂载路由导航守卫
router.beforeEach((to,from,next) =>{
  //to 将要访问的路径
  //from 代表从哪个路径跳转而来
  //next 是一个函数,表示放行
  //next()放行 next('/login') 强制跳转
  if(to.path == '/login') return next();
  //获取token
  const tokenStr = window.sessionStorage.getItem(`token`)
  if (!tokenStr) return next('/login')
  next()
})

export default router
