import axios from 'axios'

const API_URL = 'http://localhost:8080/schedules'
const DAILY_API_URL = 'http://localhost:8080/DailySchedules'

// Configuración de axios para manejar errores
axios.interceptors.response.use(
  response => response,
  error => {
    console.error('Error en la petición:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

export const schedulesService = {
  // Regular Schedules
  async getAllSchedules() {
    try {
      console.log('Obteniendo todos los horarios...')
      const response = await axios.get(`${API_URL}/get`)
      console.log('Respuesta de horarios:', response.data)
      return response.data
    } catch (error) {
      console.error('Error al obtener horarios:', error)
      throw error
    }
  },

  async getScheduleById(id) {
    try {
      console.log(`Obteniendo horario con ID: ${id}`)
      const response = await axios.get(`${API_URL}/get/${id}`)
      return response.data
    } catch (error) {
      console.error(`Error al obtener horario ${id}:`, error)
      throw error
    }
  },

  async createSchedule(scheduleData) {
    try {
      console.log('Creando nuevo horario:', scheduleData)
      const transformedData = {
        idSchedule: scheduleData.id || null,
        startTime: scheduleData.open_time,
        endTime: scheduleData.close_time,
        dayOfWeek: scheduleData.id_week_day,
        status: scheduleData.status,
        parkingLot: {
          idParkingLot: scheduleData.id_parking_lot
        }
      }
      console.log('Datos transformados:', transformedData)
      const response = await axios.post(`${API_URL}/save`, transformedData)
      console.log('Respuesta de creación:', response.data)
      return response.data
    } catch (error) {
      console.error('Error al crear horario:', error)
      throw error
    }
  },

  async updateSchedule(id, scheduleData) {
    try {
      console.log(`Actualizando horario ${id}:`, scheduleData)
      const transformedData = {
        idSchedule: id,
        startTime: scheduleData.open_time,
        endTime: scheduleData.close_time,
        dayOfWeek: scheduleData.id_week_day,
        status: scheduleData.status,
        parkingLot: {
          idParkingLot: scheduleData.id_parking_lot
        }
      }
      console.log('Datos transformados:', transformedData)
      const response = await axios.post(`${API_URL}/save`, transformedData)
      console.log('Respuesta de actualización:', response.data)
      return response.data
    } catch (error) {
      console.error(`Error al actualizar horario ${id}:`, error)
      throw error
    }
  },

  async deleteSchedule(id) {
    try {
      console.log(`Eliminando horario ${id}`)
      const response = await axios.delete(`${API_URL}/delete/${id}`)
      console.log('Respuesta de eliminación:', response.data)
      return response.data
    } catch (error) {
      console.error(`Error al eliminar horario ${id}:`, error)
      throw error
    }
  },

  async getSchedulesByIsActive(isActive) {
    try {
      console.log(`Obteniendo horarios por estado activo: ${isActive}`)
      const response = await axios.get(`${API_URL}/filterByIsActive/${isActive}`)
      return response.data
    } catch (error) {
      console.error('Error al obtener horarios por estado:', error)
      throw error
    }
  },

  async getSchedulesByParkingLot(parkingLotId) {
    try {
      console.log(`Obteniendo horarios por parking lot: ${parkingLotId}`)
      const response = await axios.get(`${API_URL}/filterByParkingLot/${parkingLotId}`)
      return response.data
    } catch (error) {
      console.error('Error al obtener horarios por parking lot:', error)
      throw error
    }
  },

  // Daily Schedules
  async getAllDailySchedules() {
    try {
      console.log('Obteniendo todos los horarios diarios...')
      const response = await axios.get(`${DAILY_API_URL}/get`)
      console.log('Respuesta de horarios diarios:', response.data)
      return response.data
    } catch (error) {
      console.error('Error al obtener horarios diarios:', error)
      throw error
    }
  },

  async getDailyScheduleById(id) {
    try {
      console.log(`Obteniendo horario diario con ID: ${id}`)
      const response = await axios.get(`${DAILY_API_URL}/get/${id}`)
      return response.data
    } catch (error) {
      console.error(`Error al obtener horario diario ${id}:`, error)
      throw error
    }
  },

  async createDailySchedule(scheduleData) {
    try {
      console.log('Creando nuevo horario diario:', scheduleData)
      const transformedData = {
        idDailySchedule: scheduleData.id || null,
        date: scheduleData.date,
        startTime: scheduleData.open_time,
        endTime: scheduleData.close_time,
        status: scheduleData.status,
        parkingLot: {
          idParkingLot: scheduleData.id_parking_lot
        }
      }
      console.log('Datos transformados:', transformedData)
      const response = await axios.post(`${DAILY_API_URL}/save`, transformedData)
      console.log('Respuesta de creación:', response.data)
      return response.data
    } catch (error) {
      console.error('Error al crear horario diario:', error)
      throw error
    }
  },

  async updateDailySchedule(id, scheduleData) {
    try {
      console.log(`Actualizando horario diario ${id}:`, scheduleData)
      const transformedData = {
        idDailySchedule: id,
        date: scheduleData.date,
        startTime: scheduleData.open_time,
        endTime: scheduleData.close_time,
        status: scheduleData.status,
        parkingLot: {
          idParkingLot: scheduleData.id_parking_lot
        }
      }
      console.log('Datos transformados:', transformedData)
      const response = await axios.post(`${DAILY_API_URL}/save`, transformedData)
      console.log('Respuesta de actualización:', response.data)
      return response.data
    } catch (error) {
      console.error(`Error al actualizar horario diario ${id}:`, error)
      throw error
    }
  },

  async deleteDailySchedule(id) {
    try {
      console.log(`Eliminando horario diario ${id}`)
      const response = await axios.delete(`${DAILY_API_URL}/delete/${id}`)
      console.log('Respuesta de eliminación:', response.data)
      return response.data
    } catch (error) {
      console.error(`Error al eliminar horario diario ${id}:`, error)
      throw error
    }
  },

  async getDailySchedulesByStatus(isActive) {
    try {
      console.log(`Obteniendo horarios diarios por estado activo: ${isActive}`)
      const response = await axios.get(`${DAILY_API_URL}/filter?isActive=${isActive}`)
      return response.data
    } catch (error) {
      console.error('Error al obtener horarios diarios por estado:', error)
      throw error
    }
  },

  async getDailySchedulesByScheduleAndWeekDay(scheduleId, weekDayId, isActive) {
    try {
      console.log(`Obteniendo horarios diarios por schedule ${scheduleId}, weekDay ${weekDayId}, activo: ${isActive}`)
      const response = await axios.get(`${DAILY_API_URL}/filter/by-schedule-weekday?scheduleId=${scheduleId}&weekDayId=${weekDayId}&isActive=${isActive}`)
      return response.data
    } catch (error) {
      console.error('Error al obtener horarios diarios por filtro:', error)
      throw error
    }
  }
} 