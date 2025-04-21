import axios from 'axios'

const API_URL = 'http://localhost:8080/vehicle-types'

export const vehicleTypesService = {
  async getAllVehicleTypes() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getVehicleTypeById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async createVehicleType(vehicleTypeData) {
    const response = await axios.post(`${API_URL}/save`, vehicleTypeData)
    return response.data
  },

  async updateVehicleType(id, vehicleTypeData) {
    const response = await axios.post(`${API_URL}/save`, vehicleTypeData)
    return response.data
  },

  async deleteVehicleType(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 