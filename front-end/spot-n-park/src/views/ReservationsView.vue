<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiBookMarker" title="My Reservations" main>
        <BaseButton
          color="info"
          label="New Reservation"
          :icon="mdiPlus"
          @click="goToHome"
        />
      </SectionTitleLineWithButton>

      <div v-if="reservations.length === 0" class="text-center py-8">
        <p class="text-gray-500">No reservations found</p>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <ReservationCard
          v-for="reservation in reservations"
          :key="reservation.id"
          :reservation="reservation"
          @edit="handleEdit"
          @cancel="handleCancel"
        />
      </div>

      <!-- Edit Reservation Modal -->
      <CardBoxModal
        v-model="showEditReservationModal"
        title="Edit Reservation"
      >
        <StandardServiceForm
          :initial-data="selectedReservation"
          @form-update="handleFormUpdate"
        />
      </CardBoxModal>

      <!-- Cancel Confirmation Modal -->
      <CardBoxModal
        v-model="showCancelModal"
        title="Cancel Reservation"
        button="danger"
        has-cancel
        @confirm="confirmCancel"
      >
        <p>Are you sure you want to cancel this reservation?</p>
      </CardBoxModal>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { mdiBookMarker, mdiPlus } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import BaseButton from '@/components/BaseButton.vue'
import ReservationCard from '@/components/ReservationCard.vue'
import StandardServiceForm from '@/components/StandardServiceForm.vue'

const router = useRouter()

// Mock data - replace with actual API calls
const reservations = ref([
  {
    id: 1,
    parkingName: 'Downtown Parking',
    address: '123 Main St, City',
    date: '2024-03-20',
    time: '14:30',
    pricePerHour: 5.00
  },
  {
    id: 2,
    parkingName: 'Central Parking',
    address: '456 Center Ave, City',
    date: '2024-03-21',
    time: '09:00',
    pricePerHour: 4.50
  }
])

const showEditReservationModal = ref(false)
const showCancelModal = ref(false)
const selectedReservation = ref(null)

const goToHome = () => {
  router.push('/home')
}

const handleEdit = (reservation) => {
  selectedReservation.value = reservation
  showEditReservationModal.value = true
}

const handleCancel = (reservation) => {
  selectedReservation.value = reservation
  showCancelModal.value = true
}

const confirmCancel = () => {
  // Remove the reservation from the list
  reservations.value = reservations.value.filter(r => r.id !== selectedReservation.value.id)
  showCancelModal.value = false
  selectedReservation.value = null
}

const handleFormUpdate = (formData) => {
  // Handle form submission - update or create reservation
  console.log('Form data:', formData)
  showEditReservationModal.value = false
}
</script> 