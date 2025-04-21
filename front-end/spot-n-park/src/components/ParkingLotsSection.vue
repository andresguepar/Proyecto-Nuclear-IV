<template>
  <div class="flex flex-col md:flex-row gap-4">
    <!-- Table Section -->
    <div class="w-full md:w-2/3">
      <div class="overflow-x-auto">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Address</th>
              <th>NIT</th>
              <th>Phone</th>
              <th>Status</th>
              <th />
            </tr>
          </thead>
          <tbody>
            <tr v-if="parkingLots.length === 0">
              <td colspan="7" class="text-center py-24 text-gray-500 dark:text-slate-400">
                <p>No parking lots found</p>
              </td>
            </tr>
            <tr v-for="lot in parkingLots" :key="lot.idParkingLot">
              <td data-label="ID">{{ lot.idParkingLot }}</td>
              <td data-label="Name">{{ lot.name }}</td>
              <td data-label="Address">{{ lot.address }}</td>
              <td data-label="NIT">{{ lot.nit }}</td>
              <td data-label="Phone">{{ lot.phone }}</td>
              <td data-label="Status">
                <span :class="getStatusClass(lot.isActive)">
                  {{ getStatusText(lot.isActive) }}
                </span>
              </td>
              <td class="before:hidden lg:w-1 whitespace-nowrap">
                <BaseButtons type="justify-start lg:justify-end" no-wrap>
                  <BaseButton
                    color="info"
                    :icon="mdiPencil"
                    small
                    @click="editParkingLot(lot)"
                  />
                  <BaseButton
                    color="danger"
                    :icon="mdiClose"
                    small
                    @click="toggleStatus(lot)"
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
        <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Parking Lot' : 'Create New Parking Lot' }}</h2>
        <form @submit.prevent="handleSubmit">
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Name</label>
            <input
              v-model="form.name"
              type="text"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Address</label>
            <input
              v-model="form.address"
              type="text"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">NIT</label>
            <input
              v-model="form.nit"
              type="text"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Phone</label>
            <input
              v-model="form.phone"
              type="text"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Coordinates X</label>
            <input
              v-model="form.coordX"
              type="text"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Coordinates Y</label>
            <input
              v-model="form.coordY"
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
import BaseIcon from '@/components/BaseIcon.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import CardBox from '@/components/CardBox.vue'
import { parkingLotsService } from '@/services/parkingLotsService'

const parkingLots = ref([])
const isEditing = ref(false)

const form = ref({
  name: '',
  address: '',
  nit: '',
  phone: '',
  coordX: '',
  coordY: '',
  isActive: true
})

const getStatusClass = (isActive) => {
  return isActive ? 'text-green-600 dark:text-green-400' : 'text-red-600 dark:text-red-400'
}

const getStatusText = (isActive) => {
  return isActive ? 'Active' : 'Inactive'
}

const editParkingLot = (lot) => {
  isEditing.value = true
  form.value = {
    idParkingLot: lot.idParkingLot,
    name: lot.name,
    address: lot.address,
    nit: lot.nit,
    phone: lot.phone,
    coordX: lot.coordX,
    coordY: lot.coordY,
    isActive: lot.isActive
  }
}

const toggleStatus = async (lot) => {
  try {
    const updatedLot = { ...lot, isActive: !lot.isActive }
    await parkingLotsService.updateParkingLot(lot.idParkingLot, updatedLot)
    await fetchParkingLots()
  } catch (error) {
    console.error('Error toggling parking lot status:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      await parkingLotsService.updateParkingLot(form.value.idParkingLot, form.value)
    } else {
      await parkingLotsService.createParkingLot(form.value)
    }
    await fetchParkingLots()
    resetForm()
  } catch (error) {
    console.error('Error saving parking lot:', error)
  }
}

const resetForm = () => {
  form.value = {
    name: '',
    address: '',
    nit: '',
    phone: '',
    coordX: '',
    coordY: '',
    isActive: true
  }
  isEditing.value = false
}

const fetchParkingLots = async () => {
  try {
    parkingLots.value = await parkingLotsService.getAllParkingLots()
  } catch (error) {
    console.error('Error fetching parking lots:', error)
  }
}

onMounted(async () => {
  await fetchParkingLots()
})
</script> 