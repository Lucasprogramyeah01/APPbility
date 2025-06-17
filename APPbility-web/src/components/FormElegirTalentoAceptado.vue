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

const datosTalentoAceptado = ref({ 
    talentoAceptadoID: null 
});

const router = useRouter();

const route = useRoute();
const intercambioID = route.params.intercambioid;
const usuarioDemandanteID = route.params.id;

const usuarioDemandante = ref(null);

const listaTalentosUsuarioDemandante = ref({ 
    content: [], 
    empty: true 
});
//PagUD
const currentPageUD = ref(1);
const pageSizeUD = 21;
const totalElementsUD = ref(0);

const isLoading = ref(true);
const error = ref(null);

const toast = useToast();

const abierto = ref(null);

const selectedTalentoAceptadoId = ref(null);

const currentStep = ref(1);
const maxStep = 2;

// CREATED() ---------------------------------------------------------------

onMounted(() => buscarUsuarioPorID(usuarioDemandanteID));

onMounted(() => listarTalentosUsuarioDemandante());

// METHODS ---------------------------------------------------------------

const buscarUsuarioPorID = async () => {
  try {
    usuarioDemandante.value = await UserService.findById(usuarioDemandanteID);
    console.log(usuarioDemandante);
    console.log(usuarioDemandante.value);
  } catch (err) {
    error.value = err.message;
    usuarioDemandante.value = null;
  } finally {
    isLoading.value = false;
  }
};

async function listarTalentosUsuarioDemandante(page = 1) {
  isLoading.value = true;
  try {
    const response = await TalentoService.findTalentosFromUsuario(page - 1, pageSizeUD, usuarioDemandanteID);
    listaTalentosUsuarioDemandante.value = response;
    totalElementsUD.value = response.totalElements;
    console.log(listaTalentosUsuarioDemandante.value);
    currentPageUD.value = page;
  } catch (err) {
    error.value = err.message;
  } finally {
    isLoading.value = false;
  }
};

async function onClickNext(){
  currentStep.value++;
}
async function onClickBack(){
  currentStep.value--;
}

function onSelectTalentoAceptado(id) {
  if (selectedTalentoAceptadoId.value === id) {
    selectedTalentoAceptadoId.value = null;
    datosTalentoAceptado.value.talentoAceptadoID = null;
  } else {
    selectedTalentoAceptadoId.value = id;
    datosTalentoAceptado.value.talentoAceptadoID = id;
  }
}

async function aceptarIntercambio(intercambioID, usuarioDemandanteUsername) {
    const result = await Swal.fire({
        title: '¿Estás seguro de quieres aceptar la propuesta de este intercambio?',
        text: `El intercambio pasará a ser ACTIVO y tanto tú como ${usuarioDemandanteUsername} podréis encontrarlo y configurarlo en la pestaña "Activos".`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#0cad00',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Aceptar intercambio',
        cancelButtonText: 'Cancelar'
    });
    if (result.isConfirmed) {
        try {
            await IntercambioService.aceptarIntercambio(intercambioID, datosTalentoAceptado.value);
            toast.success('Se ha aceptado la propuesta de intercambio correctamente.');
            router.replace(`/intercambios`);
        } catch (err) {
            toast.error(err.message);
        }
    }
}

function toggleAcordeon(id) {
  abierto.value = abierto.value === id ? null : id;
};

// COMPUTED ---------------------------------------------------------------

//PagUD
const totalPagesUD = computed(() => Math.ceil(totalElementsUD.value / pageSizeUD));

const progress = computed(() => Math.round(100 / maxStep) * (currentStep.value - 1));

// WATCH ---------------------------------------------------------------

//PagUD
watch(currentPageUD, (newPage) => {
  listarTalentosUsuarioDemandante(newPage);
});
</script>


