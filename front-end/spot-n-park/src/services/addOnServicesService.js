import axios from 'axios'

const API_URL = 'http://localhost:8080/AddOnServices'

export const addOnServicesService = {
  async getAllAddOnServices() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(service => ({
      ...service,
      id: service.idAddOnService,
      status: service.isActive ? 'active' : 'inactive'
    }))
  },

  async getAddOnServiceById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    const service = response.data
    return {
      ...service,
      id: service.idAddOnService,
      status: service.isActive ? 'active' : 'inactive'
    }
  },

  async createAddOnService(serviceData) {
    const transformedData = {
      name: serviceData.name,
      description: serviceData.description,
      price: serviceData.price,
      isActive: serviceData.status === 'active',
      parkingLot: {
        idParkingLot: serviceData.id_parking_lot
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      id: response.data.idAddOnService,
      status: response.data.isActive ? 'active' : 'inactive'
    }
  },

  async updateAddOnService(id, serviceData) {
    const transformedData = {
      idAddOnService: id,
      name: serviceData.name,
      description: serviceData.description,
      price: serviceData.price,
      isActive: serviceData.status === 'active',
      parkingLot: {
        idParkingLot: serviceData.id_parking_lot
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      id: response.data.idAddOnService,
      status: response.data.isActive ? 'active' : 'inactive'
    }
  },

  async deleteAddOnService(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 