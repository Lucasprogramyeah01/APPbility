<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref } from 'vue';

const passwordVisible = ref(false);
</script>


<template>
    <div class="d-flex flex-column">
        <div class="container-fluid fondoDegradado">
            <p class="m-0" style="font-size: 15px; color: transparent">APPbility</p>
        </div>
        <div class="container-fluid d-flex flex-column fondoOscuro py-3 ps-5">
            <div class="w-auto d-flex">
                <img src="../assets/img/APPbilityLogo.png" width="40px" height="40px" />
                <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                    APP<span class="amarillo">bility</span>
                </span>
            </div>
            <div class="text-white carterOne mt-3">
                <h1 class="fw-normal" style="font-size: 80px;">Regístrate</h1>
            </div>
            <div class="text-white afacad mt-3">
                <h1 class="fw-normal" style="font-size: 20px;">
                    Crea tu cuenta y disfruta de un mundo de aprendizaje y habilidades sin límite.
                </h1>
            </div>
        </div>
    </div>

    <div class="d-flex flex-column justify-content-center mt-5">
        <b-form>
            <!-- Datos de Acceso ----------------------------------------------------------------------->
            <div class="text-black mx-5 px-5">
                <h1 class="fw-normal madimiOne" style="font-size: 28px;">Datos de Acceso</h1>
                <h1 class="fw-normal fst-italic afacad text-secondary" style="font-size: 16px;">
                    Crea tus credenciales seguras. Usa un nombre de usuario único y una contraseña robusta para proteger tu cuenta. 
                    Tu correo electrónico será necesario para verificar tu identidad y recuperar el acceso.
                </h1>
            </div>
            <div class="fondoGris rounded-5" style="margin-left: 85px; margin-right: 85px;">
                <p class="m-0" style="font-size: 1px; color: transparent">APPbility</p>
            </div>
            <div class="d-flex justify-content-between mx-5 px-3 mt-4">
                <!-- Username -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <b-input-group-prepend is-text>
                        <i class="bi bi-person-fill"></i>
                    </b-input-group-prepend>
                    <b-form-input size="lg" placeholder="Nombre de usuario"></b-form-input>
                </b-input-group>
                <!-- Password -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <b-input-group-prepend is-text>
                    <i class="bi bi-lock-fill"></i>
                    </b-input-group-prepend>
                    <b-form-input
                    class="border-end-0"
                    :type="passwordVisible ? 'text' : 'password'"
                    size="lg" placeholder="Contraseña"
                    ></b-form-input>
                    <b-input-group-append is-text class="bg-white">
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
                <!-- Email -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <b-input-group-prepend is-text>
                        <i class="bi bi-envelope-fill"></i>
                    </b-input-group-prepend>
                    <b-form-input size="lg" type="email" placeholder="Correo electrónico"></b-form-input>
                </b-input-group>
            </div>
            <!-- Información Personal ------------------------------------------------------------>
            <div class="text-black madimiOne mx-5 px-5 mt-5">
                <h1 class="fw-normal madimiOne" style="font-size: 28px;">Información Personal</h1>
                <h1 class="fw-normal fst-italic afacad text-secondary" style="font-size: 16px;">
                    Ayúdanos a conocerte mejor. Esta información nos permite personalizar tu experiencia y garantizar 
                    que cumples con los requisitos de edad para usar la plataforma.
                </h1>
            </div>
            <div class="fondoGris rounded-5" style="margin-left: 85px; margin-right: 85px;">
                <p class="m-0" style="font-size: 1px; color: transparent">APPbility</p>
            </div>
            <div class="d-flex justify-content-between mx-5 px-3 mt-3">
                <!-- Nombre -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <!--b-input-group-prepend is-text>
                        <i class="bi bi-person-fill"></i>
                    </b-input-group-prepend-->
                    <b-form-input size="lg" placeholder="Nombre"></b-form-input>
                </b-input-group>
                <!-- Apellidos -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <!--b-input-group-prepend is-text>
                        <i class="bi bi-person-fill"></i>
                    </b-input-group-prepend-->
                    <b-form-input size="lg" placeholder="Apellidos"></b-form-input>
                </b-input-group>
            </div>
            <!-- Ubicación ------------------------------------------------------------>
            <div class="text-black madimiOne mx-5 px-5 mt-5">
                <h1 class="fw-normal madimiOne" style="font-size: 28px;">Ubicación</h1>
                <h1 class="fw-normal fst-italic afacad text-secondary" style="font-size: 16px;">
                    Indica tu ubicación para conectarte con usuarios cercanos y encontrar oportunidades 
                    relevantes en tu zona horaria.
                </h1>
            </div>
            <div class="fondoGris rounded-5" style="margin-left: 85px; margin-right: 85px;">
                <p class="m-0" style="font-size: 1px; color: transparent">APPbility</p>
            </div>
            <div class="d-flex justify-content-between mx-5 px-3 mt-3"></div>
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
  src: url('../src/assets/fonts/CarterOne-Regular.ttf');
}
.carterOne{
  font-family: 'carterOne';
}

.fondoDegradado {
  background: linear-gradient(to right, #FF00CC, #00F2FF);
  backdrop-filter: blur(4px);
}

.fondoOscuro {
  background-color: #050027;
}

.fondoGris {
  background-color: #a7a7a7;
}

.amarillo{
  color: #FFF200;
}

.b-button:hover{
  background-color: #FFF200 !important;
  color: #050027 !important;
}
</style>