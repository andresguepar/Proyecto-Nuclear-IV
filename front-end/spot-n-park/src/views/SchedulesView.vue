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
import { schedulesService } from '@/services/schedulesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { weekDaysService } from '@/services/weekDaysService'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'

const tabs = [
  { id: 'schedule', label: 'Regular Schedules' },
  { id: 'daily', label: 'Daily Schedules' }
]

const activeTab = ref('schedule')
const schedules = ref([])
const dailySchedules = ref([])
const parkingLots = ref([])
const weekDays = ref([])
const isEditing = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const scheduleForm = ref({
  id: null,
  id_parking_lot: '',
  id_week_day: '',
  open_time: '',
  close_time: '',
  status: 'active'
})

const dailyScheduleForm = ref({
  id: null,
  id_parking_lot: '',
  date: '',
  open_time: '',
  close_time: '',
  status: 'active'
})

const showError = (message) => {
  errorMessage.value = message
  setTimeout(() => {
    errorMessage.value = ''
  }, 5000)
}

const showSuccess = (message) => {
  successMessage.value = message
  setTimeout(() => {
    successMessage.value = ''
  }, 5000)
}

const getStatusClass = (status) => {
  return status === 'active' ? 'text-green-500' : 'text-red-500'
}

const fetchSchedules = async () => {
  try {
    schedules.value = await schedulesService.getAllSchedules()
  } catch (error) {
    console.error('Error fetching schedules:', error)
    showError('Error al cargar los horarios. Por favor, intente nuevamente.')
  }
}

const fetchDailySchedules = async () => {
  try {
    dailySchedules.value = await schedulesService.getAllDailySchedules()
  } catch (error) {
    console.error('Error fetching daily schedules:', error)
    showError('Error al cargar los horarios diarios. Por favor, intente nuevamente.')
  }
}

const fetchParkingLots = async () => {
  try {
    parkingLots.value = await parkingLotsService.getAllParkingLots()
  } catch (error) {
    console.error('Error fetching parking lots:', error)
    showError('Error al cargar los estacionamientos. Por favor, intente nuevamente.')
  }
}

const fetchWeekDays = async () => {
  try {
    weekDays.value = await weekDaysService.getAllWeekDays()
  } catch (error) {
    console.error('Error fetching week days:', error)
    showError('Error al cargar los días de la semana. Por favor, intente nuevamente.')
  }
}

const handleScheduleSubmit = async () => {
  try {
    if (isEditing.value) {
      await schedulesService.updateSchedule(scheduleForm.value.id, scheduleForm.value)
      showSuccess('Horario actualizado exitosamente')
    } else {
      await schedulesService.createSchedule(scheduleForm.value)
      showSuccess('Horario creado exitosamente')
    }
    resetScheduleForm()
    await fetchSchedules()
  } catch (error) {
    console.error('Error saving schedule:', error)
    showError('Error al guardar el horario. Por favor, intente nuevamente.')
  }
}

const handleDailyScheduleSubmit = async () => {
  try {
    if (isEditing.value) {
      await schedulesService.updateDailySchedule(dailyScheduleForm.value.id, dailyScheduleForm.value)
      showSuccess('Horario diario actualizado exitosamente')
    } else {
      await schedulesService.createDailySchedule(dailyScheduleForm.value)
      showSuccess('Horario diario creado exitosamente')
    }
    resetDailyScheduleForm()
    await fetchDailySchedules()
  } catch (error) {
    console.error('Error saving daily schedule:', error)
    showError('Error al guardar el horario diario. Por favor, intente nuevamente.')
  }
}

const editSchedule = (schedule) => {
  isEditing.value = true
  scheduleForm.value = { ...schedule }
}

const editDailySchedule = (schedule) => {
  isEditing.value = true
  dailyScheduleForm.value = { ...schedule }
}

const deleteSchedule = async (id) => {
  if (!confirm('¿Está seguro de que desea eliminar este horario?')) {
    return
  }
  try {
    await schedulesService.deleteSchedule(id)
    showSuccess('Horario eliminado exitosamente')
    await fetchSchedules()
  } catch (error) {
    console.error('Error deleting schedule:', error)
    showError('Error al eliminar el horario. Por favor, intente nuevamente.')
  }
}

const deleteDailySchedule = async (id) => {
  if (!confirm('¿Está seguro de que desea eliminar este horario diario?')) {
    return
  }
  try {
    await schedulesService.deleteDailySchedule(id)
    showSuccess('Horario diario eliminado exitosamente')
    await fetchDailySchedules()
  } catch (error) {
    console.error('Error deleting daily schedule:', error)
    showError('Error al eliminar el horario diario. Por favor, intente nuevamente.')
  }
}

const resetScheduleForm = () => {
  scheduleForm.value = {
    id: null,
    id_parking_lot: '',
    id_week_day: '',
    open_time: '',
    close_time: '',
    status: 'active'
  }
  isEditing.value = false
}

const resetDailyScheduleForm = () => {
  dailyScheduleForm.value = {
    id: null,
    id_parking_lot: '',
    date: '',
    open_time: '',
    close_time: '',
    status: 'active'
  }
  isEditing.value = false
}

onMounted(async () => {
  try {
    await Promise.all([
      fetchSchedules(),
      fetchDailySchedules(),
      fetchParkingLots(),
      fetchWeekDays()
    ])
  } catch (error) {
    console.error('Error initializing component:', error)
    showError('Error al cargar los datos. Por favor, intente nuevamente.')
  }
})
</script> 