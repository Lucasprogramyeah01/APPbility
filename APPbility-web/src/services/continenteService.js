import axios from 'axios';

const apiForJSON = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const ContinenteService = {

    async findAll(page = 0, size = 10, sort = 'id,asc') {
        try {
            const response = await apiForJSON.get('/continente/', {
                params: {
                    page,
                    size,
                    sort,
                },
            });
            return response.data;
        } catch (error) {
            const errorMessage = error.response?.status === 404
            ? error.response?.data?.message
            : "No se encontraron continentes";
            
            throw new Error(errorMessage);
        }
    },

    async findById(id) {
        try {
            const response = await apiForJSON.get(`/continente/${id}`);
            return response.data;
        } catch (error) {
            if(error.response?.status === 404){
                throw new Error(error.response?.data?.message);
            }
        }
    },

    async createContinente(datosContinente) {
    try {
        const response = await apiForJSON.post('/continente/', datosContinente);
        return response.data;
    } catch (error) {
        if (error.response?.status === 400 && error.response.data?.['invalid-params']) {
            const validationErrors = error.response.data['invalid-params'].map((error) => error.message);
            throw new Error(validationErrors);
        } /*else if (error.response?.status === 400){
            throw new Error(error.response?.data?.message);
        }*/ else {
            throw new Error(error.response?.data?.message);
        }
    }
}

};