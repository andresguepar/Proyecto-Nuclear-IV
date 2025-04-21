import axios from 'axios'

const API_URL = 'http://localhost:8080/Fees'

export const feesService = {
  async getAllFees() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getFeeById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async createFee(feeData) {
    const transformedData = {
      idFee: feeData.idFee,
      name: feeData.name,
      description: feeData.description,
      price: feeData.price,
      status: feeData.status
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return response.data
  },

  async updateFee(id, feeData) {
    const transformedData = {
      idFee: feeData.idFee,
      name: feeData.name,
      description: feeData.description,
      price: feeData.price,
      status: feeData.status
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return response.data
  },

  async deleteFee(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 