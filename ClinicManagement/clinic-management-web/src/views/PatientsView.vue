<template>
    <div>
      <h1>Lista de pacientes</h1>
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
  