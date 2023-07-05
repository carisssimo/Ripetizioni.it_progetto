import axios from "axios";

export class teacherService {

    static teachers = []

    static getAllTeachers() {
        let axiosConfig = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
            }
        };
        const params = {
            action: 'getAllTeacher',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params},axiosConfig); /*libreria utilizzata per effettuare chiamate http async*/

    }

}