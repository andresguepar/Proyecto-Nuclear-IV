<script setup>
import { onMounted, ref, computed } from 'vue'
import {
  mdiParking,
  mdiChartPie,
  mdiReload,
  mdiOfficeBuilding
} from '@mdi/js'
import SectionMain from '@/components/SectionMain.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButton from '@/components/BaseButton.vue'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import axios from 'axios'
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js'
import { Bar } from 'vue-chartjs'

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

const slotStatus = ref({
  occupied: 0,
  free: 0
})

const parkingLot = ref(null)
const slotsByType = ref([])

const chartData = computed(() => ({
  labels: slotsByType.value.map(item => item.type),
  datasets: [{
    label: 'Slots por Tipo de Vehículo',
    data: slotsByType.value.map(item => item.count),
    backgroundColor: [
      'rgba(255, 99, 132, 0.5)',
      'rgba(54, 162, 235, 0.5)',
      'rgba(255, 206, 86, 0.5)',
      'rgba(75, 192, 192, 0.5)',
      'rgba(153, 102, 255, 0.5)',
    ],
    borderColor: [
      'rgba(255, 99, 132, 1)',
      'rgba(54, 162, 235, 1)',
      'rgba(255, 206, 86, 1)',
      'rgba(75, 192, 192, 1)',
      'rgba(153, 102, 255, 1)',
    ],
    borderWidth: 1
  }]
}))

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top',
    },
    title: {
      display: true,
      text: 'Distribución de Slots por Tipo de Vehículo'
    }
  }
}

const fetchParkingData = async () => {
  try {
    // Get current user from localStorage
    const currentUser = JSON.parse(localStorage.getItem('user'))
    console.log('Current User:', currentUser)
    if (!currentUser) {
      console.error('No user found in session')
      return
    }

    // Get parking lots for this user (as admin)
    const parkingLotsResponse = await axios.get(`http://localhost:8080/ParkingLots/filterByAdmin?adminId=${currentUser.idUser}&isActive=true`)
    const parkingLots = parkingLotsResponse.data
    console.log('Parking Lots:', parkingLots)

    if (parkingLots.length === 0) {
      console.error('No parking lots found for user')
      return
    }

    // Store the parking lot info
    parkingLot.value = parkingLots[0]

    // Get all slots
    const slotsResponse = await axios.get('http://localhost:8080/slots/get')
    const allSlots = slotsResponse.data
    console.log('All Slots:', allSlots)

    // Filter slots for this parking lot
    const parkingLotSlots = allSlots.filter(slot => slot.parkingLotId === parkingLots[0].id)
    console.log('Parking Lot Slots:', parkingLotSlots)
    
    // Calculate occupied and free slots
    const occupied = parkingLotSlots.filter(slot => !slot.isAvailable).length
    const free = parkingLotSlots.filter(slot => slot.isAvailable).length
    console.log('Occupied Slots:', occupied)
    console.log('Free Slots:', free)

    slotStatus.value = {
      occupied,
      free
    }

    // Get vehicle types
    const vehicleTypesResponse = await axios.get('http://localhost:8080/vehicle-types/get')
    const vehicleTypes = vehicleTypesResponse.data
    console.log('Vehicle Types:', vehicleTypes)

    // Count slots by vehicle type
    slotsByType.value = vehicleTypes.map(type => ({
      type: type.name,
      count: parkingLotSlots.filter(slot => slot.vehicleType.idVehicleType === type.idVehicleType).length
    }))
    console.log('Slots by Type:', slotsByType.value)

  } catch (error) {
    console.error('Error fetching parking data:', error)
  }
}

onMounted(() => {
  fetchParkingData()
})
</script>

<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiChartPie" title="Dashboard del Parqueadero" main>
        <BaseButton
          :icon="mdiReload"
          color="contrast"
          rounded-full
          small
          @click="fetchParkingData"
        />
      </SectionTitleLineWithButton>

      <div class="grid grid-cols-1 gap-6 lg:grid-cols-2 mb-6">
        <!-- Estado de Slots -->
        <CardBox>
          <h3 class="text-lg font-semibold mb-4">Estado de Slots</h3>
          <div class="grid grid-cols-2 gap-4">
            <div class="p-4 bg-red-50 rounded-lg text-center">
              <div class="text-2xl mb-2">
                <i :class="mdiParking"></i>
              </div>
              <div class="text-sm text-gray-600">Ocupados</div>
              <div class="text-2xl font-bold text-red-600">{{ slotStatus.occupied }}</div>
            </div>
            <div class="p-4 bg-green-50 rounded-lg text-center">
              <div class="text-2xl mb-2">
                <i :class="mdiParking"></i>
              </div>
              <div class="text-sm text-gray-600">Libres</div>
              <div class="text-2xl font-bold text-green-600">{{ slotStatus.free }}</div>
            </div>
          </div>
        </CardBox>

        <!-- Información del Parqueadero -->
        <CardBox>
          <h3 class="text-lg font-semibold mb-4">Información del Parqueadero</h3>
          <div class="space-y-4">
            <div class="flex items-center p-3 bg-gray-50 rounded-lg">
              <span class="text-2xl mr-3">
                <i :class="mdiOfficeBuilding"></i>
              </span>
              <div>
                <div class="font-medium">{{ parkingLot?.name }}</div>
                <div class="text-sm text-gray-600">{{ parkingLot?.address }}</div>
              </div>
            </div>
            <div class="grid grid-cols-2 gap-4">
              <div class="p-3 bg-gray-50 rounded-lg">
                <div class="text-sm text-gray-600">Capacidad Total</div>
                <div class="font-medium">{{ slotStatus.occupied + slotStatus.free }} slots</div>
              </div>
              <div class="p-3 bg-gray-50 rounded-lg">
                <div class="text-sm text-gray-600">Estado</div>
                <div class="font-medium text-green-600">Activo</div>
              </div>
            </div>
          </div>
        </CardBox>
      </div>

      <!-- Gráfico de Slots por Tipo de Vehículo -->
      <CardBox class="mb-6">
        <div class="h-96">
          <Bar v-if="slotsByType.length > 0" :data="chartData" :options="chartOptions" />
        </div>
      </CardBox>
    </SectionMain>
  </LayoutAuthenticated>
</template>
