import $ from "jquery";

export class dayService{
    static getAllDays() {
        /*const params = {
            action: 'getAllDays',
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';

        return $.get(dataURL,{action: 'getAllDays'});

    }
}