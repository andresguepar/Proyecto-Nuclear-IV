<template>
  <LayoutAuthenticated>
    <SectionMain>
      <div class="flex flex-col items-center justify-center mb-6">
        <img src="@/assets/images/spotnpark-logo-home.png" alt="SpotNPark Logo" class="h-120" />

      </div>
      <div class="mt-4 text-center font-semibold text-3xl mb-6" style="color: #0e2338;">
        <span style="color: #0e2338;">Book </span>
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
          </div>
        </div>
      </CardBox>

      <!-- Service Form with Card -->
      <CardBox class="mb-3">
        <CardBoxComponentBody class="p-1">
          <StandardServiceForm
            v-if="activeTab === 'standart'"
            @form-update="handleFormUpdate"
            @vehicle-type-change="handleVehicleTypeChange"
          />
          <MonthlyServiceForm
            v-else-if="activeTab === 'monthly'"
            @form-update="handleFormUpdate"
            @vehicle-type-change="handleVehicleTypeChange"
          />
        </CardBoxComponentBody>
      </CardBox>

      <!-- Contenedor dinámico para cards y mapa -->
      <div
        :class="showParkingLots ? 'grid grid-cols-1 lg:grid-cols-3 gap-3 mb-6' : 'w-full mb-6'"
      >
        <!-- Cards de parqueaderos solo después de buscar -->
        <TransitionGroup
          v-if="showParkingLots"
          name="fade-slide"
          tag="div"
          class="lg:col-span-1 space-y-2 mb-3 lg:mb-0"
        >
          <CardBox
            v-for="lot in parkingLots"
            :key="lot.id"
            class="transform transition-all duration-500 hover:shadow-lg"
          >
            <CardBoxComponentBody class="p-2">
              <div class="flex justify-between items-start mb-2">
                <div class="flex-grow">
                  <h3 class="text-sm font-semibold text-gray-800 mb-1">{{ lot.name }}</h3>
                  <!-- Precio más prominente -->
                  <div v-if="selectedVehicleType" class="mb-2">
                    <p class="text-lg font-bold text-blue-600">
                      {{ lot.price }}
                    </p>
                    <p class="text-xs text-gray-500">
                      {{ lot.selectedVehicleTypeName }}
                    </p>
                  </div>
                  <div v-else class="mb-2">
                    <p class="text-sm text-gray-600">Select a vehicle type to see prices</p>
                  </div>
                </div>
                <BaseButton
                  color="info"
                  label="Choose"
                  class="text-sm px-4 py-2"
                  @click="addHours(lot)"
                />
              </div>
              <div class="space-y-1.5">
                <!-- Schedule display with day indicator -->
                <div class="flex items-center gap-1.5 bg-gray-50 dark:bg-slate-700 p-2 rounded">
                  <BaseIcon
                    path="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                    class="w-4 h-4"
                    style="color: #ff8f2b"
                  />
                  <div class="flex-grow">
                    <p class="text-sm font-medium text-gray-800 dark:text-gray-200">
                      {{
                        (() => {
                          const dateStr = form.startDate ? form.startDate + 'T12:00:00' : new Date(new Date().toLocaleDateString('en-CA') + 'T12:00:00');
                          const dateObj = typeof dateStr === 'string' ? new Date(dateStr) : dateStr;
                          let day = dateObj.toLocaleDateString('es-CO', { weekday: 'long' });
                          day = day.charAt(0).toUpperCase() + day.slice(1);
                          return `Horario del ${day}`;
                        })()
                      }}
                    </p>
                    <p class="text-sm text-gray-600 dark:text-gray-400">{{ lot.hours }}</p>
                  </div>
                </div>
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
                    path="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-1 16H6c-.55 0-1-.45-1-1V6c0-.55.45-1 1-1h12c.55 0 1 .45 1 1v12c0 .55-.45 1-1 1z"
                    class="w-4 h-4"
                    style="color: #ff8f2b"
                  />
                  <span class="text-sm text-gray-600">{{ lot.availableSlots }} slots available</span>
                </div>
              </div>
            </CardBoxComponentBody>
          </CardBox>
        </TransitionGroup>
        <!-- Mapa -->
        <div :class="showParkingLots ? 'lg:col-span-2' : ''" class="w-full transition-all duration-500">
          <CardBox class="h-[500px] transition-all duration-500">
            <CardBoxComponentBody class="h-full p-0">
              <div id="home-map" class="w-full h-full rounded-lg transition-all duration-500"></div>
            </CardBoxComponentBody>
          </CardBox>
        </div>
      </div>

      <!-- Reservation Modal -->
      <ReservationModal
        :show="showReservationModal"
        @update:show="val => showReservationModal = val"
        :lot="selectedLot"
        :vehicleTypeId="selectedVehicleType"
        :startDate="form.startDate"
        :startTime="selectedStartTime"
        :plate="selectedPlate"
        :vehicleTypeName="selectedVehicleTypeName"
        :addOnServices="filteredAddOnServices"
        :selectedAddOns="selectedAddOnServices"
        :addOnServicesTotal="addOnServicesTotal"
        @add-on-services-change="handleAddOnServicesChange"
        @reserved="handleReservationComplete"
        @update:startTime="val => selectedStartTime = val"
        @update:plate="val => selectedPlate = val"
      />
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth.js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import CardBox from '@/components/CardBox.vue'
import CardBoxComponentBody from '@/components/CardBoxComponentBody.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseIcon from '@/components/BaseIcon.vue'
import StandardServiceForm from '@/components/StandardServiceForm.vue'
import MonthlyServiceForm from '@/components/MonthlyServiceForm.vue'
import ReservationModal from '@/components/ReservationModal.vue'
import { parkingLotsService } from '@/services/parkingLotsService'
import { slotsService } from '@/services/slotsService'
import { standardFeesService } from '@/services/standardFeesService'
import { monthlyFeesService } from '@/services/monthlyFeesService'
import { schedulesService } from '@/services/schedulesService.js'
import { addOnServicesService } from '@/services/addOnServicesService'

