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
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  <tr v-if="parkingServices.length === 0">
                    <td colspan="7" class="text-center py-24 text-gray-500 dark:text-slate-400">
                      <p>No parking services found</p>
                    </td>
                  </tr>
                  <tr v-for="service in parkingServices" :key="service.id">
                    <td data-label="ID">{{ service.id }}</td>
                    <td data-label="Name">{{ service.name }}</td>
                    <td data-label="Description">{{ service.description }}</td>
                    <td data-label="Price">${{ service.price }}</td>
                    <td data-label="Status">
                      <BaseBadge
                        :color="service.status === 'active' ? 'success' : 'danger'"
                        :label="service.status"
                      />
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
                          @click="deleteService(service.id)"
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
            <h2 class="text-xl font-bold mb-4">{{ isEditing ? 'Edit' : 'Create' }} {{ activeTab === 'standard' ? 'Standard' : activeTab === 'monthly' ? 'Monthly' : 'Service' }} Fee</h2>
            <form @submit.prevent="handleSubmit" class="space-y-4">
              <!-- Standard Fees Form -->
              <div v-if="activeTab === 'standard'">
                <div v-if="user.value && user.value.role && user.value.role.name === 'super_admin'" class="mb-4">
                  <label class="block text-sm font-medium mb-2">Parking Lot</label>
                  <select
                    v-model="form.id_parking_lot"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  >
                    <option value="">Select a parking lot</option>
                    <option v-for="lot in parkingLots" :key="lot.id" :value="lot.id">
                      {{ lot.name }}
                    </option>
                  </select>
                </div>
                <div class="mb-4">
                  <label class="block text-sm font-medium mb-2">Vehicle Type</label>
                  <select
                    v-model="form.vehicleType"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  >
                    <option value="">Select a vehicle type</option>
                    <option v-for="type in vehicleTypes" :key="type.idVehicleType" :value="type.idVehicleType">
                      {{ type.name }}
                    </option>
                  </select>
                </div>
                <div class="mb-4">
                  <label class="block text-sm font-medium mb-2">Price per Hour</label>
                  <input
                    v-model="form.price_x_hours"
                    type="number"
                    step="0.01"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  />
                </div>
                <div class="mb-4">
                  <label class="block text-sm font-medium mb-2">Price per 12 Hours</label>
                  <input
                    v-model="form.price_x_12_hours"
                    type="number"
                    step="0.01"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  />
                </div>
              </div>

              <!-- Monthly Fees Form -->
              <div v-if="activeTab === 'monthly'">
                <div v-if="user.value && user.value.role && user.value.role.name === 'super_admin'" class="mb-4">
                  <label class="block text-sm font-medium mb-2">Parking Lot</label>
                  <select
                    v-model="form.id_parking_lot"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  >
                    <option value="">Select a parking lot</option>
                    <option v-for="lot in parkingLots" :key="lot.id" :value="lot.id">
                      {{ lot.name }}
                    </option>
                  </select>
                </div>
                <div class="mb-4">
                  <label class="block text-sm font-medium mb-2">Vehicle Type</label>
                  <select
                    v-model="form.vehicleType"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  >
                    <option value="">Select a vehicle type</option>
                    <option v-for="type in vehicleTypes" :key="type.idVehicleType" :value="type.idVehicleType">
                      {{ type.name }}
                    </option>
                  </select>
                </div>
                <div class="mb-4">
                  <label class="block text-sm font-medium mb-2">Monthly Price</label>
                  <input
                    v-model="form.price"
                    type="number"
                    step="0.01"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  />
                </div>
              </div>

              <!-- Services Form (existing code) -->
              <div v-if="activeTab === 'services'">
                <div v-if="user.value && user.value.role && user.value.role.name === 'super_admin'" class="mb-4">
                  <label class="block text-sm font-medium mb-2">Parking Lot</label>
                  <select
                    v-model="form.id_parking_lot"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                  >
                    <option value="">Select a parking lot</option>
                    <option v-for="lot in parkingLots" :key="lot.id" :value="lot.id">
                      {{ lot.name }}
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
                    placeholder="Enter service name"
                  />
                </div>
                <div class="mb-4">
                  <label class="block text-sm font-medium mb-2">Description</label>
                  <textarea
                    v-model="form.description"
                    class="w-full p-2 border rounded dark:bg-slate-700 dark:border-slate-600"
                    required
                    placeholder="Enter service description"
                    rows="3"
                  ></textarea>
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
              </div>

              <div class="flex justify-end gap-2">
                <BaseButton
                  type="submit"
                  color="info"
                  :label="isEditing ? 'Update' : 'Create'"
                />
                <BaseButton
                  v-if="isEditing"
                  color="danger"
                  label="Cancel"
                  @click="resetForm"
                />
              </div>
            </form>
          </CardBox>
        </div>
      </div>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { mdiCashMultiple, mdiPencil, mdiClose } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseBadge from '@/components/BaseBadge.vue'
