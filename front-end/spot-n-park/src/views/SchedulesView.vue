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
      
      <!-- Tabs -->
      <div class="flex mb-4">
        <button
          v-for="tab in tabs"
          :key="tab.id"
          @click="activeTab = tab.id"
          :class="[
            'px-4 py-2 mr-2 rounded-t-lg',
            activeTab === tab.id
              ? 'bg-blue-600 text-white'
              : 'bg-gray-200 dark:bg-slate-700 text-gray-700 dark:text-gray-300'
          ]"
        >
          {{ tab.label }}
        </button>
      </div>

      <!-- Schedule Tab Content -->
      <div v-if="activeTab === 'schedule'" class="flex flex-col md:flex-row gap-4">
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
                  <th>Status</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                <tr v-if="schedules.length === 0">
                  <td colspan="7" class="text-center py-24 text-gray-500 dark:text-slate-400">
                    <p>No schedules found</p>
                  </td>
                </tr>
                <tr v-for="schedule in schedules" :key="schedule.id">
                  <td data-label="ID">{{ schedule.id }}</td>
                  <td data-label="Parking Lot">{{ schedule.parking_lot_name }}</td>
                  <td data-label="Day">{{ schedule.day_name }}</td>
                  <td data-label="Open Time">{{ schedule.open_time }}</td>
                  <td data-label="Close Time">{{ schedule.close_time }}</td>
                  <td data-label="Status">
                    <span :class="getStatusClass(schedule.status)">
                      {{ schedule.status }}
                    </span>
                  </td>
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
                        @click="deleteSchedule(schedule.id)"
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
                  v-model="scheduleForm.id_parking_lot"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="">Select a parking lot</option>
                  <option v-for="lot in parkingLots" :key="lot.id" :value="lot.id">
                    {{ lot.name }}
                  </option>
                </select>
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Day</label>
                <select
                  v-model="scheduleForm.id_week_day"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="">Select a day</option>
                  <option v-for="day in weekDays" :key="day.id" :value="day.id">
                    {{ day.name }}
                  </option>
                </select>
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Open Time</label>
                <input
                  v-model="scheduleForm.open_time"
                  type="time"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Close Time</label>
                <input
                  v-model="scheduleForm.close_time"
                  type="time"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Status</label>
                <select
                  v-model="scheduleForm.status"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="active">Active</option>
                  <option value="inactive">Inactive</option>
                </select>
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

      <!-- Daily Schedule Tab Content -->
      <div v-if="activeTab === 'daily'" class="flex flex-col md:flex-row gap-4">
        <!-- Table Section -->
        <div class="w-full md:w-2/3">
          <CardBox has-table>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Parking Lot</th>
                  <th>Date</th>
                  <th>Open Time</th>
                  <th>Close Time</th>
                  <th>Status</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                <tr v-if="dailySchedules.length === 0">
                  <td colspan="7" class="text-center py-24 text-gray-500 dark:text-slate-400">
                    <p>No daily schedules found</p>
                  </td>
                </tr>
                <tr v-for="schedule in dailySchedules" :key="schedule.id">
                  <td data-label="ID">{{ schedule.id }}</td>
                  <td data-label="Parking Lot">{{ schedule.parking_lot_name }}</td>
                  <td data-label="Date">{{ schedule.date }}</td>
                  <td data-label="Open Time">{{ schedule.open_time }}</td>
                  <td data-label="Close Time">{{ schedule.close_time }}</td>
                  <td data-label="Status">
                    <span :class="getStatusClass(schedule.status)">
                      {{ schedule.status }}
                    </span>
                  </td>
                  <td class="before:hidden lg:w-1 whitespace-nowrap">
                    <BaseButtons type="justify-start lg:justify-end" no-wrap>
                      <BaseButton
                        color="info"
                        :icon="mdiPencil"
                        small
                        @click="editDailySchedule(schedule)"
                      />
                      <BaseButton
                        color="danger"
                        :icon="mdiClose"
                        small
                        @click="deleteDailySchedule(schedule.id)"
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
            <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Daily Schedule' : 'Create New Daily Schedule' }}</h2>
            <form @submit.prevent="handleDailyScheduleSubmit">
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Parking Lot</label>
                <select
                  v-model="dailyScheduleForm.id_parking_lot"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="">Select a parking lot</option>
                  <option v-for="lot in parkingLots" :key="lot.id" :value="lot.id">
                    {{ lot.name }}
                  </option>
                </select>
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Date</label>
                <input
                  v-model="dailyScheduleForm.date"
                  type="date"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Open Time</label>
                <input
                  v-model="dailyScheduleForm.open_time"
                  type="time"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Close Time</label>
                <input
                  v-model="dailyScheduleForm.close_time"
                  type="time"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div class="mb-4">
                <label class="block text-sm font-medium mb-2">Status</label>
                <select
                  v-model="dailyScheduleForm.status"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option value="active">Active</option>
                  <option value="inactive">Inactive</option>
                </select>
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

