import api from '../security/api';

export const TalentoService = {

  async findTalentosFromUsuario(page = 0, size = 10, id) {
      try {
          const response = await api.get(`/talento/${id}`, {
              params: {
                  page,
                  size,
              },
          });
          return response.data;
      } catch (error) {
          const errorMessage = error.response?.status === 404
          ? error.response?.data?.message
          : "No se encontraron talentos.";
          
          throw new Error(errorMessage);
      }
  },

  async findById(id) {
      try {
          const response = await api.get(`/user/${id}`);
          return response.data;
      } catch (error) {
          if(error.response?.status === 404){
              throw new Error(error.response?.data?.message);
          }
      }
  },

};
