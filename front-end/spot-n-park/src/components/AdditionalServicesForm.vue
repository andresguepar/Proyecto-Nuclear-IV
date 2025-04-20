<script setup>
import {ref} from 'vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'

const form = ref({
  parkingLot: '',
  serviceDate: '',
  serviceTime: '',
  plate: '',
  serviceType: 'wash',
  notes: ''
})

const serviceOptions = [
  { value: 'wash', label: 'Car Wash' },
  { value: 'oil', label: 'Oil Change' },
  { value: 'tire', label: 'Tire Service' },
  { value: 'battery', label: 'Battery Check' },
  { value: 'inspection', label: 'Vehicle Inspection' }
]

defineEmits(['form-update'])
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-2">
    <FormField label="Parking Lot" class="text-sm">
      <FormControl
        v-model="form.parkingLot"
        type="text"
        placeholder="Select parking lot"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Service date" class="text-sm">
      <FormControl
        v-model="form.serviceDate"
        type="date"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Service time" class="text-sm">
      <FormControl
        v-model="form.serviceTime"
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
    <FormField label="Service type" class="text-sm">
      <FormControl
        v-model="form.serviceType"
        type="select"
        class="h-7 text-sm"
        :options="serviceOptions"
      />
    </FormField>
    <FormField label="Additional notes" class="text-sm">
      <FormControl
        v-model="form.notes"
        type="textarea"
        placeholder="Enter any special requirements"
        class="h-7 text-sm"
      />
    </FormField>
  </div>
</template> 