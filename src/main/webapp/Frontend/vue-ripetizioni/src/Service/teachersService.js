import axios from "axios";

export class teacherService {

    static teachers = []

    static getAllTeachers() {
        const params = {
            action: 'getAllTeacher',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}