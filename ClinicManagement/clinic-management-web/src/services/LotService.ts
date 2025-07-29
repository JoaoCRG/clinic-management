import axios from "axios";

const API_URL = "http://192.168.1.194:8080/lots"
const credencials = {
    username: "user", 
    password: "12345"
}


export async function createLot(lotData) {
    try {
        const response = await axios.post(API_URL + '/add', lotData, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}

export async function editLot(lotId, lotData) {
    try {
        const response = await axios.put(API_URL + '/edit/'+ lotId, lotData, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}

export async function deleteLot(lotId) {
    try {
        const response = await axios.delete(API_URL + "/" + lotId, {
            auth: credencials
    });
    return response.data;
    } catch (error) {
        console.log("Algo correu mal", error);
        return [];
    }
}