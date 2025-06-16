<template>
  <transition name="fade">
    <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
      <div class="bg-white dark:bg-slate-900 rounded-2xl shadow-2xl max-w-2xl w-full mx-4 p-8 relative border-2 border-blue-400">
        <button class="absolute top-4 right-4 text-gray-400 hover:text-gray-700 text-2xl font-bold" @click="emit('update:show', false)">&times;</button>
        <h2 class="text-2xl font-bold text-blue-700 mb-6 text-center">Reservar Parqueadero</h2>
        <div class="flex flex-col md:flex-row gap-8 min-h-[400px]">
          <!-- Left: Parking lot and reservation details -->
          <div class="flex-1 space-y-3 p-2">
            <h3 class="text-lg font-bold text-gray-800 mb-1">{{ lot && lot.name ? lot.name : '' }}</h3>
            <p class="text-sm text-gray-600">Dirección: {{ lot && lot.address ? lot.address : '' }}</p>
            <p class="text-sm text-gray-600">Horario: {{ lot && lot.hours ? lot.hours : '' }}</p>
            <p class="text-sm text-gray-600">Teléfono: {{ lotPhone }}</p>
            <div class="mt-2">
              <label class="block text-sm font-medium mb-1" for="slot-asignado">Slot asignado:</label>
              <input id="slot-asignado" type="text" class="w-full border rounded p-2" :value="availableSlots.length > 0 ? availableSlots[0].name : 'No disponible'" disabled />
            </div>
            <div class="mt-2">
              <label class="block text-sm font-medium mb-1" for="fecha">Fecha:</label>
              <input id="fecha" type="text" class="w-full border rounded p-2" :value="formData.startDate" disabled />
            </div>
            <div class="mt-2">
              <label class="block text-sm font-medium mb-1" for="hora-inicio">Hora de inicio:</label>
              <input id="hora-inicio" type="text" class="w-full border rounded p-2" :value="formData.startTime" disabled />
            </div>
            <div class="mt-2">
              <label class="block text-sm font-medium mb-1" for="placa">Placa:</label>
              <input id="placa" type="text" class="w-full border rounded p-2" :value="formData.plate" disabled />
            </div>
            <div class="mt-2">
              <label class="block text-sm font-medium mb-1" for="tipo-vehiculo">Tipo de vehículo:</label>
              <input id="tipo-vehiculo" type="text" class="w-full border rounded p-2" :value="formData.vehicleTypeName" disabled />
            </div>
          </div>
          <!-- Right: Add-on services -->
          <div class="flex-1 space-y-3 p-2">
            <h4 class="text-base font-semibold mb-2">Servicios adicionales</h4>
            <div v-if="props.addOnServices && props.addOnServices.length > 0">
              <div v-for="service in props.addOnServices" :key="service.id" class="flex items-center mb-2">
                <input type="checkbox" :id="'service-' + service.id" v-model="selectedAddOnsProxy" :value="service" class="mr-2" />
                <label :for="'service-' + service.id">{{ service.name }} - ${{ service.price }}</label>
              </div>
              <div class="mt-2 font-semibold text-blue-700">Total servicios: ${{ props.addOnServicesTotal }}</div>
            </div>
            <div v-else class="text-gray-500">No hay servicios adicionales disponibles.</div>
            <div class="mt-6">
              <BaseButton color="info" label="Reservar" class="w-full" :disabled="!availableSlots.length" @click="handleReserve" />
            </div>
          </div>
        </div>
      </div>
      <div v-if="showLoginPopup" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-60">
        <div class="bg-white p-6 rounded-lg shadow-lg flex flex-col items-center">
          <p class="mb-4 text-lg font-semibold text-gray-800">Necesitas iniciar sesión para reservar.</p>
          <BaseButton color="info" label="Iniciar sesión" @click="() => router.push('/login')" />
          <BaseButton color="danger" label="Cerrar" class="mt-2" @click="() => showLoginPopup = false" />
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, watch, computed } from 'vue'
import BaseButton from '@/components/BaseButton.vue'
import { slotsService } from '@/services/slotsService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { reservationsService } from '@/services/reservationsService'
import { addOnServiceFeesService } from '@/services/addOnServiceFeesService'
import { useAuthStore } from '@/stores/auth.js'
import { useRouter } from 'vue-router'

const props = defineProps({
  show: Boolean,
  lot: Object,
  vehicleTypeId: Number,
  startDate: String,
  startTime: String,
  plate: String,
  vehicleTypeName: String,
  addOnServices: Array,
  selectedAddOns: Array,
  addOnServicesTotal: Number
})
const emit = defineEmits(['update:show', 'reserved', 'add-on-services-change'])

const authStore = useAuthStore()
const router = useRouter()
const selectedSlotId = ref('')
const availableSlots = ref([])
const lotPhone = ref('')
const formData = ref({
  startDate: '',
  startTime: '',
  plate: '',
  vehicleTypeName: ''
})
const showLoginPopup = ref(false)

