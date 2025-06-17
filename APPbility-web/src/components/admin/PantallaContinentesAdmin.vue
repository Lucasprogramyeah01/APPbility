<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, computed, onMounted, watch } from 'vue';
import { ContinenteService } from '../../services/continenteService';
import Swal from 'sweetalert2';
import { useToast } from "vue-toastification";

// DATA() ---------------------------------------------------------------

const listaContinentes = ref({ 
    content: [], 
    empty: true 
});

//Pag
const currentPage = ref(1);
const pageSize = 9;
const totalElements = ref(0);

const isLoading = ref(true);
const error = ref(null);

const toast = useToast();

// CREATED() ---------------------------------------------------------------

onMounted(() => listarContinentes());

// METHODS ---------------------------------------------------------------

async function listarContinentes(page = 1) {
  isLoading.value = true;
  try {
    const response = await ContinenteService.findAll(page - 1, pageSize);
    listaContinentes.value = response;
    totalElements.value = response.totalElements;
    currentPage.value = page;
  } catch (err) {
    error.value = err.message;
  } finally {
    isLoading.value = false;
  }
}

async function eliminarContinente(id) {
  const result = await Swal.fire({
    title: '¿Estás seguro?',
    text: 'Esta acción no se puede deshacer.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar'
  });

  if (result.isConfirmed) {
    isLoading.value = true;
    try {
      await ContinenteService.deleteContinente(id);
      await listarContinentes(currentPage.value);
      toast.success('Continente eliminado con éxito.');
    } catch (err) {
      toast.error(err.message);
    } finally {
      isLoading.value = false;
    }
  }
}

// COMPUTED ---------------------------------------------------------------

const isEmpty = computed(() => listaContinentes.value.empty);

//Pag
const totalPages = computed(() => Math.ceil(totalElements.value / pageSize));

// WATCH ---------------------------------------------------------------

//Pag
watch(currentPage, (newPage) => {
  listarContinentes(newPage);
});

</script>


<template>
    <LoadingComponent v-if="isLoading"></LoadingComponent>

    <div class="d-flex flex-column">
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
                <div class="d-flex align-items-center me-3 afacad" style="cursor: pointer;">
                    <h3 href="#" class="nav-item-Textsize px-4 m-0 texto"><i class="bi bi-house-fill"></i> Volver al inicio</h3>
                </div>
            </div>
        </div>
        <!-- Botones -->
        <div class="d-flex flex-column justify-content-center mt-4 mb-5 mx-5 pt-2">
            <div class="afacad ms-5">
                <RouterLink :to="`/registrarContinente`" class="text-decoration-none">
                    <b-button size="lg" class="border-0 a-button bg-primary" style="font-size: 22px;">
                        <i class="bi bi-plus-lg"></i> Añadir Continente
                    </b-button>
                </RouterLink>
            </div>
        </div>
        <div class="text-black madimiOne mx-5 px-5 mt-2">
            <h1 class="fw-normal madimiOne" style="font-size: 28px;">Lista de Continentes</h1>
        </div>
        <div class="fondoGris rounded-5" style="margin-left: 85px; margin-right: 85px;">
            <p class="m-0" style="font-size: 1px; color: transparent">APPbility</p>
        </div>
        <div 
            class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5"
        >
            <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 30%;"
                v-for="continente in listaContinentes.content" 
                :key="continente.id"
            >
                <b-card id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                    <b-card-text>
                        <div class="d-flex justify-content-between">
                            <h1 class="amarillo madimiOne">{{ continente.id }}</h1>
                            <div>
                              <RouterLink :to="`/editarContinente/${continente.id}`" class="text-decoration-none">
                                <b-button href="#" class="h-auto ms-2 fondoNaranja border-0">
                                    <i class="bi bi-pencil-fill" style="font-size: 25px;"></i>
                                </b-button>
                              </RouterLink>

                                <b-button 
                                    class="h-auto ms-2 fondoRojo border-0"
                                    @click="eliminarContinente(continente.id)"
                                >
                                    <i class="bi bi-trash3-fill" style="font-size: 25px;"></i>
                                </b-button>
                            </div>
                        </div>
                        <h2 class="mt-2">{{ continente.nombre }}</h2>
                    </b-card-text>
                    <RouterLink :to="`/continente/${continente.id}`" class="text-decoration-none">
                        <b-button href="#" variant="primary" class="border-0 w-100">
                            <h5 class="m-0">Ver países</h5>
                        </b-button>
                    </RouterLink>
                </b-card>
            </div>
        </div>
        <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
          <b-pagination
            v-model="currentPage"
            :total-rows="totalElements"
            :per-page="pageSize"
            @input="listarContinentes"
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

</style>