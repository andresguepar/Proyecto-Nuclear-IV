<script setup>
import {computed, onMounted, ref, watch} from 'vue'
import {
  CategoryScale,
  Chart,
  LinearScale,
  LineController,
  LineElement,
  PointElement,
  Tooltip,
} from 'chart.js'

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
})

const root = ref(null)

let chart

Chart.register(LineElement, PointElement, LineController, LinearScale, CategoryScale, Tooltip)

onMounted(() => {
  chart = new Chart(root.value, {
    type: 'line',
    data: props.data,
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          display: false,
        },
        x: {
          display: true,
        },
      },
      plugins: {
        legend: {
          display: false,
        },
      },
    },
  })
})

const chartData = computed(() => props.data)

watch(chartData, (data) => {
  if (chart) {
    chart.data = data
    chart.update()
  }
})
</script>

<template>
  <canvas ref="root" />
</template>
