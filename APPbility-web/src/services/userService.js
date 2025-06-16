import axios from 'axios';

const apiForMFD = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'multipart/form-data',
  },
});

const apiForJSON = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const UserService = {

  // MÉTODOS RELACIONADOS CON LA SEGURIDAD ----------------------------------------------------

  register(userData, profileImage = null) {
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
  },

};
