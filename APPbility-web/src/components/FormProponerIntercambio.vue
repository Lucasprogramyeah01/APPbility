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

const datosNuevoIntercambio = ref({ 
    usuarioSolicitadoID: '',
    talentoSolicitadoID: null,
    talentoSugeridoID: null, 
});

const router = useRouter();

const route = useRoute();
const usuarioSolicitadoID = route.params.id;

const usuarioSolicitado = ref(null);

const usuarioDemandanteID = localStorage.getItem('id');

const listaTalentosUsuarioSolicitado = ref({ 
    content: [], 
    empty: true 
});
//PagUS
const currentPageUS = ref(1);
const pageSizeUS = 21;
const totalElementsUS = ref(0);

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

const selectedTalentoSolicitadoId = ref(null);
const selectedTalentoSugeridoId = ref(null);

const currentStep = ref(1);
const maxStep = 3;

// CREATED() ---------------------------------------------------------------

onMounted(() => buscarUsuarioPorID(usuarioSolicitadoID));

onMounted(() => listarTalentosUsuarioSolicitado());
onMounted(() => listarTalentosUsuarioDemandante());

// METHODS ---------------------------------------------------------------

const buscarUsuarioPorID = async () => {
  try {
    usuarioSolicitado.value = await UserService.findById(usuarioSolicitadoID);
    console.log(usuarioSolicitadoID);
    console.log(usuarioSolicitado.value);
  } catch (err) {
    error.value = err.message;
    usuarioSolicitado.value = null;
  } finally {
    isLoading.value = false;
  }
};

