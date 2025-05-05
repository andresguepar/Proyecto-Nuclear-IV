<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiCashMultiple" title="Fees" main />
      
      <div class="mb-6">
        <BaseButtons>
          <BaseButton
            v-for="tab in tabs"
            :key="tab.key"
            :label="tab.label"
            :color="activeTab === tab.key ? 'info' : 'whiteDark'"
            @click="activeTab = tab.key"
          />
        </BaseButtons>
      </div>

      <div class="flex flex-col md:flex-row gap-4">
        <!-- Table Section -->
        <div class="w-full md:w-2/3">
          <!-- Standard Fees -->
          <div v-if="activeTab === 'standard'" class="mb-6">
            <CardBox has-table>
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Parking Lot</th>
                    <th>Vehicle Type</th>
                    <th>Price per Hour</th>
                    <th>12 Hours Price</th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  <tr v-if="standardFees.length === 0">
                    <td colspan="6" class="text-center py-24 text-gray-500 dark:text-slate-400">
                      <p>No standard fees found</p>
                    </td>
                  </tr>
                  <tr v-for="fee in standardFees" :key="fee.id">
                    <td data-label="ID">{{ fee.id }}</td>
                    <td data-label="Parking Lot">{{ fee.parking_lot_name }}</td>
                    <td data-label="Vehicle Type">{{ fee.vehicle_type_name }}</td>
                    <td data-label="Price per Hour">${{ fee.price_x_hours }}</td>
                    <td data-label="12 Hours Price">${{ fee.price_x_12_hours }}</td>
                    <td class="before:hidden lg:w-1 whitespace-nowrap">
                      <BaseButtons type="justify-start lg:justify-end" no-wrap>
                        <BaseButton
                          color="info"
                          :icon="mdiPencil"
                          small
                          @click="editStandardFee(fee)"
                        />
                        <BaseButton
                          color="danger"
                          :icon="mdiClose"
                          small
                          @click="deactivateStandardFee(fee.id)"
                        />
                      </BaseButtons>
                    </td>
                  </tr>
                </tbody>
              </table>
            </CardBox>
          </div>

          <!-- Monthly Fees -->
          <div v-if="activeTab === 'monthly'" class="mb-6">
            <CardBox has-table>
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Parking Lot</th>
                    <th>Vehicle Type</th>
                    <th>Price</th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  <tr v-if="monthlyFees.length === 0">
                    <td colspan="5" class="text-center py-24 text-gray-500 dark:text-slate-400">
                      <p>No monthly fees found</p>
                    </td>
                  </tr>
                  <tr v-for="fee in monthlyFees" :key="fee.id">
                    <td data-label="ID">{{ fee.id }}</td>
                    <td data-label="Parking Lot">{{ fee.parking_lot_name }}</td>
                    <td data-label="Vehicle Type">{{ fee.vehicle_type_name }}</td>
                    <td data-label="Price">${{ fee.price }}</td>
                    <td class="before:hidden lg:w-1 whitespace-nowrap">
                      <BaseButtons type="justify-start lg:justify-end" no-wrap>
                        <BaseButton
                          color="info"
                          :icon="mdiPencil"
                          small
                          @click="editMonthlyFee(fee)"
                        />
                        <BaseButton
                          color="danger"
                          :icon="mdiClose"
                          small
                          @click="deactivateMonthlyFee(fee.id)"
                        />
                      </BaseButtons>
                    </td>
                  </tr>
                </tbody>
              </table>
            </CardBox>
          </div>

          <!-- Services Fees -->
          <div v-if="activeTab === 'services'" class="mb-6">
            <CardBox has-table>
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Parking Lot</th>
                    <th>Vehicle Type</th>
                    <th>Service</th>
                    <th>Add-on Services</th>
                    <th>Price</th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  <tr v-if="serviceFees.length === 0">
                    <td colspan="7" class="text-center py-24 text-gray-500 dark:text-slate-400">
                      <p>No service fees found</p>
                    </td>
                  </tr>
                  <tr v-for="fee in serviceFees" :key="fee.id">
                    <td data-label="ID">{{ fee.id }}</td>
                    <td data-label="Parking Lot">{{ fee.parking_lot_name }}</td>
                    <td data-label="Vehicle Type">{{ fee.vehicle_type_name }}</td>
                    <td data-label="Service">{{ fee.service_name }}</td>
                    <td data-label="Add-on Services">
                      <div class="flex flex-wrap gap-1">
                        <span 
                          v-for="id in fee.add_on_services" 
                          :key="id"
                          class="px-2 py-1 bg-blue-100 text-blue-800 rounded-full text-xs dark:bg-blue-900 dark:text-blue-200"
                        >
                          {{ addOnServices.find(s => s.id === id)?.name }}
                        </span>
                        <span v-if="!fee.add_on_services || fee.add_on_services.length === 0" class="text-gray-500">
                          None
                        </span>
                      </div>
                    </td>
                    <td data-label="Price">${{ fee.price }}</td>
                    <td class="before:hidden lg:w-1 whitespace-nowrap">
                      <BaseButtons type="justify-start lg:justify-end" no-wrap>
                        <BaseButton
                          color="info"
                          :icon="mdiPencil"
                          small
                          @click="editServiceFee(fee)"
                        />
                        <BaseButton
                          color="danger"
                          :icon="mdiClose"
                          small
                          @click="deactivateServiceFee(fee.id)"
                        />
                      </BaseButtons>
                    </td>
                  </tr>
                </tbody>
              </table>
            </CardBox>
          </div>
        </div>

        <!-- Form Section -->
        <div class="w-full md:w-1/3">
          <CardBox>
            <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit Fee' : 'Create New Fee' }}</h2>
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
              <div v-if="activeTab === 'standard'" class="mb-4">
                <label class="block text-sm font-medium mb-2">Price per Hour</label>
                <input
                  v-model="form.price_x_hours"
                  type="number"
                  step="0.01"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div v-if="activeTab === 'standard'" class="mb-4">
                <label class="block text-sm font-medium mb-2">12 Hours Price</label>
                <input
                  v-model="form.price_x_12_hours"
                  type="number"
                  step="0.01"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div v-if="activeTab === 'monthly'" class="mb-4">
                <label class="block text-sm font-medium mb-2">Monthly Price</label>
                <input
                  v-model="form.price"
                  type="number"
                  step="0.01"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                />
              </div>
              <div v-if="activeTab === 'services'" class="mb-4">
                <label class="block text-sm font-medium mb-2">Service</label>
                <select
                  v-model="form.id_service"
                  class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                  required
                >
                  <option v-for="service in services" :key="service.id" :value="service.id">
                    {{ service.name }}
                  </option>
                </select>
              </div>
              <div v-if="activeTab === 'services'" class="mb-4">
                <label class="block text-sm font-medium mb-2">Add-on Services</label>
                <div class="space-y-2 max-h-48 overflow-y-auto p-2 border rounded dark:bg-slate-700 dark:border-slate-600">
                  <label v-for="service in addOnServices" :key="service.id" class="flex items-center space-x-2 p-2 hover:bg-gray-100 dark:hover:bg-slate-600 rounded">
                    <input
                      type="checkbox"
                      :value="service.id"
                      v-model="form.add_on_services"
                      class="rounded border-gray-300 text-blue-600 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                    />
                    <span>{{ service.name }} - ${{ service.price }}</span>
                  </label>
                </div>
              </div>
              <div v-if="activeTab === 'services'" class="mb-4">
                <label class="block text-sm font-medium mb-2">Service Price</label>
                <input
                  v-model="form.price"
                  type="number"
                  step="0.01"
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
                  <option value="active">Active</option>
                  <option value="inactive">Inactive</option>
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
          </CardBox>
        </div>
      </div>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiCashMultiple, mdiPencil, mdiClose } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import { standardFeesService } from '@/services/standardFeesService'
