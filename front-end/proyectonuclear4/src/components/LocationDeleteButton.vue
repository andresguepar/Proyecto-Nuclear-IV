<template>
  <button class="px-3 py-1 bg-red-500 text-white rounded">Delete</button>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: "LocationDeleteButton",
  props: {
    id: {
      type: String,
      required: true
    }
  }
});
</script>
