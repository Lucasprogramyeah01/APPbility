<script setup>
import 'bootstrap-icons/font/bootstrap-icons.css';
import { ref } from 'vue';
import { UserService } from '../services/userService';

/*export default {
  data() {
    return {
      UsuarioRegistrado: {
        username: '',
        password: '',
        nombre: '',
        apellidos: '',
        email: '',
        fechaNacimiento: '',
        sexo: '',
        modalidadPreferida: '',
        numTelefono: '',
        mostrarNumTelefono: false,
        imagenPerfil: null,
        idiomaNativo: '',
        listaOtrosIdiomas: [],
        descripcionProfesional: '',
        presentacionPersonal: '',
        listaEnlacesExternos: [],
        paisNativoID: null,
        paisResidenciaID: null
      },
      profileImage: null,
      passwordVisible: false,
    }
  },

  created(){
    
  },

  methods: {
    handleFileUpload(event) {
      this.imagenPerfil = event.target.files[0];
    },

    async handleRegister() {
      try {
        const response = await UserService.register(this.userData, this.profileImage);
        this.successMessage = '¡Registro exitoso!';
        this.errorMessage = '';
        
        // Redirigir al login u otra página
        // this.$router.push('/login');
      } catch (error) {
        this.errorMessage = error.response?.data?.message || 'Error en el registro';
        this.successMessage = '';
      }
    }
  }

}*/

// data() ---------------------------------------------------------------

const datosUsuario = ref({
    username: '',
    password: '',
    nombre: '',
    apellidos: '',
    email: '',
    fechaNacimiento: '',
    sexo: '',
    modalidadPreferida: '',
    numTelefono: '',
    mostrarNumTelefono: false,
    imagenPerfil: null,
    idiomaNativo: '',
    listaOtrosIdiomas: [],
    descripcionProfesional: '',
    presentacionPersonal: '',
    listaEnlacesExternos: [],
    paisNativoID: null,
    paisResidenciaID: null
});

const imagenPerfil = ref(null);
const passwordVisible = ref(false);

// methods ---------------------------------------------------------------

const handleFileUpload = (event) => {
    this.imagenPerfil = event.target.files[0];
}

/*const handleRegister = async () => {
    try {
        const response = await UserService.register(userData.value, profileImage.value);
        successMessage.value = '¡Registro exitoso!';
        errorMessage.value = '';

        // Redirigir al login u otra página
        // router.push('/login');
    } catch (error) {
        errorMessage.value = error.response?.data?.message || 'Error en el registro';
        successMessage.value = '';
    }
}*/


const opcionesSexo = [
    { text: 'Hombre', value: 'HOMBRE' },
    { text: 'Mujer', value: 'MUJER' },
    { text: 'Otro', value: 'OTRO' }
];

const options = [
    { value: null, text: 'Please select an option' },
    { value: 'a', text: 'This is First option' },
    { value: 'b', text: 'Selected Option' },
    { value: { C: '3PO' }, text: 'This is an option with object value' },
    { value: 'd', text: 'This one is disabled', disabled: true }
]

