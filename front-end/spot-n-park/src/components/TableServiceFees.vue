<template>
  <CardBox class="mb-6" has-table>
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold">Service Fees</h2>
      <BaseButton
        color="info"
        label="Add Service Fee"
        @click="showAddModal = true"
      />
    </div>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Parking Lot</th>
          <th>Service</th>
          <th>Price</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="fee in serviceFees" :key="fee.idServiceFee">
          <td data-label="ID">{{ fee.idServiceFee }}</td>
          <td data-label="Parking Lot">{{ fee.parkingLot.name }}</td>
          <td data-label="Service">{{ fee.service.name }}</td>
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
                @click="deleteFee(fee.idServiceFee)"
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
    title="Service Fee"
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
    <FormField label="Service">
      <FormControl
        v-model="currentFee.id_service"
        :options="services"
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
import { serviceFeesService } from '@/services/serviceFeesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { servicesService } from '@/services/servicesService'
import CardBox from '@/components/CardBox.vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseBadge from '@/components/BaseBadge.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'

const serviceFees = ref([])
const parkingLots = ref([])
const services = ref([])
const showAddModal = ref(false)
const currentFee = ref({
  idServiceFee: null,
  id_parking_lot: '',
  id_service: '',
  price: 0,
  status: 'active'
})

const statusOptions = [
  { id: 'active', label: 'Active' },
  { id: 'inactive', label: 'Inactive' }
]

const loadData = async () => {
  try {
    const [fees, lots, svcs] = await Promise.all([
      serviceFeesService.getAllServiceFees(),
      parkingLotsService.getAllParkingLots(),
      servicesService.getAllServices()
    ])
    
    serviceFees.value = fees
    parkingLots.value = lots.map(lot => ({
      id: lot.idParkingLot,
      label: lot.name
    }))
    services.value = svcs.map(svc => ({
      id: svc.idService,
      label: svc.name
    }))
  } catch (error) {
    console.error('Error loading data:', error)
  }
}

const editFee = (fee) => {
  currentFee.value = {
    idServiceFee: fee.idServiceFee,
    id_parking_lot: fee.parkingLot.idParkingLot,
    id_service: fee.service.idService,
    price: fee.price,
    status: fee.status
  }
  showAddModal.value = true
}

const saveFee = async () => {
  try {
    if (currentFee.value.idServiceFee) {
      await serviceFeesService.updateServiceFee(currentFee.value.idServiceFee, currentFee.value)
    } else {
      await serviceFeesService.createServiceFee(currentFee.value)
    }
    await loadData()
    showAddModal.value = false
    currentFee.value = {
      idServiceFee: null,
      id_parking_lot: '',
      id_service: '',
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
      await serviceFeesService.deleteServiceFee(id)
      await loadData()
    } catch (error) {
      console.error('Error deleting fee:', error)
    }
  }
}

onMounted(loadData)
</script> 