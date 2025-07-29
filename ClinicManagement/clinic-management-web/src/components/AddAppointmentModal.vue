<template>
  <v-dialog v-model="dialog" max-width="400px" persistent>
    <v-card>
      <v-card-title class="headline">
        Nova Consulta para {{ patient?.name }}
      </v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid">
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-menu
                  ref="dateMenu"
                  v-model="dateMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="appointment.date"
                      label="Data"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                      outlined
                      dense
                      :rules="[v => !!v || 'Data obrigatória']"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="appointment.date"
                    @input="dateMenu = false"
                    locale="pt-PT"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="12">
                <v-select
                  v-model="appointment.time"
                  :items="timeOptions"
                  label="Hora"
                  prepend-icon="mdi-clock"
                  outlined
                  dense
                  :rules="[v => !!v || 'Hora obrigatória']"
                ></v-select>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="appointment.procedure"
                  label="Procedimento"
                  outlined
                  dense
                  :rules="[v => !!v || 'Procedimento obrigatório']"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="close">Cancelar</v-btn>
        <v-btn color="primary" :loading="loading" @click="submit">Adicionar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { createAppointment } from "@/services/AppointmentService.ts";

export default {
  name: 'AddAppointmentModal',
  props: {
    isOpen: Boolean,
    patient: Object
  },
  data() {
    return {
      dialog: this.isOpen,
      valid: false,
      loading: false,
      dateMenu: false,
      appointment: {
        date: '',
        time: '',
        procedure: ''
      },
      timeOptions: [
        '08:00', '08:30', '09:00', '09:30', '10:00', '10:30',
        '11:00', '11:30', '12:00', '12:30', '13:00', '13:30',
        '14:00', '14:30', '15:00', '15:30', '16:00', '16:30',
        '17:00', '17:30', '18:00'
      ]
    };
  },
  watch: {
    isOpen(val) {
      this.dialog = val;
      if (val) this.resetForm();
    },
    dialog(val) {
      this.$emit('update:isOpen', val);
    }
  },
  methods: {
    resetForm() {
      this.appointment = {
        date: '',
        time: '',
        procedure: ''
      };
      this.valid = false;
      this.loading = false;
    },
    close() {
      this.dialog = false;
    },
    async submit() {
      if (!this.$refs.form.validate()) return;
      this.loading = true;
      try {
        const dateTime = `${this.appointment.date}T${this.appointment.time}`;
        const appointmentData = {
          patient: { id: this.patient.id },
          date: dateTime,
          procedure: this.appointment.procedure
        };
        await createAppointment(appointmentData);
        this.$emit('appointment-added', appointmentData);
        this.close();
      } catch (error) {
        this.$emit('show-message', {
          text: 'Erro ao criar consulta',
          color: 'error'
        });
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>