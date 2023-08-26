import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router";
import VueCookies from 'vue-cookies';
import SmartTable from 'vuejs-smart-table';
import jQuery from 'jquery';

createApp(App).use(VueCookies).use(router).use(SmartTable).use(jQuery).mount('#app')
