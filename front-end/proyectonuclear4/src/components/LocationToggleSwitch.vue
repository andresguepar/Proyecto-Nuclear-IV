<template>
  <button class="px-3 py-1 bg-gray-200 rounded">Toggle</button>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: "LocationToggleSwitch",
  props: {
    id: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    status: {
      type: String,
      required: true
    }
  }
});
</script>
