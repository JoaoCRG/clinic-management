<template>
    <v-dialog v-model="dialog" max-width="800px" height="900px">
      <v-card>
        <v-card-title>
          <img alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />

        </v-card-title>
  
        <v-card-text v-if="isEdit">
          <v-form ref="form">
            <v-text-field v-model="editedPatient.name" label="Nome" required></v-text-field>
            <v-text-field v-model="editedPatient.phoneNumber" label="Telemovel" required></v-text-field>
            <v-text-field v-model="editedPatient.city" label="Cidade" required></v-text-field>
            <v-text-field v-model="editedPatient.birthday" label="Data de Nascimento" required></v-text-field>
            <v-tabs  v-model="tab">
              <v-tab href="#medical">Registo Médico</v-tab>
            </v-tabs>
  
            <v-tabs-items v-model="tab">
              <v-tab-item value="medical">
                <v-text-field v-model="editedPatient.medicalRegistry.lastDoctorVisit" label="Última Visita Ao Médico"></v-text-field>
                <v-switch v-model="editedPatient.medicalRegistry.hospitalization" label="Hospitalização"></v-switch>
                <v-text-field 
                  v-if="editingPatient.medicalRegistry.hospitalization" 
                  v-model="editedPatient.medicalRegistry.hospitalizationReason" 
                  label="Motivo da Hospitalização">
                </v-text-field>
                <v-text-field v-model="editedPatient.medicalRegistry.medication" label="Medicação"></v-text-field>
                <v-text-field v-model="editedPatient.medicalRegistry.diseases" label="Patologias"></v-text-field>
                <v-text-field v-model="editedPatient.medicalRegistry.allergies" label="Alergias"></v-text-field>
              </v-tab-item>
            </v-tabs-items>
          </v-form>
        </v-card-text>


        <v-card-text v-else>
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
                <v-text-field v-model="newPatient.medicalRegistry.allergies" label="Alergias"></v-text-field>
              </v-tab-item>
            </v-tabs-items>
          </v-form>
        </v-card-text>
  
        <v-card-actions class="buttons">
          <v-spacer></v-spacer>
          <button  @click="close">Cancelar</button>
          <button v-if="isEdit" color="golden" @click="editPatient">Guardar</button>
          <button v-else color="golden" @click="submitPatient">Guardar</button>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  import { createPatient, editPatient } from "@/services/PatientService.ts";
  
  export default {
    props: {
      isOpen: Boolean,
      isEdit: Boolean,
      editingPatient: Object
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
            allergies: ""
            
          }
        },
        editedPatient: {}
      };
    },
    watch: {
      editingPatient: {
     handler(newVal) {
       if (newVal) {
         console.log("Recebendo paciente para edição:", newVal); // Debug
         this.editedPatient = JSON.parse(JSON.stringify(newVal)); // Garante uma cópia profunda
       }
     },
     deep: true,
     immediate: true
   },
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
          alert("Erro ao adicionar paciente.");
        }
      },
      
      async editPatient() {
  if (!this.editedPatient.name || !this.editedPatient.phoneNumber) {
    alert("Nome e Telefone são obrigatórios!");
    return;
  }

  try {
    await editPatient(this.editedPatient.id, this.editedPatient);
    alert("Dados alterados com sucesso!");
    this.$emit("patientEdited");
    this.close();
  } catch (error) {
    alert("Erro ao editar paciente.");
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
  