//FORM TAGS
const options2 = ref(['Apple', 'Orange', 'Banana', 'Lime', 'Peach', 'Chocolate', 'Strawberry']);
const value = ref([]);
//const availableOptions = computed(() => his.options.filter(opt => this.value.indexOf(opt) === -1));

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

    <div class="d-flex flex-column justify-content-center mt-5 mb-5">
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
                    <b-input-group-prepend is-text class="bordeGris border-2 border-end-0">
                        <i class="bi bi-person-fill"></i>
                    </b-input-group-prepend>
                    <b-form-input 
                        class="bordeGris border-2 paddingParaInputs"
                        placeholder="Nombre de usuario *">
                    </b-form-input>
                </b-input-group>
                <!-- Password -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <b-input-group-prepend is-text class="bordeGris border-2 border-end-0">
                    <i class="bi bi-lock-fill"></i>
                    </b-input-group-prepend>
                    <b-form-input
                    class="border-2 border-end-0 bordeGris paddingParaInputs"
                    :type="passwordVisible ? 'text' : 'password'"
                        placeholder="Contraseña *"
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
                <!-- Email -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <b-input-group-prepend is-text class="bordeGris border-2 border-end-0">
                        <i class="bi bi-envelope-fill"></i>
                    </b-input-group-prepend>
                    <b-form-input 
                        class="border-2 bordeGris paddingParaInputs"
                         type="email" placeholder="Correo electrónico *">
                    </b-form-input>
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
                    <b-form-input placeholder="Nombre *" class="bordeGris paddingParaInputs"></b-form-input>
                </b-input-group>
                <!-- Apellidos -->
                <b-input-group class="mt-2 mb-3 px-4">
                    <b-form-input placeholder="Apellidos *" class="bordeGris paddingParaInputs"></b-form-input>
                </b-input-group>
                <!-- Fecha de Nacimiento -->
                <b-input-group class="mt-2 mb-3 px-4 bordeGris">
                    <b-input-group-prepend is-text class="bordeGris">
                        <p class="m-0">Fecha Nac. *</p>
                    </b-input-group-prepend>
                    <b-form-input 
                        type="date" class="bordeGris paddingParaInputs">
                    </b-form-input>
                </b-input-group>   
            </div>
            <div class="d-flex justify-content-between mx-5 px-3 mt-3">
                <!-- Sexo -->
                <!--b-form-group label="Sexo*" class="mb-0">
                    <b-form-radio-group
                        :options="opcionesSexo"
                        name="sexo-radio-group"
                        buttons
                        button-variant="outline-primary"
                        class="mt-2"
                        required
                    ></b-form-radio-group>
                </b-form-group-->
                
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
            <div class="d-flex justify-content-between mx-5 px-3 mt-3">
                <!-- País Nativo -->
                <div class="w-100 mt-2 mb-3 px-4">
                    <div class="text-black afacad">
                        <h1 class="fw-normal" style="font-size: 20px;">
                            País nativo *
                        </h1>
                    </div>
                    <b-form-select
                        class="w-100 bordeGris paddingParaInputs"
                        :options="options">
                    </b-form-select>
                </div>
                <!-- País de Residencia -->
                <div class="w-100 mt-2 mb-3 px-4">
                    <div class="text-black afacad">
                        <h1 class="fw-normal" style="font-size: 20px;">
                            País de residencia *
                        </h1>
                    </div>
                    <b-form-select
                        class="w-100 bordeGris paddingParaInputs"
                        :options="options">
                    </b-form-select>
                </div>
            </div>
            <!-- Idiomas ------------------------------------------------------------>
            <div class="text-black madimiOne mx-5 px-5 mt-5">
                <h1 class="fw-normal madimiOne" style="font-size: 28px;">Idiomas</h1>
                <h1 class="fw-normal fst-italic afacad text-secondary" style="font-size: 16px;">
                    ¿Qué idiomas hablas? Selecciona tu lengua nativa y otros idiomas que domines. 
                    Esto te ayudará a encontrar compañeros de intercambio lingüístico.
                </h1>
            </div>
            <div class="fondoGris rounded-5" style="margin-left: 85px; margin-right: 85px;">
                <p class="m-0" style="font-size: 1px; color: transparent">APPbility</p>
            </div>
            <div class="d-flex justify-content-between mx-5 px-3 mt-3">
                <!-- Idioma Nativo -->
                <div class="w-100 mt-2 mb-3 px-4">
                    <div class="text-black afacad">
                        <h1 class="fw-normal" style="font-size: 20px;">
                            Idioma nativo *
                        </h1>
                    </div>
                    <b-form-select
                        class="w-100 bordeGris paddingParaInputs"
                        :options="options">
                    </b-form-select>
                </div>
                <!-- Otros Idiomas -->
                <!--div class="w-100 mt-2 mb-3 px-4">
                    <b-form-group label="Otros Idiomas">
                        <b-form-tags
                        id="tags-component-select"
                        v-model="value"
                        size="lg"
                        class="mb-2"
                        add-on-change
                        no-outer-focus
                        >
                        <template v-slot="{ tags, inputAttrs, inputHandlers, disabled, removeTag }">
                            <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
                                <li v-for="tag in tags" :key="tag" class="list-inline-item">
                                    <b-form-tag
                                    @remove="removeTag(tag)"
                                    :title="tag"
                                    :disabled="disabled"
                                    variant="info"
                                    >{{ tag }}</b-form-tag>
                                </li>
                            </ul>
                            <b-form-select
                                v-bind="inputAttrs"
                                v-on="inputHandlers"
                                :disabled="disabled || availableOptions.length === 0"
                                :options="availableOptions"
                            >
                            <template #first>
                                <option disabled value="">Choose a tag...</option>
                            </template>
                            </b-form-select>
                        </template>
                        </b-form-tags>
                    </b-form-group>
                </div-->
            </div>

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
  src: url('../assets/fonts/CarterOne-Regular.ttf');
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

.bordeGris{
  border-color: #C5C5C5;
}

.paddingParaInputs{
  padding-top: 11.2px;
  padding-bottom: 11.2px;
}

.amarillo{
  color: #FFF200;
}

.b-button:hover{
  background-color: #FFF200 !important;
  color: #050027 !important;
}
</style>