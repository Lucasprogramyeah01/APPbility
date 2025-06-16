<script setup>
import NavBar from './shared/NavBar.vue';
import { useRoute } from 'vue-router';
import { computed } from 'vue';

// DATA() ---------------------------------------------------------------

const route = useRoute();

const rutasOcultas = [
  //Seguridad
  '/login', 
  '/registrarUsuario', 
  //ROL: Admin
  '/continentes', 
  /^\/continente\/\d+$/,
  '/registrarContinente',
  /^\/editarContinente\/\d+$/,
];

// COMPUTED ---------------------------------------------------------------

const mostrarNavBar = computed(() => {
  return !rutasOcultas.some(ruta => 
    typeof ruta === 'string' 
      ? route.path === ruta
      : ruta.test(route.path)
  )
});

</script>


<template>
  <NavBar v-if="mostrarNavBar"></NavBar>
  <router-view></router-view>
</template>


<style scoped>
</style>