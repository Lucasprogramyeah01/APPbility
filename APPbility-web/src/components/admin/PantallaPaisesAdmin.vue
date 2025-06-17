<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, watch, onMounted } from 'vue';
import { ContinenteService } from '../../services/continenteService';
import { useRoute } from 'vue-router';
import LoadingComponent from '../loadingComponent.vue';

// DATA() ---------------------------------------------------------------

const continente = ref(null);

const route = useRoute();
const id = route.params.id;

const isLoading = ref(true);
const error = ref(null);

// CREATED() ---------------------------------------------------------------

onMounted(() => buscarContinentePorID(id));

// METHODS ---------------------------------------------------------------

const buscarContinentePorID = async () => {
  try {
    continente.value = await ContinenteService.findById(id);
  } catch (err) {
    error.value = err.message;
    continente.value = null;
  } finally {
    isLoading.value = false;
  }
};

// WATCH ---------------------------------------------------------------

watch(
  () => id,
  (nuevoId) => {
    if (nuevoId) buscarContinentePorID(nuevoId);
  },
  { immediate: true }
);

</script>


<template>
    <LoadingComponent v-if="isLoading"></LoadingComponent>

    <div v-else class="d-flex flex-column">
        <div class="container-fluid fondoDegradado">
            <p class="m-0" style="font-size: 15px; color: transparent">APPbility</p>
        </div>
        <div class="container-fluid d-flex flex-column fondoOscuro py-3 ps-5">
            <div class="w-auto d-flex">
                <img src="../../assets/img/APPbilityLogo.png" width="40px" height="40px" />
                <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                    APP<span class="amarillo">bility</span> <span style="color: aqua; font-size: 20px;">admin</span>
                </span>
            </div>
            <div class="w-auto d-flex justify-content-between mt-2 text-white">
                <div class="w-50">
                    <i class="bi bi-globe-americas carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 carterOne w-auto" style="font-size: 42px;">Continentes y Países</span>
                </div>
                <RouterLink :to="`/inicioAdmin`" class="text-decoration-none">
                  <div class="d-flex align-items-center me-3 afacad text-white">
                      <h3 href="#" class="nav-item-Textsize px-4 m-0 texto"><i class="bi bi-house-fill"></i> Volver al inicio</h3>
                  </div>
                </RouterLink>
            </div>
        </div>
        <!-- Botones -->
        <div class="d-flex flex-column justify-content-center mt-4 mb-5 mx-5 pt-2">
            <div class="afacad ms-5">
                <b-button size="lg" class="ms-3 border-0 a-button bg-primary" style="font-size: 22px;">
                    <i class="bi bi-plus-lg"></i> Añadir País
                </b-button>
            </div>
        </div>
        <div class="text-black madimiOne mx-5 px-5 mt-2">
            <h1 class="fw-normal madimiOne" style="font-size: 28px;">Países de {{ continente.nombre }}</h1>
        </div>
        <div class="fondoGris rounded-5" style="margin-left: 85px; margin-right: 85px;">
            <p class="m-0" style="font-size: 1px; color: transparent">APPbility</p>
        </div>
        <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-5 pb-5 mb-5"
            v-if="continente.listaPaises?.length"
        >
            <!-- Lista de tarjetas-->
            <div class="col-md-2 col-12 p-2 my-2 mx-1 afacad" style="width: 20%"
                v-for="pais in continente.listaPaises" 
                :key="pais.id"
            >
                <b-card id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-4 shadow">
                    <b-card-text>
                        <div class="d-flex justify-content-between">
                            <h1 class="amarillo madimiOne">{{ pais.id }}</h1>
                            <div>
                                <b-button href="#" class="h-auto ms-2 fondoNaranja border-0">
                                    <i class="bi bi-pencil-fill" style="font-size: 25px;"></i>
                                </b-button>
                                <b-button href="#" class="h-auto ms-2 fondoRojo border-0">
                                    <i class="bi bi-trash3-fill" style="font-size: 25px;"></i>
                                </b-button>
                            </div>
                        </div>
                        <h2 class="mt-2">{{ pais.nombre }}</h2>
                    </b-card-text>
                    <div class="d-flex align-items-center">
                        <img :src="pais.bandera" class="rounded-3 recortada" />
                        <h1 class="mt-2 ms-4" style="font-size: 60px;">{{ pais.codigoISO }}</h1>
                    </div>
                </b-card>
            </div>
        </div>
        <div v-else>
            <div class="d-flex flex-column justify-content-center mb-5 mt-4 pt-2 text-center">
                <div>
                  <img src="../assets/img/withoutContent.jpg" width="200px"/>
                </div>
                <div class="afacad mt-3">
                    <h1 class="fw-normal text-secondary" style="font-size: 25px;">
                        No hay países añadidos a este continente.
                    </h1>
                </div>
            </div>
        </div>
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

.fondoDegradado {
  background: linear-gradient(to right, #00e5ff, #001686);
  backdrop-filter: blur(4px);
}

.amarillo{
  color: #FFF200;
}

.texto:hover{
  color: #FFF200 !important;
}

.fondoOscuro {
  background-color: #050027;
}

.fondoRojo{
  background-color: #cd0000;
}

.fondoNaranja{
  background-color: #ff8400;
}

.fondoGris {
  background-color: #a7a7a7;
}

.fondoRojo:hover{
  background-color: #bc0000 !important;
  color: #ffffff !important;
}

.fondoNaranja:hover{
  background-color: #e97800 !important;
  color: #ffffff !important;
}

.a-button:hover{
  background-color: #001dad !important;
  color: #ffffff !important;
}

#tarjeta{
  transition-duration: 0.2s;
}

#tarjeta:hover{
  transform: scale(1.07);
}

.recortada {
  object-fit: cover;  /* Recorta la imagen manteniendo la proporción. */
  width: 150px;       /* Ancho deseado. */
  height: 110px;      /* Alto deseado. */
}
</style>