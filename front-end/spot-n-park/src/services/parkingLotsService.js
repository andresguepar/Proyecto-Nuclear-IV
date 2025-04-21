import axios from 'axios'

const API_URL = 'http://localhost:8080/ParkingLots'

export const parkingLotsService = {
  async getAllParkingLots() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getParkingLotById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async createParkingLot(parkingLotData) {
    const response = await axios.post(`${API_URL}/save`, parkingLotData)
    return response.data
  },

  async updateParkingLot(id, parkingLotData) {
    const response = await axios.post(`${API_URL}/save`, parkingLotData)
    return response.data
  },

  async deleteParkingLot(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 