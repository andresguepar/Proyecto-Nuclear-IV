<template>
  <div class="rounded-lg border bg-white text-gray-900 shadow-sm" :class="className">
    <div class="flex flex-col space-y-1.5 p-6">
      <slot name="header"></slot>
    </div>
    <div class="p-6 pt-0">
      <slot name="content"></slot>
    </div>
    <div class="flex items-center p-6 pt-0">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Card",
  props: {
    className: {
      type: String,
      default: ""
    }
  }
};
</script>
