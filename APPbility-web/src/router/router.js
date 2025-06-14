import { createRouter, createWebHistory } from 'vue-router'

// También se puede utilizar la @ en lugar de los 2 puntos.
// La @ hace referencia a la carpeta "src", por lo que la ruta se inicia desde allí.
// Si la @ no funciona de primeras, es porque hay que configurarla en el vite.config.js

import PantallaLogin from '../components/PantallaLogin.vue';
import FormRegistroUsuario from '../components/FormRegistroUsuario.vue';
import PantallaContinentesAdmin from '../components/PantallaContinentesAdmin.vue';
import PantallaPaisesAdmin from '../components/PantallaPaisesAdmin.vue';
import FormRegistroContinente from '../components/FormRegistroContinente.vue';
import PantallaInicio from '../components/PantallaInicio.vue';
import PantallaInicioAdmin from '../components/PantallaInicioAdmin.vue';

const routes = [
  { path: '/login', component: PantallaLogin },
  { path: '/registrarUsuario', component: FormRegistroUsuario },
  { path: '/continentes', component: PantallaContinentesAdmin },
  { path: '/continente/:id', component: PantallaPaisesAdmin },
  { path: '/registrarContinente', component: FormRegistroContinente },
  { path: '/inicio', component: PantallaInicio },
  { path: '/inicioAdmin', component: PantallaInicio },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router