const authStore = useAuthStore()
const router = useRouter()

const activeTab = ref('standart')
const parkingLots = ref([])
const map = ref(null)
const markers = ref([])
const parkingLotsData = ref([])
const selectedVehicleType = ref(null)
const standardFees = ref([])
const monthlyFees = ref([])
const dailySchedules = ref([])
const availableSlots = ref([])
const isMapReady = ref(false)
const form = ref({
  vehicleType: '',
  startDate: '',
  startTime: '',
  plate: '',
  type: 'vehicle',
  duration: '1'
})
const showReservationModal = ref(false)
const selectedLot = ref(null)
const selectedStartTime = ref('')
const selectedPlate = ref('')
const selectedVehicleTypeName = ref('')
const showParkingLots = ref([])
const selectedAddOnServices = ref([])
const filteredAddOnServices = ref([])
const addOnServicesTotal = ref(0)
const allAddOnServices = ref([])

const updateFilteredAddOnServices = (lotId) => {
  filteredAddOnServices.value = allAddOnServices.value.filter(service => service.parkingLot?.idParkingLot === lotId && service.status === 'active')
  addOnServicesTotal.value = selectedAddOnServices.value.reduce((acc, s) => acc + (s.price || 0), 0)
}

const handleAddOnServicesChange = (selected) => {
  selectedAddOnServices.value = selected
  addOnServicesTotal.value = selected.reduce((acc, s) => acc + (s.price || 0), 0)
}

