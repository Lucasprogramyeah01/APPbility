<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref, onMounted } from 'vue';
import { ContinenteService } from '../services/continenteService';
import { useRouter, useRoute } from 'vue-router';
import Swal from 'sweetalert2';
import { useToast } from "vue-toastification";

// DATA() ---------------------------------------------------------------

const datosContinente = ref({ 
    nombre: '' 
});

const router = useRouter();
const route = useRoute();

const isLoading = ref(true);

const toast = useToast();

// CREATED() ---------------------------------------------------------------

onMounted(async () => {
  const id = route.params.id;
  if (id) {
    isEditMode.value = true;
    isLoading.value = true;
    try {
      const response = await ContinenteService.findById(id);
      datosContinente.value = { ...response };
    } catch (err) {
      toast.error('No se pudo cargar el continente.');
      router.replace('/continentes');
    } finally {
      isLoading.value = false;
    }
  }
});

// METHODS ---------------------------------------------------------------

const finalizarFormulario = async () => {
  isLoading.value = true;
  try {
    if (isEditMode.value) {
      await ContinenteService.editContinente(datosContinente.value, route.params.id);
      toast.success('Continente actualizado con éxito.');
    } else {
      await ContinenteService.createContinente(datosContinente.value);
      toast.success('Continente añadido con éxito.');
      datosContinente.value.nombre = '';
    }
    router.replace('/continentes');
  } catch (err) {
    toast.error(err.message);
  } finally {
    isLoading.value = false;
  }
};

</script>


<template>
    <LoadingComponent v-if="isLoading"></LoadingComponent>

    <div class="d-flex flex-column">
        <div class="container-fluid fondoDegradado">
            <p class="m-0" style="font-size: 15px; color: transparent">APPbility</p>
        </div>
        <div class="container-fluid d-flex flex-column fondoOscuro py-3 ps-5">
            <div class="w-auto d-flex">
                <img src="../assets/img/APPbilityLogo.png" width="40px" height="40px" />
                <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                    APP<span class="amarillo">bility</span> <span style="color: aqua; font-size: 20px;">admin</span>
                </span>
            </div>
            <div class="text-white carterOne mt-3">
                <h1 class="fw-normal" style="font-size: 80px;">
                  {{ isEditMode ? 'Editar Continente' : 'Añadir Continente' }}
                </h1>
            </div>
            <div class="text-white afacad mt-3">
                <h1 class="fw-normal" style="font-size: 20px;">
                  {{ isEditMode ? 'Modifica los datos del continente para actualizarlo en la plataforma.' : 'Rellena el formulario para agregar un nuevo continente a la plataforma.' }}
                </h1>
            </div>
        </div>
    </div>

    <div class="d-flex flex-column justify-content-center mt-3 mb-5">
        <b-form @submit.prevent="finalizarFormulario">
            <div class="d-flex flex-column align-items-center mx-5 px-3 mt-4">
                <!-- Nombre -->
                <b-input-group class="mt-2 mb-3 mx-4 w-50">
                    <b-form-input 
                        class="bordeGris paddingParaInputs border-2"
                        placeholder="Nombre del continente *"
                        v-model="datosContinente.nombre"
                    >
                    </b-form-input>
                </b-input-group>
                <b-button 
                    class="w-25 b-button paddingParaInputs mx-4 fs-5 border-0 mt-4 fondoOscuro afacad"
                    type="submit"
                >{{ isEditMode ? 'Editar Continente' : 'Añadir Continente' }}
                </b-button>
            </div>
        </b-form>
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

.fondoOscuro {
  background-color: #050027;
}

.bordeGris{
  border-color: #C5C5C5;
}

.paddingParaInputs{
  padding-top: 11.2px;
  padding-bottom: 11.2px;
}

.amarillo{
  color: #FFF200;
}

.texto:hover{
  color: #FFF200 !important;
}

.b-button:hover{
  background-color: rgb(0, 64, 255) !important;
}
</style>