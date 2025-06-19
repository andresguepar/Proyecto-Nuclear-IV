<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiBookMarker" :title="pageTitle" main>
        <BaseButton
          v-if="authStore.user?.role?.name === 'USER'"
          color="info"
          label="New Reservation"
          :icon="mdiPlus"
          @click="goToHome"
        />
      </SectionTitleLineWithButton>

      <div v-if="reservations.length === 0" class="text-center py-8">
        <p class="text-gray-500">{{ noReservationsMessage }}</p>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <ReservationCard
          v-for="reservation in reservations"
          :key="reservation.id"
          :reservation="reservation"
          :is-admin="isParkAdmin"
          @edit="handleEdit"
          @cancel="handleCancel"
          @confirm="handleConfirm"
          @admin-confirm="handleAdminConfirm"
          @start="handleStart"
          @complete="handleComplete"
          @pay="handlePay"
          @user-request-start="handleUserRequestStart"
        />
      </div>

      <!-- Edit Reservation Modal -->
      <CardBoxModal
        v-model="showEditReservationModal"
        title="Edit Reservation"
      >
        <StandardServiceForm
          :initial-data="selectedReservation"
          @form-update="handleFormUpdate"
        />
      </CardBoxModal>

      <!-- Cancel Confirmation Modal -->
      <CardBoxModal
        v-model="showCancelModal"
        title="Cancel Reservation"
        button="danger"
        has-cancel
        @confirm="confirmCancel"
      >
        <p>Are you sure you want to cancel this reservation?</p>
      </CardBoxModal>

      <!-- Payment Modal -->
      <PaymentModal
        v-if="selectedReservation"
        v-model="showPaymentModal"
        :reservation="selectedReservation"
        @payment-processed="handlePaymentProcessed"
      />
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRouter } from 'vue-router'
import { mdiBookMarker, mdiPlus } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import BaseButton from '@/components/BaseButton.vue'
import ReservationCard from '@/components/ReservationCard.vue'
import StandardServiceForm from '@/components/StandardServiceForm.vue'
import PaymentModal from '@/components/PaymentModal.vue'
import { reservationsService } from '@/services/reservationsService.js'
import { statusReservationService } from '@/services/statusReservationService.js'
import { schedulesService } from '@/services/schedulesService.js'
import { standardFeesService } from '@/services/standardFeesService.js'
import { useAuthStore } from '@/stores/auth.js'

const router = useRouter()
const reservations = ref([])
const showEditReservationModal = ref(false)
const showCancelModal = ref(false)
const showPaymentModal = ref(false)
const selectedReservation = ref(null)
const authStore = useAuthStore()

// Computed properties for role-based UI
const isParkAdmin = computed(() => {
  const roleName = authStore.user?.role?.name
  const roleId = authStore.user?.role?.idRole
  console.log('DEBUG: Role name:', roleName)
  console.log('DEBUG: Role ID:', roleId)
  console.log('DEBUG: Role object:', authStore.user?.role)
  
  // Verificar por nombre o ID del rol
  const isAdmin = roleName === 'PARK_ADMIN' || 
                 roleName === 'park_admin' || 
                 roleId === 2 || 
                 roleId === '2'
  
  console.log('DEBUG: Is Park Admin result:', isAdmin)
  return isAdmin
})

const pageTitle = computed(() => {
  if (isParkAdmin.value) {
    return 'Parking Lot Reservations'
  }
  return 'My Reservations'
})

const noReservationsMessage = computed(() => {
  if (isParkAdmin.value) {
    return 'No reservations found for your parking lot'
  }
  return 'No reservations found'
})

// Watcher para limpiar la reserva seleccionada cuando se cierre el modal de pago
watch(showPaymentModal, (newValue) => {
  if (!newValue) {
    selectedReservation.value = null
  }
})

// Función temporal para verificar estados
const checkStatuses = async () => {
  try {
    const statuses = await statusReservationService.getStatusReservationsDebug()
    console.log('Available statuses in database:', statuses)
  } catch (error) {
    console.error('Error checking statuses:', error)
  }
}

const fetchReservations = async () => {
  try {
    console.log('DEBUG: Fetching reservations... VERSION UPDATED')
    const userInfo = authStore.user
    console.log('DEBUG: User info from store:', userInfo)
    
    if (userInfo && userInfo.role) {
      console.log('DEBUG: User role:', userInfo.role)
      
      if (userInfo.role.name === 'park_admin' || userInfo.role.name === 'PARK_ADMIN' || userInfo.role.idRole === 2) {
        console.log('DEBUG: Fetching reservations for park_admin')
        const reservationsData = await reservationsService.getParkingLotAdminReservations(userInfo.idUser)
        console.log('DEBUG: Park admin reservations:', reservationsData)
        reservations.value = reservationsData
      } else {
        console.log('DEBUG: Fetching reservations for basic_user')
        const reservationsData = await reservationsService.getUserReservations(userInfo.idUser)
        console.log('DEBUG: Basic user reservations:', reservationsData)
        reservations.value = reservationsData
      }
      
      // Mapear las reservas y obtener información adicional
      await mapReservationsWithDetails()
    }
  } catch (error) {
    console.error('Error fetching reservations:', error)
  }
}