const activeTab = ref('schedule')
const isEditing = ref(false)
const errorMessage = ref('')
const successMessage = ref('')
const schedules = ref([])
const dailySchedules = ref([])
const parkingLots = ref([])
const weekDays = ref([])

const tabs = [
  { id: 'schedule', label: 'Schedule' },
  { id: 'daily', label: 'Daily Schedule' }
]

const scheduleForm = ref({
  id_parking_lot: '',
  id_week_day: '',
  open_time: '',
  close_time: '',
  status: 'active'
})

const dailyScheduleForm = ref({
  id_parking_lot: '',
  date: '',
  open_time: '',
  close_time: '',
  status: 'active'
})

const fetchData = async () => {
  try {
    // Fetch parking lots
    const lots = await parkingLotsService.getAllParkingLots()
    parkingLots.value = lots.map(lot => ({
      id: lot.idParkingLot,
      name: lot.name
    }))

    // Fetch week days
    try {
      const days = await weekDaysService.getAllWeekDays()
      console.log('Week days from service:', days) // Debug log
      if (Array.isArray(days) && days.length > 0) {
        weekDays.value = days.map(day => ({
          id: day.idWeekDay,
          name: day.name
        }))
        console.log('Mapped week days:', weekDays.value) // Debug log
      } else {
        console.warn('No week days found or invalid data format')
        weekDays.value = []
      }
    } catch (weekDaysError) {
      console.error('Error fetching week days:', weekDaysError)
      errorMessage.value = 'Error loading week days. Please try again.'
      weekDays.value = []
    }

    // Fetch schedules
    const scheduleData = await schedulesService.getAllSchedules()
    schedules.value = scheduleData

    // Fetch daily schedules
    const dailyData = await schedulesService.getAllDailySchedules()
    dailySchedules.value = dailyData
  } catch (error) {
    console.error('Error fetching data:', error)
    errorMessage.value = 'Error loading data. Please try again.'
  }
}

const getStatusClass = (status) => {
  return {
    'px-2 py-1 rounded text-xs font-semibold': true,
    'bg-green-100 text-green-800': status === 'active',
    'bg-red-100 text-red-800': status === 'inactive'
  }
}

const handleScheduleSubmit = async () => {
  try {
    if (isEditing.value) {
      await schedulesService.updateSchedule(scheduleForm.value.id, scheduleForm.value)
      successMessage.value = 'Schedule updated successfully'
    } else {
      await schedulesService.createSchedule(scheduleForm.value)
      successMessage.value = 'Schedule created successfully'
    }
    await fetchData()
    resetForm()
  } catch (error) {
    console.error('Error saving schedule:', error)
    errorMessage.value = 'Error saving schedule. Please try again.'
  }
}

const handleDailyScheduleSubmit = async () => {
  try {
    if (isEditing.value) {
      await schedulesService.updateDailySchedule(dailyScheduleForm.value.id, dailyScheduleForm.value)
      successMessage.value = 'Daily schedule updated successfully'
    } else {
      await schedulesService.createDailySchedule(dailyScheduleForm.value)
      successMessage.value = 'Daily schedule created successfully'
    }
    await fetchData()
    resetForm()
  } catch (error) {
    console.error('Error saving daily schedule:', error)
    errorMessage.value = 'Error saving daily schedule. Please try again.'
  }
}

const editSchedule = (schedule) => {
  isEditing.value = true
  scheduleForm.value = {
    id: schedule.id,
    id_parking_lot: schedule.id_parking_lot,
    id_week_day: schedule.id_week_day,
    open_time: schedule.open_time,
    close_time: schedule.close_time,
    status: schedule.status
  }
}

const editDailySchedule = (schedule) => {
  isEditing.value = true
  dailyScheduleForm.value = {
    id: schedule.id,
    id_parking_lot: schedule.id_parking_lot,
    date: schedule.date,
    open_time: schedule.open_time,
    close_time: schedule.close_time,
    status: schedule.status
  }
}

const deleteSchedule = async (id) => {
  if (confirm('Are you sure you want to delete this schedule?')) {
    try {
      await schedulesService.deleteSchedule(id)
      successMessage.value = 'Schedule deleted successfully'
      await fetchData()
    } catch (error) {
      console.error('Error deleting schedule:', error)
      errorMessage.value = 'Error deleting schedule. Please try again.'
    }
  }
}

const deleteDailySchedule = async (id) => {
  if (confirm('Are you sure you want to delete this daily schedule?')) {
    try {
      await schedulesService.deleteDailySchedule(id)
      successMessage.value = 'Daily schedule deleted successfully'
      await fetchData()
    } catch (error) {
      console.error('Error deleting daily schedule:', error)
      errorMessage.value = 'Error deleting daily schedule. Please try again.'
    }
  }
}

const resetForm = () => {
  isEditing.value = false
  scheduleForm.value = {
    id_parking_lot: '',
    id_week_day: '',
    open_time: '',
    close_time: '',
    status: 'active'
  }
  dailyScheduleForm.value = {
    id_parking_lot: '',
    date: '',
    open_time: '',
    close_time: '',
    status: 'active'
  }
}

onMounted(() => {
  fetchData()
})
</script> 