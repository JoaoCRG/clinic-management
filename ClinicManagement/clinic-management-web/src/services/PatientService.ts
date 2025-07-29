import axios from "axios";

const API_URL = "http://192.168.1.194:8080/patients"
const credencials = {
    username: "user", 
    password: "12345"
}

export async function getAllPatients() {
  try {
    const response = await axios.get(API_URL, {
       auth: credencials
    });
    return response.data;
  } catch (error) {
    console.error("Erro ao obter pacientes:", error);
    return [];
  }
}

export async function getPatientById(patientId) {
  try {
    const response = await axios.get(API_URL + "/" + patientId, {
      auth: credencials
    });
    return response.data;
  } catch (error) {
    console.error("Erro ao obter paciente:", error);
    return null;
  }
}

export async function createPatient(patient, consent) {
  const formData = new FormData();
formData.append("patient", new Blob([JSON.stringify(patient)], { type: "application/json" }));
formData.append("consent", consent); 

await axios.post(API_URL + "/add", formData, {
  headers: { "Content-Type": "multipart/form-data" },
  auth: credencials
});
}  

  export async function editPatient(patientId, patient, consent) {
    const formData = new FormData();
      formData.append("patientId", patientId)
      formData.append("patient", new Blob([JSON.stringify(patient)], { type: "application/json" }));
      formData.append("consent", consent); 

    try {
      const response = await axios.put(API_URL + "/edit/" + patientId , formData, {
        auth: credencials
      });
      return response.data; 
    } catch (error) {
      console.error("Erro ao editar paciente:", error);
      throw error;
    }
  }   

  export async function deletePatient(patientId) {
    try {
        const response = await axios.delete(API_URL + "/" + patientId, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}