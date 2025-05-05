<template>
  <LayoutAuthenticated>
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
                  <BaseButton
                    color="info"
                    :icon="mdiPencil"
                    small
                    @click="editUser(user)"
                  />
                  <BaseButton
                    color="danger"
                    :icon="mdiClose"
                    small
                    @click="deactivateUser(user.idUser)"
                  />
                </BaseButtons>
              </td>
            </tr>
          </tbody>
        </table>
      </CardBox>
    </SectionMain>
  </LayoutAuthenticated>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { mdiAccountMultiple, mdiPencil, mdiClose } from '@mdi/js'
import LayoutAuthenticated from '@/layouts/LayoutAuthenticated.vue'
import SectionMain from '@/components/SectionMain.vue'
import SectionTitleLineWithButton from '@/components/SectionTitleLineWithButton.vue'
import CardBox from '@/components/CardBox.vue'
import BaseButtons from '@/components/BaseButtons.vue'
import BaseButton from '@/components/BaseButton.vue'
import { usersService } from '@/services/usersService'

const users = ref([])

const getStatusClass = (isActive) => {
  return isActive ? 'text-green-600 dark:text-green-400' : 'text-red-600 dark:text-red-400'
}

const editUser = (user) => {
  console.log('Edit user:', user)
}

const deactivateUser = async (id) => {
  if (confirm('Are you sure you want to deactivate this user?')) {
    try {
      await usersService.updateUser(id, { isActive: false })
      await fetchUsers()
    } catch (error) {
      console.error('Error deactivating user:', error)
    }
  }
}

const fetchUsers = async () => {
  try {
    const allUsers = await usersService.getAllUsers()
    users.value = allUsers.filter(user => user.isActive)
  } catch (error) {
    console.error('Error fetching users:', error)
    users.value = []
  }
}

onMounted(() => {
  fetchUsers()
})
</script> 