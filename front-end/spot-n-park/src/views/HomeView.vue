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
        <span style="color: #ff8f2b;">spot</span>
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
            v-for="(lot, index) in parkingLots"
            :key="index"
            class="transform transition-all duration-200 hover:shadow-lg"
          >
            <CardBoxComponentBody class="p-2">
              <div class="flex justify-between items-start mb-1.5">
                <div>
                  <h3 class="text-sm font-semibold text-gray-800">{{ lot.name }}</h3>
                  <p class="text-s text-gray-800">{{ lot.distance }}</p>
                </div>
                <BaseButton
                  color="info"
                  label="Choose"
                  medium
                  class="text-xs px-2 py-0.5"
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

const activeTab = ref('standart')

const parkingLots = ref([
  {
    name: 'Parking Lot 1',
    distance: '300m',
    address: 'Dame Gruev 12 - Zone 1 - Car',
    hours: '08:00-12:00',
  },
  {
    name: 'Parking Lot 2',
    distance: '450m',
    address: 'Dame Gruev 15 - Zone 2 - Car',
    hours: '08:00-12:00',
  },
  {
    name: 'Parking Lot 3',
    distance: '600m',
    address: 'Dame Gruev 18 - Zone 3 - Car',
    hours: '08:00-12:00',
  },
])

const map = ref(null)

const parkingLotsCoordinates = [
  { lat: 41.9981, lng: 21.4254 }, // Example coordinates for Parking Lot 1
  { lat: 41.9985, lng: 21.4260 }, // Example coordinates for Parking Lot 2
  { lat: 41.9990, lng: 21.4265 }  // Example coordinates for Parking Lot 3
]

const addHours = (lot) => {
  console.log('Adding hours for', lot.name)
}

onMounted(async () => {
  try {
    // Load Google Maps API
    const script = document.createElement('script')
    script.src = `https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&callback=Function.prototype`
    script.async = true
    script.defer = true
    document.head.appendChild(script)

    // Wait for the script to load
    await new Promise((resolve) => {
      script.onload = resolve
    })

    // Initialize the map
    const { Map } = await google.maps.importLibrary("maps")
    const { Marker } = await google.maps.importLibrary("marker")

    map.value = new Map(document.getElementById("map"), {
      center: parkingLotsCoordinates[0],
      zoom: 15,
    })

    // Add markers for each parking lot
    parkingLotsCoordinates.forEach(coord => {
      new Marker({
        position: coord,
        map: map.value
      })
    })
  } catch (error) {
    console.error('Failed to load Google Maps', error)
  }
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
