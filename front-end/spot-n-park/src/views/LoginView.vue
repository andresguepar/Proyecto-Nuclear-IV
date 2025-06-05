<script setup>
import {reactive} from 'vue'
import {useRouter} from 'vue-router'
import {mdiAccount, mdiAsterisk} from '@mdi/js'
import SectionFullScreen from '@/components/SectionFullScreen.vue'
import CardBox from '@/components/CardBox.vue'
import FormCheckRadio from '@/components/FormCheckRadio.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import LayoutGuest from '@/layouts/LayoutGuest.vue'

const form = reactive({
  login: 'john.doe',
  pass: 'highly-secure-password-fYjUw-',
  remember: true,
})

const router = useRouter()

const submit = () => {
  router.push('/dashboard')
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

          <template #footer>
            <BaseButtons>
              <BaseButton type="submit" color="info" label="Login" />
              <BaseButton to="/dashboard" color="info" outline label="Back" />
            </BaseButtons>
          </template>
        </CardBox>
      </div>
    </div>
  </LayoutGuest>
</template>
