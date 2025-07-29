import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import PatientsView from '@/views/PatientsView.vue'
import CalendarView from '@/views/CalendarView.vue'
import AddAppointmentModal from '@/components/AddAppointmentModal.vue'

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
  },
  {
    path:'/calendar',
    name:'Calendar',
    component: CalendarView,
  },
  {
    path: '/add-appointment',
    name: 'AddAppointmentModal',
    component: AddAppointmentModal,
  },
]

const router = new VueRouter({
  mode: 'history',
  routes,
})

export default router