import { monthlyFeesService } from '@/services/monthlyFeesService'
import { serviceFeesService } from '@/services/serviceFeesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'
import { servicesService } from '@/services/servicesService'
import { addOnServicesService } from '@/services/addOnServicesService'

const tabs = [
  { key: 'standard', label: 'Standard' },
  { key: 'monthly', label: 'Monthly' },
  { key: 'services', label: 'Services' }
]

const activeTab = ref('standard')
const standardFees = ref([])
const monthlyFees = ref([])
const serviceFees = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const services = ref([])
const addOnServices = ref([])
const isEditing = ref(false)

const form = ref({
  id_parking_lot: '',
  id_vehicle_type: '',
  id_service: '',
  price_x_hours: '',
  price_x_12_hours: '',
  price: '',
  status: 'active',
  add_on_services: []
})

const getStatusClass = (status) => {
  const classes = {
    active: 'text-green-600 dark:text-green-400',
    inactive: 'text-red-600 dark:text-red-400'
  }
  return classes[status] || ''
}

const editStandardFee = (fee) => {
  isEditing.value = true
  form.value = { ...fee }
}

const deactivateStandardFee = async (id) => {
  if (confirm('Are you sure you want to deactivate this fee?')) {
    try {
      await standardFeesService.updateStandardFee(id, { status: 'inactive' })
      await loadStandardFees()
    } catch (error) {
      console.error('Error deactivating standard fee:', error)
    }
  }
}

