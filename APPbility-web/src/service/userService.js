import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const UserService = {



    // Métodos relacionados al apartado de SEGURIDAD ---------------------------------------

    register(){
        return api.post(`/auth/register`);
    },

    login(){
        return api.post(`/auth/login`);
    },

};
