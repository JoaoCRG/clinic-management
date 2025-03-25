import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import PatientsView from '@/views/PatientsView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path:'/patients',
    name:'Patients List',
    component: PatientsView,
  }
]

const router = new VueRouter({
  mode: 'history',
  routes,
})

export default router
