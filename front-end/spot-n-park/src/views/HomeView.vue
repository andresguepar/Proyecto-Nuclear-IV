<template>
  <LayoutAuthenticated>
    <SectionMain>
      <div class="flex justify-center mb-6">
        <img src="@/assets/images/spotnpark-logo-home.png" alt="SpotNPark Logo" class="h-100" />
      </div>
      <div class="mt-4 text-center font-semibold text-3xl mb-6" style="color: #0e2338;">
        <span style="color: #e1ebf5;">Book </span>
        <span style="color: #2e76ae;">your spot, </span>
        <span style="color: #0e2338;">park without </span>
        <span style="color: #ff8f2b;">stop</span>
      </div>

      <!-- Tabs with Card -->
      <CardBox class="mb-3">
        <div class="border-b border-gray-200">
          <div class="flex -mb-px">
            <button
              class="px-4 py-1.5 text-sm font-medium border-b-2 transition-colors duration-150"
              :class="{
                'border-blue-500 text-blue-600': activeTab === 'standart',
                'border-transparent hover:border-gray-300': activeTab !== 'standart',
              }"
              @click="activeTab = 'standart'"
            >
              Standart
            </button>
            <button
              class="px-4 py-1.5 text-sm font-medium border-b-2 transition-colors duration-150"
              :class="{
                'border-blue-500 text-blue-600': activeTab === 'monthly',
                'border-transparent hover:border-gray-300': activeTab !== 'monthly',
              }"
              @click="activeTab = 'monthly'"
            >
              Monthly
            </button>
            <button
              class="px-4 py-1.5 text-sm font-medium border-b-2 transition-colors duration-150"
              :class="{
                'border-blue-500 text-blue-600': activeTab === 'services',
                'border-transparent hover:border-gray-300': activeTab !== 'services',
              }"
              @click="activeTab = 'services'"
            >
              Services
            </button>
          </div>
        </div>
      </CardBox>

      <!-- Service Form with Card -->
      <CardBox class="mb-3">
        <CardBoxComponentBody class="p-1">
          <StandardServiceForm v-if="activeTab === 'standart'" />
          <MonthlyServiceForm v-else-if="activeTab === 'monthly'" />
          <AdditionalServicesForm v-else-if="activeTab === 'services'" />
        </CardBoxComponentBody>
      </CardBox>

      <!-- Parking Lots List and Map -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-3">
        <div class="lg:col-span-1 space-y-2">
          <CardBox
            v-for="lot in parkingLots"
            :key="lot.id"
            class="transform transition-all duration-200 hover:shadow-lg"
          >
            <CardBoxComponentBody class="p-2">
              <div class="flex justify-between items-start mb-1.5">
                <div>
                  <h3 class="text-sm font-semibold text-gray-800">{{ lot.name }}</h3>
                  <p class="text-s text-gray-800">{{ lot.price }}</p>
                </div>
                <BaseButton
                  color="info"
                  label="Choose"
                  class="text-sm px-4 py-2"
                  @click="addHours(lot)"
                />
              </div>
              <div class="space-y-1">
                <div class="flex items-center gap-1.5">
                  <BaseIcon
                    path="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zM12 11.5c-1.38 0-2.5-1.12-2.5-2.5S10.62 6.5 12 6.5s2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z"
                    class="w-4 h-4"
                    style="color: #ff8f2b"
                  />
                  <span class="text-sm text-gray-600">{{ lot.address }}</span>
                </div>
                <div class="flex items-center gap-1.5">
                  <BaseIcon
                    path="M12 20c4.41 0 8-3.59 8-8s-3.59-8-8-8-8 3.59-8 8 3.59 8 8 8zm0-14c3.31 0 6 2.69 6 6s-2.69 6-6 6-6-2.69-6-6 2.69-6 6-6zm-.5 3h-1v5l4.25 2.52.75-1.23-3.5-2.08V9z"
                    class="w-4 h-4"
                    style="color: #ff8f2b"
                  />
                  <span class="text-sm text-gray-600">{{ lot.hours }}</span>
                </div>
                <div class="flex items-center gap-1.5">
                  <BaseIcon
                    path="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-1 16H6c-.55 0-1-.45-1-1V6c0-.55.45-1 1-1h12c.55 0 1 .45 1 1v12c0 .55-.45 1-1 1z"
                    class="w-4 h-4"
                    style="color: #ff8f2b"
                  />
                  <span class="text-sm text-gray-600">{{ lot.availableSlots }} slots available</span>
                </div>
              </div>
            </CardBoxComponentBody>
          </CardBox>
        </div>

        <!-- Map Card -->
        <CardBox class="lg:col-span-2 h-[650px]">
          <CardBoxComponentBody class="h-full p-0">
            <div id="map" class="w-full h-full rounded-lg"></div>
          </CardBoxComponentBody>
        </CardBox>
      </div>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
