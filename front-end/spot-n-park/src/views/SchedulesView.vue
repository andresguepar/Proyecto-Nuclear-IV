<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiCalendar" title="Schedules" main />

      <!-- Mensajes de error y éxito -->
      <div v-if="errorMessage" class="mb-4 p-4 bg-red-100 text-red-700 rounded">
        {{ errorMessage }}
      </div>
      <div v-if="successMessage" class="mb-4 p-4 bg-green-100 text-green-700 rounded">
        {{ successMessage }}
      </div>

      <!-- Schedule Content -->
      <div class="flex flex-col md:flex-row gap-4">
        <!-- Table Section -->
        <div class="w-full md:w-2/3">
          <CardBox has-table>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Parking Lot</th>
                  <th>Day</th>
                  <th>Open Time</th>
                  <th>Close Time</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                <tr v-if="combinedSchedules.length === 0">
                  <td colspan="6" class="text-center py-24 text-gray-500 dark:text-slate-400">
                    <p>No schedules found</p>
                  </td>
                </tr>
                <tr v-for="schedule in combinedSchedules" :key="schedule.idSchedule + '-' + schedule.dayOfWeek">
                  <td data-label="ID">{{ schedule.idDailySchedule || schedule.idSchedule }}</td>
                  <td data-label="Parking Lot">{{ schedule.parkingLot?.name }}</td>
                  <td data-label="Day">{{ getWeekDayName(schedule.dayOfWeek) }}</td>
                  <td data-label="Open Time">{{ formatTime(schedule.startTime) }}</td>
                  <td data-label="Close Time">{{ formatTime(schedule.endTime) }}</td>
                  <td class="before:hidden lg:w-1 whitespace-nowrap">
                    <BaseButtons type="justify-start lg:justify-end" no-wrap>
                      <BaseButton
                        color="info"
                        :icon="mdiPencil"
                        small
                        @click="editSchedule(schedule)"
                      />
                      <BaseButton
                        color="danger"
                        :icon="mdiClose"
                        small
                        @click="deactivateSchedule(schedule.idDailySchedule || schedule.idSchedule)"
                      />
                    </BaseButtons>
                  </td>
                </tr>
              </tbody>
            </table>
          </CardBox>
        </div>

        <!-- Form Section -->
        <div class="w-full md:w-1/3">
          <CardBox>
            <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Schedule' : 'Create New Schedule' }}</h2>
            <form @submit.prevent="handleScheduleSubmit">
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Parking Lot</label>
                <select
                  v-model="form.parkingLot.id"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="">Select a parking lot</option>
                  <option v-for="lot in parkingLots" :key="lot.idParkingLot" :value="lot.idParkingLot">
                    {{ lot.name }}
                  </option>
                </select>
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Day</label>
                <select
                  v-model="form.dayOfWeek"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="">Select a day</option>
                  <option v-for="day in weekDays" :key="day.idWeekDay" :value="day.idWeekDay">
                    {{ day.name }}
                  </option>
                </select>
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Open Time</label>
                <input
                  v-model="form.startTime"
                  type="time"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Close Time</label>
                <input
                  v-model="form.endTime"
                  type="time"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="flex justify-end">
                <button
                  type="submit"
                  class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 dark:bg-blue-500 dark:hover:bg-blue-600"
                >
                  {{ isEditing ? 'Update' : 'Create' }}
                </button>
              </div>
            </form>
          </CardBox>
        </div>
      </div>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiCalendar, mdiPencil, mdiClose } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import { parkingLotsService } from '@/services/parkingLotsService'
import { weekDaysService } from '@/services/weekDaysService'
import { schedulesService } from '@/services/schedulesService'

const schedules = ref([])
const dailySchedules = ref([])
const combinedSchedules = ref([])
const parkingLots = ref([])
const weekDays = ref([])
const isEditing = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const form = ref({
  idSchedule: null,
  idDailySchedule: null,
  parkingLot: { id: '' },
  dayOfWeek: '',
  startTime: '',
  endTime: '',
  status: 'true'
})

