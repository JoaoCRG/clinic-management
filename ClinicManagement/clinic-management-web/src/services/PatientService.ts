import axios from "axios";

const API_URL = "http://localhost:8080/patients"

export async function getAllPatients() {
  try {
    const response = await axios.get(API_URL, {
      auth: {
        username: "user",
        password: "12345"
      }
    });
    return response.data;
  } catch (error) {
    console.error("Erro ao obter pacientes:", error);
    return [];
  }
}

export async function createPatient(patientData) {
    try {
      const response = await axios.post(API_URL + "/add",{
        auth: {
          username: "user",
          password: "12345"
        }
      }, patientData);
      return response.data; 
    } catch (error) {
      console.error("Erro ao criar paciente:", error);
      throw error;
    }
  }   
