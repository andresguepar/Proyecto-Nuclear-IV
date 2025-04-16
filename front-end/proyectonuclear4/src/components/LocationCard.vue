<template>
  <Card class="w-full lg:w-[350px]">
    <template #header>
      <LocationToggleSwitch :id="id" :name="name" :status="status" />
      <p class="text-md">{{ address }}</p>
    </template>

    <template #content>
      <div class="mb-4 grid grid-cols-1 items-start pb-4">
        <div class="space-y-2">
          <p class="text-sm font-medium leading-none">Hourly price: {{ formatAmountForDisplay(price.hourly, 'CAD') }}</p>
          <p class="text-sm font-medium leading-none">Number of spots: {{ numberOfSpots }}</p>
          <hr />
          <p class="text-sm font-medium leading-none">Spots booked: {{ spotsBooked }}</p>
          <p class="text-sm font-medium leading-none">Spots Available: {{ spotsAvailable }}</p>
        </div>
      </div>
    </template>

    <template #footer>
      <LocationDeleteButton :id="id" />
      <router-link :to="`/edit/${id}`">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5h2m-1 0v14m-7-7h14" />
        </svg>
      </router-link>
    </template>
  </Card>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import Card from './Card.vue';
import LocationToggleSwitch from './LocationToggleSwitch.vue';
import LocationDeleteButton from './LocationDeleteButton.vue';

interface Price {
  hourly: number;
}

export default defineComponent({
  name: 'LocationCard',
  components: {
    Card,
    LocationToggleSwitch,
    LocationDeleteButton,
  },
  props: {
    id: { type: String, required: true },
    name: { type: String, required: true },
    address: { type: String, required: true },
    numberOfSpots: { type: Number, required: true },
    spotsBooked: { type: Number, required: true },
    spotsAvailable: { type: Number, required: true },
    status: { type: String, required: true },
    price: {
      type: Object as PropType<Price>,
      required: true,
      default: () => ({ hourly: 0 }),
    },
  },
  methods: {
    formatAmountForDisplay(amount: number, currency: string): string {
      return new Intl.NumberFormat('en-CA', {
        style: 'currency',
        currency,
      }).format(amount);
    },
  },
});
</script>
