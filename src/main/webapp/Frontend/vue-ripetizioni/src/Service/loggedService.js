import axios from "axios";

export class loggedService {



    static getIsLogged(email,password,role) {
        const params = {
            action: 'submitLogin',
            email:email,
            password:password,
            role:role
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}