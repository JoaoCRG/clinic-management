<template>
  <v-dialog
    v-model="dialogModel"
    fullscreen
    hide-overlay
    transition="dialog-bottom-transition"
  >
    <v-card>
      <!-- Header -->
      <v-toolbar dark color="primary">
        <v-btn icon @click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-toolbar-title>{{ patientData?.name }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon @click="editPatient">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </v-toolbar>

      <!-- Content -->
      <v-container class="pa-0">
        <v-tabs
          v-model="tab"
          fixed-tabs
          background-color="primary"
          dark
          show-arrows
        >
          <v-tab href="#personal">
            <v-icon left>mdi-account</v-icon>
            Dados Pessoais
          </v-tab>
          <v-tab href="#medical">
            <v-icon left>mdi-medical-bag</v-icon>
            Registo Médico
          </v-tab>
          <v-tab href="#appointments">
            <v-icon left>mdi-calendar-clock</v-icon>
            Consultas
          </v-tab>
        </v-tabs>

        <v-tabs-items 
          v-model="tab" 
          class="pt-2"
          touchless
          @touchstart="handleTouchStart"
          @touchmove="handleTouchMove"
          @touchend="handleTouchEnd"
        >
          <!-- Personal Details Tab -->
          <v-tab-item value="personal">
            <v-list>
              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-account</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Nome</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.name }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-phone</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Telefone</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.phoneNumber }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-map-marker</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Cidade</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.city }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-cake-variant</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Data de Nascimento</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ formatDate(patientData?.birthDate) || 'Não informado' }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-tab-item>

          <!-- Medical Tab -->
          <v-tab-item value="medical">
            <v-list>
              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-calendar-check</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Última Consulta</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.medicalRegistry?.lastDoctorVisit || 'Não registrado' }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-medication</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Hospitalização</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.medicalRegistry?.hospitalization ? 'Sim' : 'Não' }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <template v-if="patientData?.medicalRegistry?.hospitalization">
                <v-list-item two-line>
                  <v-list-item-content class="pl-16">
                    <v-list-item-title>Motivo</v-list-item-title>
                    <v-list-item-subtitle>
                      {{ patientData?.medicalRegistry?.hospitalizationReason }}
                    </v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </template>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-pill</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Medicação</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.medicalRegistry?.medication || 'Nenhuma' }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-allergy</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Alergias</v-list-item-title>
                  <v-list-item-subtitle>
                    {{ patientData?.medicalRegistry?.allergies || 'Nenhuma' }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-tab-item>

          <!-- Appointments Tab -->
          <v-tab-item value="appointments">
            <v-list>
              <template v-if="patientData?.appointmentHistory?.length">
                <v-list-item
                  v-for="appointment in patientData.appointmentHistory"
                  :key="appointment.id"
                  class="mb-2"
                >
                  <v-list-item-content>
                    <v-list-item-title class="text-h6">
                      {{ formatDate(appointment.date) }}
                    </v-list-item-title>
                    <v-list-item-subtitle>
                      {{ appointment.procedure }}
                    </v-list-item-subtitle>
                    
                    <v-chip-group v-if="appointment.lots?.length" class="mt-2">
                      <v-chip
                        v-for="lot in appointment.lots"
                        :key="lot.id"
                        small
                        outlined
                      >
                        {{ lot.brand }} - Lote: {{ lot.lotNumber }}
                      </v-chip>
                    </v-chip-group>
                  </v-list-item-content>
                </v-list-item>
              </template>
              <v-list-item v-else>
                <v-list-item-content>
                  <v-list-item-title class="text-center grey--text">
                    Nenhuma consulta registrada
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-tab-item>
        </v-tabs-items>
      </v-container>

      <!-- Floating Action Button -->
      <v-fab-transition>
        <v-btn
          v-if="tab === 'appointments'"
          color="primary"
          dark
          fixed
          bottom
          right
          fab
          class="mb-8 mr-8"
          @click="$emit('add-appointment')"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </v-fab-transition>

      <!-- Edit Patient Dialog -->
      <v-dialog v-model="showEditModal" max-width="600px">
        <v-card>
          <v-card-title>
            <span class="text-h5">Editar Paciente</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="editedPatient.name"
                    label="Nome"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field
                    v-model="editedPatient.phoneNumber"
                    label="Telefone"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field
                    v-model="editedPatient.city"
                    label="Cidade"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="showEditModal = false"
            >
              Cancelar
            </v-btn>
            <v-btn
              color="primary"
              text
              @click="savePatient"
            >
              Salvar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </v-dialog>

 
</template>

<script>
import { editPatient } from '@/services/PatientService.ts';

export default {
  name: 'PatientsDetailsModal',
  

  props: {
    isOpen: Boolean,
    patient: Object
  },

  data() {
    return {
      tab: 'medical',
      isModalOpen: false,
      selectedPatient: null,
      patientData: null,
      touchStart: null,
      touchEnd: null,
      showEditModal: false,
      editedPatient: {
        name: '',
        phoneNumber: '',
        city: ''
      }
    };
  },

  computed: {
    dialogModel: {
      get() {
        return this.isOpen;
      },
      set(value) {
        this.$emit('update:isOpen', value);
      }
    }
  },

  methods: {
    close() {
      this.$emit('update:isOpen', false);
    },

    openPatientModal(patient) {
      this.selectedPatient = { ...patient };
      this.isModalOpen = true;
    },

    onPatientSaved() {
      this.isModalOpen = false;
      this.$emit('refresh');
    },

    formatDate(date) {
      return new Date(date).toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });
    },

    handleTouchStart(e) {
      this.touchStart = e.changedTouches[0].screenX;
    },
    
    handleTouchMove(e) {
      this.touchEnd = e.changedTouches[0].screenX;
    },
    
    handleTouchEnd() {
      const swipeLength = this.touchStart - this.touchEnd;
      if (Math.abs(swipeLength) > 50) {
        const tabs = ['medical', 'appointments'];
        const currentIndex = tabs.indexOf(this.tab);
        if (swipeLength > 0 && currentIndex < tabs.length - 1) {
          this.tab = tabs[currentIndex + 1];
        } else if (swipeLength < 0 && currentIndex > 0) {
          this.tab = tabs[currentIndex - 1];
        }
      }
      this.touchStart = null;
      this.touchEnd = null;
    },

    editPatient() {
      this.editedPatient = { ...this.patientData };
      this.showEditModal = true;
    },

    async savePatient() {
      try {
        await editPatient(this.editedPatient.id, this.editedPatient);
        this.showEditModal = false;
        this.$emit('patient-updated');
        // Refresh patient data
        this.patientData = { ...this.editedPatient };
      } catch (error) {
        console.error('Error updating patient:', error);
        // You might want to show an error message to the user
      }
    }
  },

  watch: {
    patient: {
      handler(newPatient) {
        this.patientData = newPatient;
      },
      immediate: true
    }
  }
};
</script>

<style scoped>
.v-list-item {
  padding: 12px 16px;
}

.v-list-item__avatar {
  margin-right: 16px;
}

.v-chip-group {
  margin-left: -4px;
}

.v-chip {
  margin: 4px;
}
</style>

