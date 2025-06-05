import {createRouter, createWebHashHistory} from 'vue-router'
import Style from '@/views/StyleView.vue'
import Home from '@/views/HomeView.vue'

const routes = [
 /* {
    meta: {
      title: 'Select style',
    },
    path: '/',
    name: 'style',
    component: Style,
  },*/
  {
        // Document title tag
    // We combine it with defaultDocumentTitle set in `src/main.js` on router.afterEach hook
    meta: {
      title: 'Home',
    },
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    meta: {
      title: 'Slots',
    },
    path: '/slots',
    name: 'slots',
    component: () => import('@/views/SlotsView.vue'),
  },
  {
    meta: {
      title: 'Reservations',
    },
    path: '/reservations',
    name: 'reservations',
    component: () => import('@/views/ReservationsView.vue'),
  },
  {
    meta: {
      title: 'Dashboard',
    },
    path: '/dashboard',
    name: 'dashboard',
    component: () => import("@/views/DashboardView.vue"),
  },
  {
    meta: {
      title: 'Parking Lots',
    },
    path: '/parking-lots',
    name: 'parking-lots',
    component: () => import('@/views/ParkingLotsView.vue'),
  },
  
  {
    meta: {
      title: 'Schedules',
    },
    path: '/schedules',
    name: 'schedules',
    component: () => import('@/views/SchedulesView.vue'),
  },
  {
    meta: {
      title: 'Fees',
    },
    path: '/fees',
    name: 'fees',
    component: () => import('@/views/FeesView.vue'),
  },
  {
    meta: {
      title: 'Users',
    },
    path: '/users',
    name: 'users',
    component: () => import('@/views/UsersView.vue'),
  },
  {
    meta: {
      title: 'Tables',
    },
    path: '/tables',
    name: 'tables',
    component: () => import('@/views/TablesView.vue'),
  },
  {
    meta: {
      title: 'Forms',
    },
    path: '/forms',
    name: 'forms',
    component: () => import('@/views/FormsView.vue'),
  },
  {
    meta: {
      title: 'Profile',
    },
    path: '/profile',
    name: 'profile',
    component: () => import('@/views/ProfileView.vue'),
  },
  {
    meta: {
      title: 'Ui',
    },
    path: '/ui',
    name: 'ui',
    component: () => import('@/views/UiView.vue'),
  },
  {
    meta: {
      title: 'Responsive layout',
    },
    path: '/responsive',
    name: 'responsive',
    component: () => import('@/views/ResponsiveView.vue'),
  },
  {
    meta: {
      title: 'Login',
    },
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
  },
  {
    meta: {
      title: 'Error',
    },
    path: '/error',
    name: 'error',
    component: () => import('@/views/ErrorView.vue'),
  },
  {
    meta: {
      title: 'Recuperar contraseña',
    },
    path: '/forgot-password',
    name: 'forgot-password',
    component: () => import('@/views/ForgotPasswordView.vue'),
  },
  {
    meta: {
      title: 'Registro de usuario',
    },
    path: '/register',
    name: 'register',
    component: () => import('@/views/RegisterView.vue'),
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 }
  },
})

export default router
