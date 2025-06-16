import axios from 'axios'

const API_URL = 'http://localhost:8080/standard-reservations'

export const reservationsService = {
  async createStandardReservation(reservationData) {
    // Adapt reservationData to backend expected format if needed
    const response = await axios.post(`${API_URL}/save`, reservationData)
    return response.data
  },
  // Puedes agregar aquí métodos para obtener, cancelar, etc.
}
