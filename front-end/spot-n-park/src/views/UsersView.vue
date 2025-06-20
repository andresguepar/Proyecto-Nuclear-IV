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
      <CardBox class="mt-8" is-form @submit.prevent="isEditing ? updateUser() : createUser()">
        <h3 class="text-lg font-bold mb-4">{{ isEditing ? 'Editar usuario' : 'Crear nuevo usuario' }}</h3>
        <FormField label="Nombre de Usuario">
          <FormControl v-model="formData.username" name="username" />
        </FormField>
        <FormField label="Email">
          <FormControl v-model="formData.email" name="email" type="email" />
        </FormField>
        <FormField label="Contraseña">
          <FormControl v-model="formData.password" name="password" type="password" :placeholder="isEditing ? 'Dejar vacío para mantener la actual' : ''" />
        </FormField>
        <FormField label="Teléfono">
          <FormControl v-model="formData.phone" name="phone" type="tel" />
        </FormField>
        <FormField label="Rol">
          <select v-model="formData.id_role" class="form-select">
            <option v-for="role in availableRoles" :key="role.id" :value="role.id">
              {{ role.name }}
            </option>
          </select>
        </FormField>
        <div class="flex gap-2 mt-4">
          <BaseButton type="submit" color="info" :label="isEditing ? 'Actualizar usuario' : 'Crear usuario'" class="flex-1" />
          <BaseButton v-if="isEditing" type="button" color="danger" label="Cancelar" @click="cancelEdit" class="flex-1" />
        </div>
        <div v-if="error" class="text-red-600 text-sm mt-2 text-center">{{ error }}</div>
        <div v-if="success" class="text-green-600 text-sm mt-2 text-center">{{ success }}</div>
      </CardBox>
    </SectionMain>
  </LayoutRoleBased>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
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
import { useAuthStore } from '@/stores/auth.js'

const authStore = useAuthStore()
const users = ref([])
const isEditing = ref(false)
const editingUserId = ref(null)
const formData = ref({ username: '', email: '', password: '', phone: '', id_role: 2 })
const error = ref('')
const success = ref('')

// Definir roles disponibles según el usuario actual
const availableRoles = computed(() => {
  const currentUserRole = authStore.user?.role?.idRole
  
  if (currentUserRole === 3) { // super_admin puede asignar cualquier rol
    return [
      { id: 1, name: 'basic_user' },
      { id: 2, name: 'park_admin' },
      { id: 3, name: 'super_admin' }
    ]
  } else if (currentUserRole === 2) { // park_admin solo puede asignar basic_user
    return [
      { id: 1, name: 'basic_user' }
    ]
  } else { // basic_user no puede crear usuarios
    return []
  }
})

const resetForm = () => {
  formData.value = { 
    username: '', 
    email: '', 
    password: '', 
    phone: '', 
    id_role: availableRoles.value.length > 0 ? availableRoles.value[0].id : 1 
  }
  isEditing.value = false
  editingUserId.value = null
  error.value = ''
  success.value = ''
}

const editUser = (user) => {
  isEditing.value = true
  editingUserId.value = user.idUser
  formData.value = {
    username: user.username,
    email: user.email,
    password: '', // No mostrar la contraseña actual
    phone: user.phone || '',
    id_role: user.role?.idRole || 1
  }
  error.value = ''
  success.value = ''
}

const cancelEdit = () => {
  resetForm()
}

const deactivateUser = async (id) => {
  if (confirm('Are you sure you want to deactivate this user?')) {
    try {
      await usersService.updateUser(id, { isActive: false })
      await fetchUsers()
      success.value = 'Usuario desactivado exitosamente.'
      setTimeout(() => success.value = '', 3000)
    } catch {
      error.value = 'Error al desactivar usuario.'
      setTimeout(() => error.value = '', 3000)
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
  success.value = ''
  try {
    await usersService.createUser({
      username: formData.value.username,
      email: formData.value.email,
      password: formData.value.password,
      phone: formData.value.phone,
      isActive: true,
      id_role: formData.value.id_role
    })
    resetForm()
    await fetchUsers()
    success.value = 'Usuario creado exitosamente.'
    setTimeout(() => success.value = '', 3000)
  } catch (err) {
    console.error('Error creating user:', err)
    error.value = 'Error al crear usuario.'
    setTimeout(() => error.value = '', 3000)
  }
}

const updateUser = async () => {
  error.value = ''
  success.value = ''
  try {
    const updateData = {
      idUser: editingUserId.value, // Incluir el ID del usuario a actualizar
      username: formData.value.username,
      email: formData.value.email,
      phone: formData.value.phone,
      isActive: true,
      id_role: formData.value.id_role
    }
    
    // Solo incluir contraseña si se proporcionó una nueva
    if (formData.value.password.trim()) {
      updateData.password = formData.value.password
    }
    
    await usersService.updateUser(editingUserId.value, updateData)
    resetForm()
    await fetchUsers()
    success.value = 'Usuario actualizado exitosamente.'
    setTimeout(() => success.value = '', 3000)
  } catch (err) {
    console.error('Error updating user:', err)
    error.value = 'Error al actualizar usuario.'
    setTimeout(() => error.value = '', 3000)
  }
}

onMounted(() => {
  fetchUsers()
})
</script>
