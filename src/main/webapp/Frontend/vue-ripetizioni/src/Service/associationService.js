import $ from 'jquery';
import Cookie from "vue-cookies";

export class associationService {


    static getAllAssociations() {


        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL,{action: 'getAllAssociations',token: Cookie.get(localStorage.getItem("email"))});


    }
    /*static async getTeacherId(teacherName) {
        let response2 = await teacherService.getAllTeachers();
        let teachers = response2;
        console.log(teachers)
        for (let i = 0; i < teachers.length; i++) {
            if (teachers[i].name === teacherName) {
                console.log(teachers[i]);
                return teachers[i].teacherId;
            }
        }
        return null;

    }*/

}