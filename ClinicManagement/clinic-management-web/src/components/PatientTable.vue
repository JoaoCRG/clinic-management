<template>
  <v-data-table
    :headers="headers"
    :items="patients"
    :items-per-page="5"
    class="elevation-1"
  >
    <template v-slot:foot>
      <tr>
        <td>
          <v-text-field
            v-model="name"
            class="ma-2"
            density="compact"
            placeholder="Search name..."
            hide-details
          ></v-text-field>
        </td>
      </tr>
    </template>
  </v-data-table>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      name: "",
      headers: [
        { text: "Nome", value: "name" },
        { text: "Idade", value: "age" },
        { text: "Detalhes", value: "details" }
      ],
      patients: []  
    };
  },
  methods: {
    getPatients() {
      axios
        .get("http://localhost:8080/patients", {
          auth: {
            username: "user",
            password: "12345"
          }
        })
        .then((response) => {
          // Atualiza a lista de pacientes com a resposta da API
          this.patients = response.data;  // Assuming response.data is an array of patients
        })
        .catch((error) => {
          console.error("Erro ao obter pacientes:", error);
        });
    }
  },
  mounted() {
    // Chama a função para buscar os pacientes assim que o componente for montado
    this.getPatients()
    console.log(this.patients)
  }


};
</script>
