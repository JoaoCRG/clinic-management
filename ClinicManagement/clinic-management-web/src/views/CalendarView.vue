<template>
  <div>
    <div class="home">
      <button>
        <img @click="goToHome" alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />
      </button>
    </div>
    <AppointmentCalendar :appointments="appointments" />
  </div>
</template>

<script>
import AppointmentCalendar from '@/components/AppointmentCalendar.vue';
import { getAllAppointments } from '@/services/AppointmentService.ts';

export default {
  components: {
    AppointmentCalendar
  },
  data() {
    return {
      appointments: []
    };
  },
  async mounted() {
    this.appointments = await getAllAppointments();
  },
  methods: {
    goToHome() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.logo {
  justify-content: center;
  width: 200px;
}

</style>

