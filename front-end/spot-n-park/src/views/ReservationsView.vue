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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { mdiBookMarker, mdiPlus } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBoxModal from '@/components/CardBoxModal.vue'
import BaseButton from '@/components/BaseButton.vue'
import ReservationCard from '@/components/ReservationCard.vue'
import StandardServiceForm from '@/components/StandardServiceForm.vue'
import axios from 'axios'

const router = useRouter()
const reservations = ref([])
const showEditReservationModal = ref(false)
const showCancelModal = ref(false)
const selectedReservation = ref(null)

const fetchReservations = async () => {
  try {
    const response = await axios.get('http://localhost:8080/standard-reservations/get')
    reservations.value = response.data.map(reservation => ({
      id: reservation.idStandardReservation,
      parkingName: reservation.slot.parkingLot.name,
      address: reservation.slot.parkingLot.address,
      date: new Date(reservation.scheduledDateTime).toLocaleDateString(),
      time: new Date(reservation.scheduledDateTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      pricePerHour: reservation.slot.parkingLot.pricePerHour,
      slot: reservation.slot.number,
      status: reservation.statusReservation.name,
      plate: reservation.plate
    }))
  } catch (error) {
    console.error('Error fetching reservations:', error)
  }
}

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

const confirmCancel = async () => {
  try {
    await axios.delete(`http://localhost:8080/standard-reservations/delete/${selectedReservation.value.id}`)
    await fetchReservations() // Refresh the list
    showCancelModal.value = false
    selectedReservation.value = null
  } catch (error) {
    console.error('Error canceling reservation:', error)
  }
}

const handleFormUpdate = async (formData) => {
  try {
    // Update the reservation with the new data
    const updatedReservation = {
      ...selectedReservation.value,
      ...formData
    }
    await axios.put(`http://localhost:8080/standard-reservations/update/${selectedReservation.value.id}`, updatedReservation)
    await fetchReservations() // Refresh the list
    showEditReservationModal.value = false
  } catch (error) {
    console.error('Error updating reservation:', error)
  }
}

onMounted(() => {
  fetchReservations()
})
</script> 