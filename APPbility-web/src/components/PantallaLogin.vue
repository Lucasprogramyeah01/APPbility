<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';

</script>

<template>
    <div class="d-flex justify-content-center align-items-center vh-100 fondoDegradado">
        <div class="w-50 fondoOscuro p-3 rounded-3">
            <div class="text-center">
                <img src="../assets/img/APPbilityLogo.png" width="150px" height="150px" />
                <h1>APPbility</h1>
            </div>
            <div>
                <b-form class="w-75 px-2 d-flex justify-content-center">
                    <b-input-group class="mb-3">
                        <b-input-group-prepend is-text>
                        <i class="bi bi-person-fill"></i>
                        </b-input-group-prepend>
                        <b-form-input placeholder="Username"></b-form-input>
                    </b-input-group>

                    <b-input-group>
                        <b-input-group-prepend is-text>
                        <i class="bi bi-lock-fill"></i>
                        </b-input-group-prepend>
                        <b-form-input type="password" placeholder="Password"></b-form-input>
                    </b-input-group>

                    <b-button block variant="primary" class="mt-4">Login</b-button>
                </b-form>
            </div>
        </div>
    </div>
</template>

<!--template>
  <div class="d-flex justify-content-center align-items-center fondoDegradado">
    <b-card
      class="p-4 shadow"
      style="width: 400px; background-color: #1a237e;"
      text-variant="white"
    >
      <h3 class="text-center text-white mb-4">APPbility</h3>

      <b-form>
        <b-form-group>
          <b-input-group class="mb-3">
            <b-input-group-prepend is-text>
              <i class="bi bi-person-fill"></i>
            </b-input-group-prepend>
            <b-form-input placeholder="Username"></b-form-input>
          </b-input-group>
        </b-form-group>

        <b-form-group>
          <b-input-group>
            <b-input-group-prepend is-text>
              <i class="bi bi-lock-fill"></i>
            </b-input-group-prepend>
            <b-form-input type="password" placeholder="Password"></b-form-input>
          </b-input-group>
        </b-form-group>

        <b-button block variant="primary" class="mt-4">Login</b-button>
      </b-form>
    </b-card>
  </div>
</template-->

<style scoped>
.fondoDegradado {
  background: linear-gradient(to right, #FF00CC, #00F2FF);
  backdrop-filter: blur(4px);
}

.fondoOscuro {
  background-color: #050027;
}

</style>