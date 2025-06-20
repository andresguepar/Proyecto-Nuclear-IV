<template>
  <span
    class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
    :class="[
      colorClasses[color] || colorClasses.default,
      className
    ]"
  >
    {{ label }}
  </span>
</template>

<script setup>
defineProps({
  label: {
    type: String,
    required: true
  },
  color: {
    type: String,
    default: 'default',
    validator: (value) => ['default', 'success', 'danger', 'warning', 'info'].includes(value)
  },
  className: {
    type: String,
    default: ''
  }
})

const colorClasses = {
  default: 'bg-gray-100 text-gray-800 dark:bg-gray-700 dark:text-gray-300',
  success: 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-300',
  danger: 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-300',
  warning: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-300',
  info: 'bg-[#0D2F78]/10 text-[#0D2F78] dark:bg-[#0B245A] dark:text-[#0D2F78]'
}
</script> 