// Computed property to get schedule for selected date
const getScheduleForDate = (parkingLotId, selectedDate) => {
  console.log('Getting schedule for parking lot:', parkingLotId, 'and date:', selectedDate)
  // Obtener todos los horarios activos para este parqueadero
  const parkingLotSchedules = dailySchedules.value.filter(s =>
    s.schedule?.parkingLot?.idParkingLot === parkingLotId &&
    s.isActive
  )
  console.log('Active schedules for parking lot:', parkingLotSchedules)
  if (!selectedDate) {
    // Si no hay fecha seleccionada, usar el día actual
    const today = new Date()
    const currentDay = today.getDay() || 7 // Convert Sunday (0) to 7
    console.log('No date selected, using current day:', currentDay)
    // Buscar horario para el día actual
    const schedule = parkingLotSchedules.find(s => s.weekDay?.idWeekDay === currentDay)
    console.log('Found schedule for current day:', schedule)
    if (!schedule) {
      console.log('No schedule found for current day, checking for any active schedule')
      const anySchedule = parkingLotSchedules[0]
      console.log('Found any active schedule:', anySchedule)
      return anySchedule
    }
    return schedule
  }
  // Convertir la fecha seleccionada a día de la semana (Bogotá, UTC-5, sin desfase)
  const [year, month, day] = selectedDate.split('-').map(Number)
  // Usar mediodía local para evitar desfase de zona horaria
  const date = new Date(year, month - 1, day, 12, 0, 0)
  const dayOfWeek = date.getDay() || 7 // Convert Sunday (0) to 7
  console.log('Selected date converted to day of week (Bogotá, mediodía local):', dayOfWeek)
  // Buscar horario para la fecha seleccionada
  const schedule = parkingLotSchedules.find(s => s.weekDay?.idWeekDay === dayOfWeek)
  console.log('Found schedule for selected day:', schedule)
  if (!schedule) {
    console.log('No schedule found for selected day, checking for any active schedule')
    const anySchedule = parkingLotSchedules[0]
    console.log('Found any active schedule:', anySchedule)
    return anySchedule
  }
  return schedule
}

const formatScheduleTime = (schedule) => {
  if (!schedule) {
    console.log('No schedule provided to formatScheduleTime')
    return 'Horario no disponible'
  }

  const formatTime = (time) => {
    if (!time) return ''
    const [hours, minutes] = time.split(':')
    return `${hours}:${minutes}`
  }

  const formattedTime = `${formatTime(schedule.startTime)} - ${formatTime(schedule.endTime)}`
  console.log('Formatted schedule time:', formattedTime)
  return formattedTime
}

// Computed property to get available slots for a parking lot and vehicle type
const getAvailableSlots = (parkingLotId, vehicleTypeId) => {
  return availableSlots.value.filter(slot =>
    slot.parkingLot?.idParkingLot === parkingLotId &&
    slot.vehicleType?.idVehicleType === vehicleTypeId &&
    slot.isActive
  ).length
}

const handleFormUpdate = (formData) => {
  // Validar que todos los campos estén llenos
  if (!formData.vehicleType || !formData.startDate || !formData.startTime || !formData.plate) {
    showParkingLots.value = false
    return
  }
  form.value = { ...formData }
  // Asegurarse de que selectedVehicleType sea siempre el ID numérico
  if (formData.vehicleType && typeof formData.vehicleType === 'object' && 'value' in formData.vehicleType) {
    selectedVehicleType.value = formData.vehicleType.value
  } else {
    selectedVehicleType.value = formData.vehicleType
  }
  showParkingLots.value = true
  updateParkingLotsDisplay()
}

const handleVehicleTypeChange = ({ vehicleTypeId }) => {
  console.log('Vehicle type change raw:', vehicleTypeId)
  // Extraer el valor numérico del objeto Proxy si es necesario
  selectedVehicleType.value = typeof vehicleTypeId === 'object' ? vehicleTypeId.value : vehicleTypeId
  console.log('Selected vehicle type processed:', selectedVehicleType.value)
  updateParkingLotsDisplay()
}

