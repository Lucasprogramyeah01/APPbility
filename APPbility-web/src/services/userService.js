import api from '../security/api';

export const UserService = {

  async findAll(page = 0, size = 10, sort = 'id,asc') {
      try {
          const response = await api.get('/user/', {
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
          : "No se encontraron usuarios.";
          
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

  // MÉTODOS RELACIONADOS CON LA SEGURIDAD ----------------------------------------------------

  /*register(userData, profileImage = null) {
    const formData = new FormData();
    
    //Este método convierte el objeto userData a JSON y lo agrega al FormData.
    formData.append('usuario', new Blob([JSON.stringify(userData)], {
      type: 'application/json'
    }));

    //Si hay imagen, la agrega al formData.
    if (profileImage) {
      formData.append('imagenPerfil', profileImage);
    }

    return apiForMFD.post('/auth/register', formData);
  },*/

};
