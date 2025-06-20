import axios from 'axios'
import { reservationAddOnServicesService } from './reservationAddOnServicesService'

const API_URL = 'http://localhost:8080/standard-reservations'

export const reservationsService = {
  async createStandardReservation(reservationData) {
    // Adapt reservationData to backend expected format if needed
    const response = await axios.post(`${API_URL}/save`, reservationData)
    return response.data
  },

  async getUserReservations(userId) {
    const response = await axios.get(`${API_URL}/user/${userId}`)
    const reservations = response.data
    
    // Fetch add-on services for each reservation
    const reservationsWithServices = await Promise.all(
      reservations.map(async (reservation) => {
        try {
          const addOnServices = await reservationAddOnServicesService.getActiveByReservationId(reservation.idStandardReservation)
          return {
            ...reservation,
            addOnServices
          }
        } catch (error) {
          console.error(`Error fetching add-on services for reservation ${reservation.idStandardReservation}:`, error)
          return {
            ...reservation,
            addOnServices: []
          }
        }
      })
    )
    
    return reservationsWithServices
  },

  async confirmReservation(reservationId) {
    const response = await axios.put(`${API_URL}/confirm/${reservationId}`)
    return response.data
  },

  async adminConfirmReservation(reservationId) {
    const response = await axios.put(`${API_URL}/admin-confirm/${reservationId}`)
    return response.data
  },

  async startReservation(reservationId) {
    const response = await axios.put(`${API_URL}/start/${reservationId}`)
    return response.data
  },

  async completeReservation(reservationId) {
    const response = await axios.put(`${API_URL}/complete/${reservationId}`)
    return response.data
  },

  async processPayment(reservationId, paymentMethodId) {
    const response = await axios.put(`${API_URL}/payment/${reservationId}?paymentMethodId=${paymentMethodId}`)
    return response.data
  },

  async cancelReservation(reservationId) {
    const response = await axios.delete(`${API_URL}/delete/${reservationId}`)
    return response.data
  },

  async updateReservation(reservationId, reservationData) {
    const response = await axios.put(`${API_URL}/update/${reservationId}`, reservationData)
    return response.data
  },

  async getParkingLotAdminReservations(adminId) {
    const response = await axios.get(`${API_URL}/parking-lot-admin/${adminId}`)
    const reservations = response.data
    
    // Fetch add-on services for each reservation
    const reservationsWithServices = await Promise.all(
      reservations.map(async (reservation) => {
        try {
          const addOnServices = await reservationAddOnServicesService.getActiveByReservationId(reservation.idStandardReservation)
          return {
            ...reservation,
            addOnServices
          }
        } catch (error) {
          console.error(`Error fetching add-on services for reservation ${reservation.idStandardReservation}:`, error)
          return {
            ...reservation,
            addOnServices: []
          }
        }
      })
    )
    
    return reservationsWithServices
  },

  async userRequestStart(reservationId) {
    const response = await axios.put(`${API_URL}/user-request-start/${reservationId}`)
    return response.data
  },

  async debugAdminInfo(adminId) {
    const response = await axios.get(`${API_URL}/debug/admin-info/${adminId}`)
    return response.data
  }
}
