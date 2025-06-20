<template>
  <CardBox class="mb-3 h-full flex flex-col">
    <CardBoxComponentBody class="flex-1">
      <div class="flex flex-col space-y-4">
        <div>
          <h3 class="text-xl font-semibold text-gray-800">{{ reservation.parkingName }}</h3>
          <p class="text-base text-gray-600">{{ reservation.address }}</p>
        </div>

        <div class="grid grid-cols-2 gap-3">
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">{{ reservation.date }}</span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">{{ reservation.time }}</span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Slot: {{ reservation.slot?.name || reservation.slot }}
            </span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Slot: {{ reservation.slot?.name || reservation.slot }}
            </span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M9 17a2 2 0 11-4 0 2 2 0 014 0zM19 17a2 2 0 11-4 0 2 2 0 014 0z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">Plate: {{ reservation.plate }}</span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
              class="w-6 h-6 text-gray-500"
            />
            <span :class="getStatusClass(reservation.status)" class="text-base">
              {{ getStatusText(reservation.status) }}
            </span>
          </div>

          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">Teléfono: {{ reservation.phone }}</span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M5 13l4 4L19 7"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Tipo de vehículo: {{ reservation.vehicleType }}
            </span>
          </div>
          <!-- Precio por tipo de vehículo -->
          <div v-if="reservation.pricePerHour" class="flex items-center space-x-2">
            <BaseIcon
              path="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Precio: ${{ reservation.pricePerHour }}/hora
              <span v-if="reservation.priceForTwelveHours" class="text-sm text-gray-500">
                (12h: ${{ reservation.priceForTwelveHours }})
              </span>
            </span>
          </div>
          <!-- Información del usuario para admins -->
          <div v-if="isAdmin" class="flex items-center space-x-2">
            <BaseIcon
              path="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Usuario: {{ reservation.userName }}
            </span>
          </div>
          <div v-if="isAdmin" class="flex items-center space-x-2">
            <BaseIcon
              path="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Email: {{ reservation.userEmail }}
            </span>
          </div>
        </div>

        <!-- Servicios adicionales -->
        <div v-if="reservation.addOnServices && reservation.addOnServices.length > 0" class="mt-4">
          <h4 class="text-lg font-semibold text-gray-800 mb-2">Servicios adicionales:</h4>
          <div class="space-y-2">
            <div v-for="service in reservation.addOnServices" :key="service.idReservationAddOnService" 
                 class="flex justify-between items-center p-2 bg-gray-50 rounded">
              <span class="text-sm text-gray-700">{{ service.addOnService?.name || 'Servicio' }}</span>
              <span class="text-sm font-medium text-gray-800">${{ service.price }}</span>
            </div>
            <div class="border-t pt-2 flex justify-between items-center">
              <span class="font-semibold text-gray-800">Total servicios:</span>
              <span class="font-bold text-[#0D2F78]">
                ${{ reservation.addOnServices.reduce((total, service) => total + (service.price || 0), 0) }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </CardBoxComponentBody>
    <div class="p-4 border-t border-gray-100">
      <div class="flex justify-between space-x-2">
        <BaseButton
          color="danger"
          label="Cancelar"
          :disabled="reservation.status === 'CANCELED'"
          @click="$emit('cancel', reservation)"
        />
        <BaseButton
          v-if="canEdit"
          color="info"
          label="Editar"
          @click="$emit('edit', reservation)"
        />
        <BaseButton
          v-if="isPending"
          color="success"
          label="Confirmar"
          @click="$emit('confirm', reservation)"
        />
        <BaseButton
          v-if="isPendingAdminConfirmation"
          color="warning"
          label="Confirmar Reserva"
          @click="$emit('adminConfirm', reservation)"
        />
        <BaseButton
          v-if="isUserConfirmed"
          color="info"
          label="Iniciar"
          @click="$emit('userRequestStart', reservation)"
        />
        <BaseButton
          v-if="isConfirmed && isAdmin"
          color="warning"
          label="Confirmar Inicio"
          @click="$emit('start', reservation)"
        />
        <BaseButton
          v-if="isInProgress"
          color="info"
          label="Confirmar Salida"
          @click="$emit('complete', reservation)"
        />
        <BaseButton
          v-if="isCompleted"
          color="success"
          label="Pagar"
          @click="$emit('pay', reservation)"
        />
      </div>
    </div>
  </CardBox>
</template>

<script setup>
import CardBox from '@/components/CardBox.vue'
import CardBoxComponentBody from '@/components/CardBoxComponentBody.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseIcon from '@/components/BaseIcon.vue'
import { computed } from 'vue'

const props = defineProps({
  reservation: {
    type: Object,
    required: true
  },
  isAdmin: {
    type: Boolean,
    default: false
  }
})

// Debug log para ver el estado de la reserva
console.log('ReservationCard received reservation:', props.reservation)
console.log('Reservation status:', props.reservation?.status)

const getStatusClass = (status) => {
  const statusUpper = status?.toUpperCase()
  switch (statusUpper) {
    case 'PENDIENTE':
    case 'PENDING':
    case 'ACTIVE':
      return 'text-yellow-600'
    case 'PENDIENTE DE CONFIRMACIÓN DEL ADMIN':
    case 'PENDIENTE_ADMIN_CONFIRMATION':
      return 'text-orange-600'
    case 'CONFIRMADA':
    case 'CONFIRMED':
      return 'text-[#0D2F78]'
    case 'EN CURSO':
    case 'IN PROGRESS':
    case 'EN_PROGRESO':
      return 'text-green-600'
    case 'COMPLETADA':
    case 'COMPLETED':
      return 'text-purple-600'
    case 'PAGADA':
    case 'PAID':
      return 'text-green-700'
    case 'CANCELADA':
    case 'CANCELED':
      return 'text-red-600'
    default:
      return 'text-gray-600'
  }
}

const getStatusText = (status) => {
  const statusUpper = status?.toUpperCase()
  switch (statusUpper) {
    case 'PENDIENTE':
    case 'PENDING':
    case 'ACTIVE':
      return 'Pendiente'
    case 'PENDIENTE DE CONFIRMACIÓN DEL ADMIN':
    case 'PENDIENTE_ADMIN_CONFIRMATION':
      return 'Pendiente de Confirmación del Admin'
    case 'CONFIRMADA':
    case 'CONFIRMED':
      return 'Confirmada'
    case 'EN CURSO':
    case 'IN PROGRESS':
    case 'EN_PROGRESO':
      return 'En Curso'
    case 'COMPLETADA':
    case 'COMPLETED':
      return 'Completada'
    case 'PAGADA':
    case 'PAID':
      return 'Pagada'
    case 'CANCELADA':
    case 'CANCELED':
      return 'Cancelada'
    default:
      return status || 'Desconocido'
  }
}

const canEdit = computed(() => {
  const status = props.reservation.status?.toUpperCase()
  return ['PENDIENTE', 'PENDING', 'ACTIVE', 'CONFIRMADA', 'CONFIRMED'].includes(status) && !props.isAdmin
})

const isPending = computed(() => {
  // Solo usuarios normales pueden confirmar sus propias reservas
  const status = props.reservation.status?.toUpperCase()
  return (status === 'PENDIENTE' || status === 'PENDING' || status === 'ACTIVE') && !props.isAdmin
})

const isPendingAdminConfirmation = computed(() => {
  const status = props.reservation.status?.toUpperCase()
  // Solo admins pueden confirmar reservas pendientes
  return (status === 'PENDIENTE' || status === 'PENDING' || status === 'ACTIVE') && props.isAdmin
})

const isConfirmed = computed(() => {
  const status = props.reservation.status?.toUpperCase()
  // Usuarios pueden solicitar inicio, admins pueden iniciar directamente
  return (status === 'CONFIRMADA' || status === 'CONFIRMED')
})

const isInProgress = computed(() => {
  const status = props.reservation.status?.toUpperCase()
  // Solo admins pueden finalizar reservas en curso
  return (status === 'EN CURSO' || status === 'IN PROGRESS' || status === 'EN_PROGRESO') && props.isAdmin
})

const isCompleted = computed(() => {
  const status = props.reservation.status?.toUpperCase()
  // Solo usuarios normales pueden pagar reservas completadas
  return (status === 'COMPLETADA' || status === 'COMPLETED') && !props.isAdmin
})

const isUserConfirmed = computed(() => {
  const status = props.reservation.status?.toUpperCase()
  // Usuarios pueden solicitar inicio cuando está confirmada
  return (status === 'CONFIRMADA' || status === 'CONFIRMED') && !props.isAdmin
})

defineEmits(['edit', 'cancel', 'confirm', 'start', 'complete', 'pay', 'adminConfirm', 'userRequestStart'])
</script>
