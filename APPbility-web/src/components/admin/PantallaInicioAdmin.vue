<script setup>
import { useRouter } from 'vue-router';
import { UserService } from '@/services/userService';

const router = useRouter();

const handleLogout = async () => {
  await UserService.logout(router);
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
                <img src="../../assets/img/APPbilityLogo.png" width="40px" height="40px" />
                <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                    APP<span class="amarillo">bility</span> <span style="color: aqua; font-size: 20px;">admin</span>
                </span>
            </div>
            <div class="w-auto d-flex justify-content-between mt-2 text-white">
                <div class="w-50">
                    <i class="bi bi-person-bounding-box carterOne" style="font-size: 42px;"></i>
                    <span class="fw-normal m-0 pt-1 ps-3 carterOne w-auto" style="font-size: 42px;">Cuenta de administrador</span>
                </div>
            </div>
            <b-button 
              class="btn fondoRojo text-white ms-4 w-25 afacad"
              style="height: 60px; font-size: 22px;"
              @click="handleLogout"
            >
              <i class="bi bi-box-arrow-right"></i> Logout
            </b-button>
        </div>
    </div>
    <div class="d-flex flex-column justify-content-center mt-4 mb-5 mx-5 pt-2">
      <RouterLink :to="`/continentes`" class="text-decoration-none">
        <div 
          class="d-flex justify-content-center align-items-center w-50 bg-primary afacad p-3 rounded-4 text-white" 
          id="tarjeta"
        >
          <i class="bi bi-globe-americas carterOne" style="font-size: 42px;"></i>
          <h1 class="m-0 ms-3">Continentes y Países</h1>
        </div>
      </RouterLink>
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