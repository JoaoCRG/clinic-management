<template>
    <v-container>
      <v-calendar
        v-model="selectedDate"
        type="day"
        :events="events"
        @click:event="onEventClick"
        @click:time="onTimeClick"
      />
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        selectedDate: new Date().toISOString().substr(0, 10),
        events: [],
      };
    },
    methods: {
      onTimeClick(time) {
        const start = new Date(time.date);
        const end = new Date(start.getTime() + 30 * 60 * 1000);
  
        this.events.push({
          name: 'Nova Marcação',
          start,
          end,
          color: 'red',
        });
      },
      onEventClick(event) {
        alert(`Evento: ${event.event.name}`);
      },
    },
  };
  </script>
  
  <style>
  .v-calendar {
    height: 600px;
  }
  </style>
  