<template>
    <v-dialog v-model="dialog" max-width="700px">
      <v-card>
        <v-card-title>
          {{ patientData?.name }}
          <v-spacer></v-spacer>
          <v-btn icon @click="close">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
  
        <v-card-text>
          <v-tabs v-model="tab">
            <v-tab href="#medical">Registo Médico</v-tab>
            <v-tab href="#appointments">Histórico de Consultas</v-tab>
          </v-tabs>
  
          <v-tabs-items v-model="tab">
            <!-- Aba Registo Médico -->
            <v-tab-item value="medical">
              <v-list dense>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title> <v-icon>mdi-arrow-left</v-icon> Última Consulta</v-list-item-title>
                    <v-list-item-subtitle>{{ patient?.medicalRegistry?.lastDoctorVisit }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title><v-icon>mdi-medication</v-icon>  Hospitalização</v-list-item-title>
                    <v-list-item-subtitle>{{ patient?.medicalRegistry?.hospitalization ? 'Sim' : 'Não' }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item v-if="patient?.medicalRegistry?.hospitalization">
                  <v-list-item-content>
                    <v-list-item-title><v-icon>mdi-information</v-icon> Motivo</v-list-item-title>
                    <v-list-item-subtitle>{{ patient?.medicalRegistry?.hospitalizationReason }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title><v-icon>mdi-pill</v-icon>  Medicação</v-list-item-title>
                    <v-list-item-subtitle>{{ patient?.medicalRegistry?.medication }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title><v-icon>mdi-medical-bag</v-icon> Doenças</v-list-item-title>
                    <v-list-item-subtitle>{{ patient?.medicalRegistry?.diseases }}</v-list-item-subtitle>
                  </v-list-item-content>
                  <v-list-item-content>
                    <v-list-item-title><v-icon>mdi-allergy</v-icon> Alergias</v-list-item-title>
                    <v-list-item-subtitle>{{ patient?.medicalRegistry?.allergies }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
              <v-card-actions class="buttons">
          <v-spacer></v-spacer>
          <button>Editar Informação</button>
        </v-card-actions>
            </v-tab-item>
  
            <!-- Aba Histórico de Consultas -->
            <v-tab-item value="appointments">
              <v-list dense>
                <template v-if="patient?.appointmentHistory.length">
                  <v-list-item v-for="appointment in patient.appointmentHistory" :key="appointment.id">
                    <v-list-item-content>
                      <v-list-item-title>Data: {{ appointment.date }}</v-list-item-title>
                      <v-list-item-subtitle>Procedimento: {{ appointment.procedure }}</v-list-item-subtitle>
                      
                      <v-list dense>
                        <v-list-item v-for="lot in appointment.lots" :key="lot.id">
                          <v-list-item-content>
                            <v-list-item-title>Lote: {{ lot.lotNumber }}</v-list-item-title>
                            <v-list-item-subtitle>Marca: {{ lot.brand }} - Ref: {{ lot.reference }}</v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                      </v-list>
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <v-list-item v-else>
                  <v-list-item-content>
                    <v-list-item-title>Nenhum histórico encontrado.</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
              <v-card-actions class="buttons">
          <v-spacer></v-spacer>
          <button>Adicionar Marcação</button>
        </v-card-actions>
            </v-tab-item>
          </v-tabs-items>
        </v-card-text>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
export default {
  methods: {
  close() {
    this.$emit("update:isOpen", false); // Emite evento para fechar o modal
  }
},
  props: {
    isOpen: Boolean,
    patient: Object
  },
  data() {
    return {
      tab: "medical",
      patientData: null // Armazena localmente para garantir atualização reativa
    };
  },
  computed: {
    dialog: {
      get() {
        return this.isOpen;
      },
      set(value) {
        this.$emit("update:isOpen", value);
      }
    }
  },
  watch: {
    patient: {
      handler(newPatient) {
        console.log("Novo paciente recebido na modal:", newPatient); // Debug
        this.patientData = newPatient;
      },
      immediate: true // Garante que o watch roda na criação do componente
    }
  }
};
</script>

<style scoped>
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

title {
  background: rgb(216, 170, 2);
font-size: large;
}
.logo {
align-content: center;
width: 200px;
}
</style>

  