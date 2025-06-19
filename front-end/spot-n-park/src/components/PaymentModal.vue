<template>
  <CardBoxModal
    v-model="isActive"
    title="Procesar Pago"
    button="success"
    has-cancel
    @confirm="processPayment"
  >
    <div v-if="reservation" class="space-y-4">
      <div class="bg-gray-50 p-4 rounded-lg">
        <h4 class="font-semibold text-gray-800 mb-2">Detalles de la Reserva</h4>
        <div class="grid grid-cols-2 gap-2 text-sm">
          <div><span class="font-medium">Parqueadero:</span> {{ reservation.parkingName }}</div>
          <div><span class="font-medium">Slot:</span> {{ reservation.slot }}</div>
          <div><span class="font-medium">Fecha:</span> {{ reservation.date }}</div>
          <div><span class="font-medium">Hora:</span> {{ reservation.time }}</div>
          <div><span class="font-medium">Placa:</span> {{ reservation.plate }}</div>
          <div><span class="font-medium">Precio/hora:</span> ${{ reservation.pricePerHour }}</div>
        </div>
      </div>

      <!-- Cálculo de horas y costo -->
      <div class="bg-green-50 p-4 rounded-lg">
        <h4 class="font-semibold text-green-800 mb-2">Cálculo del Pago</h4>
        <div class="space-y-2 text-sm">
          <div class="flex justify-between">
            <span>Horas de estacionamiento:</span>
            <span class="font-medium">{{ calculatedHours }} horas</span>
          </div>
          <div class="flex justify-between">
            <span>Precio por hora:</span>
            <span class="font-medium">${{ reservation.pricePerHour }}</span>
          </div>
          <div class="border-t pt-2 flex justify-between text-lg font-bold text-green-800">
            <span>Total a pagar:</span>
            <span>${{ totalAmount }}</span>
          </div>
        </div>
      </div>

      <div class="space-y-3">
        <label class="block text-sm font-medium text-gray-700">
          Método de Pago
        </label>
        <select
          v-model="selectedPaymentMethod"
          class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          required
        >
          <option value="">Selecciona un método de pago</option>
          <option
            v-for="method in paymentMethods"
            :key="method.idPaymentMethod"
            :value="method.idPaymentMethod"
          >
            {{ method.name }}
          </option>
        </select>
      </div>

      <div class="bg-blue-50 p-3 rounded-lg">
        <p class="text-sm text-blue-800">
          <strong>Nota:</strong> Este es un proceso de pago simulado. En un entorno de producción, 
          se integraría con una pasarela de pago real como Stripe, PayPal, etc.
        </p>
      </div>
    </div>
    
    <div v-else class="text-center py-8">
      <p class="text-gray-500">Cargando detalles de la reserva...</p>
    </div>
  </CardBoxModal>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import { paymentMethodsService } from '@/services/paymentMethodsService.js'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  reservation: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['update:modelValue', 'payment-processed'])

const isActive = ref(props.modelValue)
const paymentMethods = ref([])
const selectedPaymentMethod = ref('')

// Computed properties para el cálculo
const calculatedHours = computed(() => {
  if (!props.reservation) return 0
  
  // Simular cálculo de horas (en un caso real, esto vendría del backend)
  // Por ahora, usamos un valor fijo para demostración
  return 2.5
})

const totalAmount = computed(() => {
  if (!props.reservation || !props.reservation.pricePerHour) return 0
  
  const hours = calculatedHours.value
  const pricePerHour = parseFloat(props.reservation.pricePerHour) || 15
  return (hours * pricePerHour).toFixed(2)
})

// Computed property para sincronizar el v-model
const updateModelValue = (value) => {
  emit('update:modelValue', value)
}

// Watch para cambios en modelValue
watch(() => props.modelValue, (newValue) => {
  isActive.value = newValue
  // Si se abre el modal pero no hay reserva, cerrarlo automáticamente
  if (newValue && !props.reservation) {
    setTimeout(() => {
      isActive.value = false
    }, 100)
  }
})

// Watch para cambios en isActive
watch(isActive, (newValue) => {
  updateModelValue(newValue)
})

const fetchPaymentMethods = async () => {
  try {
    paymentMethods.value = await paymentMethodsService.getActivePaymentMethods()
  } catch (error) {
    console.error('Error fetching payment methods:', error)
  }
}

const processPayment = async () => {
  if (!props.reservation) {
    alert('No hay reserva seleccionada')
    return
  }
  
  if (!selectedPaymentMethod.value) {
    alert('Por favor selecciona un método de pago')
    return
  }

  try {
    // Aquí se procesaría el pago con el backend
    emit('payment-processed', {
      reservationId: props.reservation.id,
      paymentMethodId: selectedPaymentMethod.value
    })
    
    // Cerrar el modal
    isActive.value = false
    selectedPaymentMethod.value = ''
  } catch (error) {
    console.error('Error processing payment:', error)
    alert('Error al procesar el pago. Por favor intenta de nuevo.')
  }
}

onMounted(() => {
  fetchPaymentMethods()
})
</script> 