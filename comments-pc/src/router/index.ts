import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Login from '../views/Login.vue'
import Message from '../views/Message.vue'
import Register from '../views/Register.vue'
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/message",
},
  {
    path: '/login',
    name: 'login',
    component: Login 
  },
  {
    path: '/message',
    name: 'message',
    component: Message 
  },
  {
    path: '/register',
    name: 'register',
    component: Register 
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
