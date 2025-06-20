<script setup>
import {ref, computed} from 'vue'
import {mdiClose, mdiDotsVertical} from '@mdi/js'
import {containerMaxW} from '@/config.js'
import BaseIcon from '@/components/BaseIcon.vue'
import NavBarMenuList from '@/components/NavBarMenuList.vue'
import NavBarItemPlain from '@/components/NavBarItemPlain.vue'
import { useAuthStore } from '@/stores/auth.js'

const props = defineProps({
  menu: {
    type: Array,
    required: true,
  },
})

const emit = defineEmits(['menu-click'])
const authStore = useAuthStore()

const menuClick = (event, item) => {
  emit('menu-click', event, item)
}

const isMenuNavBarActive = ref(false)

const isAuthenticated = computed(() => authStore.isAuthenticated())
const username = computed(() => authStore.user?.username || '')

function reloadOnLogin(e) {
  // Forzar recarga al ir a login desde HomeView/NavBar
  e.preventDefault();
  window.location.href = '/#/login';
}
</script>

<template>
  <nav
    class="top-0 inset-x-0 fixed bg-gray-50 h-14 z-30 transition-position w-screen lg:w-auto dark:bg-slate-800"
  >
    <div class="flex lg:items-stretch" :class="containerMaxW">
      <div class="flex flex-1 items-stretch h-14">
        <slot />
      </div>
      <div class="flex-none items-stretch flex h-14 lg:hidden">
        <NavBarItemPlain @click.prevent="isMenuNavBarActive = !isMenuNavBarActive">
          <BaseIcon :path="isMenuNavBarActive ? mdiClose : mdiDotsVertical" size="24" />
        </NavBarItemPlain>
      </div>
      <div
        class="max-h-screen-menu overflow-y-auto lg:overflow-visible absolute w-screen top-14 left-0 bg-gray-50 shadow-lg lg:w-auto lg:flex lg:static lg:shadow-none dark:bg-slate-800"
        :class="[isMenuNavBarActive ? 'block' : 'hidden']"
      >
        <template v-if="isAuthenticated">
          <div class="flex items-center px-4 py-2 border-b border-gray-200 dark:border-slate-700">
            <span class="text-sm font-medium text-gray-700 dark:text-gray-200">
              {{ username }}
            </span>
          </div>
          <NavBarMenuList :menu="menu" @menu-click="menuClick" />
        </template>
        <template v-else>
          <div class="flex items-center px-4 py-2">
            <router-link to="/login" class="text-sm font-medium text-[#0D2F78] hover:text-[#0B245A] mr-4" @click.native="reloadOnLogin">
              Iniciar Sesión
            </router-link>
            <router-link to="/register" class="text-sm font-medium text-[#0D2F78] hover:text-[#0B245A]">
              Registrarse
            </router-link>
          </div>
        </template>
      </div>
    </div>
  </nav>
</template>
