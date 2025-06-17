<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, watch, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import LoadingComponent from './loadingComponent.vue';
import { TalentoService } from '../services/talentoService';
import { UserService } from '../services/userService';
import { useToast } from "vue-toastification";
import Swal from 'sweetalert2';
import { IntercambioService } from '../services/intercambioService';

// DATA() ---------------------------------------------------------------


// CREATED() ---------------------------------------------------------------


// METHODS ---------------------------------------------------------------


// COMPUTED ---------------------------------------------------------------


// WATCH ---------------------------------------------------------------


</script>


<template>
  <LoadingComponent v-if="isLoading"></LoadingComponent>
    <p>AAAAAAAAAAAAAAAAAAAAAAAAAAAA</p>
    <div>
        <b-calendar block locale="en-US"></b-calendar>
        <b-calendar></b-calendar>
    </div>
</template>


<style scoped>
@font-face {
  font-family: 'madimiOne';
  src: url('../assets/fonts/MadimiOne-Regular.ttf');
}
.madimiOne{
  font-family: 'madimiOne';
}

@font-face {
  font-family: 'afacad';
  src: url('../assets/fonts/Afacad-VariableFont_wght.ttf');
}
.afacad{
  font-family: 'afacad';
}

@font-face {
  font-family: 'carterOne';
  src: url('../assets/fonts/CarterOne-Regular.ttf');
}
.carterOne{
  font-family: 'carterOne';
}

.fondoOscuro {
  background-color: #050027;
}

.fondoVerde {
  background-color: #0cad00;
}

.fondoVerde:hover{
  background-color: #087a00 !important;
  color: #ffffff !important;
}

.amarillo{
  color: #FFF200 !important;
}

.rosa{
  color: #FF00CC;
}
</style>