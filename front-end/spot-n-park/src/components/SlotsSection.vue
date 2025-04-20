<template>
  <div class="flex flex-col md:flex-row gap-4 p-4">
    <!-- Table Section -->
    <div class="w-full md:w-2/3">
      <div class="bg-white dark:bg-slate-800 rounded-lg shadow">
        <div class="p-4">
          <h2 class="text-xl font-bold mb-4">Slots</h2>
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
                <tr v-for="slot in slots" :key="slot.id">
                  <td data-label="ID">{{ slot.id }}</td>
                  <td data-label="Parking Lot">{{ slot.parking_lot_name }}</td>
                  <td data-label="Vehicle Type">{{ slot.vehicle_type_name }}</td>
                  <td data-label="Name">{{ slot.name }}</td>
                  <td data-label="Status">
                    <span :class="getStatusClass(slot.status)">
                      {{ slot.status }}
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
                        @click="deleteSlot(slot.id)"
                      />
                    </BaseButtons>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!-- Form Section -->
    <div class="w-full md:w-1/3">
      <div class="bg-white dark:bg-slate-800 rounded-lg shadow p-4">
        <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Slot' : 'Create New Slot' }}</h2>
        <form @submit.prevent="handleSubmit">
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Parking Lot</label>
            <select
              v-model="form.id_parking_lot"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            >
              <option v-for="lot in parkingLots" :key="lot.id" :value="lot.id">
                {{ lot.name }}
              </option>
            </select>
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Vehicle Type</label>
            <select
              v-model="form.id_vehicle_type"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            >
              <option v-for="type in vehicleTypes" :key="type.id" :value="type.id">
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
          <div class="mb-4">
            <label class="block text-sm font-medium mb-2">Status</label>
            <select
              v-model="form.status"
              class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
              required
            >
              <option value="available">Available</option>
              <option value="occupied">Occupied</option>
              <option value="maintenance">Maintenance</option>
            </select>
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
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiPencil, mdiClose } from '@mdi/js'
import BaseIcon from '@/components/BaseIcon.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'

const slots = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const isEditing = ref(false)

const form = ref({
  id_parking_lot: '',
  id_vehicle_type: '',
  name: '',
  status: 'available'
})

const getStatusClass = (status) => {
  const classes = {
    available: 'text-green-600 dark:text-green-400',
    occupied: 'text-red-600 dark:text-red-400',
    maintenance: 'text-yellow-600 dark:text-yellow-400'
  }
  return classes[status] || ''
}

const editSlot = (slot) => {
  isEditing.value = true
  form.value = { ...slot }
}

const deleteSlot = async (id) => {
  // Implement delete functionality
  console.log('Delete slot:', id)
}

const handleSubmit = async () => {
  // Implement create/update functionality
  console.log('Form submitted:', form.value)
}

onMounted(() => {
  // Fetch initial data
  // slots.value = await fetchSlots()
  // parkingLots.value = await fetchParkingLots()
  // vehicleTypes.value = await fetchVehicleTypes()
})
</script> 