import { standardFeesService } from '@/services/standardFeesService'
import { monthlyFeesService } from '@/services/monthlyFeesService'
import { addOnServicesService } from '@/services/addOnServicesService'
import { parkingLotsService } from '@/services/parkingLotsService'
import { vehicleTypesService } from '@/services/vehicleTypesService'
import { useAuthStore } from '@/stores/auth.js'

const authStore = useAuthStore()
const user = computed(() => authStore.user)

const tabs = [
  { key: 'standard', label: 'Standard Fees' },
  { key: 'monthly', label: 'Monthly Fees' },
  { key: 'services', label: 'Parking Services' }
]

const activeTab = ref('standard')
const standardFees = ref([])
const monthlyFees = ref([])
const parkingServices = ref([])
const parkingLots = ref([])
const vehicleTypes = ref([])
const isEditing = ref(false)

const form = ref({
  id: null,
  id_parking_lot: '',
  vehicleType: '',
  name: '',
  description: '',
  price: 0,
  price_x_hours: 0,
  price_x_12_hours: 0,
  status: 'active'
})

const loadVehicleTypes = async () => {
  try {
    const types = await vehicleTypesService.getAllVehicleTypes()
    vehicleTypes.value = types
  } catch (error) {
    console.error('Error loading vehicle types:', error)
  }
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

const loadParkingServices = async () => {
  try {
    const services = await addOnServicesService.getAllAddOnServices()
    parkingServices.value = services
  } catch (error) {
    console.error('Error loading parking services:', error)
  }
}

const loadParkingLots = async () => {
  try {
    const lots = await parkingLotsService.getAllParkingLots()
    parkingLots.value = lots.map(lot => ({
      id: lot.idParkingLot,
      name: lot.name
    }))
  } catch (error) {
    console.error('Error loading parking lots:', error)
  }
}

const editService = (service) => {
  form.value = {
    id: service.id,
    id_parking_lot: service.parkingLot.idParkingLot,
    vehicleType: service.vehicleType,
    name: service.name,
    description: service.description,
    price: service.price,
    status: service.status
  }
  isEditing.value = true
}

const deleteService = async (id) => {
  if (confirm('Are you sure you want to delete this service?')) {
    try {
      await addOnServicesService.deleteAddOnService(id)
      await loadParkingServices()
    } catch (error) {
      console.error('Error deleting service:', error)
    }
  }
}

const resetForm = () => {
  form.value = {
    id: null,
    id_parking_lot: '',
    vehicleType: '',
    name: '',
    description: '',
    price: 0,
    price_x_hours: 0,
    price_x_12_hours: 0,
    status: 'active'
  }
  isEditing.value = false
}

const handleSubmit = async () => {
  try {
    // Si el usuario es park_admin, asignar automáticamente su parking lot
    if (user.value && user.value.role && user.value.role.name === 'park_admin') {
      const myLot = parkingLots.value.find(lot => lot.adminId === user.value.idUser || lot.admin?.idUser === user.value.idUser)
      if (myLot) {
        form.value.id_parking_lot = myLot.id
      }
    }
    if (activeTab.value === 'services') {
      if (form.value.id) {
        await addOnServicesService.updateAddOnService(form.value.id, form.value)
      } else {
        await addOnServicesService.createAddOnService(form.value)
      }
      await loadParkingServices()
    } else if (activeTab.value === 'standard') {
      const standardFeeData = {
        id_parking_lot: parseInt(form.value.id_parking_lot),
        id_vehicle_type: parseInt(form.value.vehicleType),
        price_x_hours: parseFloat(form.value.price_x_hours),
        price_x_12_hours: parseFloat(form.value.price_x_12_hours),
        status: 'active'
      }
      console.log('Creating standard fee with data:', standardFeeData)
      if (form.value.id) {
        await standardFeesService.updateStandardFee(form.value.id, standardFeeData)
      } else {
        const response = await standardFeesService.createStandardFee(standardFeeData)
        console.log('Standard fee created:', response)
      }
      await loadStandardFees()
    } else if (activeTab.value === 'monthly') {
      const monthlyFeeData = {
        id_parking_lot: parseInt(form.value.id_parking_lot),
        id_vehicle_type: parseInt(form.value.vehicleType),
        price: parseFloat(form.value.price),
        status: 'active'
      }
      console.log('Creating monthly fee with data:', monthlyFeeData)
      if (form.value.id) {
        await monthlyFeesService.updateMonthlyFee(form.value.id, monthlyFeeData)
      } else {
        const response = await monthlyFeesService.createMonthlyFee(monthlyFeeData)
        console.log('Monthly fee created:', response)
      }
      await loadMonthlyFees()
    }
    resetForm()
  } catch (error) {
    console.error('Error saving fee:', error)
    alert('Error saving fee: ' + (error.response?.data?.message || error.message))
  }
}

const loadData = async () => {
  await Promise.all([
    loadStandardFees(),
    loadMonthlyFees(),
    loadParkingServices(),
    loadParkingLots(),
    loadVehicleTypes()
  ])
}

onMounted(loadData)
</script>

