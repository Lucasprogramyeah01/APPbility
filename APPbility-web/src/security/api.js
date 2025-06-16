import axios from 'axios';
import { setupTokenInterceptor } from '../interceptors/tokenInterceptor';
import { setupRefreshTokenInterceptor } from '../interceptors/RefreshTokenInterceptor';

//Crear una instancia base de Axios.
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
});

//Aplicar interceptores.
setupTokenInterceptor(api);
setupRefreshTokenInterceptor(api);

export default api;