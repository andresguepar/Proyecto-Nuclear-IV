<template>
  <div class="flex flex-col md:flex-row gap-4 max-w-5xl mx-auto">
    <!-- Table Section (izquierda en desktop) -->
    <div class="md:w-3/4 w-full order-1 md:order-1">
      <CardBox>
        <div class="overflow-x-auto p-2">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <!-- <th>Parking Lot</th> -->
                <th>Vehicle Type</th>
                <th>Name</th>
                <th>Status</th>
                <th />
              </tr>
            </thead>
            <tbody>
              <tr v-if="filteredSlots.length === 0">
                <td colspan="5" class="text-center py-24 text-gray-500 dark:text-slate-400">
                  <p>No slots found</p>
                </td>
              </tr>
              <tr v-for="slot in filteredSlots" :key="slot.idSlot">
                <td data-label="ID">{{ slot.idSlot }}</td>
                <!-- <td data-label="Parking Lot">{{ slot.parkingLot?.name }}</td> -->
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
      </CardBox>
    </div>

    <!-- Form Section (derecha en desktop) -->
    <div class="md:w-1/4 w-full order-2 md:order-2">
      <CardBox>
        <div class="p-2">
          <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Slot' : 'Create New Slot' }}</h2>
          <form @submit.prevent="handleSubmit">
            <!-- Mostrar el campo Parking Lot solo si es super_admin -->
            <div v-if="user.value && user.value.role && user.value.role.name === 'super_admin'" class="mb-4">
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
            <div class="mb-4">
              <label class="block text-sm font-medium mb-2">Disponible</label>
              <input
                v-model="form.isAvailable"
                type="checkbox"
                class="mr-2"
              />
              <span>{{ form.isAvailable ? 'Sí' : 'No' }}</span>
            </div>
            <div class="flex justify-end">
              <button
                type="submit"
                class="px-4 py-2 bg-[#0D2F78] text-white rounded hover:bg-[#0B245A] dark:bg-[#0B245A] dark:hover:bg-[#0D2F78]"
              >
                {{ isEditing ? 'Update' : 'Create' }}
              </button>
            </div>
          </form>
        </div>
      </CardBox>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { mdiPencil, mdiClose } from '@mdi/js'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import CardBox from '@/components/CardBox.vue'
import { slotsService } from '@/services/slotsService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'
import { useAuthStore } from '@/stores/auth.js'

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

const authStore = useAuthStore()
const user = computed(() => authStore.user)

// Filtrar slots solo del parking lot del admin logueado
const filteredSlots = computed(() => {
  if (!user.value) return []
  // Si es super_admin, mostrar todos
  if (user.value.role?.name === 'super_admin') return slots.value
  // Si es park_admin, mostrar solo los slots de sus parqueaderos
  return slots.value.filter(slot => slot.parkingLot?.admin?.idUser === user.value.idUser)
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
    // Si el usuario es park_admin, asignar automáticamente su parking lot
    if (user.value && user.value.role && user.value.role.name === 'park_admin') {
      // Buscar el parking lot administrado por el usuario logueado
      const myLot = parkingLots.value.find(lot => lot.admin?.idUser === user.value.idUser)
      if (myLot) {
        form.value.parkingLot.id = myLot.idParkingLot
      }
    }
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
