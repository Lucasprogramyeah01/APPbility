import { useAuthService } from '../security/authService';

const excludedPaths = [
  '/user/auth/login',
  '/user/auth/register',
  '/user/auth/refresh/token'
];

export function setupTokenInterceptor(axiosInstance) {
  const authService = useAuthService();
  
  axiosInstance.interceptors.request.use(
    (config) => {
      const urlPath = new URL(config.url, window.location.origin).pathname;
      
      if (excludedPaths.some(path => urlPath.startsWith(path))) {
        return config;
      }
      
      const token = authService.getToken();
      console.log('TOKEN:', token);
      
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
  
  return axiosInstance;
}