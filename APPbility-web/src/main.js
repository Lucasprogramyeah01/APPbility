import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import BootstrapVue3 from 'bootstrap-vue-3'
import Toast from "vue-toastification";

//BOOTSTRAP
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'

//BOOTSTRAP VUE 3
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

//TOAST (VUE-TOASTIFICATION)
import "vue-toastification/dist/index.css";
    //Configuración de estilos personalizada para los toasts.
    const options = {
        position: "top-right",
        transition: "Vue-Toastification__bounce",
        timeout: 4500,
        closeOnClick: true,
        draggable: true,
        draggablePercent: 0.6,
        pauseOnFocusLoss: true,
        pauseOnHover: true,
        closeButton: "button",
        showCloseButtonOnHover: false,
        hideProgressBar: false,
        maxToasts: 5,
        newestOnTop: true,
        icon: true,
        rtl: false
    };

const app = createApp(App)

app.use(router)
app.use(BootstrapVue3)
app.use(Toast, options)

app.mount('#app')
