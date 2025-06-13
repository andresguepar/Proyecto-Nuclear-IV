import axios from 'axios'

const API_URL = 'http://localhost:8080/vehicle-types'

export const vehicleTypesService = {
  async getAllVehicleTypes() {
    try {
      const response = await axios.get(`${API_URL}/get`)
      console.log('Vehicle types loaded:', response.data)
      return response.data
    } catch (error) {
      console.error('Error loading vehicle types:', error.response?.data || error)
      throw error
    }
  },

  async getVehicleTypeById(id) {
    try {
      const response = await axios.get(`${API_URL}/get/${id}`)
      return response.data
    } catch (error) {
      console.error('Error loading vehicle type:', error.response?.data || error)
      throw error
    }
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