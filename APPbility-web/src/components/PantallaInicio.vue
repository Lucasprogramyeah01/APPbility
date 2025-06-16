<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, watch, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import LoadingComponent from './loadingComponent.vue';
import { UserService } from '../services/userService';
import { useToast } from "vue-toastification";

// DATA() ---------------------------------------------------------------

const listaUsuarios = ref({ 
    content: [], 
    empty: true 
});

//Pag
const currentPage = ref(1);
const pageSize = 20;
const totalElements = ref(0);

const usuarioAutenticadoActual = ref(null);

const isLoading = ref(true);
const error = ref(null);

const toast = useToast();

// CREATED() ---------------------------------------------------------------

onMounted(() => listarUsuarios());
onMounted(() => obtenerUsuarioAutenticado());

// METHODS ---------------------------------------------------------------

async function listarUsuarios(page = 1) {
  isLoading.value = true;
  try {
    const response = await UserService.findAll(page - 1, pageSize);
    listaUsuarios.value = response;
    totalElements.value = response.totalElements;
    currentPage.value = page;
    console.log(response);
  } catch (err) {
    error.value = err.message;
  } finally {
    isLoading.value = false;
  }
}

async function obtenerUsuarioAutenticado() {
  const user = localStorage.getItem('token');
  if (user) {
    usuarioAutenticadoActual.value = JSON.parse(user);
  }
  listarUsuarios();
}

async function obtenerListaUsuariosFiltrados() {
  if (!usuarioAutenticadoActual.value) return listaUsuarios.value.content;
    return listaUsuarios.value.content.filter(
      usuario => usuario.id !== usuarioAutenticadoActual.value.id
    );
}

// COMPUTED ---------------------------------------------------------------

const isEmpty = computed(() => listaUsuarios.value.empty);

const usuariosFiltrados = computed(() => {
  if (!usuarioAutenticadoActual.value) return listaUsuarios.value.content;
  return listaUsuarios.value.content.filter(
    usuario => usuario.id !== usuarioAutenticadoActual.value.id
  );
});

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

  <div class="d-flex flex-wrap justify-content-around mx-5 mt-4 px-3 pb-5">
      <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 30%;"
          v-for="usuario in usuariosFiltrados" 
          :key="usuario.id"
      >
        <RouterLink :to="`/perfil/${usuario.id}`" class="text-decoration-none">
          <b-card id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
              <!--b-card-text>
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
              </RouterLink-->
              <b-card-text ><h3 class="m-0 afacad">{{ usuario.username }}</h3></b-card-text>
          </b-card>
        </RouterLink>
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

#tarjeta{
    transition-duration: 0.2s;
}

#tarjeta:hover{
    transform: scale(1.07);
}
</style>