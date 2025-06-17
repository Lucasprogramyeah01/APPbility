import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export function useAuthService() {
  const router = useRouter();
  
  const currentUser = ref(null);
  const error = ref(null);
  const loading = ref(false);

  // MÉTODOS -------------------------------------------------------------------

  const login = async (loginRequest) => {
    try {
        loading.value = true;
        const response = await apiClient.post('/user/auth/login', loginRequest);
        console.log('RESPONSE', response);

        //Almacenar los datos en el localStorage.
        localStorage.setItem('id', response.data.id);
        localStorage.setItem('color', response.data.color);
        localStorage.setItem('role', response.data.rol[0]);
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('refreshToken', response.data.refreshToken);
        
        currentUser.value = response.data;
        return response.data;
    } catch (err) {
        error.value = 'Error en el inicio de sesión';
        console.error('Login error:', err);
        throw err;
    } finally {
        loading.value = false;
    }
  };

  const logout = async () => {
    try {
      const token = this.getToken();

      if(!token){
        localStorage.clear();
        router.push('/login');
        return;
      }

      await apiClient.post('/auth/logout', {}, { responseType: 'text' });
      console.log('Logout en backend exitoso.');
    } catch (err) {
      console.error('Error cerrando sesión en backend.', err);
    } finally {
      //Limpiar siempre el localStorage y redirigir.
      localStorage.clear();
      currentUser.value = null;
      router.push('/login');
    }
  };

  const isLoggedIn = () => {
    return !!localStorage.getItem('token');
  };

  const getRole = () => {
    return localStorage.getItem('role');
  };

  const getToken = () => {
    return localStorage.getItem('token');
  };

  const getRefreshToken = () => {
    return localStorage.getItem('refreshToken');
  };

  const hasRole = (expectedRole) => {
    return getRole() === expectedRole;
  };

  const activateAccount = async (req) => {
    try {
        loading.value = true;
        const response = await apiClient.post('/activate/account', req);
        return response.data;
    } catch (err) {
        error.value = 'Error activando cuenta';
        console.error('Activate account error:', err);
        throw err;
    } finally {
        loading.value = false;
    }
  };

  return {
    currentUser,
    error,
    loading,
    login,
    logout,
    isLoggedIn,
    getRole,
    getToken,
    getRefreshToken,
    hasRole,
    activateAccount
  };
}