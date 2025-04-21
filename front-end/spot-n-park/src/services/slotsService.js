import axios from 'axios'

const API_URL = 'http://localhost:8080/slots'

export const slotsService = {
  async getAllSlots() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getSlotById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async createSlot(slotData) {
    const transformedData = {
      idSlot: slotData.idSlot,
      name: slotData.name,
      isAvailable: slotData.isAvailable,
      parkingLot: {
        idParkingLot: slotData.parkingLot.id
      },
      vehicleType: {
        idVehicleType: slotData.vehicleType.id
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return response.data
  },

  async updateSlot(id, slotData) {
    const transformedData = {
      idSlot: slotData.idSlot,
      name: slotData.name,
      isAvailable: slotData.isAvailable,
      parkingLot: {
        idParkingLot: slotData.parkingLot.id
      },
      vehicleType: {
        idVehicleType: slotData.vehicleType.id
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return response.data
  },

  async deleteSlot(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  },

  async getSlotsByIsActive(isActive) {
    const response = await axios.get(`${API_URL}/filterByIsActive/${isActive}`)
    return response.data
  },

  async getSlotsByParkingLotAndVehicleType(parkingLotId, vehicleTypeId, isAvailable) {
    const response = await axios.get(`${API_URL}/filterByParkingLotAndVehicleType/${parkingLotId}/${vehicleTypeId}/${isAvailable}`)
    return response.data
  }
} 