import axios from "axios";
export class userService{
    static getAllUsers(){
        const params = {
            action: 'getAllUsers',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }
}