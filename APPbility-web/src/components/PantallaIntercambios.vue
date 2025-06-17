<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, watch, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import LoadingComponent from './loadingComponent.vue';
import { UserService } from '../services/userService';
import { IntercambioService } from '../services/intercambioService';
import Swal from 'sweetalert2';
import { useToast } from "vue-toastification";

// DATA() ---------------------------------------------------------------

//const usuario = ref(null);

/*const route = useRoute();
const id = route.params.id;*/

const router = useRouter();

const listaIntercambios = ref({ 
    content: [], 
    empty: true 
});
//Pag
const currentPage = ref(1);
const pageSize = 10;
const totalElements = ref(0);

const datosTalentoAceptado = ref({ 
    talentoAceptadoID: null 
});

const userID = localStorage.getItem('id');

const toast = useToast();

const isLoading = ref(true);
const error = ref(null);

// CREATED() ---------------------------------------------------------------

//onMounted(() => buscarUsuarioPorID(id));
onMounted(() => listarIntercambios());

// METHODS ---------------------------------------------------------------

/*const buscarUsuarioPorID = async () => {
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
};*/

async function listarIntercambios(page = 1) {
  isLoading.value = true;
  try {
    const response = await IntercambioService.findIntercambiosFromUsuario(page - 1, pageSize);
    listaIntercambios.value = response;
    totalElements.value = response.totalElements;
    console.log(listarIntercambios.value);
    currentPage.value = page;
  } catch (err) {
    error.value = err.message;
  } finally {
    isLoading.value = false;
  }
};

async function cancelarIntercambio(intercambioID, usuarioSolicitadoUsername) {
    const result = await Swal.fire({
        title: '¿Estás seguro de quieres cancelar la propuesta de este intercambio?',
        text: `Se retirará dicha oferta de intercambio de la pestaña de "Propuestas recibidas" de ${usuarioSolicitadoUsername}.`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#cd0000',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Cancelar intercambio',
        cancelButtonText: 'Volver atrás'
    });
    if (result.isConfirmed) {
        try {
            await IntercambioService.cancelarIntercambioPropuesto(intercambioID);
            toast.success('Se ha cancelado la propuesta de intercambio correctamente.');
            listarIntercambios(currentPage.value);
        } catch (err) {
            toast.error(err.message);
        }
    }
}

async function aceptarIntercambio(intercambioID, talentoAceptadoID, usuarioDemandanteUsername) {
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
            datosTalentoAceptado.value.talentoAceptadoID = talentoAceptadoID;
            await IntercambioService.aceptarIntercambio(intercambioID, datosTalentoAceptado.value);
            toast.success('Se ha aceptado la propuesta de intercambio correctamente.');
            listarIntercambios(currentPage.value);
        } catch (err) {
            toast.error(err.message);
        }
    }
}

async function rechazarIntercambio(intercambioID, usuarioDemandanteUsername) {
    const result = await Swal.fire({
        title: '¿Estás seguro de quieres rechazar este intercambio?',
        text: `Se le hará saber a ${usuarioDemandanteUsername} en su pestaña de "Rechazados".`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#cd0000',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Rechazar intercambio',
        cancelButtonText: 'Cancelar'
    });
    if (result.isConfirmed) {
        try {
            await IntercambioService.rechazarIntercambio(intercambioID);
            toast.success('Se ha rechazado el intercambio correctamente.');
            listarIntercambios(currentPage.value);
        } catch (err) {
            toast.error(err.message);
        }
    }
}

/*const esUsuarioDemandante = (intercambio) => {
  return String(intercambio.usuarioDemandante.id) === userID;
};

const esUsuarioSolicitado = (intercambio) => {
  return String(intercambio.usuarioSolicitado.id) === userID;
};*/

// COMPUTED ---------------------------------------------------------------

const isEmpty = computed(() => listaIntercambios.value.empty);

//Pag
const totalPages = computed(() => Math.ceil(totalElements.value / pageSize));

// FILTRO 1 (Activos): intercambios con estado 'ACTIVO'.
const intercambiosActivos = computed(() =>
  listaIntercambios.value.content.filter(i => i.estado === 'ACTIVO')
);

/* FILTRO 2: estado 'PROPUESTO' y usuarioDemandante.id igual al id del 
   usuario autenticado. */
const intercambiosPropuestosEnviados = computed(() =>
  listaIntercambios.value.content.filter(
    i => i.estado === 'PROPUESTO' && String(i.usuarioDemandante.id) === userID
  )
);

