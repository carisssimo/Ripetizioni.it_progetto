import axios from "axios";
import {teacherService} from "@/Service/teachersService";

export class subjectsService {



    static getAllSubjects() {
        const params = {
            action: 'getAllSubjects',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

    static async getSubjectsId(subjectName) {


        let response2 = await teacherService.getAllTeachers();
        let subjects = response2.data;
        for (let i = 0; i < subjects.length; i++) {
            if (subjects[i].subjectName === subjectName) {
                console.log(subjects[i]);
                return subjects[i].subjectID;
            }
        }
        return null;

    }
    static getTeachersBySubject(subjectSelectedId){
        const params = {
            action: 'getTeachersBySubject',
            subjectId: subjectSelectedId,
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}