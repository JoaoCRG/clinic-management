<script>
export default {
  methods: {
    goToPatients() {
      this.$router.push('/patients')
    },
    goToCalendar() {
      this.$router.push('/calendar')
    }
  }
}
</script>

<template>
  <div class="home">
    <img alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />
    <div class="buttons">
      <button @click="goToPatients">Lista de Pacientes</button>
      <button @click="goToCalendar">Calendário</button>
    </div>
  </div>
</template>


<style scoped>
.home {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  flex-direction: column;
  background: white;
}

.logo {
  max-width: 400px;
  margin-bottom: 20px;
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
