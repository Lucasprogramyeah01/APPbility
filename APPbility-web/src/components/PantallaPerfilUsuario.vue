<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, watch, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import LoadingComponent from './loadingComponent.vue';
import { UserService } from '../services/userService';
import { useToast } from "vue-toastification";

// DATA() ---------------------------------------------------------------

const usuario = ref(null);

const route = useRoute();
const id = route.params.id;

const isLoading = ref(true);
const error = ref(null);

// CREATED() ---------------------------------------------------------------

onMounted(() => buscarUsuarioPorID(id));

// METHODS ---------------------------------------------------------------

const buscarUsuarioPorID = async () => {
  try {
    usuario.value = await UserService.findById(id);
    console.log(id);
    console.log(usuario.value);
  } catch (err) {
    error.value = err.message;
    usuario.value = null;
  } finally {
    isLoading.value = false;
  }
};

// COMPUTED ---------------------------------------------------------------


// WATCH ---------------------------------------------------------------


</script>


<template>
    <LoadingComponent v-if="isLoading"></LoadingComponent>

    <b-tabs content-class="mt-3" card justified>
        <b-tab title="First" active>
            <p>I'm the first tab</p>
        </b-tab>
        <b-tab title="Second">
            <p>I'm the second tab</p>
        </b-tab>
    </b-tabs>

    <p>{{ usuario?.username }}</p>
    <RouterLink :to="`/proponerIntercambio/${usuario?.id}`" class="text-decoration-none">
        <b-button href="#" variant="primary" class="border-0 w-100">
            <h5 class="m-0">Ofrecer Intercambio</h5>
        </b-button>
    </RouterLink>   
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
</style>