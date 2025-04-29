import axios from 'axios'

const API_URL = 'http://localhost:8080/weekdays'

export const weekDaysService = {
  async getAllWeekDays() {
    try {
      const response = await axios.get(`${API_URL}/get`)
      console.log('Raw week days response:', response.data) // Debug log
      return response.data.map(day => ({
        idWeekDay: day.idWeekDay,
        name: day.name,
        isActive: day.isActive
      }))
    } catch (error) {
      console.error('Error fetching week days:', error)
      throw error
    }
  },

  async getWeekDayById(id) {
    try {
      const response = await axios.get(`${API_URL}/get/${id}`)
      return {
        idWeekDay: response.data.idWeekDay,
        name: response.data.name,
        isActive: response.data.isActive
      }
    } catch (error) {
      console.error('Error fetching week day:', error)
      throw error
    }
  },

  async createWeekDay(weekDayData) {
    try {
      const response = await axios.post(`${API_URL}/save`, weekDayData)
      return {
        idWeekDay: response.data.idWeekDay,
        name: response.data.name,
        isActive: response.data.isActive
      }
    } catch (error) {
      console.error('Error creating week day:', error)
      throw error
    }
  },

  async updateWeekDay(id, weekDayData) {
    try {
      const response = await axios.post(`${API_URL}/save`, {
        idWeekDay: id,
        ...weekDayData
      })
      return {
        idWeekDay: response.data.idWeekDay,
        name: response.data.name,
        isActive: response.data.isActive
      }
    } catch (error) {
      console.error('Error updating week day:', error)
      throw error
    }
  },

  async deleteWeekDay(id) {
    try {
      const response = await axios.delete(`${API_URL}/delete/${id}`)
      return response.data
    } catch (error) {
      console.error('Error deleting week day:', error)
      throw error
    }
  }
} 