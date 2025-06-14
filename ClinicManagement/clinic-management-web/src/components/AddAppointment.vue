<!-- filepath: c:\Users\joao.guimaraes\workspace\myProjects\clinic-management\ClinicManagement\clinic-management-web\src\components\AddAppointment.vue -->
<template>
    <div class="add-appointment">
      <h2>Add New Appointment</h2>
      <form @submit.prevent="submitAppointment">
        <div>
          <label for="patientName">Patient Name:</label>
          <input
            type="text"
            id="patientName"
            v-model="appointment.patientName"
            required
          />
        </div>
        <div>
          <label for="date">Date:</label>
          <input
            type="date"
            id="date"
            v-model="appointment.date"
            required
          />
        </div>
        <div>
          <label for="time">Time:</label>
          <input
            type="time"
            id="time"
            v-model="appointment.time"
            required
          />
        </div>
        <div>
          <label for="notes">Notes:</label>
          <textarea
            id="notes"
            v-model="appointment.notes"
            placeholder="Optional notes"
          ></textarea>
        </div>
        <button type="submit">Add Appointment</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        appointment: {
          patientName: '',
          date: '',
          time: '',
          notes: '',
        },
      };
    },
    methods: {
      submitAppointment() {
        // Emit the appointment data to the parent or handle it here
        console.log('New Appointment:', this.appointment);
        alert('Appointment added successfully!');
        // Reset the form
        this.appointment = {
          patientName: '',
          date: '',
          time: '',
          notes: '',
        };
      },
    },
  };
  </script>
  
  <style scoped>
  .add-appointment {
    max-width: 400px;
    margin: 0 auto;
  }
  .add-appointment form div {
    margin-bottom: 1rem;
  }
  .add-appointment label {
    display: block;
    margin-bottom: 0.5rem;
  }
  .add-appointment input,
  .add-appointment textarea,
  .add-appointment button {
    width: 100%;
    padding: 0.5rem;
    font-size: 1rem;
  }
  </style>