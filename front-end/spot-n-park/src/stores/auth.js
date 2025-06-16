import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

// Configurar axios para incluir el token en todas las peticiones
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// Interceptor para manejar errores 401 (no autorizado)
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      const authStore = useAuthStore()
      authStore.clearAuth()
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)

  // Restaurar usuario desde localStorage si existe
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    try {
      user.value = JSON.parse(storedUser)
    } catch (e) {
      user.value = null
    }
  }
  const role = ref(localStorage.getItem('role') || null)

  function setAuth(userData) {
    user.value = userData
    localStorage.setItem('user', JSON.stringify(userData))
    role.value = userData?.role?.name || null
    localStorage.setItem('role', role.value)
    console.log('Auth data set:', { user: user.value, role: role.value })
  }

  function clearAuth() {
    user.value = null
    localStorage.removeItem('user')
    role.value = null
    localStorage.removeItem('role')
  }

  async function login(username, password) {
    try {
      const response = await axios.post('http://localhost:8080/users/authenticate', null, {
        params: { username, password }
      })
      console.log('Login response:', response.data)

      if (response.data && response.data.role) {
        setAuth(response.data)
        return true
      } else {
        console.error('Invalid response format:', response.data)
        clearAuth()
        return false
      }
    } catch (e) {
      console.error('Login error:', e)
      clearAuth()
      // Mostrar mensaje específico si es error 401 o 400
      if (e.response && (e.response.status === 401 || e.response.status === 400)) {
        return 'invalid_credentials'
      }
      return false
    }
  }

  function logout() {
    clearAuth()
    // La redirección se manejará en el componente que llama a logout
  }

  function isAuthenticated() {
    return !!role.value
  }

  return { user, role, login, logout, isAuthenticated, setAuth, clearAuth }
})
