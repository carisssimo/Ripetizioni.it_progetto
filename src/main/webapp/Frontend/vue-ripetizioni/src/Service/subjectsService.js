import axios from "axios";

export class subjectsService {



    static getAllSubjects() {
        const params = {
            action: 'getAllSubjects',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}