import  {createRouter, createWebHistory} from 'vue-router';
import Home from "@/components/Home.vue";
import Login from "@/components/Login.vue";
import Signin from "@/components/Signin.vue";


const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },

    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/signin',
        name: 'Signin',
        component: Signin
    },
    /*{
        path: '/users/:userId',
        props: true,
        name: 'UsersDetails',
        component: UserDetails
    },*/
];

const router=createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router;