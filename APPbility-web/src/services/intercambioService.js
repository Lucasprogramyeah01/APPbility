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

    async findIntercambiosFromUsuario(page = 0, size = 10) {
        try {
            const response = await api.get('/intercambio/mis-intercambios', {
                params: {
                    page,
                    size,
                },
            });
            return response.data;
        } catch (error) {
            const errorMessage = error.response?.status === 404
            ? error.response?.data?.message
            : "No se encontraron intercambios.";
            
            throw new Error(errorMessage);
        }
    },



};