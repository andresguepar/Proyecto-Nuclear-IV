<template>
  <div class="flex flex-col md:flex-row gap-4">
    <!-- Table Section -->
    <div class="w-full md:w-2/3">
      <div class="overflow-x-auto">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Parking Lot</th>
              <th>Vehicle Type</th>
              <th>Name</th>
              <th>Status</th>
              <th />
            </tr>
          </thead>
          <tbody>
            <tr v-if="slots.length === 0">
              <td colspan="6" class="text-center py-24 text-gray-500 dark:text-slate-400">
                <p>No slots found</p>
              </td>
            </tr>
            <tr v-for="slot in slots" :key="slot.idSlot">
              <td data-label="ID">{{ slot.idSlot }}</td>
              <td data-label="Parking Lot">{{ slot.parkingLot?.name }}</td>
              <td data-label="Vehicle Type">{{ slot.vehicleType?.name }}</td>
              <td data-label="Name">{{ slot.name }}</td>
              <td data-label="Status">
                <span :class="getStatusClass(slot.isAvailable)">
                  {{ getStatusText(slot.isAvailable) }}
                </span>
              </td>
              <td class="before:hidden lg:w-1 whitespace-nowrap">
                <BaseButtons type="justify-start lg:justify-end" no-wrap>
                  <BaseButton
                    color="info"
                    :icon="mdiPencil"
                    small
                    @click="editSlot(slot)"
                  />
                  <BaseButton
                    color="danger"
                    :icon="mdiClose"
                    small
                    @click="toggleStatus(slot)"
                  />
                </BaseButtons>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Form Section -->
    <div class="w-full md:w-1/3">
      <CardBox>
        <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Slot' : 'Create New Slot' }}</h2>
        <form @submit.prevent="handleSubmit">
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Parking Lot</label>
            <select
              v-model="form.parkingLot.id"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            >
              <option v-for="lot in parkingLots" :key="lot.idParkingLot" :value="lot.idParkingLot">
                {{ lot.name }}
              </option>
            </select>
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Vehicle Type</label>
            <select
              v-model="form.vehicleType.id"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            >
              <option v-for="type in vehicleTypes" :key="type.idVehicleType" :value="type.idVehicleType">
                {{ type.name }}
              </option>
            </select>
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Name</label>
            <input
              v-model="form.name"
              type="text"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            />
          </div>
          <div class="flex justify-end">
            <button
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 dark:bg-blue-500 dark:hover:bg-blue-600"
            >
              {{ isEditing ? 'Update' : 'Create' }}
            </button>
          </div>
        </form>
      </CardBox>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiPencil, mdiClose } from '@mdi/js'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import CardBox from '@/components/CardBox.vue'
import { slotsService } from '@/services/slotsService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'

const slots = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const isEditing = ref(false)

const form = ref({
  idSlot: null,
  parkingLot: { id: '' },
  vehicleType: { id: '' },
  name: '',
  isAvailable: true
})

const getStatusClass = (isAvailable) => {
  return isAvailable ? 'text-green-600 dark:text-green-400' : 'text-red-600 dark:text-red-400'
}

const getStatusText = (isAvailable) => {
  return isAvailable ? 'Available' : 'Occupied'
}

const editSlot = (slot) => {
  isEditing.value = true
  form.value = {
    idSlot: slot.idSlot,
    parkingLot: { id: slot.parkingLot?.idParkingLot },
    vehicleType: { id: slot.vehicleType?.idVehicleType },
    name: slot.name,
    isAvailable: slot.isAvailable
  }
}

const toggleStatus = async (slot) => {
  try {
    const updatedSlot = { ...slot, isAvailable: !slot.isAvailable }
    await slotsService.updateSlot(slot.idSlot, updatedSlot)
    await fetchSlots()
  } catch (error) {
    console.error('Error toggling slot status:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      await slotsService.updateSlot(form.value.idSlot, form.value)
    } else {
      await slotsService.createSlot(form.value)
    }
    await fetchSlots()
    resetForm()
  } catch (error) {
    console.error('Error saving slot:', error)
  }
}

const resetForm = () => {
  form.value = {
    idSlot: null,
    parkingLot: { id: '' },
    vehicleType: { id: '' },
    name: '',
    isAvailable: true
  }
  isEditing.value = false
}

const fetchSlots = async () => {
  try {
    slots.value = await slotsService.getAllSlots()
  } catch (error) {
    console.error('Error fetching slots:', error)
  }
}

const fetchParkingLots = async () => {
  try {
    parkingLots.value = await parkingLotsService.getAllParkingLots()
  } catch (error) {
    console.error('Error fetching parking lots:', error)
  }
}

const fetchVehicleTypes = async () => {
  try {
    vehicleTypes.value = await vehicleTypesService.getAllVehicleTypes()
  } catch (error) {
    console.error('Error fetching vehicle types:', error)
  }
}

onMounted(async () => {
  await Promise.all([
    fetchSlots(),
    fetchParkingLots(),
    fetchVehicleTypes()
  ])
})
</script>
