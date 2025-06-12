<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref } from 'vue';

</script>


<template>
    <b-navbar toggleable="lg" class="fondoOscuro py-2">
        <b-navbar-brand href="#" class="d-flex w-auto ms-4">
            <img src="../assets/img/APPbilityLogo.png" width="40px" height="40px" />
            <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                APP<span class="amarillo">bility</span>
            </span>
        </b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
        <!--b-navbar-nav>
            <b-nav-item href="#">Link</b-nav-item>
            <b-nav-item href="#" disabled>Disabled</b-nav-item>
        </b-navbar-nav-->

        <!-- Opciones de la barra de navegación -->
        <b-navbar-nav class="afacad ps-4">
            <b-nav-item href="#" class="nav-item-Textsize px-4"><i class="bi bi-person-circle"></i> Mi cuenta</b-nav-item>
            <b-nav-item href="#" class="nav-item-Textsize px-4"><i class="bi bi-arrow-left-right"></i> Intercambios</b-nav-item>
            <b-nav-item href="#" class="nav-item-Textsize px-4"><i class="bi bi-chat-fill"></i> Valoraciones</b-nav-item>

            <!--b-nav-form>
                <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
                <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
            </b-nav-form>

            <b-nav-item-dropdown text="Lang" right>
            <b-dropdown-item href="#">EN</b-dropdown-item>
            <b-dropdown-item href="#">ES</b-dropdown-item>
            <b-dropdown-item href="#">RU</b-dropdown-item>
            <b-dropdown-item href="#">FA</b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown right>
            <template #button-content>
                <em>User</em>
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown-->
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