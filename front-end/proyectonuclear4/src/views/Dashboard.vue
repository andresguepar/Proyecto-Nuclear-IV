<template>
  <div class="grid lg:grid-cols-4 md:grid-cols-2 gap-2 p-2">
    <LocationCard
      v-for="location in locations"
      :key="location.id"
      :id="location.id"
      :name="location.name"
      :address="location.address"
      :numberOfSpots="location.numberOfSpots"
      :spotsBooked="location.spotsBooked"
      :spotsAvailable="location.spotsAvailable"
      :status="location.status"
      :price="location.price"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import LocationCard from '../components/LocationCard.vue';
import type { PropType } from 'vue';

interface Location {
  id: string;
  name: string;
  address: string;
  numberOfSpots: number;
  spotsBooked: number;
  spotsAvailable: number;
  status: string;
  price: {
    hourly: number;
  };
}

export default defineComponent({
  name: "Dashboard",
  components: {
    LocationCard,
  },
  data() {
    return {
      locations: [
        {
          id: "1",
          name: "Main St",
          address: "123 Main St, City",
          numberOfSpots: 10,
          spotsBooked: 6,
          spotsAvailable: 4,
          status: "active",
          price: { hourly: 5 },
        },
        {
          id: "2",
          name: "Second St",
          address: "456 Second St, City",
          numberOfSpots: 8,
          spotsBooked: 3,
          spotsAvailable: 5,
          status: "inactive",
          price: { hourly: 4 },
        },
      ] as Location[],
    };
  },
});
</script>
