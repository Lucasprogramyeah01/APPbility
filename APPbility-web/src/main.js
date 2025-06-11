//Bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// Vuetify
import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router.js';
import vuetify from './plugins/vuetify';

const app = createApp(App);

app.use(router);

app.use(vuetify);

app.mount('#app');
