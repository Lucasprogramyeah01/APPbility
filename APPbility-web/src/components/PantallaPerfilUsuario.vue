<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, watch, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import LoadingComponent from './loadingComponent.vue';
import { UserService } from '../services/userService';
import { TalentoService } from '../services/talentoService';

// DATA() ---------------------------------------------------------------

const usuario = ref(null);

const route = useRoute();
const id = route.params.id;

const listaTalentos = ref({ 
    content: [], 
    empty: true 
});
//Pag
const currentPage = ref(1);
const pageSize = 21;
const totalElements = ref(0);

const isLoading = ref(true);
const error = ref(null);

const abierto = ref(null);

// CREATED() ---------------------------------------------------------------

onMounted(() => buscarUsuarioPorID(id));
onMounted(() => listarTalentos());

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

async function listarTalentos(page = 1) {
  isLoading.value = true;
  try {
    const response = await TalentoService.findTalentosFromUsuario(page - 1, pageSize, id);
    listaTalentos.value = response;
    totalElements.value = response.totalElements;
    console.log(listaTalentos.value);
    currentPage.value = page;
  } catch (err) {
    error.value = err.message;
  } finally {
    isLoading.value = false;
  }
};

function toggleAcordeon(id) {
  abierto.value = abierto.value === id ? null : id;
};

// COMPUTED ---------------------------------------------------------------

const isEmpty = computed(() => listaTalentos.value.empty);

//Pag
const totalPages = computed(() => Math.ceil(totalElements.value / pageSize));

// WATCH ---------------------------------------------------------------

//Pag
watch(currentPage, (newPage) => {
  listarTalentos(newPage);
});
</script>


<template>
    <LoadingComponent v-if="isLoading"></LoadingComponent>

    <b-tabs content-class="mt-3" card justified>
        <b-tab title="Perfil" active>
            <p>{{ usuario?.username }}</p>
            <RouterLink :to="`/proponerIntercambio/${usuario?.id}`" class="text-decoration-none">
                <b-button variant="primary" class="border-0 w-100">
                    <h5 class="m-0">Ofrecer Intercambio</h5>
                </b-button>
            </RouterLink>  
        </b-tab>
        <b-tab title="Talentos">
            <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
                <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 40%;"
                    v-for="talento in listaTalentos.content" 
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
            <div v-if="!isEmpty && totalPages > 1" class="d-flex justify-content-center mt-3 mb-5">
                <b-pagination
                  v-model="currentPage"
                  :total-rows="totalElements"
                  :per-page="pageSize"
                  @input="listarTalentos"
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
</style>