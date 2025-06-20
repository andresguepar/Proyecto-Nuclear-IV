<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import CardBox from '@/components/CardBox.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import BaseButton from '@/components/BaseButton.vue'
import LayoutGuest from '@/layouts/LayoutGuest.vue'
import { usersService } from '@/services/usersService.js'

const form = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  phone: ''
})

const error = ref('')
const router = useRouter()

const submit = async () => {
  error.value = ''
  if (form.password !== form.confirmPassword) {
    error.value = 'Las contraseñas no coinciden.'
    return
  }
  try {
    await usersService.createUser({
      idUser: undefined,
      username: form.username, // Usar username
      email: form.email,
      password: form.password,
      phone: form.phone,
      isActive: true,
      id_role: 1 // Asignar siempre rol basic_user
    })
    router.push('/login')
  } catch (e) {
    error.value = 'Error al registrar usuario.'
  }
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
          <h2 class="text-2xl font-bold mb-6 text-center">Registro de usuario</h2>
          <FormField label="Nombre de Usuario">
            <FormControl v-model="form.username" name="username" autocomplete="username" />
          </FormField>
          <FormField label="Email">
            <FormControl v-model="form.email" name="email" type="email" autocomplete="email" />
          </FormField>
          <FormField label="Contraseña">
            <FormControl v-model="form.password" name="password" type="password" autocomplete="new-password" />
          </FormField>
          <FormField label="Confirmar Contraseña">
            <FormControl v-model="form.confirmPassword" name="confirmPassword" type="password" autocomplete="new-password" />
          </FormField>
          <FormField label="Teléfono">
            <FormControl v-model="form.phone" name="phone" type="tel" autocomplete="tel" />
          </FormField>
          <div v-if="error" class="text-red-600 text-sm mb-2 text-center">{{ error }}</div>
          <BaseButton type="submit" color="info" label="Registrarse" class="w-full        </CardBox>
      </div>
    </div>
  </LayoutGuest>
</template>
