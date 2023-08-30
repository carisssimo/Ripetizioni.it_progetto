//import axios from "axios";
import $ from 'jquery';

export class teacherService {


    static getAllTeachers() {
        /*const axiosConfig = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
            }
        };*/
        
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL,{action: 'getAllTeacher'});

        /*const params = {
            action: 'getAllTeacher',
        };
        fetch('http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet',{
            method:'POST',
           headers:{
               'Content-Type': 'application/json',
           },
            body: JSON.stringify(params),
        }).then(response => {
            if (!response.ok) {
                throw new Error('Errore nella richiesta HTTP: ' + response.status);
            }
            console.log("Request has status code:"+response.status)
            return response.json(); // Se la risposta è in formato JSON, converte i dati in un oggetto JavaScript
        })
            .then(data => {
                // Elabora i dati di risposta qui (se necessario)
                console.log('Risposta dal server:', data);
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
            });*/
    }
    static async getTeacherId(teacherName) {
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

    }

}