<template>
    <v-dialog v-model="dialog" max-width="800px" height="900px">
      <v-card>
        <v-card-title>
          <img alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />

        </v-card-title>
  
        <v-card-text>
          <v-form ref="form">
            <v-text-field v-model="newPatient.name" label="Nome" required></v-text-field>
            <v-text-field v-model="newPatient.phoneNumber" label="Telemovel" required></v-text-field>
            <v-text-field v-model="newPatient.city" label="Cidade" required></v-text-field>
            <v-text-field v-model="newPatient.birthday" label="Data de Nascimento" required></v-text-field>
            <v-tabs  v-model="tab">
              <v-tab href="#medical">Registo Médico</v-tab>
            </v-tabs>
  
            <v-tabs-items v-model="tab">
              <v-tab-item value="medical">
                <v-text-field v-model="newPatient.medicalRegistry.lastDoctorVisit" label="Última Visita Ao Médico"></v-text-field>
                <v-switch v-model="newPatient.medicalRegistry.hospitalization" label="Hospitalização"></v-switch>
                <v-text-field 
                  v-if="newPatient.medicalRegistry.hospitalization" 
                  v-model="newPatient.medicalRegistry.hospitalizationReason" 
                  label="Motivo da Hospitalização">
                </v-text-field>
                <v-text-field v-model="newPatient.medicalRegistry.medication" label="Medicação"></v-text-field>
                <v-text-field v-model="newPatient.medicalRegistry.diseases" label="Patologias"></v-text-field>
              </v-tab-item>
            </v-tabs-items>
          </v-form>
        </v-card-text>
  
        <v-card-actions class="buttons">
          <v-spacer></v-spacer>
          <button  @click="close">Cancelar</button>
          <button color="golden" @click="submitPatient">Guardar</button>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  import { createPatient } from "@/services/PatientService.ts";
  
  export default {
    props: {
      isOpen: Boolean
    },
    data() {
      return {
        tab: "medical",
        newPatient: {
          name: "",
          phoneNumber: "",
          city:"",
          birthday:"",
          medicalRegistry: {
            lastDoctorVisit: "",
            hospitalization: false,
            hospitalizationReason: "",
            medication: "",
            diseases: "",
            allergy:""
          }
        }
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
    methods: {
      async submitPatient() {
        if (!this.newPatient.name || !this.newPatient.phoneNumber) {
          alert("Nome e Telefone são obrigatórios!");
          return;
        }
  
        try {
          await createPatient(this.newPatient);
          alert("Paciente adicionado com sucesso!");
          this.$emit("patientCreated");
          this.close();
        } catch (error) {
          console.error("Erro ao adicionar paciente:", error);
          alert("Erro ao adicionar paciente.");
        }
      },
      close() {
        this.$emit("update:isOpen", false);
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
  