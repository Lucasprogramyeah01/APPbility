import api from '../security/api';

export const UserService = {

  async findAll(page = 0, size = 10, sort = 'id,asc') {
    try {
      const response = await api.get('/user/', {
        params: { page, size, sort },
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
      if (error.response?.status === 404) {
        throw new Error(error.response?.data?.message);
      }
    }
  },

  async logout(router) {
    const token = localStorage.getItem('token');
    if (!token) {
      localStorage.clear();
      router.push('/login');
      return;
    }
    try {
      const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/user/auth/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Error cerrando sesión en backend');
      }
      console.log('Logout en backend exitoso.');
    } catch (error) {
      console.error('Error cerrando sesión en backend.', error);
    } finally {
      localStorage.clear();
      router.push('/login');
    }
  },

};