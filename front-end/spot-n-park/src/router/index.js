import {createRouter, createWebHashHistory} from 'vue-router'
import Home from '@/views/HomeView.vue'
import { useAuthStore } from '@/stores/auth.js'
import { createPinia } from 'pinia'

const pinia = createPinia()
const authStore = useAuthStore(pinia)

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
    meta: {
      title: 'Home',
      requiresAuth: true
    },
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    meta: {
      title: 'Slots',
      requiresAuth: true,
      roles: ['park_admin']
    },
    path: '/slots',
    name: 'slots',
    component: () => import('@/views/SlotsView.vue'),
  },
  {
    meta: {
      title: 'Reservations',
      requiresAuth: true
    },
    path: '/reservations',
    name: 'reservations',
    component: () => import('@/views/ReservationsView.vue'),
  },
  {
    meta: {
      title: 'Dashboard',
      requiresAuth: true,
      roles: ['park_admin']
    },
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('@/views/DashboardView.vue'),
  },
  {
    meta: {
      title: 'Parking Lots',
      requiresAuth: true,
      roles: ['super_admin']
    },
    path: '/parking-lots',
    name: 'parking-lots',
    component: () => import('@/views/ParkingLotsView.vue'),
  },
  {
    meta: {
      title: 'Schedules',
      requiresAuth: true,
      roles: ['park_admin']
    },
    path: '/schedules',
    name: 'schedules',
    component: () => import('@/views/SchedulesView.vue'),
  },
  {
    meta: {
      title: 'Fees',
      requiresAuth: true,
      roles: ['park_admin']
    },
    path: '/fees',
    name: 'fees',
    component: () => import('@/views/FeesView.vue'),
  },
  {
    meta: {
      title: 'Users',
      requiresAuth: true,
      roles: ['super_admin']
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
      requiresAuth: true
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
      requiresAuth: false
    },
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
  },
  {
    meta: {
      title: 'Error',
      requiresAuth: false
    },
    path: '/error',
    name: 'error',
    component: () => import('@/views/ErrorView.vue'),
  },
  {
    meta: {
      title: 'Recuperar contraseña',
      requiresAuth: false
    },
    path: '/forgot-password',
    name: 'forgot-password',
    component: () => import('@/views/ForgotPasswordView.vue'),
  },
  {
    meta: {
      title: 'Registro de usuario',
      requiresAuth: false
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

router.beforeEach((to, from, next) => {
  const isAuthenticated = authStore.isAuthenticated()
  const role = authStore.role

  // Si la ruta requiere autenticación
  if (to.meta.requiresAuth) {
    // Si no está autenticado, redirige a login
    if (!isAuthenticated) {
      console.log('Not authenticated, redirecting to login')
      return next('/login')
    }

    // Si la ruta requiere roles específicos
    if (to.meta.roles && !to.meta.roles.includes(role)) {
      // Protección extra para /parking-lots
      if (to.path === '/parking-lots') {
        console.log('Intento de acceso no autorizado a /parking-lots, redirigiendo a Home')
        return next('/')
      }
      console.log('Role not authorized, redirecting to home')
      return next('/')
    }
  }

  // Si está autenticado y trata de acceder a login/register, redirige a home
  if (isAuthenticated && ['/login', '/register', '/forgot-password'].includes(to.path)) {
    console.log('Already authenticated, redirecting to home')
    return next('/')
  }

  console.log('Navigation allowed:', { to: to.path, isAuthenticated, role })
  next()
})

export default router
