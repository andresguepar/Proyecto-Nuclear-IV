import axios from 'axios'

const API_URL = 'http://localhost:8080/AddOnServiceFees'

export const reservationAddOnServicesService = {
  async getAllReservationAddOnServices() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getReservationAddOnServiceById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async saveReservationAddOnService(data) {
    const response = await axios.post(`${API_URL}/save`, data)
    return response.data
  },

  async saveMultipleForReservation(reservationId, services) {
    const response = await axios.post(`${API_URL}/save-multiple/${reservationId}`, services)
    return response.data
  },

  async getByReservationId(reservationId) {
    const response = await axios.get(`${API_URL}/by-reservation/${reservationId}`)
    return response.data
  },

  async getActiveByReservationId(reservationId) {
    const response = await axios.get(`${API_URL}/active-by-reservation/${reservationId}`)
    return response.data
  },

  async deleteReservationAddOnService(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 