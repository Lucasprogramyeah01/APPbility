import api from '../security/api';

export const IntercambioService = {

    async proponerIntercambio(datosNuevoIntercambio) {
        try {
            const response = await api.post('/intercambio/proponer', datosNuevoIntercambio);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data?.message);
        }
    },

};