const editMonthlyFee = (fee) => {
  isEditing.value = true
  form.value = { ...fee }
}

const deactivateMonthlyFee = async (id) => {
  if (confirm('Are you sure you want to deactivate this fee?')) {
    try {
      await monthlyFeesService.updateMonthlyFee(id, { status: 'inactive' })
      await loadMonthlyFees()
    } catch (error) {
      console.error('Error deactivating monthly fee:', error)
    }
  }
}

const editServiceFee = (fee) => {
  isEditing.value = true
  form.value = { ...fee }
}

const deactivateServiceFee = async (id) => {
  if (confirm('Are you sure you want to deactivate this fee?')) {
    try {
      await serviceFeesService.updateServiceFee(id, { status: 'inactive' })
      await loadServiceFees()
    } catch (error) {
      console.error('Error deactivating service fee:', error)
    }
  }
}

const loadStandardFees = async () => {
  try {
    const fees = await standardFeesService.getAllStandardFees()
    standardFees.value = fees.filter(fee => fee.status === 'active')
  } catch (error) {
    console.error('Error loading standard fees:', error)
  }
}

const loadMonthlyFees = async () => {
  try {
    const fees = await monthlyFeesService.getAllMonthlyFees()
    monthlyFees.value = fees.filter(fee => fee.status === 'active')
  } catch (error) {
    console.error('Error loading monthly fees:', error)
  }
}

const loadServiceFees = async () => {
  try {
    const fees = await serviceFeesService.getAllServiceFees()
    serviceFees.value = fees.filter(fee => fee.status === 'active')
  } catch (error) {
    console.error('Error loading service fees:', error)
  }
}

const loadParkingLots = async () => {
  try {
    const lots = await parkingLotsService.getAllParkingLots()
    parkingLots.value = lots
  } catch (error) {
    console.error('Error loading parking lots:', error)
  }
}

const loadVehicleTypes = async () => {
  try {
    const types = await vehicleTypesService.getAllVehicleTypes()
    vehicleTypes.value = types
  } catch (error) {
    console.error('Error loading vehicle types:', error)
  }
}

const loadServices = async () => {
  try {
    const svcs = await servicesService.getAllServices()
    services.value = svcs
  } catch (error) {
    console.error('Error loading services:', error)
  }
}

const loadAddOnServices = async () => {
  try {
    const svcs = await addOnServicesService.getAllAddOnServices()
    addOnServices.value = svcs
  } catch (error) {
    console.error('Error loading add-on services:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      if (activeTab.value === 'standard') {
        await standardFeesService.updateStandardFee(form.value.id, form.value)
      } else if (activeTab.value === 'monthly') {
        await monthlyFeesService.updateMonthlyFee(form.value.id, form.value)
      } else if (activeTab.value === 'services') {
        // Ensure add_on_services is an array of IDs
        const feeData = {
          ...form.value,
          add_on_services: Array.isArray(form.value.add_on_services) 
            ? form.value.add_on_services 
            : [form.value.add_on_services].filter(Boolean)
        }
        await serviceFeesService.updateServiceFee(form.value.id, feeData)
      }
    } else {
      if (activeTab.value === 'standard') {
        await standardFeesService.createStandardFee(form.value)
      } else if (activeTab.value === 'monthly') {
        await monthlyFeesService.createMonthlyFee(form.value)
      } else if (activeTab.value === 'services') {
        // Ensure add_on_services is an array of IDs
        const feeData = {
          ...form.value,
          add_on_services: Array.isArray(form.value.add_on_services) 
            ? form.value.add_on_services 
            : [form.value.add_on_services].filter(Boolean)
        }
        await serviceFeesService.createServiceFee(feeData)
      }
    }
    
    // Reload data after submission
    await loadData()
    form.value = {
      id_parking_lot: '',
      id_vehicle_type: '',
      id_service: '',
      price_x_hours: '',
      price_x_12_hours: '',
      price: '',
      status: 'active',
      add_on_services: []
    }
    isEditing.value = false
  } catch (error) {
    console.error('Error submitting form:', error)
  }
}

const loadData = async () => {
  await Promise.all([
    loadStandardFees(),
    loadMonthlyFees(),
    loadServiceFees(),
    loadParkingLots(),
    loadVehicleTypes(),
    loadServices(),
    loadAddOnServices()
  ])
}

onMounted(loadData)
</script> 