import { useAuthService } from '../security/authService';

export const roleGuard = (to, from, next) => {
  const authService = useAuthService();
  const expectedRoles = to.meta.roles || [];
  const userRole = authService.getRole();

  // 1- Verificar si el usuario está autenticado.
  if (!authService.isLoggedIn()) {
    authService.logout();
    return next('/login');
  }

  // 2- Verificar roles.
  if (!userRole || !expectedRoles.includes(userRole)) {
    return next('/accesoNoAutorizado');
  }

  // 3- Permitir acceso.
  next();
};