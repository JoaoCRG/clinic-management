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
        <v-btn 
          v-if="tab !== 'appointments'"
          icon 
          @click="editPatient"
        >
          <v-icon>{{ (editingPersonal || editingMedical) ? 'mdi-content-save' : 'mdi-pencil' }}</v-icon>
        </v-btn>
        <v-btn icon color="error" @click="deletePatient">
          <v-icon>mdi-delete</v-icon>
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
                    <v-text-field
                      v-if="editingPersonal"
                      v-model="editedPatient.name"
                      dense
                      hide-details
                      single-line
                    ></v-text-field>
                    <template v-else>
                      {{ patientData?.name }}
                    </template>
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
                    <v-text-field
                      v-if="editingPersonal"
                      v-model="editedPatient.phoneNumber"
                      dense
                      hide-details
                      single-line
                    ></v-text-field>
                    <template v-else>
                      {{ patientData?.phoneNumber }}
                    </template>
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
                    <v-text-field
                      v-if="editingPersonal"
                      v-model="editedPatient.city"
                      dense
                      hide-details
                      single-line
                    ></v-text-field>
                    <template v-else>
                    {{ patientData?.city }}
                    </template>
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
                    {{ patientData?.birthday }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-file-pdf-box</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Consentimento (PDF)</v-list-item-title>
                  <v-list-item-subtitle>
                    <template v-if="editingPersonal">
                      <v-file-input
                        v-model="editedConsentFile"
                        accept=".pdf"
                        label="Selecione um PDF"
                        prepend-icon="mdi-file-pdf-box"
                        dense
                        outlined
                        show-size
                      ></v-file-input>
                      <div v-if="patientData?.consent && !editedConsentFile" class="mt-2">
                        <v-btn
                          color="primary"
                          text
                          small
                          :href="patientData.consent"
                          target="_blank"
                          rel="noopener"
                        >
                          Visualizar PDF atual
                          <v-icon right small>mdi-open-in-new</v-icon>
                        </v-btn>
                      </div>
                    </template>
                    <template v-else>
                      <template v-if="patientData?.consent">
                        <v-btn
                          color="primary"
                          text
                          small
                          @click="openConsentPdf"
                        >
                          Visualizar PDF
                          <v-icon right small>mdi-open-in-new</v-icon>
                        </v-btn>
                      </template>
                      <template v-else>
                        Nenhum arquivo anexado
                      </template>
                    </template>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-row v-if="editingPersonal" class="px-4 mt-2">
                <v-spacer></v-spacer>
                <v-btn text color="error" @click="cancelEdit">Cancelar</v-btn>
                <v-btn text color="primary" @click="savePersonal">Salvar</v-btn>
              </v-row>
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
                    <v-text-field
                      v-if="editingMedical"
                      v-model="editedMedical.lastDoctorVisit"
                      label="Última Consulta"
                      dense
                      hide-details
                      single-line
                      type="date"
                    ></v-text-field>
                    <template v-else>
                      {{ patientData?.medicalRegistry?.lastDoctorVisit || 'Não registrado' }}
                    </template>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-medication</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Hospitalização</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-switch
                      v-if="editingMedical"
                      v-model="editedMedical.hospitalization"
                      label="Hospitalização"
                      color="primary"
                      dense
                      hide-details
                    ></v-switch>
                    <template v-else>
                      {{ patientData?.medicalRegistry?.hospitalization ? 'Sim' : 'Não' }}
                    </template>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <template v-if="editingMedical ? editedMedical.hospitalization : patientData?.medicalRegistry?.hospitalization">
                <v-list-item two-line>
                  <v-list-item-content class="pl-16">
                    <v-list-item-title>Motivo</v-list-item-title>
                    <v-list-item-subtitle>
                      <v-text-field
                        v-if="editingMedical"
                        v-model="editedMedical.hospitalizationReason"
                        label="Motivo da Hospitalização"
                        dense
                        hide-details
                        single-line
                      ></v-text-field>
                      <template v-else>
                        {{ patientData?.medicalRegistry?.hospitalizationReason }}
                      </template>
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
                    <v-text-field
                      v-if="editingMedical"
                      v-model="editedMedical.medication"
                      label="Medicação"
                      dense
                      hide-details
                      single-line
                    ></v-text-field>
                    <template v-else>
                      {{ patientData?.medicalRegistry?.medication || 'Nenhuma' }}
                    </template>
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
                    <v-text-field
                      v-if="editingMedical"
                      v-model="editedMedical.allergies"
                      label="Alergias"
                      dense
                      hide-details
                      single-line
                    ></v-text-field>
                    <template v-else>
                      {{ patientData?.medicalRegistry?.allergies || 'Nenhuma' }}
                    </template>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list-item two-line>
                <v-list-item-avatar>
                  <v-icon large color="primary">mdi-virus</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>Patologias</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-text-field
                      v-if="editingMedical"
                      v-model="editedMedical.diseases"
                      label="Patologias"
                      dense
                      hide-details
                      single-line
                    ></v-text-field>
                    <template v-else>
                      {{ patientData?.medicalRegistry?.diseases || 'Nenhuma' }}
                    </template>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-row v-if="editingMedical" class="px-4 mt-2">
                <v-spacer></v-spacer>
                <v-btn text color="error" @click="cancelEdit">Cancelar</v-btn>
                <v-btn text color="primary" @click="saveMedical">Salvar</v-btn>
              </v-row>
           
          </v-tab-item>

          <!-- Appointments Tab -->
          <v-tab-item value="appointments">
            <v-list>
              <v-list-item
                v-for="appointment in patientData?.appointmentHistory"
                :key="appointment.id"
                class="mb-2"
              >
                <v-list-item-content>
                  <v-list-item-title>
                    {{ formatDate(appointment.date) }}
                    <v-btn
                      icon
                      small
                      @click="startAppointmentEdit(appointment)"
                      v-if="editingAppointmentId !== appointment.id"
                    >
                      <v-icon small>mdi-pencil</v-icon>
                    </v-btn>
                  </v-list-item-title>
                  <v-list-item-subtitle>
                    <div v-if="editingAppointmentId === appointment.id">
                      <v-text-field
                        v-model="editedAppointment.date"
                        label="Data e Hora"
                        type="datetime-local"
                        outlined
                        dense
                      />
                      <v-text-field
                        v-model="editedAppointment.procedure"
                        label="Procedimento"
                        outlined
                        dense
                      />
                      <v-text-field
                        v-model="editedAppointment.price"
                        label="Preço"
                        type="number"
                        outlined
                        dense
                      />

                      <v-divider class="my-4"></v-divider>
                      <div class="d-flex align-center justify-space-between mb-2">
                        <span class="subtitle-1 font-weight-bold">Lotes</span>
                        <v-btn small color="primary" outlined @click="openLotDialog(appointment.id)">
                          Criar Novo Lote
                        </v-btn>
                      </div>
                      <v-chip-group
                        v-model="selectedLotIds"
                        multiple
                        column
                      >
                        <v-chip
                          v-for="lot in allLots"
                          :key="lot.id"
                          :value="lot.id"
                          outlined
                          color="primary"
                          class="ma-1"
                        >
                          {{ lot.brand }} - {{ lot.lotNumber }}
                        </v-chip>
                      </v-chip-group>

                      <v-row class="px-4 mt-2">
                        <v-spacer></v-spacer>
                        <v-btn text color="error" @click="cancelAppointmentEdit">Cancelar</v-btn>
                        <v-btn text color="primary" @click="saveAppointmentEdit(appointment.id)">Salvar</v-btn>
                      </v-row>
                    </div>
                    <div v-else>
                      <div><strong>Hora:</strong> {{ appointment.date ? appointment.date.split('T')[1]?.substring(0,5) : '' }}</div>
                      <div><strong>Procedimento:</strong> {{ appointment.procedure }}</div>
                      <div><strong>Preço:</strong> {{ appointment.price | currency }}</div>
                      <div v-if="appointment.lots && appointment.lots.length">
                        <v-simple-table dense>
                          <thead>
                            <tr>
                              <th>Marca</th>
                              <th>Nº Lote</th>
                              <th>Referência</th>
                              <th>Modelo</th>
                              <th>Validade Início</th>
                              <th>Validade Fim</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr v-for="lot in appointment.lots" :key="lot.id">
                              <td>{{ lot.brand }}</td>
                              <td>{{ lot.lotNumber }}</td>
                              <td>{{ lot.ref }}</td>
                              <td>{{ lot.model }}</td>
                              <td>{{ lot.validityStart }}</td>
                              <td>{{ lot.validityEnd }}</td>
                              <td>
                                <v-btn icon small color="error" @click="handleDeleteLot(lot, appointment)">
                                  <v-icon small>mdi-delete</v-icon>
                                </v-btn>
                              </td>
                            </tr>
                          </tbody>
                        </v-simple-table>
                      </div>
                      <span v-else class="grey--text">Nenhum lote registrado</span>
                    </div>
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>

            <!-- Lot creation dialog -->
            <v-dialog v-model="showLotForm" max-width="500px">
              <v-card>
                <v-card-title>Criar Lote</v-card-title>
                <v-card-text>
                  <v-text-field v-model="newLot.brand" label="Marca" />
                  <v-text-field v-model="newLot.lotNumber" label="Número do Lote" />
                  <v-text-field v-model="newLot.ref" label="Referência" />
                  <v-text-field v-model="newLot.model" label="Modelo" />
                  <v-text-field v-model="newLot.validityStart" label="Validade Início" type="date" />
                  <v-text-field v-model="newLot.validityEnd" label="Validade Fim" type="date" />
                </v-card-text>
                <v-card-actions>
                  <v-spacer />
                  <v-btn text color="error" @click="showLotForm = false">Cancelar</v-btn>
                  <v-btn text color="primary" @click="createLotForAppointment">Salvar Lote</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
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
          @click="showAddAppointmentModal = true"
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

      <!-- Edit Appointment Dialog -->
      <v-dialog v-model="editingAppointmentDialog" max-width="600px">
        <v-card>
          <v-card-title>Editar Consulta</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="editedAppointment.date"
              label="Data e Hora"
              type="datetime-local"
              outlined
              dense
            />
            <v-text-field
              v-model="editedAppointment.procedure"
              label="Procedimento"
              outlined
              dense
            />
            <v-text-field
              v-model="editedAppointment.price"
              label="Preço"
              type="number"
              outlined
              dense
            />

            <v-divider class="my-4"></v-divider>
            <div class="d-flex align-center justify-space-between mb-2">
              <span class="subtitle-1 font-weight-bold">Lotes</span>
              <v-btn small color="primary" outlined @click="openLotDialog(appointment.id)">
                Criar Novo Lote
              </v-btn>
            </div>
            <v-chip-group
              v-model="selectedLotIds"
              multiple
              column
            >
              <v-chip
                v-for="lot in allLots"
                :key="lot.id"
                :value="lot.id"
                outlined
                color="primary"
                class="ma-1"
              >
                {{ lot.brand }} - {{ lot.lotNumber }}
              </v-chip>
            </v-chip-group>
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn text color="error" @click="cancelAppointmentEdit">Cancelar</v-btn>
            <v-btn text color="primary" @click="saveAppointmentEdit">Salvar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!-- Lot creation dialog -->
      <v-dialog v-model="showLotForm" max-width="500px">
        <v-card>
          <v-card-title>Criar Lote</v-card-title>
          <v-card-text>
            <v-text-field v-model="newLot.brand" label="Marca" />
            <v-text-field v-model="newLot.lotNumber" label="Número do Lote" />
            <v-text-field v-model="newLot.ref" label="Referência" />
            <v-text-field v-model="newLot.model" label="Modelo" />
            <v-text-field v-model="newLot.validityStart" label="Validade Início" type="date" />
            <v-text-field v-model="newLot.validityEnd" label="Validade Fim" type="date" />
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn text color="error" @click="showLotForm = false">Cancelar</v-btn>
            <v-btn text color="primary" @click="createLotForAppointment">Salvar Lote</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <AddAppointmentModal
        :isOpen="showAddAppointmentModal"
        :patient="patientData"
        @update:isOpen="showAddAppointmentModal = $event"
        @appointment-added="handleAppointmentAdded"
      />
    </v-card>
  </v-dialog>

 
</template>

<script>
import { editPatient, deletePatient, getPatientById } from '@/services/PatientService.ts';
import { createLot } from '@/services/LotService.ts';
import { editAppointment, deleteAppointment } from '@/services/AppointmentService.ts';
import AddAppointmentModal from './AddAppointmentModal.vue';
import { deleteLot } from '@/services/LotService.ts';


export default {
  name: 'PatientsDetailsModal',
  components: {
     AddAppointmentModal
    },

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
      showAddAppointmentModal: false,
      editedPatient: {
        name: '',
        phoneNumber: '',
        city: ''
      },
      editingPersonal: false,
      editingMedical: false,
      editedMedical: {
        hospitalization: false,
        hospitalizationReason: '',
        medication: '',
        allergies: '',
        diseases: ''
      },
      editedConsentFile: null,
      editingLotsId: null,
      editedAppointment: {
        date: '',
        procedure: '',
        price: null,
        lots: []
      },
      showLotForm: false,
      newLot: {
        brand: '',
        lotNumber: '',
        ref: '',
        model: '',
        validityStart: '',
        validityEnd: '',
                },
      allLots: [],
      selectedLotIds: [],
      editingAppointmentDialog: false,
      editingAppointmentId: null,
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

     async reloadPatientData() {
    if (!this.patientData?.id) return;
    try {
      const updated = await getPatientById(this.patientData.id);
      this.patientData = updated;
    } catch (error) {
      this.$emit('show-message', { text: 'Erro ao atualizar dados do paciente', color: 'error' });
    }
  },

    editPatient() {
      if (this.tab === 'personal') {
        this.editingPersonal = true;
        this.editedPatient = { ...this.patientData };
      } else if (this.tab === 'medical') {
        this.editingMedical = true;
        this.editedMedical = { ...this.patientData.medicalRegistry };
      }
    },

    cancelEdit() {
      this.editingPersonal = false;
      this.editingMedical = false;
      this.editedPatient = {};
      this.editedMedical = {};
    },

    async savePatient() {
      try {
        await editPatient(this.editedPatient.id, this.editedPatient, this.editedConsentFile);
        this.showEditModal = false;
        this.$emit('patient-updated');
        // Refresh patient data
        this.patientData = { ...this.editedPatient };
      } catch (error) {
        console.error('Error updating patient:', error);
        // You might want to show an error message to the user
      }
    },

    async savePersonal() {
      try {
        await editPatient(this.patientData.id, this.editedPatient, this.editedConsentFile);
        this.patientData = { ...this.patientData, ...this.editedPatient };
        this.editingPersonal = false;
        this.$emit('patient-updated');
      } catch (error) {
        console.error('Error updating patient:', error);
      }
    },

    async saveMedical() {
      try {
        const updatedPatient = {
          ...this.patientData,
          medicalRegistry: this.editedMedical
        };
        await editPatient(this.patientData.id, updatedPatient);
        this.patientData = updatedPatient;
        this.editingMedical = false;
        this.$emit('patient-updated');
      } catch (error) {
        console.error('Error updating medical registry:', error);
      }
    },

    async openConsentPdf() {
      try {
        if (!this.patientData.consent) {
          alert('Nenhum PDF disponível.');
          return;
        }
        const byteCharacters = atob(this.patientData.consent);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        window.open(url, '_blank');
        setTimeout(() => window.URL.revokeObjectURL(url), 10000);
      } catch (e) {
        alert('Não foi possível abrir o PDF.');
      }
    },

    addNewAppointment() {
      this.$emit('add-appointment', this.patientData);
    },

    submitAppointment() {
      this.$emit('appointment-added', this.appointment);
      this.close();
    },

    handleAppointmentAdded(appointment) {
      // You can push to patientData.appointmentHistory or reload the patient data
      if (this.patientData && this.patientData.appointmentHistory) {
        this.patientData.appointmentHistory.push(appointment);
      }
      this.showAddAppointmentModal = false;
    },

    addLot() {
      this.$set(this.editedAppointment.lots, this.editedAppointment.lots.length, {
        brand: '',
        lotNumber: '',
        ref: '',
        model: '',
        validityStart: '',
        validityEnd: ''
      });
    },
    removeLot(idx) {
      this.editedAppointment.lots.splice(idx, 1);
    },
    startAppointmentEdit(appointment) {
      this.editingAppointmentId = appointment.id;
      this.editedAppointment = {
        date: appointment.date,
        procedure: appointment.procedure,
        price: appointment.price,
        lots: appointment.lots ? [...appointment.lots] : []
      };
      this.selectedLotIds = (appointment.lots || []).map(lot => lot.id);
      this.loadAllLots();
    },
    cancelAppointmentEdit() {
      this.editingAppointmentId = null;
      this.editedAppointment = { date: '', procedure: '', price: null, lots: [] };
      this.selectedLotIds = [];
    },
    async saveAppointmentEdit(appointmentId) {
      const selectedLots = this.allLots.filter(lot => this.selectedLotIds.includes(lot.id));
      const updated = {
        ...this.editedAppointment,
        lots: selectedLots
      };
      await editAppointment(appointmentId, updated, updated.consent);
      this.editingAppointmentId = null;
      this.$emit('show-message', { text: 'Consulta atualizada!', color: 'success' });
      await this.reloadPatientData();
    },
    async createLotAndAdd() {
      this.newLot.appointmentId = this.editingAppointmentId;
      const createdLot = await createLot(this.newLot);
      this.allLots.push(createdLot);
      this.selectedLotIds.push(createdLot.id);
      this.showLotForm = false;
      this.newLot = { brand: '', lotNumber: '', ref: '', model: '', validityStart: '', validityEnd: '' };
      this.$emit('show-message', { text: 'Lote criado!', color: 'success' });
    },
    async loadAllLots() {
      // Load all lots from backend if needed, or merge appointment lots and created lots
      const lotsSet = new Map();
      (this.editedAppointment.lots || []).forEach(lot => lotsSet.set(lot.id, lot));
      (this.allLots || []).forEach(lot => lotsSet.set(lot.id, lot));
      this.allLots = Array.from(lotsSet.values());
    },
    async deletePatient() {
      if (!this.patientData?.id) return;
      try {
        await deletePatient(this.patientData.id);
        this.$emit('patient-deleted', this.patientData.id); // This emits the event
        this.close();
        this.$emit('show-message', { text: 'Paciente excluído!', color: 'success' });
      } catch (error) {
        this.$emit('show-message', { text: 'Erro ao excluir paciente', color: 'error' });
      }
    },
    async deleteAppointment(appointment) {
      try {
        await deleteAppointment(appointment.id);
        this.patientData.appointmentHistory = this.patientData.appointmentHistory.filter(a => a.id !== appointment.id);
        this.$emit('show-message', { text: 'Consulta excluída!', color: 'success' });
      } catch (error) {
        this.$emit('show-message', { text: 'Erro ao excluir consulta', color: 'error' });
      }
    },

    // Open the lot creation dialog for a specific appointment
    openLotDialog(appointmentId) {
      this.editingAppointmentId = appointmentId;
      this.showLotForm = true;
      this.newLot = { brand: '', lotNumber: '', ref: '', model: '', validityStart: '', validityEnd: '' };
    },

    // Create a lot and add it to the appointment
    async createLotForAppointment() {
      const lotPayload = {
        ...this.newLot,
        appointment: { id: this.editingAppointmentId }
      };
      try {
        await createLot(lotPayload);
        this.showLotForm = false;
        this.newLot = { brand: '', lotNumber: '', ref: '', model: '', validityStart: '', validityEnd: '' };
        this.editingAppointmentId = null;
        this.$emit('show-message', { text: 'Lote criado!', color: 'success' });
        await this.reloadPatientData(); // This will fetch the latest data from backend
      } catch (error) {
        this.$emit('show-message', { text: 'Erro ao criar lote', color: 'error' });
      }
    },
    async handleDeleteLot(lot, appointment) {
      try {
        await deleteLot(lot.id);
        // Remove from appointment.lots
        appointment.lots = appointment.lots.filter(l => l.id !== lot.id);
        this.$emit('show-message', { text: 'Lote eliminado!', color: 'success' });
      } catch (error) {
        this.$emit('show-message', { text: 'Erro ao eliminar lote', color: 'error' });
      }
    },
  },

  watch: {
    patient: {
      handler(newPatient) {
        this.patientData = newPatient;
      },
      immediate: true
    },
    tab() {
      // Reset editing state when changing tabs
      this.editingPersonal = false;
      this.editingMedical = false;
    }
  },
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

