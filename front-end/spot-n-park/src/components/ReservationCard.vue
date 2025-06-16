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
              Precio/hora: ${{ reservation.pricePerHour }}
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
              {{ reservation.status }}
            </span>
          </div>
          <div class="flex items-center space-x-2">
            <BaseIcon
              path="M12 3v1m0 4v1m6 4h2a2 2 0 002-2V7a2 2 0 00-2-2h-2M6 5H4a2 2 0 00-2 2v4a2 2 0 002 2h2"
              class="w-6 h-6 text-gray-500"
            />
            <span class="text-base text-gray-600">
              Horario: {{ reservation.startDate && reservation.startTime ? `${reservation.startDate} ${reservation.startTime}${reservation.endTime ? ' - ' + reservation.endTime : ''}` : reservation.hours }}
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
        </div>
      </div>
    </CardBoxComponentBody>
    <div class="p-4 border-t border-gray-100">
      <div class="flex justify-between space-x-2">
        <BaseButton
          color="danger"
          label="Cancel"
          :disabled="reservation.status === 'CANCELED'"
          @click="$emit('cancel', reservation)"
        />
        <BaseButton
          color="info"
          label="Edit"
          :disabled="reservation.status === 'CANCELED'"
          @click="$emit('edit', reservation)"
        />
        <BaseButton
          color="success"
          label="Confirmar"
          :disabled="reservation.status === 'CANCELED'"
          @click="$emit('confirm', reservation)"
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

const getStatusClass = (status) => {
  switch (status) {
    case 'ACTIVE':
      return 'text-green-600'
    case 'CANCELED':
      return 'text-red-600'
    case 'COMPLETED':
      return 'text-blue-600'
    default:
      return 'text-gray-600'
  }
}

defineProps({
  reservation: {
    type: Object,
    required: true
  }
})

defineEmits(['edit', 'cancel', 'confirm'])
</script>
