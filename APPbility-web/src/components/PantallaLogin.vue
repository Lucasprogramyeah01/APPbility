<!-- SCRIPT ---------------------------------------------------------------------------------------------------------------->

<script setup>

</script>

<!-- TEMPLATE -------------------------------------------------------------------------------------------------------------->

<!--template>
  <v-container class="fill-height d-flex align-center justify-center" fluid>
    <v-card class="pa-5" width="400" elevation="10" color="indigo-darken-4">
      <v-card-title class="text-center text-white text-h5">APPbility</v-card-title>
      <v-card-text>
        <v-text-field
          label="Username"
          prepend-icon="mdi-account"
          variant="outlined"
          color="white"
          class="mb-3"
        />
        <v-text-field
          label="Password"
          prepend-icon="mdi-lock"
          type="password"
          variant="outlined"
          color="white"
        />
        <v-btn class="mt-4" block color="deep-purple-accent-2">Login</v-btn>
      </v-card-text>
    </v-card>
  </v-container>
</template-->

<template>
  <v-app>
    <v-main
      class="d-flex align-center justify-center"
      style="min-height: 100vh; background: linear-gradient(to right, #FF00CC, #00F2FF);"
    >
      <v-card
        class="pa-6 text-white"
        width="400"
        elevation="12"
        style="background-color: #050027;"
      >
        <div class="text-center mb-4">
          <v-img
            src="@/assets/img/APPbilityLogo.png"
            alt="APPbility Logo"
            width="100"
            height="100"
            cover
          />
        </div>

        <v-card-title class="justify-center text-h5">APPbility Login</v-card-title>

        <v-card-text>
          <v-text-field
            v-model="username"
            label="Nombre de usuario"
            prepend-inner-icon="mdi-account"
            variant="outlined"
            density="comfortable"
            color="white"
            class="mb-3"
          />

          <v-text-field
            v-model="password"
            label="Contraseña"
            prepend-inner-icon="mdi-lock"
            type="password"
            variant="outlined"
            density="comfortable"
            color="white"
          />
        </v-card-text>

        <v-card-actions class="justify-center mt-4">
          <v-btn color="deep-purple-accent-2" class="text-white" @click="login">
            Entrar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-main>
  </v-app>
</template>

<!-- STYLE ----------------------------------------------------------------------------------------------------------------->

<style scoped>
.fondoDegradado {
  background: linear-gradient(to right, #FF00CC, #00F2FF);
  backdrop-filter: blur(4px);
}

.fondoOscuro {
  background-color: #050027;
}

.fill-height {
  min-height: 100vh;
  background: linear-gradient(to right, #1e003a, #000015);
}
</style>