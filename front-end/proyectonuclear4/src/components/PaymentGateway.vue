<template>
  <div class="max-w-md mx-auto p-4 border rounded-lg shadow-md bg-white">
    <h2 class="text-xl font-semibold mb-4">Simulated Payment Gateway</h2>
    <form @submit.prevent="handlePayment">
      <div class="mb-4">
        <label for="cardNumber" class="block mb-1 font-medium">Card Number</label>
        <input id="cardNumber" v-model="cardNumber" type="text" placeholder="1234 5678 9012 3456" class="w-full border rounded px-3 py-2" required />
      </div>
      <div class="mb-4">
        <label for="expiry" class="block mb-1 font-medium">Expiry Date</label>
        <input id="expiry" v-model="expiry" type="text" placeholder="MM/YY" class="w-full border rounded px-3 py-2" required />
      </div>
      <div class="mb-4">
        <label for="cvc" class="block mb-1 font-medium">CVC</label>
        <input id="cvc" v-model="cvc" type="text" placeholder="123" class="w-full border rounded px-3 py-2" required />
      </div>
      <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">Pay</button>
    </form>
    <p v-if="message" class="mt-4 text-green-600 font-medium">{{ message }}</p>
  </div>
</template>

<script>
export default {
  name: "PaymentGateway",
  data() {
    return {
      cardNumber: "",
      expiry: "",
      cvc: "",
      message: "",
    };
  },
  methods: {
    handlePayment() {
      // Simulate payment processing delay
      this.message = "";
      setTimeout(() => {
        this.message = "Payment successful! (Simulation)";
        this.cardNumber = "";
        this.expiry = "";
        this.cvc = "";
      }, 1000);
    },
  },
};
</script>
