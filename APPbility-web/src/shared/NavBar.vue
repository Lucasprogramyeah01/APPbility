<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref } from 'vue';

// PROPS() ---------------------------------------------------------------

defineProps({
  userID: String
});

</script>


<template>
    <b-navbar toggleable="lg" class="fondoOscuro py-2">
        <RouterLink :to="`/inicio`" class="text-decoration-none">
            <b-navbar-brand class="d-flex w-auto ms-4">
                <img src="../assets/img/APPbilityLogo.png" width="40px" height="40px" />
                <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                    APP<span class="amarillo">bility</span>
                </span>
            </b-navbar-brand>
        </RouterLink>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <!-- Opciones de la barra de navegación -->
          <b-navbar-nav class="afacad ps-4">
              <RouterLink :to="`/perfil/${userID}`" class="text-decoration-none">
                <b-nav-item class="nav-item-Textsize px-4"><i class="bi bi-person-circle"></i> Mi cuenta</b-nav-item>
              </RouterLink>
              <RouterLink :to="`/intercambios`" class="text-decoration-none">
                <b-nav-item href="#" class="nav-item-Textsize px-4"><i class="bi bi-arrow-left-right"></i> Intercambios</b-nav-item>
              </RouterLink>
              <b-nav-item href="#" class="nav-item-Textsize px-4"><i class="bi bi-chat-fill"></i> Valoraciones</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
    </b-navbar>
  <div class="container-fluid fondoDegradado">
    <p class="m-0" style="font-size: 5px; color: transparent">APPbility</p>
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
}

.fondoOscuro {
  background-color: #050027;
}

.navbar-nav {
  --bs-nav-link-color: white;
  --bs-nav-link-hover-color: #FFF200;
}

.nav-item-Textsize{
  font-size: 22px;
}

.amarillo{
  color: #FFF200;
}

.b-button:hover{
  background-color: #FFF200 !important;
  color: #050027 !important;
}
</style>