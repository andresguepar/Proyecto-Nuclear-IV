<script setup>
import {ref} from 'vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'

const form = ref({
  parkingLot: '',
  startDate: '',
  startTime: '',
  plate: '',
  type: 'vehicle',
  duration: '1'
})

const durationOptions = [
  { value: '1', label: '1 hour' },
  { value: '2', label: '2 hours' },
  { value: '3', label: '3 hours' },
  { value: '4', label: '4 hours' },
  { value: '5', label: '5 hours' },
  { value: '6', label: '6 hours' },
  { value: '7', label: '7 hours' },
  { value: '8', label: '8 hours' }
]

defineEmits(['form-update'])
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-2">
    <FormField label="Parking Lot" class="text-sm">
      <FormControl
        v-model="form.parkingLot"
        type="text"
        placeholder="Select parking lot"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Parking start date" class="text-sm">
      <FormControl
        v-model="form.startDate"
        type="date"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Start time" class="text-sm">
      <FormControl
        v-model="form.startTime"
        type="time"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Registration plate" class="text-sm">
      <FormControl
        v-model="form.plate"
        type="text"
        placeholder="Enter plate number"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Duration" class="text-sm">
      <FormControl
        v-model="form.duration"
        type="select"
        class="h-7 text-sm"
        :options="durationOptions"
      />
    </FormField>
  </div>
</template> 