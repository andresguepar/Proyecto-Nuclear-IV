<template>
  <div>
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
    <!-- New Map Section Below Table and Form -->
    <div id="google-parking-map" class="w-full h-96 mt-4 rounded-lg border border-gray-300"></div>
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
  // Assume Google Maps API is loaded globally, just initialize the map
  if (window.google) {
    initGoogleMap()
  } else {
    console.error('Google Maps API not loaded globally')
  }
})

/* global google */
const map = ref(null)
const placesService = ref(null)
const markers = ref([])

const initGoogleMap = () => {
  if (!window.google) {
    console.error('Google Maps API not loaded')
    return
  }
  map.value = new google.maps.Map(document.getElementById('google-parking-map'), {
    center: { lat: 4.5333, lng: -75.6833 }, // Armenia, Quindio
    zoom: 14
  })
  placesService.value = new google.maps.places.PlacesService(map.value)
  searchNearbyParkingLots()
}

const clearMarkers = () => {
  markers.value.forEach(marker => marker.setMap(null))
  markers.value = []
}

const searchNearbyParkingLots = () => {
  if (!placesService.value || !map.value) return
  const request = {
    query: 'parking lots in Armenia Quindio',
    location: map.value.getCenter(),
    radius: 5000
  }
  console.log('Google Places textSearch request:', request)
  placesService.value.textSearch(request, (results, status) => {
    console.log('Google Places textSearch status:', status)
    if (status === google.maps.places.PlacesServiceStatus.OK) {
      clearMarkers()
      console.log('Google Places textSearch results:', results)
      results.forEach(place => {
        const marker = new google.maps.Marker({
          position: place.geometry.location,
          map: map.value,
          title: place.name,
          icon: {
            url: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
          }
        })
        marker.addListener('click', () => {
          form.value.name = place.name || ''
          form.value.address = place.formatted_address || place.vicinity || ''
          form.value.coordX = place.geometry.location.lat()
          form.value.coordY = place.geometry.location.lng()
          isEditing.value = false
        })
        markers.value.push(marker)
      })
    } else {
      console.warn('Google Places textSearch failed with status:', status)
    }
  }).catch(error => {
    console.error('Error during textSearch:', error)
  })
}
</script>
