<script setup>
defineProps({
  noPadding: Boolean,
})
</script>

<template>
  <div class="flex-1" :class="{ 'p-2': !noPadding }">
    <slot />
  </div>
</template>
