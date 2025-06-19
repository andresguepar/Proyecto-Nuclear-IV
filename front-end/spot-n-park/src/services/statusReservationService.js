import axios from 'axios'

const API_URL = 'http://localhost:8080/status-reservations'

export const statusReservationService = {
  async getAllStatusReservations() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getStatusReservationsDebug() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getStatusReservationById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  }
} 