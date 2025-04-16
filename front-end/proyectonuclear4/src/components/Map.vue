<template>
  <div ref="mapContainer" class="w-full h-64 rounded-lg border"></div>
</template>

<script>
export default {
  name: "Map",
  props: {
    center: {
      type: Object,
      default: () => ({ lat: 43.6532, lng: -79.3832 }) // Default to Toronto
    },
    markers: {
      type: Array,
      default: () => []
    },
    apiKey: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      map: null,
      google: null,
    };
  },
  mounted() {
    if (!window.google) {
      const script = document.createElement("script");
      script.src = `https://maps.googleapis.com/maps/api/js?key=${this.apiKey}`;
      script.async = true;
      script.defer = true;
      script.onload = this.initMap;
      document.head.appendChild(script);
    } else {
      this.initMap();
    }
  },
  methods: {
    initMap() {
      this.google = window.google;
      this.map = new this.google.maps.Map(this.$refs.mapContainer, {
        center: this.center,
        zoom: 12,
      });

      this.markers.forEach(markerData => {
        new this.google.maps.Marker({
          position: { lat: markerData.lat, lng: markerData.lng },
          map: this.map,
          title: markerData.title || "",
        });
      });
    },
  },
};
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 256px;
}
</style>
