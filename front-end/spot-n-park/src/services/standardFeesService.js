import axios from 'axios'

const API_URL = 'http://localhost:8080/standard-fees'

export const standardFeesService = {
  async getAllStandardFees() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(fee => ({
      ...fee,
      status: fee.isActive ? 'active' : 'inactive',
      price_x_hours: fee.priceForHours,
      price_x_12_hours: fee.priceForTwelveHours,
      parking_lot_name: fee.parkingLot?.name,
      vehicle_type_name: fee.vehicleType?.name
    }))
  },

  async getStandardFeeById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    const fee = response.data
    return {
      ...fee,
      status: fee.isActive ? 'active' : 'inactive',
      price_x_hours: fee.priceForHours,
      price_x_12_hours: fee.priceForTwelveHours,
      parking_lot_name: fee.parkingLot?.name,
      vehicle_type_name: fee.vehicleType?.name
    }
  },

  async createStandardFee(feeData) {
    const transformedData = {
      priceForHours: feeData.price_x_hours,
      priceForTwelveHours: feeData.price_x_12_hours,
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
      price_x_hours: response.data.priceForHours,
      price_x_12_hours: response.data.priceForTwelveHours,
      parking_lot_name: response.data.parkingLot?.name,
      vehicle_type_name: response.data.vehicleType?.name
    }
  },

  async updateStandardFee(id, feeData) {
    const transformedData = {
      idStandardFee: id,
      priceForHours: feeData.price_x_hours,
      priceForTwelveHours: feeData.price_x_12_hours,
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
      price_x_hours: response.data.priceForHours,
      price_x_12_hours: response.data.priceForTwelveHours,
      parking_lot_name: response.data.parkingLot?.name,
      vehicle_type_name: response.data.vehicleType?.name
    }
  },

  async deleteStandardFee(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 