watch(() => props.show, async (val) => {
  if (val && props.lot && props.vehicleTypeId) {
    // Obtener slots disponibles
    const slots = await slotsService.getSlotsByParkingLotAndVehicleType(props.lot.id, props.vehicleTypeId, true)
    availableSlots.value = slots
    selectedSlotId.value = slots.length > 0 ? slots[0].idSlot : ''
    const lotData = await parkingLotsService.getParkingLotById(props.lot.id)
    lotPhone.value = lotData.phone || 'N/A'
    // Usar los servicios adicionales filtrados que vienen por props
    formData.value = {
      startDate: props.startDate,
      startTime: props.startTime,
      plate: props.plate,
      vehicleTypeName: props.vehicleTypeName
    }
  }
})

// Proxy para manejar v-model bidireccional con el padre
const selectedAddOnsProxy = computed({
  get: () => props.selectedAddOns || [],
  set: (val) => emit('add-on-services-change', val)
})

// Emitir cambios al seleccionar/deseleccionar servicios
watch(selectedAddOnsProxy, (val) => {
  emit('add-on-services-change', val)
})

const handleReserve = async () => {
  if (!authStore.isAuthenticated()) {
    showLoginPopup.value = true
    return
  }
  if (!availableSlots.value.length) return
  const slot = availableSlots.value[0]
  // Log detallado para depuración
  console.log('Slot:', slot)
  console.log('slot.parkingLot:', slot.parkingLot)
  console.log('slot.vehicleType:', slot.vehicleType)
  // Validación y log para evitar error 500
  const parkingLotId = slot.parkingLot?.id || slot.parkingLot?.idParkingLot
  const vehicleTypeId = slot.vehicleType?.id || slot.vehicleType?.idVehicleType
  if (!slot || !slot.idSlot || !slot.name || !parkingLotId || !vehicleTypeId) {
    console.error('Slot incompleto para updateSlot:', slot)
    alert('Error: El slot seleccionado no tiene toda la información necesaria. Contacte al administrador.')
    return
  }
  console.log('Enviando slot a updateSlot:', slot)
  await slotsService.updateSlot(slot.idSlot, {
    ...slot,
    isAvailable: false,
    parkingLot: { id: parkingLotId },
    vehicleType: { id: vehicleTypeId }
  })
  // 1. Guardar la reservación estándar
  const now = new Date();
  const checkInTimestamp = new Date(formData.value.startDate + 'T' + formData.value.startTime);
  const scheduledDateTime = new Date(formData.value.startDate + 'T' + formData.value.startTime);
  const userId = authStore.user && (authStore.user.idUser || authStore.user.id || authStore.user.userId);
  if (!userId) {
    console.error('Objeto user en authStore:', authStore.user);
    alert('No se pudo obtener el usuario autenticado. Intenta cerrar sesión y volver a iniciar.');
    return;
  }
  const reservation = {
    parkingLot: { idParkingLot: props.lot.id },
    slot: { idSlot: slot.idSlot },
    checkIn: checkInTimestamp.toISOString(),
    reservationDate: now.toISOString(),
    scheduledDateTime: scheduledDateTime.toISOString(),
    plate: formData.value.plate,
    vehicleType: { idVehicleType: props.vehicleTypeId },
    statusReservation: { idStatusReservation: 1 }, // 1 = pendiente
    user: { idUser: userId },
    status: 'ACTIVE',
    startDate: formData.value.startDate, // opcional, por compatibilidad
    startTime: formData.value.startTime  // opcional, por compatibilidad
  }
  let reservationResponse = null;
  try {
    reservationResponse = await reservationsService.createStandardReservation(reservation)
  } catch (error) {
    console.error('Error al guardar la reserva:', error)
    alert('Ocurrió un error al guardar la reserva. Intenta de nuevo más tarde.')
    return
  }
  // 2. Guardar add_on_services_fees si hay servicios seleccionados
  if (selectedAddOnsProxy.value.length > 0 && reservationResponse && reservationResponse.idStandardReservation) {
    try {
      const addOnServiceFeeData = {
        total: selectedAddOnsProxy.value.reduce((acc, s) => acc + (s.price || 0), 0),
        addOnServices: JSON.stringify(selectedAddOnsProxy.value.map(s => ({
          idAddOnService: s.id,
          name: s.name,
          price: s.price
        }))),
        isActive: true,
        standardReservation: reservationResponse
      }
      console.log('Payload AddOnServiceFee:', addOnServiceFeeData)
      await addOnServiceFeesService.createAddOnServiceFee(addOnServiceFeeData)
    } catch (error) {
      console.error('Error al guardar servicios adicionales:', error)
      alert('La reserva fue guardada, pero ocurrió un error al guardar los servicios adicionales.')
    }
  }
  emit('reserved')
  emit('update:show', false)
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
