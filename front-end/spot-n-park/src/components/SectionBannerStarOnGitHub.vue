<script setup>
import {mdiGithub} from '@mdi/js'
import BaseButton from '@/components/BaseButton.vue'
import SectionBanner from '@/components/SectionBanner.vue'
import {gradientBgPinkRed} from '@/colors'
</script>

<template>
  <SectionBanner :class="gradientBgPinkRed">
    <h1 class="text-3xl text-white mb-6">Like the project? Please star on <b>GitHub</b> ;-)</h1>
    <div>
      <a
        href="https://github.com/spot-n-park"
        target="_blank"
        class="text-blue-600 hover:underline"
      >
        GitHub
      </a>
    </div>
  </SectionBanner>
</template>
