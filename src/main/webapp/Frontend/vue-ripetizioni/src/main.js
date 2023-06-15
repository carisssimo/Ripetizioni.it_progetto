import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router";
import VueCookies from 'vue-cookies';
import SmartTable from 'vuejs-smart-table';

createApp(App).use(VueCookies).use(router).use(SmartTable).mount('#app')
