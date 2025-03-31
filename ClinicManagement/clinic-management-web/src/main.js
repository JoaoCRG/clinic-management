import Vue from 'vue';
import App from './App.vue';
import router from './router'; // Importa o Vue Router
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import '@mdi/font/css/materialdesignicons.css';


Vue.use(Vuetify);

const vuetify = new Vuetify();

new Vue({
  router,  // Adiciona o Vue Router
  vuetify, // Adiciona o Vuetify
  render: h => h(App)
}).$mount('#app');