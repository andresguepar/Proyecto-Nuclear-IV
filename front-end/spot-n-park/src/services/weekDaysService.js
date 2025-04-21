import axios from 'axios'

const API_URL = 'http://localhost:8080/weekdays'

export const weekDaysService = {
  async getAllWeekDays() {
    const response = await axios.get(`${API_URL}/get`)
    return response.data
  },

  async getWeekDayById(id) {
    const response = await axios.get(`${API_URL}/get/${id}`)
    return response.data
  },

  async createWeekDay(weekDayData) {
    const response = await axios.post(`${API_URL}/save`, weekDayData)
    return response.data
  },

  async updateWeekDay(id, weekDayData) {
    const response = await axios.post(`${API_URL}/save`, weekDayData)
    return response.data
  },

  async deleteWeekDay(id) {
    const response = await axios.delete(`${API_URL}/delete/${id}`)
    return response.data
  }
} 