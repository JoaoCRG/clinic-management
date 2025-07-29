<template>
  <v-dialog v-model="dialog" max-width="600px">
    <v-card>
      
<img alt="Face Clinic Logo" class="logo" src="@/assets/facecliniclogo.jpg" />
      <v-card-title class="text-h5 text-center pb-0">
        Novo Paciente
      </v-card-title>

      <v-card-text>
        <v-form ref="form" v-model="valid">
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="patient.name"
                  label="Nome*"
                  :rules="[v => !!v || 'Nome é obrigatório']"
                  outlined
                  dense
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="6">
                <v-text-field
                  v-model="patient.phoneNumber"
                  label="Telemóvel*"
                  :rules="[v => !!v || 'Telemóvel é obrigatório']"
                  outlined
                  dense
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="6">
                <v-menu
                  v-model="dateMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="patient.birthday"
                      label="Data de Nascimento"
                      readonly
                      outlined
                      dense
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="patient.birthday"
                    @input="dateMenu = false"
                    locale="pt-PT"
                  ></v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  v-model="patient.city"
                  label="Cidade"
                  outlined
                  dense
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-expansion-panels flat>
                  <v-expansion-panel>
                    <v-expansion-panel-header>
                      <v-icon left color="primary">mdi-medical-bag</v-icon>
                      Registo Médico
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      <v-row>
                        <v-col cols="12">
                          <v-switch
                            v-model="patient.medicalRegistry.hospitalization"
                            label="Hospitalização"
                            color="primary"
                          ></v-switch>
                        </v-col>

                        <v-col cols="12" v-if="patient.medicalRegistry.hospitalization">
                          <v-text-field
                            v-model="patient.medicalRegistry.hospitalizationReason"
                            label="Motivo da Hospitalização"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>

                        <v-col cols="12">
                          <v-text-field
                            v-model="patient.medicalRegistry.medication"
                            label="Medicação"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>

                        <v-col cols="12">
                          <v-text-field
                            v-model="patient.medicalRegistry.diseases"
                            label="Patologias"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>

                        <v-col cols="12">
                          <v-text-field
                            v-model="patient.medicalRegistry.allergies"
                            label="Alergias"
                            outlined
                            dense
                          ></v-text-field>
                        </v-col>

                        <v-col cols="12">
                          <v-menu
                            v-model="lastDoctorVisitMenu"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            min-width="auto"
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-text-field
                                v-model="patient.medicalRegistry.lastDoctorVisit"
                                label="Última Consulta"
                                readonly
                                outlined
                                dense
                                v-bind="attrs"
                                v-on="on"
                              ></v-text-field>
                            </template>
                            <v-date-picker
                              v-model="patient.medicalRegistry.lastDoctorVisit"
                              @input="lastDoctorVisitMenu = false"
                              locale="pt-PT"
                            ></v-date-picker>
                          </v-menu>
                        </v-col>
                      </v-row>
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                </v-expansion-panels>
              </v-col>

              <v-col cols="12">
                <v-file-input
                  v-model="consent"
                  label="Consentimento"
                  accept=".pdf"
                  prepend-icon="mdi-file-pdf-box"
                  outlined
                  dense
                  show-size

                ></v-file-input>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>

      <v-card-actions class="pa-6 pt-0">
        <v-spacer></v-spacer>
        <v-btn
          text
          @click="close"
        >
          Cancelar
        </v-btn>
        <v-btn
          color="primary"
          :loading="loading"
          :disabled="!valid"
          @click="submit"
        >
          Guardar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { createPatient } from "@/services/PatientService.ts";

export default {
  name: 'AddPatientModal',

  props: {
    isOpen: Boolean
  },

  data: () => ({
    valid: false,
    loading: false,
    dateMenu: false, 
    lastDoctorVisitMenu: false,
    patient: {
      name: "",
      phoneNumber: "",
      city: "",
      birthday: "",
      medicalRegistry: {
        lastDoctorVisit: "",
        hospitalization: false,
        hospitalizationReason: "",
        medication: "",
        diseases: "",
        allergies: ""
      }
    },
    consent: null,
  }),

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
    async submit() {
  if (!this.$refs.form.validate()) return;

  this.loading = true;
  try {
    await createPatient(this.patient, this.consent);
  
    this.$emit("patient-created");
    this.close();
    this.$emit("show-message", {
      text: "Paciente adicionado com sucesso!",
      color: "success"
    });
  } catch (error) {
    console.error("Error creating patient:", error);
    this.$emit("show-message", {
      text: "Erro ao adicionar paciente",
      color: "error"
    });
  } finally {
    this.loading = false;
  }
}
,

    close() {
      this.$refs.form.reset();
      this.dialog = false;
    }
  }
};
</script>

<style scoped>
.v-dialog > .v-card {
  border-radius: 8px;
}

.v-card__title {
  word-break: break-word;
  padding-top: 0;
}

.logo {
align-content: center;
width: 200px;
}
</style>