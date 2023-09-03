import axios from "axios";
import $ from "jquery";
import Cookie from "vue-cookies";

export class availabilityService {


    static getAllAvailabilitiesAvailable() {
        /*const params = {
            action: 'getAllAvailabilitiesAvailable',
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';


        return $.get(dataURL, {action: 'getAllAvailabilitiesAvailable'});
    }

    static getAvailabilitiesByID() {
        console.log("EMAIL"+localStorage.getItem("email"))
        let g=Cookie.get(localStorage.getItem("email"));
        console.log('prova '+g)
        /*const params = {
            action: 'getAvailabilitiesOfUser',
            token: Cookie.get(localStorage.getItem("email"))
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL, {action: 'getAvailabilitiesOfUser',
            token: Cookie.get(localStorage.getItem("email"))});
    }

    static getAvailabilitiesByID2(userId) {
        const params = {
            action: 'getAvailabilitiesOfUserById',
            userId: userId,
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL, {params});
    }

    static getAvailabilitiesByIDActive() {
        /*const params = {
            action: 'getAvailabilitiesOfUserActive',
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL, {action: 'getAvailabilitiesOfUserActive',token:Cookie.get(localStorage.getItem("email"))})
    }

    static getAllAvailabilitiesByProfessor(teacherId) {
        /*const params = {
            action: 'getAvailabilitiesByProfessor',
            teacherId: teacherId,
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';

        return $.get(dataURL, {action: 'getAvailabilitiesByProfessor', teacherId: teacherId,})
    }

    static getAllAvailabilities() {
        const params = {
            action: 'getAllAvailabilities',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL, {params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}