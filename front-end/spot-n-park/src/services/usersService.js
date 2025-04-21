import axios from 'axios'

const API_URL = 'http://localhost:8080/users'

export const usersService = {
  async getAllUsers() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getActiveUsers() {
    const response = await axios.get(`${API_URL}/is-active/true`)
    return response.data
  },

  async getUserById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async createUser(userData) {
    const transformedData = {
      idUser: userData.idUser,
      name: userData.name,
      email: userData.email,
      password: userData.password,
      phone: userData.phone,
      isActive: userData.isActive,
      role: {
        idRole: userData.id_role
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return response.data
  },

  async updateUser(id, userData) {
    const transformedData = {
      idUser: userData.idUser,
      name: userData.name,
      email: userData.email,
      password: userData.password,
      phone: userData.phone,
      isActive: userData.isActive,
      role: {
        idRole: userData.id_role
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return response.data
  },

  async deleteUser(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 