<script setup>
import {mdiBackburger, mdiForwardburger, mdiMenu} from '@mdi/js'
import {ref, computed} from 'vue'
import {useRouter} from 'vue-router'
import menuAside from '@/menuAside.js'
import menuNavBar from '@/menuNavBar.js'
import {useDarkModeStore} from '@/stores/darkMode.js'
import BaseIcon from '@/components/BaseIcon.vue'
import NavBar from '@/components/NavBar.vue'
import NavBarItemPlain from '@/components/NavBarItemPlain.vue'
import AsideMenu from '@/components/AsideMenu.vue'
import FooterBar from '@/components/FooterBar.vue'
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
  console.log('Current role:', authStore.role)
  const filteredMenu = menuAside.filter(item => {
    const hasAccess = item.roles?.includes(authStore.role)
    console.log(`Menu item ${item.label}: ${hasAccess ? 'allowed' : 'denied'}`)
    return hasAccess
  })
  console.log('Filtered menu:', filteredMenu)
  return filteredMenu
})

const menuClick = (event, item) => {
  if (item.isToggleLightDark) {
    darkModeStore.set()
  }

  if (item.isLogout) {
    authStore.logout()
    setTimeout(() => {
      router.push('/login')
    }, 100)
  }
}

const isAuthenticated = computed(() => authStore.isAuthenticated())
</script>

<template>
  <div
    :class="{
      'overflow-hidden lg:overflow-visible': isAsideMobileExpanded,
    }"
  >
    <div
      :class="[
        {
          'ml-60 lg:ml-0': isAsideMobileExpanded,
          'transition-all duration-300 ease-in-out': true,
          'xl:pl-0': !isAuthenticated,
          'xl:pl-60': isAuthenticated
        }
      ]"
      class="pt-12 min-h-screen w-screen transition-position lg:w-auto bg-gray-50 dark:bg-slate-800 dark:text-slate-100"
    >
      <NavBar
        :menu="menuNavBar"
        :class="[
          {
            'ml-60 lg:ml-0': isAsideMobileExpanded,
            'transition-all duration-300 ease-in-out': true,
            'xl:pl-0': !isAuthenticated,
            'xl:pl-60': isAuthenticated
          }
        ]"
        @menu-click="menuClick"
      >
        <NavBarItemPlain
          v-if="isAuthenticated"
          display="flex lg:hidden"
          @click.prevent="isAsideMobileExpanded = !isAsideMobileExpanded"
        >
          <BaseIcon :path="isAsideMobileExpanded ? mdiBackburger : mdiForwardburger" size="24" />
        </NavBarItemPlain>
        <NavBarItemPlain
          v-if="isAuthenticated"
          display="hidden lg:flex xl:hidden"
          @click.prevent="isAsideLgActive = true"
        >
          <BaseIcon :path="mdiMenu" size="24" />
        </NavBarItemPlain>
      </NavBar>
      <AsideMenu
        v-if="isAuthenticated"
        :is-aside-mobile-expanded="isAsideMobileExpanded"
        :is-aside-lg-active="isAsideLgActive"
        :menu="allowedMenu"
        @menu-click="menuClick"
        @aside-lg-close-click="isAsideLgActive = false"
        class="transition-all duration-300 ease-in-out"
      />
      <div
        :class="{
          'transition-all duration-300 ease-in-out': true,


        }"
      >
        <slot />
      </div>
      <FooterBar>
        <footer class="py-2 mx-auto">
          <div class="text-center">
            <p class="text-sm text-gray-500">
              &copy; {{ new Date().getFullYear() }} Spot N Park. All rights reserved.
            </p>
          </div>
        </footer>
      </FooterBar>
    </div>
  </div>
</template>
