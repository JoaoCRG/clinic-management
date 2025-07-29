import axios from "axios";

const API_URL = "http://192.168.1.194:8080/appointments"
const credencials = {
    username: "user", 
    password: "12345"
}

export async function createAppointment(appointmentData) {
    try {
        const response = await axios.post(API_URL + '/add', appointmentData, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}

export async function editAppointment(appointmentId, appointmentData) {
    try {
        const response = await axios.put(API_URL + '/edit/'+ appointmentId, appointmentData, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}

export async function getAllAppointments() {
    try {
        const response = await axios.get(API_URL, {
            auth: credencials
        });
        return response.data;
    } catch (error) {
        console.error("Error fetching appointments:", error);
        return [];
    }
}

export async function deleteAppointment(appointmentId) {
    try {
        const response = await axios.delete(API_URL + "/" + appointmentId, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}
