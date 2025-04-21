import axios from 'axios'

const API_URL = 'http://localhost:8080/MonthlyFees'

export const monthlyFeesService = {
  async getAllMonthlyFees() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(fee => ({
      ...fee,
      status: fee.isActive ? 'active' : 'inactive',
      parking_lot_name: fee.parkingLot?.name,
      vehicle_type_name: fee.vehicleType?.name
    }))
  },

  async getMonthlyFeeById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    const fee = response.data
    return {
      ...fee,
      status: fee.isActive ? 'active' : 'inactive',
      parking_lot_name: fee.parkingLot?.name,
      vehicle_type_name: fee.vehicleType?.name
    }
  },

  async createMonthlyFee(feeData) {
    const transformedData = {
      price: feeData.price,
      isActive: feeData.status === 'active',
      parkingLot: {
        idParkingLot: feeData.id_parking_lot
      },
      vehicleType: {
        idVehicleType: feeData.id_vehicle_type
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      status: response.data.isActive ? 'active' : 'inactive',
      parking_lot_name: response.data.parkingLot?.name,
      vehicle_type_name: response.data.vehicleType?.name
    }
  },

  async updateMonthlyFee(id, feeData) {
    const transformedData = {
      idMonthlyFee: id,
      price: feeData.price,
      isActive: feeData.status === 'active',
      parkingLot: {
        idParkingLot: feeData.id_parking_lot
      },
      vehicleType: {
        idVehicleType: feeData.id_vehicle_type
      }
    }
    const response = await axios.post(`${API_URL}/save`, transformedData)
    return {
      ...response.data,
      status: response.data.isActive ? 'active' : 'inactive',
      parking_lot_name: response.data.parkingLot?.name,
      vehicle_type_name: response.data.vehicleType?.name
    }
  },

  async deleteMonthlyFee(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 