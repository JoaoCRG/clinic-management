<template>
  <div class="patient-table">
    <!-- Search and Add Section -->
    <div class="table-header">
      <v-text-field
        v-model="search"
        prepend-inner-icon="mdi-magnify"
        label="Pesquisar pacientes"
        dense
        outlined
        hide-details
        class="search-field"
      ></v-text-field>
      <v-btn
       @click="showAddModal"
        color="primary"
        class="ml-4"
       
      >
        <v-icon left>mdi-plus</v-icon>
        Novo Paciente
      </v-btn>
    </div>

    <!-- Patient Table -->
    <v-data-table
      :headers="headers"
      :items="patients"
      :search="search"
      :loading="loading"
      class="elevation-1 mt-4"
      :items-per-page="10"
      :mobile-breakpoint="0"
      @click:row="openModal"
    >
      <!-- Loading Progress -->
      <template v-slot:progress>
        <v-progress-linear
          indeterminate
          color="primary"
        ></v-progress-linear>
      </template>

      <!-- Empty State -->
      <template v-slot:no-data>
        <div class="text-center pa-4">
          <v-icon large color="grey lighten-1">mdi-account-multiple</v-icon>
          <div class="text-subtitle-1 grey--text mt-2">
            Nenhum paciente encontrado
          </div>
        </div>
      </template>
    </v-data-table>

      <PatientsDetailsModal 
  :isOpen="showModal" 
  :patient="selectedPatient"
  @update:isOpen="showModal = $event"
/>
<AddPatientModal
      :isOpen="showAddModal"
      @update:isOpen="showAddModal = $event"
      @patientCreated="loadPatients"
    />
  </div>
</template>

<script>
import { getAllPatients } from '@/services/PatientService.ts';
import PatientsDetailsModal from './PatientsDetailsModal.vue';
import AddPatientModal from './AddPatientModal.vue';

export default {
  name: 'PatientTable',
  components: {
     PatientsDetailsModal,
     AddPatientModal
     },

  data: () => ({
    search: '',
    loading: false,
    patients: [],
    showDetailsModal: false,
    showModal: false,
      showAddModal: false,
      selectedPatient: null,
    headers: [
      { 
        text: 'Nome',
        align: 'start',
        value: 'name',
        width: '100%'
      }
    ]
  }),

  methods: {
    async loadPatients() {
      this.loading = true;
      try {
        const response = await getAllPatients();
        this.patients = response;
      } catch (error) {
        console.error('Error loading patients:', error);
      } finally {
        this.loading = false;
      }
    },

    openPatientDetails(patient) {
      this.selectedPatient = patient;
      this.showDetailsModal = true;
    },

    editPatient() {
      this.editingPatient = this.selectedPatient;
      this.showDetailsModal = false;
      this.showAddModal = true;
    },

    onPatientSaved() {
      this.showAddModal = false;
      this.editingPatient = null;
      this.loadPatients();
    },
    openModal(patient) {
      this.selectedPatient = patient;
      this.showModal = true;
    }
  },

  mounted() {
    this.loadPatients();
  }
};
</script>

<style scoped>
.patient-table {
  padding: 16px;
}

.table-header {
  display: flex;
  align-items: center;
  gap: 16px;
}

.search-field {
  max-width: 300px;
}

@media (max-width: 600px) {
  .table-header {
    flex-direction: column;
    align-items: stretch;
  }

  .search-field {
    max-width: 100%;
  }
}

.v-data-table ::v-deep tbody tr {
  cursor: pointer;
}

.v-data-table ::v-deep tbody tr:hover {
  background-color: #f5f5f5 !important;
}
</style>
