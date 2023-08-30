
import $ from 'jquery';

export class slotService{
    static getAllSlots() {
       /* const params = {
            action: 'getAllSlots',
        };*/
        let dataURL = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
        return $.get(dataURL,{action: 'getAllSlots'});
    }
}