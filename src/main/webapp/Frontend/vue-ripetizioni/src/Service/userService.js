import axios from "axios";
import Cookie from "vue-cookies";
export class userService{
    static getAllUsers(){
        const params = {
            action: 'getAllUsers',
            token:Cookie.get(localStorage.getItem("email"))
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }
}