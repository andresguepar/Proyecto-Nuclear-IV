import axios from 'axios'

const API_URL = 'http://localhost:8080/ServiceFees'
const ADD_ON_SERVICES_URL = 'http://localhost:8080/AddOnServiceFees'

export const serviceFeesService = {
  async getAllServiceFees() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(fee => ({
      ...fee,
      status: fee.isActive ? 'active' : 'inactive',
      parking_lot_name: fee.parkingLot?.name,
      service_name: fee.service?.name,
      vehicle_type_name: fee.vehicleType?.name,
      add_on_services: fee.addOnServices?.map(addOn => addOn.idAddOnService) || [],
      name: fee.service?.name
    }))
  },

  async getServiceFeeById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    const fee = response.data
    return {
      ...fee,
      status: fee.isActive ? 'active' : 'inactive',
      parking_lot_name: fee.parkingLot?.name,
      service_name: fee.service?.name,
      vehicle_type_name: fee.vehicleType?.name,
      add_on_services: fee.addOnServices?.map(addOn => addOn.idAddOnService) || [],
      name: fee.service?.name
    }
  },

  async createServiceFee(feeData) {
    // First create the add-on services fee
    const addOnServicesFeeData = {
      total: feeData.price,
      isActive: feeData.status === 'active',
      addOnServices: feeData.add_on_services?.map(id => ({
        idAddOnService: id
      })) || []
    }
    
    const addOnServicesResponse = await axios.post(`${ADD_ON_SERVICES_URL}/save`, addOnServicesFeeData)
    
    // Then create the main service fee
    const transformedData = {
      price: feeData.price,
      isActive: feeData.status === 'active',
      parkingLot: {
        idParkingLot: feeData.id_parking_lot
      },
      service: {
        idService: feeData.id_service
      },
      vehicleType: {
        idVehicleType: feeData.id_vehicle_type
      },
      addOnServiceFee: {
        idAddOnServiceFee: addOnServicesResponse.data.idAddOnServiceFee
      }
    }
    
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      status: response.data.isActive ? 'active' : 'inactive',
      parking_lot_name: response.data.parkingLot?.name,
      service_name: response.data.service?.name,
      vehicle_type_name: response.data.vehicleType?.name,
      add_on_services: feeData.add_on_services || [],
      name: response.data.service?.name
    }
  },

  async updateServiceFee(id, feeData) {
    // First update the add-on services fee
    const addOnServicesFeeData = {
      total: feeData.price,
      isActive: feeData.status === 'active',
      addOnServices: feeData.add_on_services?.map(id => ({
        idAddOnService: id
      })) || []
    }
    
    const addOnServicesResponse = await axios.post(`${ADD_ON_SERVICES_URL}/save`, addOnServicesFeeData)
    
    // Then update the main service fee
    const transformedData = {
      idServiceFee: id,
      price: feeData.price,
      isActive: feeData.status === 'active',
      parkingLot: {
        idParkingLot: feeData.id_parking_lot
      },
      service: {
        idService: feeData.id_service
      },
      vehicleType: {
        idVehicleType: feeData.id_vehicle_type
      },
      addOnServiceFee: {
        idAddOnServiceFee: addOnServicesResponse.data.idAddOnServiceFee
      }
    }
    
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      status: response.data.isActive ? 'active' : 'inactive',
      parking_lot_name: response.data.parkingLot?.name,
      service_name: response.data.service?.name,
      vehicle_type_name: response.data.vehicleType?.name,
      add_on_services: feeData.add_on_services || [],
      name: response.data.service?.name
    }
  },

  async deleteServiceFee(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 