const updateParkingLotsDisplay = () => {
  console.log('Updating parking lots display...')
  console.log('Selected date:', form.value.startDate)
  console.log('Selected vehicle type:', selectedVehicleType.value)

  // Si no hay tipo de vehículo seleccionado, mostrar todos los parqueaderos
  if (!selectedVehicleType.value) {
    console.log('No vehicle type selected, showing all parking lots')
    parkingLots.value = parkingLotsData.value.map(lot => {
      const schedule = getScheduleForDate(lot.id, form.value.startDate)
      console.log(`Schedule for lot ${lot.name}:`, schedule)
      return {
        ...lot,
        price: 'Select a vehicle type to see prices',
        selectedVehicleTypeName: null,
        hours: formatScheduleTime(schedule),
        availableSlots: 0
      }
    })
    updateMapMarkers()
    return
  }

  // Filtrar los parqueaderos que tienen el tipo de vehículo seleccionado
  const filteredLots = parkingLotsData.value.filter(lot => {
    const hasVehicleType = activeTab.value === 'standart' ?
      standardFees.value.some(fee => {
        const feeVehicleTypeId = typeof fee.vehicleType?.idVehicleType === 'object' ?
          fee.vehicleType.idVehicleType.value :
          fee.vehicleType?.idVehicleType

        return fee.parkingLot?.idParkingLot === lot.id &&
               feeVehicleTypeId === selectedVehicleType.value &&
               fee.isActive === true
      }) :
      monthlyFees.value.some(fee => {
        const feeVehicleTypeId = typeof fee.vehicleType?.idVehicleType === 'object' ?
          fee.vehicleType.idVehicleType.value :
          fee.vehicleType?.idVehicleType

        return fee.parkingLot?.idParkingLot === lot.id &&
               feeVehicleTypeId === selectedVehicleType.value &&
               fee.isActive === true
      })

    console.log(`Parking lot ${lot.name} has vehicle type ${selectedVehicleType.value}:`, hasVehicleType)
    return hasVehicleType
  })

  console.log('Filtered lots:', filteredLots)

  parkingLots.value = filteredLots.map(lot => {
    // Obtener la tarifa específica para el tipo de vehículo seleccionado
    const fee = activeTab.value === 'standart' ?
      standardFees.value.find(fee => {
        const feeVehicleTypeId = typeof fee.vehicleType?.idVehicleType === 'object' ?
          fee.vehicleType.idVehicleType.value :
          fee.vehicleType?.idVehicleType

        return fee.parkingLot?.idParkingLot === lot.id &&
               feeVehicleTypeId === selectedVehicleType.value &&
               fee.isActive === true
      }) :
      monthlyFees.value.find(fee => {
        const feeVehicleTypeId = typeof fee.vehicleType?.idVehicleType === 'object' ?
          fee.vehicleType.idVehicleType.value :
          fee.vehicleType?.idVehicleType

        return fee.parkingLot?.idParkingLot === lot.id &&
               feeVehicleTypeId === selectedVehicleType.value &&
               fee.isActive === true
      })

    console.log('Found fee for lot', lot.name, ':', fee)

    const schedule = getScheduleForDate(lot.id, form.value.startDate)
    const availableSlotsCount = getAvailableSlots(lot.id, selectedVehicleType.value)

    // Obtener el nombre del tipo de vehículo seleccionado
    const selectedVehicleTypeName = activeTab.value === 'standart' ?
      standardFees.value.find(fee => {
        const feeVehicleTypeId = typeof fee.vehicleType?.idVehicleType === 'object' ?
          fee.vehicleType.idVehicleType.value :
          fee.vehicleType?.idVehicleType
        return feeVehicleTypeId === selectedVehicleType.value
      })?.vehicleType?.name :
      monthlyFees.value.find(fee => {
        const feeVehicleTypeId = typeof fee.vehicleType?.idVehicleType === 'object' ?
          fee.vehicleType.idVehicleType.value :
          fee.vehicleType?.idVehicleType
        return feeVehicleTypeId === selectedVehicleType.value
      })?.vehicleType?.name

    console.log('Selected vehicle type name:', selectedVehicleTypeName)

    // Obtener el precio a mostrar según el tipo de tarifa
    let displayPrice = 'Price not available'
    if (fee) {
      if (activeTab.value === 'standart') {
        displayPrice = `$${fee.priceForHours}/hour`
      } else {
        displayPrice = `$${fee.price}/month`
      }
    }

    return {
      ...lot,
      price: displayPrice,
      selectedVehicleTypeName,
      hours: formatScheduleTime(schedule),
      availableSlots: availableSlotsCount
    }
  })

  // Actualizar los marcadores en el mapa
  updateMapMarkers()
}

const clearMarkers = () => {
  console.log('Clearing all markers...')
  if (markers.value && markers.value.length > 0) {
    markers.value.forEach(marker => {
      if (marker) {
        // Cerrar la ventana de información si está abierta
        if (marker.infoWindow) {
          marker.infoWindow.close()
          marker.infoWindow = null
        }
        // Remover el marcador del mapa y destruirlo
        marker.setMap(null)
        marker.setVisible(false)
        marker.setAnimation(null)
        // Remover todos los listeners
        google.maps.event.clearInstanceListeners(marker)
      }
    })
    markers.value = []
    console.log('All markers cleared')
  }
}

