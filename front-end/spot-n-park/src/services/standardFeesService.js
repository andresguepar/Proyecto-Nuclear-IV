import axios from 'axios'

const API_URL = 'http://localhost:8080/standard-fees'

export const standardFeesService = {
  async getAllStandardFees() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(fee => ({
      ...fee,
      id: fee.idStandardFee,
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
    console.log('Raw fee data received:', feeData)
    const transformedData = {
      priceForHours: parseFloat(feeData.price_x_hours),
      priceForTwelveHours: parseFloat(feeData.price_x_12_hours),
      isActive: feeData.status === 'active',
      parkingLot: {
        idParkingLot: parseInt(feeData.id_parking_lot)
      },
      vehicleType: {
        idVehicleType: parseInt(feeData.id_vehicle_type)
      }
    }
    console.log('Sending transformed data to backend:', JSON.stringify(transformedData, null, 2))
    try {
      const response = await axios.post(`${API_URL}/save`, transformedData)
      console.log('Backend response:', response.data)
      return {
        ...response.data,
        status: response.data.isActive ? 'active' : 'inactive',
        price_x_hours: response.data.priceForHours,
        price_x_12_hours: response.data.priceForTwelveHours,
        parking_lot_name: response.data.parkingLot?.name,
        vehicle_type_name: response.data.vehicleType?.name
      }
    } catch (error) {
      console.error('Error creating standard fee:', {
        status: error.response?.status,
        statusText: error.response?.statusText,
        data: error.response?.data,
        headers: error.response?.headers,
        config: {
          url: error.config?.url,
          method: error.config?.method,
          data: error.config?.data
        }
      })
      throw error
    }
  },

  async updateStandardFee(id, feeData) {
    console.log('Transforming standard fee update data:', feeData)
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
    console.log('Sending transformed update data to backend:', transformedData)
    try {
      const response = await axios.post(`${API_URL}/save`, transformedData)
      console.log('Backend update response:', response.data)
      return {
        ...response.data,
        status: response.data.isActive ? 'active' : 'inactive',
        price_x_hours: response.data.priceForHours,
        price_x_12_hours: response.data.priceForTwelveHours,
        parking_lot_name: response.data.parkingLot?.name,
        vehicle_type_name: response.data.vehicleType?.name
      }
    } catch (error) {
      console.error('Error updating standard fee:', error.response?.data || error)
      throw error
    }
  },

  async deleteStandardFee(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 