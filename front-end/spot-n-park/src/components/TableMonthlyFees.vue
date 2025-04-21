<template>
  <CardBox class="mb-6" has-table>
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold">Monthly Fees</h2>
      <BaseButton
        color="info"
        label="Add Monthly Fee"
        @click="showAddModal = true"
      />
    </div>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Parking Lot</th>
          <th>Vehicle Type</th>
          <th>Price</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="fee in monthlyFees" :key="fee.idMonthlyFee">
          <td data-label="ID">{{ fee.idMonthlyFee }}</td>
          <td data-label="Parking Lot">{{ fee.parkingLot.name }}</td>
          <td data-label="Vehicle Type">{{ fee.vehicleType.name }}</td>
          <td data-label="Price">${{ fee.price }}</td>
          <td data-label="Status">
            <BaseBadge
              :color="fee.status === 'active' ? 'success' : 'danger'"
              :label="fee.status"
            />
          </td>
          <td class="before:hidden lg:w-1 whitespace-nowrap">
            <BaseButtons type="justify-start lg:justify-end" no-wrap>
              <BaseButton
                color="info"
                icon="pencil"
                small
                @click="editFee(fee)"
              />
              <BaseButton
                color="danger"
                icon="trash"
                small
                @click="deleteFee(fee.idMonthlyFee)"
              />
            </BaseButtons>
          </td>
        </tr>
      </tbody>
    </table>
  </CardBox>

  <!-- Add/Edit Modal -->
  <CardBoxModal
    v-model="showAddModal"
    title="Monthly Fee"
    button="info"
    has-cancel
    @confirm="saveFee"
  >
    <FormField label="Parking Lot">
      <FormControl
        v-model="currentFee.id_parking_lot"
        :options="parkingLots"
        type="select"
      />
    </FormField>
    <FormField label="Vehicle Type">
      <FormControl
        v-model="currentFee.id_vehicle_type"
        :options="vehicleTypes"
        type="select"
      />
    </FormField>
    <FormField label="Price">
      <FormControl
        v-model="currentFee.price"
        type="number"
        step="0.01"
      />
    </FormField>
    <FormField label="Status">
      <FormControl
        v-model="currentFee.status"
        :options="statusOptions"
        type="select"
      />
    </FormField>
  </CardBoxModal>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { monthlyFeesService } from '@/services/monthlyFeesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'
import CardBox from '@/components/CardBox.vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseBadge from '@/components/BaseBadge.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'

const monthlyFees = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const showAddModal = ref(false)
const currentFee = ref({
  idMonthlyFee: null,
  id_parking_lot: '',
  id_vehicle_type: '',
  price: 0,
  status: 'active'
})

const statusOptions = [
  { id: 'active', label: 'Active' },
  { id: 'inactive', label: 'Inactive' }
]

const loadData = async () => {
  try {
    const [fees, lots, types] = await Promise.all([
      monthlyFeesService.getAllMonthlyFees(),
      parkingLotsService.getAllParkingLots(),
      vehicleTypesService.getAllVehicleTypes()
    ])
    
    monthlyFees.value = fees
    parkingLots.value = lots.map(lot => ({
      id: lot.idParkingLot,
      label: lot.name
    }))
    vehicleTypes.value = types.map(type => ({
      id: type.idVehicleType,
      label: type.name
    }))
  } catch (error) {
    console.error('Error loading data:', error)
  }
}

const editFee = (fee) => {
  currentFee.value = {
    idMonthlyFee: fee.idMonthlyFee,
    id_parking_lot: fee.parkingLot.idParkingLot,
    id_vehicle_type: fee.vehicleType.idVehicleType,
    price: fee.price,
    status: fee.status
  }
  showAddModal.value = true
}

const saveFee = async () => {
  try {
    if (currentFee.value.idMonthlyFee) {
      await monthlyFeesService.updateMonthlyFee(currentFee.value.idMonthlyFee, currentFee.value)
    } else {
      await monthlyFeesService.createMonthlyFee(currentFee.value)
    }
    await loadData()
    showAddModal.value = false
    currentFee.value = {
      idMonthlyFee: null,
      id_parking_lot: '',
      id_vehicle_type: '',
      price: 0,
      status: 'active'
    }
  } catch (error) {
    console.error('Error saving fee:', error)
  }
}

const deleteFee = async (id) => {
  if (confirm('Are you sure you want to delete this fee?')) {
    try {
      await monthlyFeesService.deleteMonthlyFee(id)
      await loadData()
    } catch (error) {
      console.error('Error deleting fee:', error)
    }
  }
}

onMounted(loadData)
</script> 