<template>
  <LoadingComponent v-if="isLoading"></LoadingComponent>

  <b-form @submit.prevent="aceptarIntercambio(intercambioID, usuarioDemandante?.username)">
      <!-- STEP 1: Elegir talento que deseas aprender del Usuario Demandante.-->
      <div v-if="currentStep==1" title="STEP1">
          <div class="container-fluid d-flex flex-column fondoOscuro text-white py-3 ps-5 my-4">
              <div class="d-flex flex-column px-5 py-3">
                <div class="w-100 mb-5">
                    <i class="bi bi-pencil-fill carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 afacad w-auto" style="font-size: 32px;">
                      <span class="carterOne" style="font-size: 42px;">PASO 1:</span> 
                        &nbsp;Elige el talento que deseas aprender de 
                        <span class="amarillo">{{ usuarioDemandante?.username }}</span>.
                    </span>
                </div>
                <div class="d-flex justify-content-center">
                  <b-progress :value="progress" class="w-50 amarillo" animated></b-progress>
                </div>
              </div>
              <div class="d-flex justify-content-center mb-4 afacad">
                <RouterLink :to="`/intercambios`" class="text-decoration-none">
                  <b-button class="float-left" variant="secondary" @click="onClickBack">
                      <h4 class="m-0">
                        <i class="bi bi-caret-left-fill"></i> &nbsp;Volver atrás
                      </h4>
                  </b-button>
                </RouterLink>
                <div style="width: 40%;"></div>
                <b-button class="float-right" variant="primary" @click="onClickNext" :disabled="!selectedTalentoAceptadoId">
                    <h4 class="m-0">
                      Siguiente &nbsp;<i class="bi bi-caret-right-fill"></i>
                    </h4>
                </b-button>
              </div>
          </div>
          <div class="d-flex flex-wrap justify-content-around mx-5 mt-5 px-3 pb-5">
              <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                  v-for="talento in listaTalentosUsuarioDemandante.content" 
                  :key="talento.id"
              >
                  <div class="accordion" role="tablist">
                      <b-card no-body class="mb-1">
                          <b-card-header 
                              header-tag="header" role="tab"
                              class="p-3" 
                              style="cursor: pointer;" 
                              @click="toggleAcordeon(talento.id)"
                          >
                              <b-form-checkbox
                                switch size="lg"
                                :checked="selectedTalentoAceptadoId === talento.id"
                                @change="onSelectTalentoAceptado(talento.id)"
                                name="talentoAceptado"
                                v-model="datosTalentoAceptado.talentoAceptadoID"
                              >
                                {{ selectedTalentoAceptadoId === talento.id ? 'Talento seleccionado' : '' }}
                              </b-form-checkbox>
                              <div class="d-flex justify-content-between">
                                  <h3 class="m-0 afacad">{{ talento.titulo }}</h3>
                                  <div 
                                      class="px-3 py-0 rounded-5 align-self-start" 
                                      :style="{ backgroundColor: talento.nivel.color }"
                                  >
                                      <p class="text-light text-center mb-0 afacad">
                                          {{ talento.nivel.nombre }}
                                      </p>
                                </div>
                              </div>
                          </b-card-header>
                          <b-collapse :id="`accordion-${talento.id}`" :visible="abierto === talento.id"
                              accordion="my-accordion" role="tabpanel"
                          >
                              <b-card-body>
                                  <b-card-text>{{ talento.descripcion }}</b-card-text>
                                  <img v-if="talento.imagen" :src="talento.imagen" class="w-100" />
                              </b-card-body>
                          </b-collapse>
                      </b-card>
                  </div>
              </div>
            </div>
            <div v-if="listaTalentosUsuarioDemandante.content.length == 0 && totalPagesUD > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPageUD"
                  :total-rows="totalElementsUD"
                  :per-page="pageSizeUD"
                  @input="listarTalentosUsuarioDemandante"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
      </div>
      <!-- STEP 2: Aceptar propuesta de Intercambio. -->
      <div v-if="currentStep==2" title="STEP2">
          <div class="container-fluid d-flex flex-column fondoOscuro text-white py-3 ps-5 my-4">
              <div class="d-flex flex-column px-5 py-3">
                <div class="w-100 mb-5">
                    <i class="bi bi-pencil-fill carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 afacad w-auto" style="font-size: 32px;">
                      <span class="carterOne" style="font-size: 42px;">PASO 2:</span> 
                        &nbsp;Acepta la propuesta de intercambio de talentos de
                        <span class="amarillo">{{ usuarioDemandante?.username }}</span>.
                    </span>
                </div>
                <div class="d-flex justify-content-center">
                  <b-progress :value="progress" class="w-50 amarillo" animated></b-progress>
                </div>
              </div>
              <div class="d-flex justify-content-center mb-4 afacad">
                  <b-button class="float-left" variant="secondary" @click="onClickBack">
                      <h4 class="m-0">
                        <i class="bi bi-caret-left-fill"></i> &nbsp;Volver atrás
                      </h4>
                  </b-button>
              </div>
          </div>
          <div class="d-flex justify-content-center mt-5 pt-3 afacad">
            <b-button type="submit" class="border-0 w-50 p-4 fondoVerde">
                <h1 class="m-0">
                    <i class="bi bi-check-circle-fill"></i>
                    &nbsp; Aceptar Intercambio
                </h1>
            </b-button>
          </div>
      </div>
  </b-form>
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