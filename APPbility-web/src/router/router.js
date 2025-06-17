import { createRouter, createWebHistory } from 'vue-router'
import { roleGuard } from '../guards/roleGuard';

// También se puede utilizar la @ en lugar de los 2 puntos.
// La @ hace referencia a la carpeta "src", por lo que la ruta se inicia desde allí.
// Si la @ no funciona de primeras, es porque hay que configurarla en el vite.config.js

import PantallaLogin from '../components/PantallaLogin.vue';
import FormRegistroUsuario from '../components/FormRegistroUsuario.vue';
import PantallaContinentesAdmin from '../components/admin/PantallaContinentesAdmin.vue';
import PantallaPaisesAdmin from '../components/admin/PantallaPaisesAdmin.vue';
import FormRegistroContinente from '../components/FormRegistroContinente.vue';
import PantallaInicio from '../components/PantallaInicio.vue';
import PantallaInicioAdmin from '../components/PantallaInicioAdmin.vue';
import PantallaAccesoNoAutorizado from '../components/PantallaAccesoNoAutorizado.vue';
import PantallaPerfilUsuario from '../components/PantallaPerfilUsuario.vue';
import FormProponerIntercambio from '../components/FormProponerIntercambio.vue';
import PantallaIntercambios from '../components/PantallaIntercambios.vue';
import FormElegirTalentoAceptado from '../components/FormElegirTalentoAceptado.vue';
import PantallaDetallesIntercambio from '../components/PantallaDetallesIntercambio.vue';

const routes = [
  { path: '/login', component: PantallaLogin },
  { path: '/registrarUsuario', component: FormRegistroUsuario },

  { path: '/inicio', component: PantallaInicio, meta:{roles: ['USER']} },
  { path: '/perfil/:id', component: PantallaPerfilUsuario, meta:{roles: ['USER']} },
  { path: '/proponerIntercambio/:id', component: FormProponerIntercambio, meta:{roles: ['USER']} },
  { path: '/intercambios', component: PantallaIntercambios, meta:{roles: ['USER']} },
  { path: '/elegirTalentoDeIntercambio/:intercambioid/:id', component: FormElegirTalentoAceptado, meta:{roles: ['USER']} },
  { path: '/intercambio/:id', component: PantallaDetallesIntercambio, meta:{roles: ['USER']} },

  { path: '/inicioAdmin', component: PantallaInicioAdmin, meta:{roles: ['ADMIN']} },
  { path: '/continentes', component: PantallaContinentesAdmin, meta:{roles: ['ADMIN']} },
  { path: '/continente/:id', component: PantallaPaisesAdmin, meta:{roles: ['ADMIN']} },
  { path: '/registrarContinente', component: FormRegistroContinente, meta:{roles: ['ADMIN']} },
  { path: '/editarContinente/:id', component: FormRegistroContinente, meta:{roles: ['ADMIN']} },

  { path: '/accesoNoAutorizado', component: PantallaAccesoNoAutorizado },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router