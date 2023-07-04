import axios from "axios";

export class availabilityService {


    static getAllAvailabilitiesAvailable() {
        const params = {
            action: 'getAllAvailabilitiesAvailable',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }
    static getAvailabilitiesByID()
    {
        const params = {
            action: 'getAvailabilitiesOfUser',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params});
    }
    static getAvailabilitiesByIDActive()
    {
        const params = {
            action: 'getAvailabilitiesOfUserActive',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params});
    }

}