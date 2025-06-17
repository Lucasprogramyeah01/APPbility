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

    async cancelarIntercambioPropuesto(IntercambioID) {
        try {
            const response = await api.delete(`/intercambio/cancelar/${IntercambioID}`);
            return response.data;
        } catch (error) {
            if(error.response?.status === 404){
                throw new Error(error.response?.data?.message);
            }
        }
    },

    async aceptarIntercambio(IntercambioID, datosTalentoAceptado) {
        try {
            const response = await api.put(`/intercambio/aceptar/${IntercambioID}`, datosTalentoAceptado);
            return response.data;
        } catch (error) {
            if(error.response?.status === 404){
                throw new Error(error.response?.data?.message);
            }
        }
    },

    async rechazarIntercambio(IntercambioID) {
        try {
            const response = await api.put(`/intercambio/rechazar/${IntercambioID}`);
            return response.data;
        } catch (error) {
            if(error.response?.status === 404){
                throw new Error(error.response?.data?.message);
            }
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