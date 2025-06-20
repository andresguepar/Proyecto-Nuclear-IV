<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import CardBox from '@/components/CardBox.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import BaseButton from '@/components/BaseButton.vue'
import LayoutGuest from '@/layouts/LayoutGuest.vue'

const form = reactive({
  username: '',
})

const router = useRouter()

const submit = () => {
  // Aquí iría la lógica para enviar el correo de recuperación
  // Por ahora solo redirige al login
  router.push('/login')
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
      <div class="flex w-full md:w-1/2 items-center justify-center bg-gradient-to-tr from-white via-[#0D2F78]/20 to-orange-100">
        <CardBox class="w-11/12 md:w-8/12 lg:w-7/12 xl:w-5/12 shadow-2xl" is-form @submit.prevent="submit">
          <h2 class="text-2xl font-bold mb-6 text-center">Recuperar contraseña</h2>
          <FormField label="Usuario" help="Ingresa tu usuario para recuperar tu contraseña">
            <FormControl
              v-model="form.username"
              name="username"
              autocomplete="username"
            />
          </FormField>
          <BaseButton type="submit" color="info" label="Enviar correo de recuperación" class="w-full mt-4" />
        </CardBox>
      </div>
    </div>
  </LayoutGuest>
</template>
