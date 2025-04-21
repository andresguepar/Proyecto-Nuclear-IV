import axios from 'axios'

const API_URL = 'http://localhost:8080/services'

export const servicesService = {
  async getAllServices() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(service => ({
      ...service,
      id: service.idService,
      status: service.isActive ? 'active' : 'inactive',
      parking_lot_name: service.parkingLot?.name || '',
      vehicle_type_name: service.vehicleType?.name || ''
    }))
  },

  async getServiceById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    const service = response.data
    return {
      ...service,
      id: service.idService,
      status: service.isActive ? 'active' : 'inactive',
      parking_lot_name: service.parkingLot?.name || '',
      vehicle_type_name: service.vehicleType?.name || ''
    }
  },

  async createService(serviceData) {
    const transformedData = {
      name: serviceData.name,
      description: serviceData.description,
      isActive: serviceData.status === 'active',
      idParkingLot: serviceData.id_parking_lot,
      idVehicleType: serviceData.id_vehicle_type,
      price: serviceData.price
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      id: response.data.idService,
      status: response.data.isActive ? 'active' : 'inactive',
      parking_lot_name: response.data.parkingLot?.name || '',
      vehicle_type_name: response.data.vehicleType?.name || ''
    }
  },

  async updateService(id, serviceData) {
    const transformedData = {
      idService: id,
      name: serviceData.name,
      description: serviceData.description,
      isActive: serviceData.status === 'active',
      idParkingLot: serviceData.id_parking_lot,
      idVehicleType: serviceData.id_vehicle_type,
      price: serviceData.price
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      id: response.data.idService,
      status: response.data.isActive ? 'active' : 'inactive',
      parking_lot_name: response.data.parkingLot?.name || '',
      vehicle_type_name: response.data.vehicleType?.name || ''
    }
  },

  async deleteService(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 