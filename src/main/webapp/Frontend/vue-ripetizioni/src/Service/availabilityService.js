import axios from "axios";
import $ from "jquery";
export class availabilityService {


    static getAllAvailabilitiesAvailable() {
        /*const params = {
            action: 'getAllAvailabilitiesAvailable',
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';


        return $.get(dataURL,{action: 'getAllAvailabilitiesAvailable'});
    }
    static getAvailabilitiesByID()
    {
        const params = {
            action: 'getAvailabilitiesOfUser',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params});
    }
    static getAvailabilitiesByID2(userId)
    {
        const params = {
            action: 'getAvailabilitiesOfUserById',
            userId:userId,
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params});
    }
    static getAvailabilitiesByIDActive()
    {
        /*const params = {
            action: 'getAvailabilitiesOfUserActive',
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL,{action: 'getAvailabilitiesOfUserActive'})
    }

    static getAllAvailabilitiesByProfessor(teacherId)
    {
        /*const params = {
            action: 'getAvailabilitiesByProfessor',
            teacherId: teacherId,
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';

        return $.get(dataURL,{action: 'getAvailabilitiesByProfessor', teacherId: teacherId,})
    }
    static getAllAvailabilities() {
        const params = {
            action: 'getAllAvailabilities',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}