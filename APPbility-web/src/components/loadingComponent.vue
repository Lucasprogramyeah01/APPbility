<script setup>

</script>


<template>
    <div class="d-flex flex-column justify-content-center align-items-center fondoDegradado vh-100">
        <div class="loader"></div>
        <h1 class="mt-4 madimiOne text-white">Cargando...</h1>
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

.fondoDegradado {
  background: linear-gradient(to right, #FF00CC, #00F2FF);
  backdrop-filter: blur(4px);
  background-size: cover;
}

.loader {
  height: 50px;
  aspect-ratio: 2.5;
  --_g: no-repeat radial-gradient(farthest-side,#ffffff 90%,#0000);
  background:var(--_g), var(--_g), var(--_g), var(--_g);
  background-size: 20% 50%;
  animation: l43 1s infinite linear; 
}

@keyframes l43 {
  0%     {background-position: calc(0*100%/3) 50% ,calc(1*100%/3) 50% ,calc(2*100%/3) 50% ,calc(3*100%/3) 50% }
  16.67% {background-position: calc(0*100%/3) 0   ,calc(1*100%/3) 50% ,calc(2*100%/3) 50% ,calc(3*100%/3) 50% }
  33.33% {background-position: calc(0*100%/3) 100%,calc(1*100%/3) 0   ,calc(2*100%/3) 50% ,calc(3*100%/3) 50% }
  50%    {background-position: calc(0*100%/3) 50% ,calc(1*100%/3) 100%,calc(2*100%/3) 0   ,calc(3*100%/3) 50% }
  66.67% {background-position: calc(0*100%/3) 50% ,calc(1*100%/3) 50% ,calc(2*100%/3) 100%,calc(3*100%/3) 0   }
  83.33% {background-position: calc(0*100%/3) 50% ,calc(1*100%/3) 50% ,calc(2*100%/3) 50% ,calc(3*100%/3) 100%}
  100%   {background-position: calc(0*100%/3) 50% ,calc(1*100%/3) 50% ,calc(2*100%/3) 50% ,calc(3*100%/3) 50% }
}
</style>