/* FILTRO 3: estado 'PROPUESTO' y usuarioSolicitado.id igual al id del 
   usuario autenticado. */
const intercambiosPropuestosRecibidos = computed(() =>
  listaIntercambios.value.content.filter(
    i => i.estado === 'PROPUESTO' && String(i.usuarioSolicitado.id) === userID
  )
);

// FILTRO 4: intercambios con estado 'RECHAZADO'.
const intercambiosRechazados = computed(() =>
  listaIntercambios.value.content.filter(i => i.estado === 'RECHAZADO')
);

// FILTRO 5: intercambios con estado 'FINALIZADO'.
const intercambiosFinalizados = computed(() =>
  listaIntercambios.value.content.filter(i => i.estado === 'FINALIZADO')
);

// WATCH ---------------------------------------------------------------

//Pag
watch(currentPage, (newPage) => {
  listarIntercambios(newPage);
});
</script>


<template>
    <LoadingComponent v-if="isLoading"></LoadingComponent>

    <b-tabs content-class="mt-3" card justified>
        <!-- PESTAÑA 1 -->
        <b-tab title="Propuestas enviadas">
            <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
                <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                    v-for="intercambio in intercambiosPropuestosEnviados" 
                    :key="intercambio.id"
                >
                    <b-card id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                        <b-card-text>
                            <h5 class="m-0 madimiOne">{{ intercambio.estado }} 
                                <span class="m-0 afacad"> el {{ intercambio.fechaSolicitud }}</span>
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                ¡Le has propuesto un intercambio a <span class="amarillo">
                                    {{ intercambio.usuarioSolicitado.username }}
                                </span>!
                            </h2>
                            <hr />
                            <h5>Buscas aprender:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Le sugieres aprender:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSugerido.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSugerido.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSugerido.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <b-button 
                                    class="border-0 w-100 fondoRojo"
                                    @click="cancelarIntercambio(intercambio.intercambioID, intercambio.usuarioSolicitado.username)"
                                >
                                    <h5 class="m-0">
                                        <i class="bi bi-x-circle-fill"></i>
                                        &nbsp; Cancelar propuesta
                                    </h5>
                                </b-button>
                            </div>
                        </b-card-text>
                    </b-card>
                </div>
            </div>
            <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalElements"
                  :per-page="pageSize"
                  @input="listarIntercambios"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
            <div v-else-if="isEmpty">
                <div class="d-flex flex-column justify-content-center mb-5 mt-4 pt-2 text-center">
                    <div>
                    <img src="../assets/img/withoutContent.jpg" width="200px"/>
                    </div>
                    <div class="afacad mt-3">
                        <h1 class="fw-normal text-secondary" style="font-size: 25px;">
                            {{ error }}
                        </h1>
                    </div>
                </div>
            </div>
        </b-tab>
        <!-- PESTAÑA 2 -->
        <b-tab title="Propuestas recibidas">
            <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
                <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                    v-for="intercambio in intercambiosPropuestosRecibidos" 
                    :key="intercambio.id"
                >
                    <b-card id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                        <b-card-text>
                            <h5 class="m-0 madimiOne">{{ intercambio.estado }} 
                                <span class="m-0 afacad"> el {{ intercambio.fechaSolicitud }}</span>
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                ¡<span class="amarillo">
                                    {{ intercambio.usuarioDemandante.username }}
                                </span> te propone un intercambio!
                            </h2>
                            <hr />
                            <h5>Busca aprender:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Te sugiere aprender:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSugerido.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSugerido.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSugerido.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <RouterLink :to="`/elegirTalentoDeIntercambio/${intercambio.intercambioID}/${intercambio.usuarioDemandante.id}`" class="text-decoration-none">
                                    <b-button class="border-0 w-100 fondoNaranja">
                                        <h5 class="m-0">
                                            <i class="bi bi-pencil-fill"></i>
                                            &nbsp; Elegir otro talento de {{ intercambio.usuarioDemandante.username }}
                                        </h5>
                                    </b-button>
                                </RouterLink>
                                <div class="d-flex justify-content-between mt-3">
                                    <b-form class="w-100" 
                                        @submit.prevent="aceptarIntercambio(intercambio.intercambioID, intercambio.talentoSugerido.id, intercambio.usuarioDemandante.username)">
                                        <b-button 
                                            class="border-0 w-100 fondoVerde"
                                            type="submit"
                                        >
                                            <h5 class="m-0">
                                                <i class="bi bi-check-circle-fill"></i>
                                                &nbsp; Aceptar Intercambio
                                            </h5>
                                        </b-button>
                                    </b-form>
                                    <b-button 
                                        class="border-0 w-100 ms-3 fondoRojo"
                                        @click="rechazarIntercambio(intercambio.intercambioID, intercambio.usuarioDemandante.username)"
                                    >
                                        <h5 class="m-0">
                                            <i class="bi bi-x-circle-fill"></i>
                                            &nbsp; Rechazar Intercambio
                                        </h5>
                                    </b-button>
                                </div>
                            </div>
                        </b-card-text>
                    </b-card>
                </div>
            </div>
            <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalElements"
                  :per-page="pageSize"
                  @input="listarIntercambios"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
            <div v-else-if="isEmpty">
                <div class="d-flex flex-column justify-content-center mb-5 mt-4 pt-2 text-center">
                    <div>
                    <img src="../assets/img/withoutContent.jpg" width="200px"/>
                    </div>
                    <div class="afacad mt-3">
                        <h1 class="fw-normal text-secondary" style="font-size: 25px;">
                            {{ error }}
                        </h1>
                    </div>
                </div>
            </div>
        </b-tab>
        <!-- PESTAÑA 3 -->
        <b-tab title="Activos" active>
            <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
                <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                    v-for="intercambio in intercambiosActivos" 
                    :key="intercambio.id"
                >

                    <b-card v-if="intercambio.usuarioDemandante.id == userID" id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                        <b-card-text>
                            <h5 class="m-0 madimiOne">{{ intercambio.estado }} 
                                <span class="m-0 afacad"> desde el {{ intercambio.fechaComienzo }}</span>
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                Intercambio con
                                <span class="amarillo">
                                    {{ intercambio.usuarioSolicitado.username }}
                                </span>
                            </h2>
                            <hr />
                            <h5>Enseñas:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoAceptado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoAceptado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoAceptado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Aprendes:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <RouterLink :to="`/intercambio/${intercambio.intercambioID}`" class="text-decoration-none">
                                    <b-button href="#" variant="primary" class="border-0 w-100">
                                        <h5 class="m-0">
                                            Ver detalles
                                        </h5>
                                    </b-button>
                                </RouterLink>
                            </div>
                        </b-card-text>
                    </b-card>
                    <b-card v-else id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                        <b-card-text>
                            <h5 class="m-0 madimiOne">{{ intercambio.estado }} 
                                <span class="m-0 afacad"> desde el {{ intercambio.fechaComienzo }}</span>
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                Intercambio con
                                <span class="amarillo">
                                    {{ intercambio.usuarioDemandante.username }}
                                </span>
                            </h2>
                            <hr />
                            <h5>Enseñas:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Aprendes:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoAceptado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoAceptado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoAceptado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <b-button href="#" variant="primary" class="border-0 w-100">
                                    <h5 class="m-0">
                                        Ver detalles
                                    </h5>
                                </b-button>
                            </div>
                        </b-card-text>
                    </b-card>
                </div>
            </div>
            <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalElements"
                  :per-page="pageSize"
                  @input="listarIntercambios"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
            <div v-else-if="isEmpty">
                <div class="d-flex flex-column justify-content-center mb-5 mt-4 pt-2 text-center">
                    <div>
                    <img src="../assets/img/withoutContent.jpg" width="200px"/>
                    </div>
                    <div class="afacad mt-3">
                        <h1 class="fw-normal text-secondary" style="font-size: 25px;">
                            {{ error }}
                        </h1>
                    </div>
                </div>
            </div>
        </b-tab>
        <!-- PESTAÑA 4 -->
        <b-tab title="Rechazados">
            <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
                <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                    v-for="intercambio in intercambiosRechazados" 
                    :key="intercambio.id"
                >
                    <b-card v-if="intercambio.usuarioDemandante.id == userID" 
                        id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow"
                    >
                        <b-card-text>
                            <h5 class="m-0 madimiOne">
                                {{ intercambio.estado }} 
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                <span class="amarillo">
                                    {{ intercambio.usuarioSolicitado.username }}
                                </span> ha rechazado tu propuesta de intercambio...
                            </h2>
                            <hr />
                            <h5>Buscabas aprender:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Le sugeriste aprender:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSugerido.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSugerido.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSugerido.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                        </b-card-text>
                    </b-card>
                </div>
            </div>
            <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalElements"
                  :per-page="pageSize"
                  @input="listarIntercambios"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
            <div v-else-if="isEmpty">
                <div class="d-flex flex-column justify-content-center mb-5 mt-4 pt-2 text-center">
                    <div>
                    <img src="../assets/img/withoutContent.jpg" width="200px"/>
                    </div>
                    <div class="afacad mt-3">
                        <h1 class="fw-normal text-secondary" style="font-size: 25px;">
                            {{ error }}
                        </h1>
                    </div>
                </div>
            </div>
        </b-tab>
        <!-- PESTAÑA 5 -->
        <b-tab title="Finalizados">
            <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
                <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                    v-for="intercambio in intercambiosFinalizados" 
                    :key="intercambio.id"
                >
                    <b-card v-if="intercambio.usuarioDemandante.id == userID" id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                        <b-card-text>
                            <h5 class="m-0 madimiOne">{{ intercambio.estado }} 
                                <span class="m-0 afacad"> desde el {{ intercambio.fechaFin }}</span>
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                Intercambio con
                                <span class="amarillo">
                                    {{ intercambio.usuarioSolicitado.username }}
                                </span>
                                finalizado con éxito.
                            </h2>
                            <hr />
                            <h5>Enseñas:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoAceptado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoAceptado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoAceptado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Aprendes:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <b-button href="#" variant="primary" class="border-0 w-100">
                                    <h5 class="m-0">
                                        Ver detalles
                                    </h5>
                                </b-button>
                            </div>
                        </b-card-text>
                    </b-card>
                    <b-card v-else id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                        <b-card-text>
                            <h5 class="m-0 madimiOne">{{ intercambio.estado }} 
                                <span class="m-0 afacad"> el {{ intercambio.fechaFin }}</span>
                            </h5>
                            <hr />
                            <h2 class="m-0 afacad text-center">
                                Intercambio con
                                <span class="amarillo">
                                    {{ intercambio.usuarioDemandante.username }}
                                </span>
                                finalizado con éxito.
                            </h2>
                            <hr />
                            <h5>Enseñas:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoSolicitado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoSolicitado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoSolicitado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <h5 class="mt-4">Aprendes:</h5>
                            <div class="d-flex justify-content-between fondoGris p-2 rounded-3">
                                <h5 class="m-0 afacad">{{ intercambio.talentoAceptado.titulo }}</h5>
                                <div 
                                    class="px-3 py-0 rounded-5 align-self-start border border-2" 
                                    :style="{ backgroundColor: intercambio.talentoAceptado.nivel.color }"
                                >
                                    <p class="text-light text-center mb-0 afacad">
                                        {{ intercambio.talentoAceptado.nivel.nombre }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <b-button href="#" variant="primary" class="border-0 w-100">
                                    <h5 class="m-0">
                                        Ver detalles
                                    </h5>
                                </b-button>
                            </div>
                        </b-card-text>
                    </b-card>
                </div>
            </div>
            <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalElements"
                  :per-page="pageSize"
                  @input="listarIntercambios"
                  limit="5"
                  size="lg"
                  class=""
                />
            </div>
            <div v-else-if="isEmpty">
                <div class="d-flex flex-column justify-content-center mb-5 mt-4 pt-2 text-center">
                    <div>
                    <img src="../assets/img/withoutContent.jpg" width="200px"/>
                    </div>
                    <div class="afacad mt-3">
                        <h1 class="fw-normal text-secondary" style="font-size: 25px;">
                            {{ error }}
                        </h1>
                    </div>
                </div>
            </div>
        </b-tab>
    </b-tabs>
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

.amarillo{
  color: #FFF200;
}

.texto:hover{
  color: #FFF200 !important;
}

.fondoOscuro {
  background-color: #050027;
}

.fondoGris{
  background-color: #7d7d7d;
}

.fondoRojo{
  background-color: #cd0000;
}

.fondoNaranja{
  background-color: #ff8400;
}

.fondoVerde {
  background-color: #0cad00;
}

.fondoRojo:hover{
  background-color: #bc0000 !important;
  color: #ffffff !important;
}

.fondoNaranja:hover{
  background-color: #e97800 !important;
  color: #ffffff !important;
}

.fondoVerde:hover{
  background-color: #087a00 !important;
  color: #ffffff !important;
}

#tarjeta{
    transition-duration: 0.2s;
}

#tarjeta:hover{
    transform: scale(1.07);
}
</style>