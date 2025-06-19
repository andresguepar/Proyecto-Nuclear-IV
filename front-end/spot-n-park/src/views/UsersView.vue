<template>
  <LayoutRoleBased>
    <SectionMain>
      <SectionTitleLineWithButton :icon="mdiAccountMultiple" title="Users" main />
      <CardBox has-table>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Role</th>
              <th />
            </tr>
          </thead>
          <tbody>
            <tr v-if="users.length === 0">
              <td colspan="5" class="text-center py-24 text-gray-500 dark:text-slate-400">
                <p>No users found</p>
              </td>
            </tr>
            <tr v-for="user in users" :key="user.idUser">
              <td data-label="ID">{{ user.idUser }}</td>
              <td data-label="Username">{{ user.username }}</td>
              <td data-label="Email">{{ user.email }}</td>
              <td data-label="Role">{{ user.role?.name || 'N/A' }}</td>
              <td class="before:hidden lg:w-1 whitespace-nowrap">
                <BaseButtons type="justify-start lg:justify-end" no-wrap>
                  <BaseButton color="info" :icon="mdiPencil" small @click="editUser(user)" />
                  <BaseButton color="danger" :icon="mdiClose" small @click="deactivateUser(user.idUser)" />
                </BaseButtons>
              </td>
            </tr>
          </tbody>
        </table>
      </CardBox>
      <CardBox class="mt-8" is-form @submit.prevent="createUser">
        <h3 class="text-lg font-bold mb-4">Crear nuevo usuario</h3>
        <FormField label="Nombre de Usuario">
          <FormControl v-model="newUser.username" name="username" />
        </FormField>
        <FormField label="Email">
          <FormControl v-model="newUser.email" name="email" type="email" />
        </FormField>
        <FormField label="Contraseña">
          <FormControl v-model="newUser.password" name="password" type="password" />
        </FormField>
        <FormField label="Teléfono">
          <FormControl v-model="newUser.phone" name="phone" type="tel" />
        </FormField>
        <FormField label="Rol">
          <select v-model="newUser.id_role" class="form-select">
            <option value="2">basic_user</option>
            <option value="3">park_admin</option>
            <option value="1">super_admin</option>
          </select>
        </FormField>
        <BaseButton type="submit" color="info" label="Crear usuario" class="w-full mt-4" />
        <div v-if="error" class="text-red-600 text-sm mt-2 text-center">{{ error }}</div>
      </CardBox>
    </SectionMain>
  </LayoutRoleBased>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiAccountMultiple, mdiPencil, mdiClose } from '@mdi/js'
import LayoutRoleBased from '@/layouts/LayoutRoleBased.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import FormField from '@/components/FormField.vue'
import FormControl from '@/components/FormControl.vue'
import { usersService } from '@/services/usersService'

const users = ref([])
const newUser = ref({ username: '', email: '', password: '', phone: '', id_role: 2 })
const error = ref('')

const editUser = (user) => {
  console.log('Edit user:', user)
}

const deactivateUser = async (id) => {
  if (confirm('Are you sure you want to deactivate this user?')) {
    try {
      await usersService.updateUser(id, { isActive: false })
      await fetchUsers()
    } catch {
      // Manejo simple de error
    }
  }
}

const fetchUsers = async () => {
  try {
    const allUsers = await usersService.getAllUsers()
    users.value = allUsers.filter(user => user.isActive)
  } catch {
    users.value = []
  }
}

const createUser = async () => {
  error.value = ''
  try {
    await usersService.createUser({
      idUser: undefined,
      username: newUser.value.username, // Usar username
      email: newUser.value.email,
      password: newUser.value.password,
      phone: newUser.value.phone,
      isActive: true,
      id_role: newUser.value.id_role
    })
    newUser.value = { username: '', email: '', password: '', phone: '', id_role: 2 }
    await fetchUsers()
  } catch {
    error.value = 'Error al crear usuario.'
  }
}

onMounted(() => {
  fetchUsers()
})
</script>
