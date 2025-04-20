<script setup>
import {ref} from 'vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'

const form = ref({
  parkingLot: '',
  startDate: '',
  plate: '',
  type: 'vehicle',
  months: '1',
  paymentMethod: 'credit'
})

const monthOptions = [
  { value: '1', label: '1 month' },
  { value: '3', label: '3 months' },
  { value: '6', label: '6 months' },
  { value: '12', label: '12 months' }
]

const paymentOptions = [
  { value: 'credit', label: 'Credit Card' },
  { value: 'debit', label: 'Debit Card' },
  { value: 'bank', label: 'Bank Transfer' }
]

defineEmits(['form-update'])
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-2">
    <FormField label="Parking Lot" class="text-sm">
      <FormControl
        v-model="form.parkingLot"
        type="text"
        placeholder="Select parking lot"
        class="h-7 text-sm"
      />
    </FormField>
    <FormField label="Subscription start date" class="text-sm">
      <FormControl
        v-model="form.startDate"
        type="date"
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
    <FormField label="Subscription period" class="text-sm">
      <FormControl
        v-model="form.months"
        type="select"
        class="h-7 text-sm"
        :options="monthOptions"
      />
    </FormField>
    <FormField label="Payment method" class="text-sm">
      <FormControl
        v-model="form.paymentMethod"
        type="select"
        class="h-7 text-sm"
        :options="paymentOptions"
      />
    </FormField>
  </div>
</template> 