<template>
  <div :class="[$style.fontSans, 'min-h-screen bg-background font-sans antialiased']">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style module>
@import url('https://fonts.googleapis.com/css2?family=Inter&display=swap');

.fontSans {
  font-family: 'Inter', sans-serif;
}

.bg-background {
  background-color: #f9fafb; /* Tailwind's gray-50 */
}
</style>
