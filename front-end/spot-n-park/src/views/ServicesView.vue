<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiCarWrench" title="Services" main />
      
      <div class="flex flex-col md:flex-row gap-4">
        <!-- Table Section -->
        <div class="w-full md:w-2/3">
          <CardBox has-table>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Parking Lot</th>
                  <th>Vehicle Type</th>
                  <th>Name</th>
                  <th>Price</th>
                  <th>Status</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                <tr v-if="services.length === 0">
                  <td colspan="7" class="text-center py-24 text-gray-500 dark:text-slate-400">
                    <p>No services found</p>
                  </td>
                </tr>
                <tr v-for="service in services" :key="service.id">
                  <td data-label="ID">{{ service.id }}</td>
                  <td data-label="Parking Lot">{{ service.parking_lot_name }}</td>
                  <td data-label="Vehicle Type">{{ service.vehicle_type_name }}</td>
                  <td data-label="Name">{{ service.name }}</td>
                  <td data-label="Price">${{ service.price }}</td>
                  <td data-label="Status">
                    <span :class="getStatusClass(service.status)">
                      {{ service.status }}
                    </span>
                  </td>
                  <td class="before:hidden lg:w-1 whitespace-nowrap">
                    <BaseButtons type="justify-start lg:justify-end" no-wrap>
                      <BaseButton
                        color="info"
                        :icon="mdiPencil"
                        small
                        @click="editService(service)"
                      />
                      <BaseButton
                        color="danger"
                        :icon="mdiClose"
                        small
                        @click="toggleStatus(service)"
                      />
                    </BaseButtons>
                  </td>
                </tr>
              </tbody>
            </table>
          </CardBox>
        </div>

        <!-- Form Section -->
        <div class="w-full md:w-1/3">
          <CardBox>
            <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Service' : 'Create New Service' }}</h2>
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
                <label class="block text-sm font-medium mb-2">Price</label>
                <input
                  v-model="form.price"
                  type="number"
                  step="0.01"
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
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiCarWrench, mdiPencil, mdiClose } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import { servicesService } from '@/services/servicesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'

const services = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const isEditing = ref(false)

const form = ref({
  id_parking_lot: '',
  id_vehicle_type: '',
  name: '',
  price: '',
  status: 'active'
})

const getStatusClass = (status) => {
  const classes = {
    active: 'text-green-600 dark:text-green-400',
    inactive: 'text-red-600 dark:text-red-400'
  }
  return classes[status] || ''
}

const editService = (service) => {
  isEditing.value = true
  form.value = { ...service }
}

const toggleStatus = async (service) => {
  try {
    const updatedService = { 
      ...service, 
      status: service.status === 'active' ? 'inactive' : 'active' 
    }
    await servicesService.updateService(service.id, updatedService)
    await fetchServices()
  } catch (error) {
    console.error('Error toggling service status:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      await servicesService.updateService(form.value.id, form.value)
    } else {
      await servicesService.createService(form.value)
    }
    await fetchServices()
    resetForm()
  } catch (error) {
    console.error('Error saving service:', error)
  }
}

const resetForm = () => {
  form.value = {
    id_parking_lot: '',
    id_vehicle_type: '',
    name: '',
    price: '',
    status: 'active'
  }
  isEditing.value = false
}

const fetchServices = async () => {
  try {
    services.value = await servicesService.getAllServices()
  } catch (error) {
    console.error('Error fetching services:', error)
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
    fetchServices(),
    fetchParkingLots(),
    fetchVehicleTypes()
  ])
})
</script> 