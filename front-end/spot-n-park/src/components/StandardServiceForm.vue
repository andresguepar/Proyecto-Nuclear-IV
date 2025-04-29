<script setup>
import {ref, onMounted} from 'vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import BaseButton from '@/components/BaseButton.vue'
import { parkingLotsService } from '@/services/parkingLotsService'

const form = ref({
  parkingLot: '',
  startDate: '',
  startTime: '',
  plate: '',
  type: 'vehicle',
  duration: '1'
})

const parkingLots = ref([])

const handleSearch = () => {
  console.log('Searching with:', form.value)
}

const fetchParkingLots = async () => {
  try {
    const lots = await parkingLotsService.getAllParkingLots()
    parkingLots.value = lots.map(lot => ({
      value: lot.id,
      label: lot.name
    }))
  } catch (error) {
    console.error('Error fetching parking lots:', error)
  }
}

onMounted(() => {
  fetchParkingLots()
})

defineEmits(['form-update'])
</script>

<template>
  <div class="space-y-4">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">
      <FormField label="Parking Lot" class="text-sm">
        <FormControl
          v-model="form.parkingLot"
          type="select"
          placeholder="Select parking lot"
          class="h-10 text-sm"
          :options="parkingLots"
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
      <div class="self-center">
        <BaseButton
          color="info"
          label="Search"
          class="h-13 w-full"
          @click="handleSearch"
        />
      </div>
    </div>
  </div>
</template>
