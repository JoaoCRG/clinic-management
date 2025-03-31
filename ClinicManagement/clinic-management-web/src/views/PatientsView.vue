<template>
    <div>
      <div class="home">
    <img alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />
  </div>
      <PatientTable/>
    </div>
  </template>
  
  <script>
    import PatientTable from '@/components/PatientTable.vue';
  
  export default {
    components: {
      PatientTable
    }
  }
  </script>

<style scoped>
.logo {
  justify-content: center;
width: 100px;
}

.buttons {
  display: flex;
  gap: 20px;
}

button {
  padding: 10px 20px;
  border: none;
  background: white;
  color: rgb(216, 170, 2);
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background: rgb(216, 170, 2);
  color: white;
}
</style>

  