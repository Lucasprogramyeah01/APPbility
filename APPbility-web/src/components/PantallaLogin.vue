<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref } from 'vue';

const passwordVisible = ref(false);



</script>


<template>
    <div class="d-flex flex-column fondoDegradado">
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
          <b-form class="px-2 d-flex flex-column justify-content-center" style="width: 30%;">
            <!-- Username -->
            <b-input-group class="mt-2 mb-3">
                <b-input-group-prepend is-text class="bordeGris">
                  <i class="bi bi-person-fill"></i>
                </b-input-group-prepend>
                <b-form-input 
                  class="bordeGris"
                  size="lg" placeholder="Nombre de usuario">
                </b-form-input>
            </b-input-group>
            <!-- Password -->
            <b-input-group class="mt-2">
              <b-input-group-prepend is-text class="bordeGris">
                <i class="bi bi-lock-fill"></i>
              </b-input-group-prepend>
              <b-form-input
                class="border-end-0 bordeGris"
                :type="passwordVisible ? 'text' : 'password'"
                size="lg" placeholder="Contraseña"
              ></b-form-input>
              <b-input-group-append is-text class="bg-white bordeGris">
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

            <b-button size="lg" class="b-button mt-4 fs-4 border-0 mt-5 fondoOscuro afacad">Iniciar sesión</b-button>

            <div class="d-flex flex-column text-center text-white afacad mt-5 pt-5 mb-5">
              <h3 class="fw-normal" >¿Todavía no tienes cuenta? &nbsp;
                <span class="fw-bold" style="text-decoration: underline;">Regístrate</span>
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