<template>
    <div>
      <div class="home">
        <button>
    <img @click="goToHome" alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />
  </button>
  </div>
      <DayCalendar/>
    </div>
  </template>

<script>
    import DayCalendar from '@/components/DayCalendar.vue';


export default {
    components: {
      DayCalendar
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

