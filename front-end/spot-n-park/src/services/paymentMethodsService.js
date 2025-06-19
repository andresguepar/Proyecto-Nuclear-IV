import axios from 'axios'

const API_URL = 'http://localhost:8080/PaymentMethods'

export const paymentMethodsService = {
  async getAllPaymentMethods() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getPaymentMethodById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async getActivePaymentMethods() {
    const response = await axios.get(`${API_URL}/filterByStatus?isActive=true`)
    return response.data
  }
} 