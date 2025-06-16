import axios from 'axios'

const API_URL = 'http://localhost:8080/AddOnServiceFees'

export const addOnServiceFeesService = {
  async createAddOnServiceFee(data) {
    // data: { total, isActive, addOnServices, standardReservation }
    const response = await axios.post(`${API_URL}/save`, data)
    return response.data
  }
}
