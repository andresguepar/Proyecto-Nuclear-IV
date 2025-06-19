import axios from 'axios'

const API_URL = 'http://localhost:8080/MonthlyFees'

export const monthlyFeesService = {
  async getAllMonthlyFees() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data.map(fee => ({
      ...fee,
      id: fee.idMonthlyFee,
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
    console.log('Raw fee data received:', feeData)
    const transformedData = {
      price: parseFloat(feeData.price),
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
        parking_lot_name: response.data.parkingLot?.name,
        vehicle_type_name: response.data.vehicleType?.name
      }
    } catch (error) {
      console.error('Error creating monthly fee:', {
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

  async updateMonthlyFee(id, feeData) {
    console.log('Transforming monthly fee update data:', feeData)
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
    console.log('Sending transformed update data to backend:', transformedData)
    try {
      const response = await axios.post(`${API_URL}/save`, transformedData)
      console.log('Backend update response:', response.data)
      return {
        ...response.data,
        status: response.data.isActive ? 'active' : 'inactive',
        parking_lot_name: response.data.parkingLot?.name,
        vehicle_type_name: response.data.vehicleType?.name
      }
    } catch (error) {
      console.error('Error updating monthly fee:', error.response?.data || error)
      throw error
    }
  },

  async deleteMonthlyFee(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 