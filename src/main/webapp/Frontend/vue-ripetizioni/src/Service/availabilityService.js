import axios from "axios";

export class availabilityService {


    static getAllAvailabilitiesAvailable() {
        const params = {
            action: 'getAllAvailabilitiesAvailable',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}