const formatTime = (time) => {
  if (!time) return ''

  // Si ya es un string en formato HH:mm, lo retornamos directamente
  if (typeof time === 'string' && time.includes(':')) {
    // Si tiene segundos, los removemos
    return time.split(':').slice(0, 2).join(':')
  }

  // Si es un timestamp o Date, extraemos solo la hora
  try {
    const date = new Date(time)
    return date.toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit',
      hour12: false // Formato 24 horas
    })
  } catch (e) {
    console.error('Error formatting time:', e)
    return time
  }
}

const getWeekDayName = (dayId) => {
  const day = weekDays.value.find(d => d.idWeekDay === dayId)
  return day ? day.name : 'Unknown'
}



const editSchedule = (schedule) => {
  isEditing.value = true
  form.value = {
    idSchedule: schedule.idSchedule,
    idDailySchedule: schedule.idDailySchedule,
    parkingLot: { id: schedule.parkingLot?.idParkingLot },
    dayOfWeek: schedule.dayOfWeek,
    startTime: formatTime(schedule.startTime),
    endTime: formatTime(schedule.endTime),
    status: schedule.status ? 'true' : 'false'
  }
}

const handleScheduleSubmit = async () => {
  try {
    const scheduleData = {
      id: form.value.idSchedule,
      idDailySchedule: form.value.idDailySchedule,
      id_parking_lot: form.value.parkingLot.id,
      id_week_day: form.value.dayOfWeek,
      open_time: form.value.startTime,
      close_time: form.value.endTime,
      status: true
    }

    if (isEditing.value) {
      await schedulesService.updateSchedule(form.value.idSchedule, scheduleData)
      successMessage.value = 'Schedule updated successfully'
    } else {
      const createdSchedule = await schedulesService.createSchedule(scheduleData)

      // Create daily schedule
      const dailyScheduleData = {
        id_schedule: createdSchedule.idSchedule,
        id_parking_lot: form.value.parkingLot.id,
        id_week_day: form.value.dayOfWeek,
        open_time: form.value.startTime,
        close_time: form.value.endTime,
        status: true
      }

      await schedulesService.createDailySchedule(dailyScheduleData)
      successMessage.value = 'Schedule created successfully'
    }

    await fetchData()
    resetForm()
  } catch (error) {
    console.error('Error saving schedule:', error)
    errorMessage.value = 'Error saving schedule. Please try again.'
  }
}

const deactivateSchedule = async (id) => {
  if (confirm('Are you sure you want to deactivate this schedule?')) {
    try {
      await schedulesService.updateSchedule(id, { status: false })
      successMessage.value = 'Schedule deactivated successfully'
      await fetchData()
    } catch (error) {
      console.error('Error deactivating schedule:', error)
      errorMessage.value = 'Error deactivating schedule. Please try again.'
    }
  }
}

const resetForm = () => {
  form.value = {
    idSchedule: null,
    idDailySchedule: null,
    parkingLot: { id: '' },
    dayOfWeek: '',
    startTime: '',
    endTime: '',
    status: 'true'
  }
  isEditing.value = false
}

const fetchData = async () => {
  try {
    // Fetch parking lots
    const lots = await parkingLotsService.getAllParkingLots()
    parkingLots.value = lots

    // Fetch week days
    const days = await weekDaysService.getAllWeekDays()
    if (Array.isArray(days) && days.length > 0) {
      weekDays.value = days
    }

    // Fetch schedules and daily schedules
    const scheduleData = await schedulesService.getAllSchedules()
    schedules.value = scheduleData

    const dailyScheduleData = await schedulesService.getAllDailySchedules()
    dailySchedules.value = dailyScheduleData

    // Combine schedules and daily schedules for display
    combinedSchedules.value = []
    schedules.value.forEach(schedule => {
      const relatedDailySchedules = dailySchedules.value.filter(ds => ds.schedule.idSchedule === schedule.idSchedule)
      relatedDailySchedules.forEach(ds => {
        combinedSchedules.value.push({
          idSchedule: schedule.idSchedule,
          idDailySchedule: ds.idDailySchedule,
          parkingLot: schedule.parkingLot,
          dayOfWeek: ds.weekDay.idWeekDay,
          startTime: ds.startTime,
          endTime: ds.endTime,
          status: ds.isActive
        })
      })
    })
  } catch (error) {
    console.error('Error fetching data:', error)
    errorMessage.value = 'Error loading data. Please try again.'
  }
}

onMounted(() => {
  fetchData()
})
</script>
