<template>
  <CardBox class="mb-6" has-table>
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold">Standard Fees</h2>
      <BaseButton
        color="info"
        label="Add Standard Fee"
        @click="showAddModal = true"
      />
    </div>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Parking Lot</th>
          <th>Vehicle Type</th>
          <th>Price per Hour</th>
          <th>Price per 12 Hours</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="fee in standardFees" :key="fee.idStandardFee">
          <td data-label="ID">{{ fee.idStandardFee }}</td>
          <td data-label="Parking Lot">{{ fee.parkingLot.name }}</td>
          <td data-label="Vehicle Type">{{ fee.vehicleType.name }}</td>
          <td data-label="Price per Hour">${{ fee.priceXHours }}</td>
          <td data-label="Price per 12 Hours">${{ fee.priceX12Hours }}</td>
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
                @click="deleteFee(fee.idStandardFee)"
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
    title="Standard Fee"
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
    <FormField label="Price per Hour">
      <FormControl
        v-model="currentFee.price_x_hours"
        type="number"
        step="0.01"
      />
    </FormField>
    <FormField label="Price per 12 Hours">
      <FormControl
        v-model="currentFee.price_x_12_hours"
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
import { standardFeesService } from '@/services/standardFeesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'
import CardBox from '@/components/CardBox.vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseBadge from '@/components/BaseBadge.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'

const standardFees = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const showAddModal = ref(false)
const currentFee = ref({
  idStandardFee: null,
  id_parking_lot: '',
  id_vehicle_type: '',
  price_x_hours: 0,
  price_x_12_hours: 0,
  status: 'active'
})

const statusOptions = [
  { id: 'active', label: 'Active' },
  { id: 'inactive', label: 'Inactive' }
]

const loadData = async () => {
  try {
    const [fees, lots, types] = await Promise.all([
      standardFeesService.getAllStandardFees(),
      parkingLotsService.getAllParkingLots(),
      vehicleTypesService.getAllVehicleTypes()
    ])
    
    standardFees.value = fees
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
    idStandardFee: fee.idStandardFee,
    id_parking_lot: fee.parkingLot.idParkingLot,
    id_vehicle_type: fee.vehicleType.idVehicleType,
    price_x_hours: fee.priceXHours,
    price_x_12_hours: fee.priceX12Hours,
    status: fee.status
  }
  showAddModal.value = true
}

const saveFee = async () => {
  try {
    if (currentFee.value.idStandardFee) {
      await standardFeesService.updateStandardFee(currentFee.value.idStandardFee, currentFee.value)
    } else {
      await standardFeesService.createStandardFee(currentFee.value)
    }
    await loadData()
    showAddModal.value = false
    currentFee.value = {
      idStandardFee: null,
      id_parking_lot: '',
      id_vehicle_type: '',
      price_x_hours: 0,
      price_x_12_hours: 0,
      status: 'active'
    }
  } catch (error) {
    console.error('Error saving fee:', error)
  }
}

const deleteFee = async (id) => {
  if (confirm('Are you sure you want to delete this fee?')) {
    try {
      await standardFeesService.deleteStandardFee(id)
      await loadData()
    } catch (error) {
      console.error('Error deleting fee:', error)
    }
  }
}

onMounted(loadData)
</script> 