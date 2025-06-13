<script setup>
import { useAuthStore } from '@/stores/auth.js'
import LayoutBasicUser from './LayoutBasicUser.vue'
import LayoutParkAdmin from './LayoutParkAdmin.vue'
import LayoutSuperAdmin from './LayoutSuperAdmin.vue'
import LayoutAuthenticated from './LayoutAuthenticated.vue'
import { computed } from 'vue'

const authStore = useAuthStore()
const role = computed(() => authStore.role)
</script>

<template>
  <component :is="
    role === 'basic_user' ? LayoutBasicUser :
    role === 'park_admin' ? LayoutParkAdmin :
    role === 'super_admin' ? LayoutSuperAdmin :
    LayoutAuthenticated
  ">
    <slot />
  </component>
</template>
