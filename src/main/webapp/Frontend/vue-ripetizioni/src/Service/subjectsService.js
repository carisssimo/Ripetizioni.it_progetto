import axios from "axios";
import $ from 'jquery';


export class subjectsService {



    static getAllSubjects() {
        const params = {
            action: 'getAllSubjects',
        };
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return axios.get(dataURL,{params}); /*libreria utilizzata per effettuare chiamate http async*/

    }

    static async getSubjectId(subjectName) {
        let response2 = await subjectsService.getAllSubjects();
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
      
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL,{action: 'getTeachersBySubject',
        subjectId: subjectSelectedId}); /*libreria utilizzata per effettuare chiamate http async*/

    }

}