<script setup>
defineProps({
  noPadding: Boolean,
})
</script>

<template>
  <div class="flex-1" :class="{ 'p-5': !noPadding }">
    <slot />
  </div>
</template>
