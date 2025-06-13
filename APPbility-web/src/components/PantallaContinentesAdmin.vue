<script setup>
</script>


<template>
    <div class="d-flex flex-column">
        <div class="container-fluid fondoDegradado">
            <p class="m-0" style="font-size: 15px; color: transparent">APPbility</p>
        </div>
        <div class="container-fluid d-flex flex-column fondoOscuro py-3 ps-5">
            <!--div class="w-auto d-flex">
                <img src="../assets/img/APPbilityLogo.png" width="40px" height="40px" />
                <span class="text-white madimiOne ms-2" style="font-size: 30px;">
                    APP<span class="amarillo">bility</span>
                </span>
            </div-->
            <div class="w-auto d-flex text-white carterOne mt-3">
                <i class="bi bi-globe-americas" style="font-size: 50px;"></i>
                <h1 class="fw-normal m-0 pt-1 ps-3" style="font-size: 50px;">Continentes y Países</h1>
            </div>
            <!--div class="text-white afacad mt-3">
                <h1 class="fw-normal" style="font-size: 20px;">
                    Esta sección dispone toda aquella información acerca de l.
                </h1>
            </div-->

        </div>
        <!-- Botones -->
        <div class="d-flex flex-column justify-content-center mt-3 mx-4 mb-5">
            <div class="afacad">
                <b-button size="lg" class="border-0 a-button fondoAzulOscuro">
                    <i class="bi bi-plus-lg"></i> Añadir Continente
                </b-button>
                <b-button size="lg" class="ms-3 border-0 a-button">
                    <i class="bi bi-plus-lg"></i> Añadir País
                </b-button>
            </div>
        </div>
        <div class="d-flex flex-wrap justify-content-between mx-5 px-3">
            <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 30%;">
                <b-card id="tarjeta" bg-variant="dark" text-variant="white" class="rounded-3 shadow">
                    <b-card-text>
                        <div class="d-flex justify-content-between">
                            <h1 class="amarillo madimiOne">1</h1>
                            <div>
                                <b-button href="#" class="h-auto ms-2 fondoNaranja b-button border-0">
                                    <i class="bi bi-pencil-fill" style="font-size: 25px;"></i>
                                </b-button>
                                <b-button href="#" class="h-auto ms-2 fondoRojo b-button border-0">
                                    <i class="bi bi-trash3-fill" style="font-size: 25px;"></i>
                                </b-button>
                            </div>
                        </div>
                        <h2 class="mt-2">América del Norte</h2>
                    </b-card-text>
                    <b-button href="#" variant="primary" class="b-button border-0 w-100">
                        <h5 class="m-0">Ver países</h5>
                    </b-button>
                </b-card>
            </div>
            <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 30%;">
                <b-card bg-variant="dark" text-variant="white" class="rounded-3">
                    <b-card-text>
                        <div class="d-flex justify-content-between">
                            <h1 class="amarillo madimiOne">1</h1>
                            <div>
                                <b-button href="#" class="h-auto ms-2 fondoNaranja b-button border-0">
                                    <i class="bi bi-pencil-fill" style="font-size: 25px;"></i>
                                </b-button>
                                <b-button href="#" class="h-auto ms-2 fondoRojo b-button border-0">
                                    <i class="bi bi-trash3-fill" style="font-size: 25px;"></i>
                                </b-button>
                            </div>
                        </div>
                        <h2 class="mt-2">América del Norte</h2>
                    </b-card-text>
                    <b-button href="#" variant="primary" class="b-button border-0 w-100">
                        <h5 class="m-0">Ver países</h5>
                    </b-button>
                </b-card>
            </div>
            <div class="col-12 p-2 my-2 mx-1 afacad" style="width: 30%;">
                <b-card bg-variant="dark" text-variant="white" class="rounded-3">
                    <b-card-text>
                        <div class="d-flex justify-content-between">
                            <h1 class="amarillo madimiOne">1</h1>
                            <div>
                                <b-button href="#" class="h-auto ms-2 fondoNaranja b-button border-0">
                                    <i class="bi bi-pencil-fill" style="font-size: 25px;"></i>
                                </b-button>
                                <b-button href="#" class="h-auto ms-2 fondoRojo b-button border-0">
                                    <i class="bi bi-trash3-fill" style="font-size: 25px;"></i>
                                </b-button>
                            </div>
                        </div>
                        <h2 class="mt-2">América del Norte</h2>
                    </b-card-text>
                    <b-button href="#" variant="primary" class="b-button border-0 w-100">
                        <h5 class="m-0">Ver países</h5>
                    </b-button>
                </b-card>
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
  src: url('../src/assets/fonts/CarterOne-Regular.ttf');
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

.fondoOscuro {
  background-color: #050027;
}

.fondoRojo{
  background-color: #cd0000;
}

.fondoNaranja{
  background-color: #ff8400;
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