/* global google */
import { ref, onMounted } from 'vue'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import CardBox from '@/components/CardBox.vue'
import CardBoxComponentBody from '@/components/CardBoxComponentBody.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseIcon from '@/components/BaseIcon.vue'
import StandardServiceForm from '@/components/StandardServiceForm.vue'
import MonthlyServiceForm from '@/components/MonthlyServiceForm.vue'
import AdditionalServicesForm from '@/components/AdditionalServicesForm.vue'
import { parkingLotsService } from '@/services/parkingLotsService'
import { slotsService } from '@/services/slotsService'
import { standardFeesService } from '@/services/standardFeesService'

const activeTab = ref('standart')
const parkingLots = ref([])
const map = ref(null)
const markers = ref([])
const parkingLotsData = ref([])

// Make initMap available globally
window.initMap = function() {
  map.value = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 4.5333, lng: -75.6833 }, // Armenia, Quindío coordinates
    zoom: 14,
  })

  // Clear existing markers if any
  markers.value.forEach(marker => marker.setMap(null))
  markers.value = []

  // Add markers for our parking lots with available slots
  parkingLotsData.value.filter(lot => lot.availableSlots > 0).forEach(lot => {
    if (lot.latitude && lot.longitude) {
      const marker = new google.maps.Marker({
        position: { lat: lot.latitude, lng: lot.longitude },
        map: map.value,
        title: lot.name,
        icon: {
          url: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'
        }
      })

      // Add info window
      const infoWindow = new google.maps.InfoWindow({
        content: `
          <div class="p-2">
            <h3 class="font-semibold">${lot.name}</h3>
            <p class="text-sm">${lot.address}</p>
            <p class="text-sm">Price: ${lot.price}</p>
            <p class="text-sm">Available Slots: ${lot.availableSlots}</p>
          </div>
        `
      })

      marker.addListener('click', () => {
        infoWindow.open(map.value, marker)
      })

      markers.value.push(marker)
    }
  })
}

const fetchParkingLotsWithInfo = async () => {
  try {
    // Get all parking lots
    const lots = await parkingLotsService.getAllParkingLots()

    // Get standard fees for each parking lot
    const fees = await standardFeesService.getAllStandardFees()

    // Get available slots for each parking lot
    const availableSlots = await slotsService.getSlotsByIsActive(true)

    // Combine the information
    parkingLotsData.value = lots.map(lot => {
      const lotFees = fees.find(fee => fee.parkingLot?.idParkingLot === lot.idParkingLot)
      const lotSlots = availableSlots.filter(slot => slot.parkingLot?.idParkingLot === lot.idParkingLot)

      return {
        id: lot.idParkingLot,
        name: lot.name,
        address: lot.address,
        price: lotFees ? `$${lotFees.price_x_hours}/hour` : 'Price not available',
        hours: lot.operatingHours || '24/7',
        availableSlots: lotSlots.length,
        latitude: lot.latitude,
        longitude: lot.longitude
      }
    })

    // Update the parkingLots ref with the same data
    parkingLots.value = parkingLotsData.value
  } catch (error) {
    console.error('Error fetching parking lots:', error)
  }
}

const addHours = (lot) => {
  console.log('Adding hours for', lot.name)
}

onMounted(async () => {
  await fetchParkingLotsWithInfo()

  // Load Google Maps API with callback to initMap
  const script = document.createElement('script')
  script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyCSWk47E6T5IrOjDyXA3d3pzG-DyIMVrs8&callback=initMap`
  script.async = true
  script.defer = true
  document.head.appendChild(script)
})
</script>

<style scoped>
.hover\:shadow-lg:hover {
  box-shadow:
    0 10px 15px -3px rgba(0, 0, 0, 0.1),
    0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

#map {
  height: 100%;
  width: 100%;
}
</style>
