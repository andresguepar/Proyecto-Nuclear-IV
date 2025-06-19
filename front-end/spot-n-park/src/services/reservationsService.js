import axios from 'axios'

const API_URL = 'http://localhost:8080/standard-reservations'

export const reservationsService = {
  async createStandardReservation(reservationData) {
    // Adapt reservationData to backend expected format if needed
    const response = await axios.post(`${API_URL}/save`, reservationData)
    return response.data
  },

  async getUserReservations(userId) {
    const response = await axios.get(`${API_URL}/user/${userId}`)
    return response.data
  },

  async confirmReservation(reservationId) {
    const response = await axios.put(`${API_URL}/confirm/${reservationId}`)
    return response.data
  },

  async adminConfirmReservation(reservationId) {
    const response = await axios.put(`${API_URL}/admin-confirm/${reservationId}`)
    return response.data
  },

  async startReservation(reservationId) {
    const response = await axios.put(`${API_URL}/start/${reservationId}`)
    return response.data
  },

  async completeReservation(reservationId) {
    const response = await axios.put(`${API_URL}/complete/${reservationId}`)
    return response.data
  },

  async processPayment(reservationId, paymentMethodId) {
    const response = await axios.put(`${API_URL}/payment/${reservationId}?paymentMethodId=${paymentMethodId}`)
    return response.data
  },

  async cancelReservation(reservationId) {
    const response = await axios.delete(`${API_URL}/delete/${reservationId}`)
    return response.data
  },

  async updateReservation(reservationId, reservationData) {
    const response = await axios.put(`${API_URL}/update/${reservationId}`, reservationData)
    return response.data
  },

  async getParkingLotAdminReservations(adminId) {
    const response = await axios.get(`${API_URL}/parking-lot-admin/${adminId}`)
    return response.data
  },

  async userRequestStart(reservationId) {
    const response = await axios.put(`${API_URL}/user-request-start/${reservationId}`)
    return response.data
  },

  async debugAdminInfo(adminId) {
    const response = await axios.get(`${API_URL}/debug/admin-info/${adminId}`)
    return response.data
  }
}
