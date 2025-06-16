<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { useAuthService } from '../security/authService';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import Swal from 'sweetalert2';
import { useToast } from "vue-toastification";

// DATA() ---------------------------------------------------------------

const datosParaLogin = ref({ 
    username: '',
    password: ''
});

const passwordVisible = ref(false);
const isSubmitting = ref(false);

const router = useRouter();
const authService = useAuthService();

const toast = useToast();

// METHODS ---------------------------------------------------------------

const iniciarSesion = async () => {
  if (!datosParaLogin.value.username || !datosParaLogin.value.password) {
    toast.error('Se deben rellenar los dos campos para iniciar sesión.');
    return;
  }

  try {
    isSubmitting.value = true;
    const login = await authService.login({
      username: datosParaLogin.value.username,
      password: datosParaLogin.value.password
    });
    toast.success('Se ha iniciado sesión con éxito.');
    if(login.rol[0] == 'USER'){
      router.replace('/inicio');
    }else if(login.rol[0] == 'ADMIN'){
      router.replace('/inicioAdmin');
    }
  } catch (error) {
    toast.error(error.response?.data?.message || 'Error al iniciar sesión.');
  } finally {
    isSubmitting.value = false;
  }
};

</script>


<template>
    <div class="d-flex flex-column fondoDegradado vh-100">
        <div class="container-fluid d-flex flex-column fondoOscuro mt-5 py-5">
          <div class="w-auto d-flex justify-content-center">
            <img src="../assets/img/APPbilityLogo.png" width="180px" height="180px" />
            <h1 class="display-1 text-white madimiOne ms-5 ps-2" style="font-size: 150px;">
              APP<span class="amarillo">bility</span>
            </h1>
          </div>
          <div class="text-center text-white afacad mt-3">
            <h1 class="fw-normal" style="font-size: 40px;">Intercambia, aprende, crece</h1>
          </div>
        </div>
        <div class="d-flex justify-content-center mt-5">
          <b-form @submit.prevent="iniciarSesion" 
            class="px-2 d-flex flex-column justify-content-center" 
            style="width: 30%;"
          >
            <!-- Username -->
            <b-input-group class="mt-2 mb-3">
                <b-input-group-prepend is-text class="bordeGris border-2 border-end-0">
                  <i class="bi bi-person-fill"></i>
                </b-input-group-prepend>
                <b-form-input 
                  class="bordeGris border-2"
                  size="lg" placeholder="Nombre de usuario"
                  v-model="datosParaLogin.username"
                  >
                </b-form-input>
            </b-input-group>
            <!-- Password -->
            <b-input-group class="mt-2">
              <b-input-group-prepend is-text class="bordeGris border-2 border-end-0">
                <i class="bi bi-lock-fill"></i>
              </b-input-group-prepend>
              <b-form-input
                class="border-end-0 bordeGris border-2"
                :type="passwordVisible ? 'text' : 'password'"
                size="lg" placeholder="Contraseña"
                v-model="datosParaLogin.password"
              ></b-form-input>
              <b-input-group-append is-text class="bg-white bordeGris border-2 border-start-0">
                <b-button
                  class="p-0 border-0 bg-transparent" variant="link"
                  @click="passwordVisible = !passwordVisible"
                >
                  <i 
                    class="text-black"
                    :class="passwordVisible ? 'bi bi-eye-fill' : ' bi bi-eye-slash-fill'">
                  </i>
                </b-button>
              </b-input-group-append>
            </b-input-group>

            <b-button 
              type="submit"
              size="lg" 
              class="w-100 b-button fs-4 border-0 mt-5 fondoOscuro afacad"
              :disabled="isSubmitting"
            >
              {{ isSubmitting ? 'Iniciando sesión...' : 'Iniciar sesión' }}
            </b-button>

            <div class="d-flex flex-column text-center text-white afacad mt-5 pt-5 mb-5">
              <h3 class="fw-normal" >¿Todavía no tienes cuenta? &nbsp;
                  <RouterLink :to="`/registrarUsuario`" class="w-100 text-decoration-none">
                    <span class="fw-bold text-white" style="text-decoration: underline;">Regístrate</span>
                  </RouterLink>
              </h3>
            </div>
          </b-form>
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

.fondoDegradado {
  background: linear-gradient(to right, #FF00CC, #00F2FF);
  backdrop-filter: blur(4px);
  background-size: cover;
}

.fondoOscuro {
  background-color: #050027;
}

.bordeGris{
  border-color: #C5C5C5;
}

.amarillo{
  color: #FFF200;
}

.b-button:hover{
  background-color: #FFF200 !important;
  color: #050027 !important;
}
</style>