const updateMapMarkers = () => {
  console.log('Updating map markers...')
  console.log('Map ready:', isMapReady.value)
  console.log('Map instance:', map.value)

  if (!map.value || !isMapReady.value) {
    console.log('Map not ready, skipping marker update')
    return
  }

  // Limpiar todos los marcadores existentes
  clearMarkers()

  // Determinar qué parqueaderos mostrar en el mapa
  let lotsToShow = []
  if (showParkingLots.value && parkingLots.value && parkingLots.value.length > 0) {
    lotsToShow = parkingLots.value
  } else if (parkingLotsData.value && parkingLotsData.value.length > 0) {
    lotsToShow = parkingLotsData.value
  }

  // Si no hay parqueaderos, centrar el mapa en Armenia
  if (!lotsToShow || lotsToShow.length === 0) {
    map.value.setCenter({ lat: 4.5333, lng: -75.6833 })
    map.value.setZoom(14)
    return
  }

  // Crear un bounds para ajustar el mapa a todos los marcadores
  const bounds = new google.maps.LatLngBounds()
  let markersAdded = 0

  lotsToShow.forEach(lot => {
    if (lot.latitude && lot.longitude) {
      const position = {
        lat: parseFloat(lot.latitude),
        lng: parseFloat(lot.longitude)
      }
      try {
        const marker = new google.maps.Marker({
          position: position,
          map: map.value,
          title: lot.name,
          icon: {
            url: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png',
            scaledSize: new google.maps.Size(32, 32)
          },
          animation: google.maps.Animation.DROP,
          zIndex: markersAdded
        })
        // InfoWindow solo si hay datos extendidos (cards filtradas)
        let infoContent = `<div class='p-3 max-w-xs'><h3 class='font-semibold text-base mb-1'>${lot.name}</h3><div class='space-y-1'><p class='text-sm text-gray-600'>${lot.address || ''}</p>`
        if (showParkingLots.value && lot.selectedVehicleTypeName) {
          infoContent += `<p class='text-base font-bold text-blue-600'>${lot.price}</p><p class='text-xs text-gray-500'>${lot.selectedVehicleTypeName}</p>`
        }
        infoContent += `</div></div>`
        const infoWindow = new google.maps.InfoWindow({
          content: infoContent,
          maxWidth: 300
        })
        marker.addListener('click', () => {
          markers.value.forEach(m => { if (m.infoWindow) m.infoWindow.close() })
          infoWindow.open(map.value, marker)
          marker.infoWindow = infoWindow
        })
        bounds.extend(position)
        marker.infoWindow = infoWindow
        markers.value.push(marker)
        markersAdded++
      } catch (error) {
        console.error('Error creating marker for lot:', lot.name, error)
      }
    }
  })
  if (markers.value.length > 0) {
    map.value.fitBounds(bounds)
    if (markers.value.length === 1) {
      map.value.setZoom(15)
    }
  }
}

// Actualizar el mapa cuando cambie el tipo de vehículo o la fecha
watch([() => selectedVehicleType.value, () => form.value.startDate], () => {
  if (isMapReady.value) {
    updateMapMarkers()
  }
})

// Modificar el watcher de la fecha para agregar más logs
watch(() => form.value.startDate, (newDate, oldDate) => {
  console.log('Date changed from', oldDate, 'to', newDate)
  if (newDate !== oldDate) {
    console.log('Updating parking lots display due to date change')
    console.log('New date details:', {
      date: newDate,
      dayOfWeek: newDate ? new Date(newDate).getDay() || 7 : 'current day',
      formattedDate: newDate ? new Date(newDate).toLocaleDateString() : 'current date'
    })
    updateParkingLotsDisplay()
  }
})

