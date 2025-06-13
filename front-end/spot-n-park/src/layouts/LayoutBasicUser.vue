// Este layout es para usuarios con rol basic_user
<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import menuNavBar from '@/menuNavBar.js'
import { useDarkModeStore } from '@/stores/darkMode.js'
import BaseIcon from '@/components/BaseIcon.vue'
import NavBar from '@/components/NavBar.vue'
import NavBarItemPlain from '@/components/NavBarItemPlain.vue'
import AsideMenu from '@/components/AsideMenu.vue'
import FooterBar from '@/components/FooterBar.vue'
import menuAside from '@/menuAside.js'
import { useAuthStore } from '@/stores/auth.js'

const darkModeStore = useDarkModeStore()
const router = useRouter()
const authStore = useAuthStore()
const isAsideMobileExpanded = ref(false)
const isAsideLgActive = ref(false)

router.beforeEach(() => {
  isAsideMobileExpanded.value = false
  isAsideLgActive.value = false
})

const allowedMenu = computed(() => {
  return menuAside.filter(item => item.roles?.includes(authStore.role))
})

const menuClick = (event, item) => {
  if (item && item.isToggleLightDark) {
    darkModeStore.set()
  }
  if (item && item.isLogout) {
    authStore.logout()
    router.push('/login')
  }
}

const isAuthenticated = computed(() => authStore.isAuthenticated())
</script>

<template>
  <div :class="{ 'overflow-hidden lg:overflow-visible': isAsideMobileExpanded }">
    <div
      :class="[
        'flex', // Hace que aside y contenido estén juntos
        'pt-14 min-h-screen w-screen transition-position bg-gray-50 dark:bg-slate-800 dark:text-slate-100',
        { 'ml-0': isAsideMobileExpanded }
      ]"
      style="padding-left:0;padding-right:0;"
    >
      <AsideMenu
        v-if="isAuthenticated"
        :menu="allowedMenu"
        :is-aside-mobile-expanded="isAsideMobileExpanded"
        :is-aside-lg-active="isAsideLgActive"
        @menu-click="menuClick"
        @aside-lg-close-click="isAsideLgActive = false"
        class="transition-all duration-300 ease-in-out"
      />
      <div style="flex:1;min-width:0;padding-left:0;padding-right:0;">
        <NavBar
          :menu="menuNavBar"
          :class="['transition-all duration-300 ease-in-out']"
          @menu-click="menuClick"
        >
          <NavBarItemPlain
            v-if="isAuthenticated"
            display="flex lg:hidden"
            @click.prevent="isAsideMobileExpanded = !isAsideMobileExpanded"
          >
            <BaseIcon :path="isAsideMobileExpanded ? 'mdiBackburger' : 'mdiForwardburger'" size="24" />
          </NavBarItemPlain>
          <NavBarItemPlain
            v-if="isAuthenticated"
            display="hidden lg:flex xl:hidden"
            @click.prevent="isAsideLgActive = true"
          >
            <BaseIcon :path="'mdiMenu'" size="24" />
          </NavBarItemPlain>
        </NavBar>
        <div>
          <slot />
        </div>
        <FooterBar>
          <footer class="py-2 mx-auto">
            <div class="text-center">
              <p class="text-sm text-gray-500">&copy; {{ new Date().getFullYear() }} Spot N Park. All rights reserved.</p>
            </div>
          </footer>
        </FooterBar>
      </div>
    </div>
  </div>
</template>
