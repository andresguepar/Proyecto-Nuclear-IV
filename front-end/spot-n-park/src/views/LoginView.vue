<script setup>
import {reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import {mdiAccount, mdiAsterisk} from '@mdi/js'
import CardBox from '@/components/CardBox.vue'
import FormCheckRadio from '@/components/FormCheckRadio.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import LayoutGuest from '@/layouts/LayoutGuest.vue'
import { useAuthStore } from '@/stores/auth.js'

const form = reactive({
  login: '',
  pass: '',
  remember: true,
})

const error = ref('')
const authStore = useAuthStore()
const router = useRouter()

const submit = async () => {
  error.value = ''
  const result = await authStore.login(form.login, form.pass)
  if (result === true) {
    console.log('Login successful, redirecting...')
    window.location.href = '/'
  } else if (result === 'invalid_credentials') {
    error.value = 'Usuario o contraseña incorrectos.'
  } else {
    error.value = 'Error al iniciar sesión. Intenta de nuevo más tarde.'
  }
}

const goHome = () => {
  router.push('/')
}
</script>

<template>
  <LayoutGuest>
    <div class="flex min-h-screen w-full">
      <!-- Columna izquierda: Logo -->
      <div class="hidden md:flex w-1/2 items-center justify-center" style="background-color: #f9fafc;">
        <img src="@/assets/images/spotnpark-logo-home.png" alt="SpotnPark Logo" class="w-[36rem] max-w-2xl" />
      </div>
      <!-- Columna derecha: Formulario con degradado -->
      <div class="flex w-full md:w-1/2 items-center justify-center bg-gradient-to-tr from-white via-blue-200 to-orange-100">
        <CardBox class="w-11/12 md:w-8/12 lg:w-7/12 xl:w-5/12 shadow-2xl" is-form @submit.prevent="submit">
          <FormField label="Login" help="Please enter your login">
            <FormControl
              v-model="form.login"
              :icon="mdiAccount"
              name="login"
              autocomplete="username"
            />
          </FormField>

          <FormField label="Password" help="Please enter tu contraseña">
            <FormControl
              v-model="form.pass"
              :icon="mdiAsterisk"
              type="password"
              name="password"
              autocomplete="current-password"
            />
          </FormField>

          <FormCheckRadio
            v-model="form.remember"
            name="remember"
            label="Remember"
            :input-value="true"
          />

          <div class="w-full flex justify-end mt-2">
            <router-link to="/forgot-password" class="text-blue-600 hover:underline text-sm font-medium">¿Olvidaste tu contraseña?</router-link>
          </div>
          <div class="w-full flex justify-end mt-2">
            <router-link to="/register" class="text-blue-600 hover:underline text-sm font-medium">¿Aún no estás registrado? Regístrate aquí</router-link>
          </div>

          <div v-if="error" class="w-full mb-4 p-2 bg-red-100 text-red-700 rounded text-center">
            {{ error }}
          </div>

          <template #footer>
            <BaseButtons>
              <BaseButton type="submit" color="info" label="Login" />
              <BaseButton @click="goHome" color="info" outline label="Back" />
            </BaseButtons>
          </template>
        </CardBox>
      </div>
    </div>
  </LayoutGuest>
</template>