const fetchParkingLotsWithInfo = async () => {
  try {
    // Get all required data
    const [lots, fees, slots, schedules] = await Promise.all([
      parkingLotsService.getAllParkingLots(),
      activeTab.value === 'standart' ?
        standardFeesService.getAllStandardFees() :
        monthlyFeesService.getAllMonthlyFees(),
      slotsService.getAllSlots(),
      schedulesService.getAllDailySchedules()
    ])

    // Filtrar solo los slots activos y disponibles
    const availableAndActiveSlots = slots.filter(slot => slot.isActive && slot.isAvailable)

    parkingLotsData.value = lots.map(lot => ({
      id: lot.idParkingLot,
      name: lot.name,
      address: lot.address,
      latitude: parseFloat(lot.coordX),
      longitude: parseFloat(lot.coordY),
      isActive: lot.isActive
    }))

    if (activeTab.value === 'standart') {
      standardFees.value = fees
    } else {
      monthlyFees.value = fees
    }

    dailySchedules.value = schedules
    availableSlots.value = availableAndActiveSlots

    // Update the display
    updateParkingLotsDisplay()

    // Initialize map if Google Maps is loaded
    if (window.google) {
      initHomeMap()
    }
  } catch (error) {
    console.error('Error fetching parking lots:', error)
  }
}

const addHours = (lot) => {
  selectedLot.value = lot
  selectedStartTime.value = form.value.startTime
  selectedPlate.value = form.value.plate
  selectedVehicleTypeName.value = lot.selectedVehicleTypeName || ''
  updateFilteredAddOnServices(lot.id)
  showReservationModal.value = true
}

const handleReservationComplete = async () => {
  showReservationModal.value = false
  // Recargar los datos de slots y parqueaderos después de reservar
  await fetchParkingLotsWithInfo()
}

// Función específica para inicializar el mapa de Home
const initHomeMap = () => {
  if (!window.google) {
    console.error('Google Maps API not loaded')
    return
  }

  try {
    console.log('Initializing map...')
    const mapElement = document.getElementById("home-map")
    if (!mapElement) {
      console.error('Map element not found')
      return
    }

    // Si ya existe una instancia del mapa, limpiarla
    if (map.value) {
      console.log('Cleaning up existing map instance')
      clearMarkers()
      map.value = null
    }

    // Crear nueva instancia del mapa
    map.value = new google.maps.Map(mapElement, {
      center: { lat: 4.5333, lng: -75.6833 }, // Armenia, Quindío coordinates
      zoom: 14,
      mapTypeControl: true,
      streetViewControl: true,
      fullscreenControl: true
    })

    console.log('Map initialized successfully')
    isMapReady.value = true

    // Actualizar marcadores después de inicializar el mapa
    updateMapMarkers()
  } catch (error) {
    console.error('Error initializing map:', error)
  }
}

// Función para cargar la API de Google Maps
const loadGoogleMapsAPI = () => {
  return new Promise((resolve, reject) => {
    if (window.google) {
      console.log('Google Maps API already loaded')
      resolve()
      return
    }

    console.log('Loading Google Maps API...')
    const script = document.createElement('script')
    script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyCSWk47E6T5IrOjDyXA3d3pzG-DyIMVrs8`
    script.async = true
    script.defer = true
    script.onload = () => {
      console.log('Google Maps API loaded successfully')
      resolve()
    }
    script.onerror = (error) => {
      console.error('Failed to load Google Maps API:', error)
      reject(new Error('Failed to load Google Maps API'))
    }
    document.head.appendChild(script)
  })
}

onMounted(async () => {
  try {
    // Cargar servicios adicionales antes de cualquier filtro
    allAddOnServices.value = await addOnServicesService.getAllAddOnServices()
    console.log('Component mounted, starting initialization...')
    // Cargar la API de Google Maps primero
    await loadGoogleMapsAPI()
    console.log('Google Maps API loaded, initializing map...')

    // Luego cargar los datos
    await Promise.all([
      fetchParkingLotsWithInfo(),
    ])
  } catch (error) {
    console.error('Error during initialization:', error)
  }
})

// Asegurarse de limpiar los marcadores cuando se desmonte el componente
onUnmounted(() => {
  clearMarkers()
  if (map.value) {
    map.value = null
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

.fade-slide-enter-active, .fade-slide-leave-active {
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}
.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(20px);
}
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>
