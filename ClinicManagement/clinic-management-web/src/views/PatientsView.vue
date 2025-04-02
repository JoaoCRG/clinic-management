<template>
    <div>
      <div class="home">
        <button>
    <img @click="goToPatients" alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />
  </button>
  </div>
      <PatientTable/>
    </div>
  </template>
  
  <script>
    import PatientTable from '@/components/PatientTable.vue';
  
  export default {
    components: {
      PatientTable
    },
    methods: {
      goToPatients() {
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

  