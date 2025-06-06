<script setup>
import {ref, onMounted, watch} from 'vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import BaseButton from '@/components/BaseButton.vue'
import { vehicleTypesService } from '@/services/vehicleTypesService'

const form = ref({
  vehicleType: '',
  startDate: '',
  startTime: '',
  plate: '',
  type: 'vehicle',
  duration: '1'
})

const vehicleTypes = ref([])
const availableVehicleTypes = ref([])

const emit = defineEmits(['form-update', 'vehicle-type-change'])

const isFormValid = () => {
  return (
    form.value.vehicleType &&
    form.value.startDate &&
    form.value.startTime &&
    form.value.plate
  )
}

const handleSearch = () => {
  if (!isFormValid()) {
    alert('Por favor completa todos los campos para buscar parqueaderos disponibles.')
    return
  }
  emit('form-update', form.value)
}

const fetchVehicleTypes = async () => {
  try {
    const types = await vehicleTypesService.getAllVehicleTypes()
    vehicleTypes.value = types.map(type => ({
      value: type.idVehicleType,
      label: type.name
    }))
    availableVehicleTypes.value = vehicleTypes.value
  } catch (error) {
    console.error('Error fetching vehicle types:', error)
  }
}

watch(() => form.value.vehicleType, (newVehicleType) => {
  emit('vehicle-type-change', {
    vehicleTypeId: newVehicleType
  })
})

watch(() => form.value.startDate, (newDate) => {
  console.log('Date changed in form:', newDate)
  emit('form-update', form.value)
})

onMounted(async () => {
  await fetchVehicleTypes()
})
</script>

<template>
  <div class="space-y-4">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">
      <FormField label="Vehicle Type" class="text-sm">
        <FormControl
          v-model="form.vehicleType"
          type="select"
          placeholder="Select vehicle type"
          class="h-10 text-sm"
          :options="availableVehicleTypes"
        />
      </FormField>
      <FormField label="Date" class="text-sm">
        <FormControl
          v-model="form.startDate"
          type="date"
          class="h-10 text-sm"
        />
      </FormField>
      <FormField label="Start Time" class="text-sm">
        <FormControl
          v-model="form.startTime"
          type="time"
          class="h-10 text-sm"
        />
      </FormField>
      <FormField label="License Plate" class="text-sm">
        <FormControl
          v-model="form.plate"
          type="text"
          placeholder="Enter plate number"
          class="h-10 text-sm"
        />
      </FormField>
      <div class="self-center flex items-end">
        <BaseButton
          color="info"
          label="Search"
          class="h-13 w-full"
          :disabled="!isFormValid()"
          @click="handleSearch"
        />
      </div>
    </div>
  </div>
</template>
