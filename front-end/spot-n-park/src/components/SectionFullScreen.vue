<script setup>
import {computed} from 'vue'
import {useDarkModeStore} from '@/stores/darkMode.js'
import {gradientBgDark, gradientBgPinkRed, gradientBgPurplePink} from '@/colors.js'

const props = defineProps({
  bg: {
    type: String,
    required: true,
    validator: (value) => ['purplePink', 'pinkRed', 'brand'].includes(value),
  },
})

const colorClass = computed(() => {
  if (useDarkModeStore().isEnabled) {
    return gradientBgDark
  }

  switch (props.bg) {
    case 'brand':
      return 'bg-gradient-to-tr from-white via-blue-200 to-orange-100';
    case 'purplePink':
      return gradientBgPurplePink
    case 'pinkRed':
      return gradientBgPinkRed
  }

  return ''
})
</script>

<template>
  <div class="flex min-h-screen items-center justify-center" :class="colorClass">
    <slot card-class="w-11/12 md:w-7/12 lg:w-6/12 xl:w-4/12 shadow-2xl" />
  </div>
</template>
