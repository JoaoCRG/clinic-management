<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="4">
        <v-date-picker
          v-model="selectedDate"
          :locale="'pt-PT'"
          color="primary"
        ></v-date-picker>
      </v-col>
      <v-col cols="12" md="8">
        <v-card>
          <v-card-title>
            Consultas para {{ formattedDate }}
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text>
            <v-list v-if="filteredAppointments.length">
              <v-list-item v-for="appointment in filteredAppointments" :key="appointment.id">
                <v-list-item-content>
                  <v-list-item-title>
                    {{ appointment.patientName || 'Paciente desconhecido' }}
                  </v-list-item-title>
                  <v-list-item-subtitle>
                    Procedimento: {{ appointment.procedure }}<br>
                    Hora: {{ formatHour(appointment.date) }}<br>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <div v-else class="grey--text">Nenhuma consulta para este dia.</div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getAllAppointments } from "@/services/AppointmentService.ts";

export default {
  name: "AppointmentCalendar",
  data() {
    return {
      selectedDate: new Date().toISOString().substr(0, 10),
      appointments: [],
    };
  },
  computed: {
    filteredAppointments() {
      if (!Array.isArray(this.appointments)) return [];
      return this.appointments.filter(a => {
        if (!a.date) return false;
        // For format "YYYY-MM-DDTHH:mm"
        const datePart = a.date.split('T')[0];
        return datePart === this.selectedDate;
      });
    },
    formattedDate() {
      return new Date(this.selectedDate).toLocaleDateString("pt-PT");
    },
  },
  methods: {
    async fetchAppointments() {
      const all = await getAllAppointments();
      this.appointments = all;
    },
    formatHour(dateStr) {
      if (!dateStr) return "";
      // For "YYYY-MM-DD HH:mm:ss.SSS"
      const timePart = dateStr.split(' ')[1]?.substring(0,5); // "11:30"
      return timePart || "";
    },
  },
  filters: {
    currency(value) {
      if (value == null) return "€ 0,00";
      return Number(value).toLocaleString("pt-PT", { style: "currency", currency: "EUR" });
    },
  },
  mounted() {
    this.fetchAppointments();
  },
};
</script>

<style scoped>
</style>