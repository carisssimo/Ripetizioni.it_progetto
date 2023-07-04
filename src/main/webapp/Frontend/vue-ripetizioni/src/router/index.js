import  {createRouter, createWebHistory} from 'vue-router';
import Home from "@/components/Home.vue";
import Login from "@/components/Login.vue";
import Signin from "@/components/Signin.vue";
import Booking from "@/components/Booking.vue";
import PersonalPage from "@/components/PersonalPage.vue";
import SmartBookig from "@/components/SmartBookig.vue";
import UpdateBooking from "@/components/UpdateBooking.vue";
import AdminPage from "@/components/AdminPage.vue";
import AddProf from "@/components/AddProf.vue";
import DeleteProf from "@/components/DeleteProf.vue";
import AddSubject from "@/components/AddSubject.vue";


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
        path: '/update',
        name: 'Update',
        component: UpdateBooking
    },
    {
        path: '/signin',
        name: 'Signin',
        component: Signin
    },
    {
        path: '/booking',
        name: 'Booking',
        component: Booking
    },
    {
        path: '/smartBooking',
        name: 'SmartBooking',
        component: SmartBookig
    },
    {
        path:'/PersonalPage',
        name: 'PersonalPage',
        component: PersonalPage
    },
    {
        path:'/Admin',
        name: 'Admin',
        component: AdminPage
    },
    {
        path:'/AddProf',
        name: 'addProf',
        component: AddProf
    },
    {
        path:'/DelProf',
        name: 'delProf',
        component: DeleteProf
    },
    {
        path:'/Addsub',
        name: 'AddSubject',
        component: AddSubject
    }


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