import axios from 'axios';
import { useAuthService } from '../security/authService';

let isRefreshing = false;
let refreshSubscribers = [];

function subscribeTokenRefresh(cb) {
  refreshSubscribers.push(cb);
}

function onRefreshed(token) {
  refreshSubscribers.forEach(cb => cb(token));
  refreshSubscribers = [];
}

export function setupRefreshTokenInterceptor(axiosInstance) {
  axiosInstance.interceptors.response.use(
    response => response,
    async error => {
      const originalRequest = error.config;

      console.log('ORIGINAL REQUEST', originalRequest);
      
      if (error.response?.status === 401 && !originalRequest._retry) {
        if (originalRequest.url.includes('/user/auth/refresh/token')) {
          await useAuthService.logout();
          return Promise.reject(error);
        }

        originalRequest._retry = true;

        if (!isRefreshing) {
          console.log(isRefreshing)

          isRefreshing = true;
          const refreshToken = useAuthService().getRefreshToken();

          console.log("NUEVO RFTOK", refreshToken);
          
          if (!refreshToken) {
            await useAuthService.logout();
            return Promise.reject(new Error('No hay token de refresco disponible'));
          }

          try {
            const response = await axiosInstance.post('/user/auth/refresh/token', { 
              refreshToken 
            });

            localStorage.setItem('token', response.data.token);
            localStorage.setItem('refreshToken', response.data.refreshToken);
            isRefreshing = false;
            onRefreshed(response.data.token);
            
            // Reintentar petición original.
            originalRequest.headers.Authorization = `Bearer ${response.data.token}`;
            return axiosInstance(originalRequest);
          } catch (refreshError) {
            isRefreshing = false;
            await useAuthService.logout();
            return Promise.reject(refreshError);
          }
        }

        return new Promise((resolve) => {
          subscribeTokenRefresh((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            resolve(axiosInstance(originalRequest));
          });
        });
      }

      return Promise.reject(error);
    }
  );
}