const mapReservationsWithDetails = async () => {
  const mapped = []
  
  console.log('DEBUG: Starting to map reservations with details')
  console.log('DEBUG: Total reservations to process:', reservations.value.length)
  
  for (const reservation of reservations.value) {
    console.log('DEBUG: Processing reservation:', reservation.idStandardReservation)
    
    const mappedReservation = {
      id: reservation.idStandardReservation,
      parkingName: reservation.slot?.parkingLot?.name || 'N/A',
      address: reservation.slot?.parkingLot?.address || 'N/A',
      date: new Date(reservation.scheduledDateTime).toLocaleDateString(),
      time: new Date(reservation.scheduledDateTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      slot: reservation.slot?.name || reservation.slot?.slotNumber || 'N/A',
      plate: reservation.plate || 'N/A',
      startDate: reservation.startDate || '',
      startTime: reservation.startTime || '',
      endTime: reservation.endTime || '',
      phone: reservation.slot?.parkingLot?.phone || 'N/A',
      vehicleType: reservation.slot?.vehicleType?.name || reservation.vehicleType?.name || 'N/A',
      vehicleTypeId: reservation.slot?.vehicleType?.idVehicleType || reservation.vehicleType?.idVehicleType || null,
      status: reservation.statusReservation?.name || 'N/A',
      // Agregar información del usuario para park_admin
      userName: reservation.user?.username || 'N/A',
      userEmail: reservation.user?.email || 'N/A',
      parkingLotId: reservation.slot?.parkingLot?.idParkingLot || null,
      scheduledDateTime: reservation.scheduledDateTime // Para obtener el día de la semana
    }
    console.log('DEBUG: Mapped reservation:', mappedReservation) // Debug mapped reservation
    console.log('DEBUG: Vehicle type ID:', mappedReservation.vehicleTypeId)
    console.log('DEBUG: Parking lot ID:', mappedReservation.parkingLotId)
    
    // Obtener el precio por tipo de vehículo (necesario para la pasarela de pago)
    if (mappedReservation.parkingLotId) {
      await loadScheduleAndPrice(mappedReservation)
    } else {
      console.log('DEBUG: No parking lot ID available for reservation')
    }
    
    mapped.push(mappedReservation)
  }
  
  console.log('DEBUG: Final mapped reservations:', mapped)
  reservations.value = mapped
}

const loadScheduleAndPrice = async (reservation) => {
  try {
    // Obtener el precio por tipo de vehículo (necesario para la pasarela de pago)
    if (reservation.vehicleTypeId) {
      console.log(`DEBUG: Loading price for vehicle type ${reservation.vehicleTypeId} and parking lot ${reservation.parkingLotId}`)
      
      const fees = await standardFeesService.getAllStandardFees()
      console.log('DEBUG: All fees loaded:', fees)
      
      const vehicleFee = fees.find(fee => 
        fee.vehicleType?.idVehicleType === reservation.vehicleTypeId && 
        fee.parkingLot?.idParkingLot === reservation.parkingLotId
      )
      
      if (vehicleFee) {
        reservation.pricePerHour = vehicleFee.priceForHours
        reservation.priceForTwelveHours = vehicleFee.priceForTwelveHours
        console.log(`DEBUG: Price found for ${reservation.vehicleType}: $${vehicleFee.priceForHours}/hora, 12h: $${vehicleFee.priceForTwelveHours}`)
      } else {
        reservation.pricePerHour = 'Precio no disponible'
        reservation.priceForTwelveHours = 'Precio no disponible'
        console.log(`DEBUG: No price found for vehicle type ${reservation.vehicleTypeId} and parking lot ${reservation.parkingLotId}`)
        console.log('DEBUG: Available fees:', fees.map(f => ({
          vehicleType: f.vehicleType?.idVehicleType,
          parkingLot: f.parkingLot?.idParkingLot,
          price: f.priceForHours
        })))
      }
    } else {
      console.log('DEBUG: No vehicleTypeId available for reservation:', reservation)
    }
    
  } catch (error) {
    console.error('Error loading price:', error)
    reservation.pricePerHour = 'Error cargando precio'
    reservation.priceForTwelveHours = 'Error cargando precio'
  }
}

const goToHome = () => {
  router.push('/home')
}

const handleEdit = (reservation) => {
  selectedReservation.value = reservation
  showEditReservationModal.value = true
}

const handleCancel = (reservation) => {
  selectedReservation.value = reservation
  showCancelModal.value = true
}

const confirmCancel = async () => {
  try {
    await reservationsService.cancelReservation(selectedReservation.value.id)
    await fetchReservations() // Refresh the list
    showCancelModal.value = false
    selectedReservation.value = null
  } catch (error) {
    console.error('Error canceling reservation:', error)
  }
}

const handleFormUpdate = async (formData) => {
  try {
    // Update the reservation with the new data
    const updatedReservation = {
      ...selectedReservation.value,
      ...formData
    }
    await reservationsService.updateReservation(selectedReservation.value.id, updatedReservation)
    await fetchReservations() // Refresh the list
    showEditReservationModal.value = false
  } catch (error) {
    console.error('Error updating reservation:', error)
  }
}

const handleConfirm = async (reservation) => {
  try {
    await reservationsService.confirmReservation(reservation.id)
    await fetchReservations()
  } catch (error) {
    console.error('Error confirming reservation:', error)
  }
}

const handleAdminConfirm = async (reservation) => {
  try {
    await reservationsService.adminConfirmReservation(reservation.id)
    await fetchReservations()
  } catch (error) {
    console.error('Error confirming reservation:', error)
  }
}

const handleStart = async (reservation) => {
  try {
    await reservationsService.startReservation(reservation.id)
    await fetchReservations()
  } catch (error) {
    console.error('Error starting reservation:', error)
  }
}

const handleComplete = async (reservation) => {
  try {
    await reservationsService.completeReservation(reservation.id)
    await fetchReservations()
  } catch (error) {
    console.error('Error completing reservation:', error)
  }
}

const handlePay = (reservation) => {
  if (reservation) {
    selectedReservation.value = reservation
    showPaymentModal.value = true
  }
}

const handlePaymentProcessed = async (paymentData) => {
  try {
    await reservationsService.processPayment(paymentData.reservationId, paymentData.paymentMethodId)
    await fetchReservations()
    showPaymentModal.value = false
    selectedReservation.value = null
  } catch (error) {
    console.error('Error processing payment:', error)
    alert('Error al procesar el pago. Por favor intenta de nuevo.')
  }
}

const handleUserRequestStart = async (reservation) => {
  try {
    await reservationsService.userRequestStart(reservation.id)
    await fetchReservations()
  } catch (error) {
    console.error('Error requesting start:', error)
  }
}

// Función para obtener y formatear el horario diario
const getDailySchedule = async (parkingLotId) => {
  try {
    const dailySchedules = await schedulesService.getDailySchedulesByParkingLot(parkingLotId)
    console.log('DEBUG: Daily schedules for parking lot', parkingLotId, ':', dailySchedules)
    
    if (dailySchedules && dailySchedules.length > 0) {
      // Agrupar por día de la semana
      const schedulesByDay = {}
      dailySchedules.forEach(schedule => {
        const dayName = schedule.weekDay?.name || 'Desconocido'
        if (!schedulesByDay[dayName]) {
          schedulesByDay[dayName] = []
        }
        schedulesByDay[dayName].push({
          start: schedule.startTime,
          end: schedule.endTime
        })
      })
      
      // Formatear el horario
      const formattedSchedule = Object.entries(schedulesByDay)
        .map(([day, times]) => {
          const timeRanges = times.map(t => `${t.start} - ${t.end}`).join(', ')
          return `${day}: ${timeRanges}`
        })
        .join(' | ')
      
      return formattedSchedule || 'Horario no disponible'
    }
    return 'Horario no disponible'
  } catch (error) {
    console.error('Error getting daily schedule:', error)
    return 'Error al cargar horario'
  }
}

// Función para actualizar el horario de todas las reservas
const updateReservationsSchedule = async () => {
  const uniqueParkingLots = [...new Set(reservations.value.map(r => r.parkingLotId).filter(id => id))]
  
  for (const parkingLotId of uniqueParkingLots) {
    const schedule = await getDailySchedule(parkingLotId)
    
    // Actualizar todas las reservas de este parking lot
    reservations.value.forEach(reservation => {
      if (reservation.parkingLotId === parkingLotId) {
        reservation.hours = schedule
      }
    })
  }
}

onMounted(() => {
  fetchReservations()
  checkStatuses() // Llamada temporal
})

// Watcher para actualizar el horario cuando cambien las reservas
watch(reservations, async (newReservations) => {
  if (newReservations && newReservations.length > 0) {
    await updateReservationsSchedule()
  }
}, { immediate: false })
</script>