async function listarTalentosUsuarioSolicitado(page = 1) {
  isLoading.value = true;
  try {
    const response = await TalentoService.findTalentosFromUsuario(page - 1, pageSizeUS, usuarioSolicitadoID);
    listaTalentosUsuarioSolicitado.value = response;
    totalElementsUS.value = response.totalElements;
    console.log(listaTalentosUsuarioSolicitado.value);
    currentPageUS.value = page;
  } catch (err) {
    error.value = err.message;
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

function onSelectTalentoSolicitado(id) {
  if (selectedTalentoSolicitadoId.value === id) {
    selectedTalentoSolicitadoId.value = null;
    datosNuevoIntercambio.value.talentoSolicitadoID = null;
  } else {
    selectedTalentoSolicitadoId.value = id;
    datosNuevoIntercambio.value.talentoSolicitadoID = id;
  }
}

function onSelectTalentoSugerido(id) {
  if (selectedTalentoSugeridoId.value === id) {
    selectedTalentoSugeridoId.value = null;
    datosNuevoIntercambio.value.talentoSugeridoID = null;
  } else {
    selectedTalentoSugeridoId.value = id;
    datosNuevoIntercambio.value.talentoSugeridoID = id;
  }
}

const proponerIntercambio = async () => {
  const usuarioSolicitadoUsername = usuarioSolicitado.value?.username;

  const result = await Swal.fire({
    title: '¿Estás seguro de quieres proponer el intercambio?',
    text: `Se enviará la oferta del intercambio a la pestaña de "Propuestas recibidas" de ${usuarioSolicitadoUsername}.`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#0cad00',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sí ¡Proponer intercambio!',
    cancelButtonText: 'Cancelar'
  });

  if (result.isConfirmed) {
    isLoading.value = true;
    try {
      datosNuevoIntercambio.value.usuarioSolicitadoID = usuarioSolicitadoID;
      await IntercambioService.proponerIntercambio(datosNuevoIntercambio.value);
      toast.success(`Se le ha enviado la propuesta de intercambio a ${usuarioSolicitadoUsername} correctamente.`);
      router.replace(`/perfil/${usuarioSolicitadoID}`);
    } catch (err) {
      toast.error(err.message);
    } finally {
      isLoading.value = false;
    }
  }
};

function toggleAcordeon(id) {
  abierto.value = abierto.value === id ? null : id;
};

// COMPUTED ---------------------------------------------------------------

//PagUS
const totalPagesUS = computed(() => Math.ceil(totalElementsUS.value / pageSizeUS));

//PagUD
const totalPagesUD = computed(() => Math.ceil(totalElementsUD.value / pageSizeUD));

const progress = computed(() => Math.round(100 / maxStep) * (currentStep.value - 1));

// WATCH ---------------------------------------------------------------

//PagUS
watch(currentPageUS, (newPage) => {
  listarTalentosUsuarioSolicitado(newPage);
});

//PagUD
watch(currentPageUD, (newPage) => {
  listarTalentosUsuarioDemandante(newPage);
});
</script>


<template>
  <LoadingComponent v-if="isLoading"></LoadingComponent>

  <b-form @submit.prevent="proponerIntercambio">
      <!-- STEP 1: Elegir talento que deseas aprender del Usuario Solicitado.-->
      <div v-if="currentStep==1" title="STEP1">
          <div class="container-fluid d-flex flex-column fondoOscuro text-white py-3 ps-5 my-4">
              <div class="d-flex flex-column px-5 py-3">
                <div class="w-100 mb-5">
                    <i class="bi bi-arrow-left-right carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 afacad w-auto" style="font-size: 32px;">
                      <span class="carterOne" style="font-size: 42px;">PASO 1:</span> 
                        &nbsp;Elige el talento que deseas aprender de 
                        <span class="amarillo">{{ usuarioSolicitado?.username }}</span>.
                    </span>
                </div>
                <div class="d-flex justify-content-center">
                  <b-progress :value="progress" class="w-50 amarillo" animated></b-progress>
                </div>
              </div>
              <div class="d-flex justify-content-center mb-4 afacad">
                <RouterLink :to="`/perfil/${usuarioSolicitadoID}`" class="text-decoration-none">
                  <b-button class="float-left" variant="secondary" @click="onClickBack">
                      <h4 class="m-0">
                        <i class="bi bi-caret-left-fill"></i> &nbsp;Volver atrás
                      </h4>
                  </b-button>
                </RouterLink>
                <div style="width: 40%;"></div>
                <b-button class="float-right" variant="primary" @click="onClickNext" :disabled="!selectedTalentoSolicitadoId">
                    <h4 class="m-0">
                      Siguiente &nbsp;<i class="bi bi-caret-right-fill"></i>
                    </h4>
                </b-button>
              </div>
          </div>
          <div class="d-flex flex-wrap justify-content-around mx-5 mt-5 px-3 pb-5">
              <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                  v-for="talento in listaTalentosUsuarioSolicitado.content" 
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
                                :checked="selectedTalentoSolicitadoId === talento.id"
                                @change="onSelectTalentoSolicitado(talento.id)"
                                name="talentoSolicitado"
                                v-model="datosNuevoIntercambio.talentoSolicitadoID"
                              >
                                {{ selectedTalentoSolicitadoId === talento.id ? 'Talento seleccionado' : '' }}
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
            <div v-if="listaTalentosUsuarioSolicitado.content.length == 0 && totalPagesUS > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPageUS"
                  :total-rows="totalElementsUS"
                  :per-page="pageSizeUS"
                  @input="listarTalentosUsuarioSolicitado"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
      </div>
      <!-- STEP 2: Elegir talento que deseas sugerir de tu propio perfil. -->
      <div v-if="currentStep==2" title="STEP2">
          <div class="container-fluid d-flex flex-column fondoOscuro text-white py-3 ps-5 my-4">
              <div class="d-flex flex-column px-5 py-3">
                <div class="w-100 mb-5">
                    <i class="bi bi-arrow-left-right carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 afacad w-auto" style="font-size: 32px;">
                      <span class="carterOne" style="font-size: 42px;">PASO 2:</span> 
                        &nbsp;Elige el talento que deseas sugerirle a
                        <span class="amarillo">{{ usuarioSolicitado?.username }}</span>
                        de tu perfil.
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
                <div style="width: 40%;"></div>
                <b-button class="float-right" variant="primary" @click="onClickNext" :disabled="!selectedTalentoSugeridoId">
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
                                :checked="selectedTalentoSugeridoId === talento.id"
                                @change="onSelectTalentoSugerido(talento.id)"
                                name="talentoSugerido"
                                v-model="datosNuevoIntercambio.talentoSugeridoID"
                              >
                                {{ selectedTalentoSugeridoId === talento.id ? 'Talento seleccionado' : '' }}
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
                  v-model="currentPageUS"
                  :total-rows="totalElementsUS"
                  :per-page="pageSizeUS"
                  @input="listarTalentosUsuarioDemandante"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
      </div>
      <!-- STEP 3: Enviar propuesta de Intercambio. -->
      <div v-if="currentStep==3" title="STEP3">
          <div class="container-fluid d-flex flex-column fondoOscuro text-white py-3 ps-5 my-4">
              <div class="d-flex flex-column px-5 py-3">
                <div class="w-100 mb-5">
                    <i class="bi bi-arrow-left-right carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 afacad w-auto" style="font-size: 32px;">
                      <span class="carterOne" style="font-size: 42px;">PASO 3:</span> 
                        &nbsp;Envíale la propuesta de intercambio de talentos a
                        <span class="amarillo">{{ usuarioSolicitado?.username }}</span>.
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
                    <i class="bi bi-send-fill"></i>
                    &nbsp; Enviar propuesta de intercambio
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