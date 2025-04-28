<template>
  <LayoutAuthenticated>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiParking" title="Parking Lots" main>
      </SectionTitleLineWithButton>

      <CardBox class="mb-6" has-table>
        <ParkingLotsSection />
      </CardBox>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import ParkingLotsSection from '@/components/ParkingLotsSection.vue'
import CardBox from '@/components/CardBox.vue'
</script>
