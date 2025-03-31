<template>
  <div>
    <button class="button" @click="showAddModal = true">Adicionar Paciente</button>
    <v-data-table
      :headers="headers"
      :items="patients"
      class="elevation-1"
    >
 
      <template v-slot:[`item.actions`]="{ item }">
        <v-btn color="black" icon @click="openModal(item)">
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>
    </v-data-table>

    <PatientDetailsModal 
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
import {createPatient, getAllPatients} from "@/services/PatientService.ts"
import PatientDetailsModal from "@/components/PatientsDetailsModal.vue"
import AddPatientModal from "@/components/AddPatientModal.vue"


export default {
  components: {
    PatientDetailsModal,
    AddPatientModal
  },
  data() {
    return {
      showModal: false,
      showAddModal: false,
      selectedPatient: null,
      patients: [],
      headers: [
        { text: "Nome", value: "name" },
        { text: "Telemóvel", value: "phoneNumber" },
        { text: "Cidade", value:"city"},
        { text: "Detalhes", value: "actions", sortable: false } 
      ]
    };
  },
  async mounted() {
    await this.loadPatients();
  },
  methods: {
    async submitForm() {
      try {
        const createdPatient = await createPatient(this.newPatient);
       
        
        // Limpar formulário
        this.newPatient.name = "";
        this.newPatient.phoneNumber = "";

        // Opcional: Atualizar a lista de pacientes
        this.$emit("patientCreated", createdPatient);
      } catch (error) {
        alert("Erro ao adicionar paciente.");
      }
    },
    async loadPatients() {
      try {
        this.patients = await getAllPatients();
      } catch (error) {
        console.error("Erro ao buscar pacientes:", error);
      }
    },
    openModal(patient) {
      this.selectedPatient = patient;
      this.showModal = true;
    }
  }
};
</script>

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
