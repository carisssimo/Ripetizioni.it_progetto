package com.example.servletjdbcmaven;

import Crypt.Service;
import DAO.*;
import com.google.gson.Gson;
import javax.servlet.http.Cookie;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;

@WebServlet(name = "jdbcServlet", value = "/jdbc-servlet")
public class HelloServlet extends HttpServlet {
    HttpSession session;
    private static final TeacherDaoImpl teacherDao = new TeacherDaoImpl();
    private static final SubjectDaoImpl subjectDao = new SubjectDaoImpl();
    private static final SubjectTeacherDAOImpl subjectTeacherDao = new SubjectTeacherDAOImpl();
    private static final AvailabilityDAOImpl availabilityDao = new AvailabilityDAOImpl();

    private static final DayDAOImpl dayDao = new DayDAOImpl();
    private static final SlotDAOImpl slotDao = new SlotDAOImpl();
    private static final UserDAOImpl userDao = new UserDAOImpl();
    private String message;
    Cookie sessionCookie = new Cookie("session_id", "123456789");


    public void init() {
        message = "Hello World!";
        DAO.registerDriver();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void destroy() {
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        sessionCookie.setMaxAge(60 * 30);
        sessionCookie.setPath("/");


        request.setCharacterEncoding("UTF-8"); // per essere robusti rispetto a caratteri speciali (', etc)
        ServletContext ctx = getServletContext();
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081"); // Sostituisci con l'URL del tuo frontend JavaScript
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods","GET,HEAD,DELETE,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers",
                "Access-Control-Allow-Headers,Origin,Accept,X-Request-With,Content-Type,Access-Control-Request-Methods,Access-Control-Request-Headers");
        ArrayList<User> users = userDao.getAll();
        ArrayList<Teacher> teachers = teacherDao.getAll();
        ArrayList<Subject> subjects = subjectDao.getAll();
        ArrayList<SubjectTeacher> associations = subjectTeacherDao.getAll();
        ArrayList<Availability> availabilities = availabilityDao.getAll();
        ArrayList<Day> days = dayDao.getAll();
        ArrayList<Slot> slots = slotDao.getAll();

        /*PrintWriter ou1 = response.getWriter();
        String userName = request.getParameter("utente");
        String sessionID = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String jsessionID = s.getId(); // estraggo il session ID
        System.out.println("JSessionID:" + jsessionID);
        System.out.println("sessionID ricevuto:" + sessionID);
        System.out.println("userName ricevuto:" + userName);

        if (userName != null) {
            s.setAttribute("userName", userName); // salvo dei dati in sessione...
        }
        if (sessionID!=null && jsessionID.equals(sessionID)) {
            //System.out.println("sessione riconosciuta!");
            out.print("sessione riconosciuta!");
        } else {
            //System.out.println(jsessionID);
            out.print(jsessionID);
        }*/

        Gson gson = new Gson();

        if (action != null) {
            switch (action) {
                case "addAvailability":
                    System.out.println("ADD-AVAILABILITY");
                    //Availability availability=new Availability();

                    break;
                case "getAllDays":
                    System.out.println("Siamo su getAllDays");

                    String daysJson = gson.toJson(days);
                    System.out.println("STRINGA JSON " + daysJson);
                    out.print(daysJson);
                    break;
                case "getAllSlots":
                    System.out.println("Siamo su getAllSlots");

                    String slotsJson = gson.toJson(slots);
                    System.out.println("STRINGA JSON " + slotsJson);
                    out.print(slotsJson);
                    break;
                case "getAllAvailabilities":
                    System.out.println("Siamo su getAllAvailabilities");

                    String availabilitiesJson = gson.toJson(availabilities);
                    System.out.println("STRINGA JSON " + availabilitiesJson);
                    out.print(availabilitiesJson);
                    break;
                case "getAllUsers":
                    System.out.println("Siamo su getAllUsers");

                    String usersJson = gson.toJson(userDao.getAll());
                    System.out.println("STRINGA JSON " + usersJson);
                    out.print(usersJson);
                    break;

                case "getAllTeacher":
                    System.out.println("Siamo su getAllTeacher");

                    String teachersJson = gson.toJson(teachers);
                    System.out.println("STRINGA JSON " + teachersJson);
                    out.print(teachersJson);
                    break;
                case "getAllSubjects":    //al client torna la pagina di login
                    System.out.println("Siamo su getAllSubjects");


                    // creo oggetto JSON con oggetto Coppia
                    String subjectsJson = gson.toJson(subjects);
                    System.out.println("STRINGA JSON " + subjectsJson);
                    out.print(subjectsJson);
                    break;

                case "submitRegistration":  //vera e propria registrazione di un utente
                    System.out.println("Siamo su submit Registration");
                    String signedJson = gson.toJson(submitRegistration(request.getParameter("name"), request.getParameter("surname"), request.getParameter("password"), request.getParameter("email"), request.getParameter("role")));
                    System.out.println("STRINGA JSON " + signedJson);
                    out.print(signedJson);
                    break;
                case "getTeachersBySubject":
                    System.out.println("Siamo su getTeachersBySubject");
                    String teachersBySubjectJson=gson.toJson(getTeachersBySubject(request.getParameter("subjectId")));
                    System.out.println("STRINGA JSON " + teachersBySubjectJson);
                    out.print(teachersBySubjectJson);
                    break;

                case "submitLogin":

                    System.out.println("Siamo su submit login");
                    //ritorna l'id dell'utente se il login è avvenuto con successo
                    int id = submitLogin(request.getParameter("email"), request.getParameter("password"), request.getParameter("role"));
                    String loggedJson;
                    if(id!=-1){
                        sessionCookie.setComment(String.valueOf(id));
                        loggedJson = gson.toJson("isLogged");
                    }else{
                         loggedJson = gson.toJson("notLogged");
                    }
                    response.addCookie(sessionCookie);
                    System.out.println("STRINGA JSON " + loggedJson);
                    out.print(loggedJson);

                    break;
                case "getAllAvailabilitiesAvailable":

                    int userId1= parseInt(sessionCookie.getComment());
                    String userRole=userDao.getById(userId1).getRole();
                    /*if(userRole.equals("Utente") || userRole.equals("admin")){*/

                        System.out.println("Siamo get Alla Availabilities available");
                        ArrayList<Availability> availabilitiesAvailable = availabilityDao.getAllAvailabilityAvailable();
                        String availabilityAvailableJson = gson.toJson(availabilitiesAvailable);
                        System.out.println("STRINGA JSON " + availabilityAvailableJson);
                        out.print(availabilityAvailableJson);
                    /*};*/
                    break;


                case "bookingAvailability":
                    System.out.println("booking!!!!!");
                    System.out.println("Sono in booking Availability");
                    int availabilityId = Integer.parseInt(request.getParameter("availabilityId"));
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    Availability a = availabilityDao.getAvailabilityByID(availabilityId);

                    System.out.println(availabilityId);
                    System.out.println(subjectId);

                    //prendo l'id dalla session cookie
                    int userId= parseInt(sessionCookie.getComment());


                    System.out.println(userId);
                    a.setBooking("attiva");
                    a.setUserId(userId);
                    a.setSubject(subjectId);
                    System.out.println("stampa1");
                    /*availabilityDao.updateAvailability(a);
                    System.out.println("stampa1");*/
                    if(availabilityDao.updateAvailability(a)!=0){
                        String bookedJson = gson.toJson("booked");
                        System.out.println("STRINGA JSON " + bookedJson);
                        out.print(bookedJson);
                    }else{
                        String bookedJson = gson.toJson("notBooked");
                        System.out.println("STRINGA JSON " + bookedJson);
                        out.print(bookedJson);
                    }
                    break;
                case "getAvailabilitiesByProfessor":
                    System.out.println("Siamo in get AvailabilitiesBProfessor");
                    int teacherId= Integer.parseInt(request.getParameter("teacherId"));
                    ArrayList<Availability> availabilitiesByProfessor= availabilityDao.getByTeacherId(teacherId);
                    String availabilitiesByTeacherJson=gson.toJson(availabilitiesByProfessor);
                    System.out.println("STRINGA JSON " + availabilitiesByTeacherJson);
                    out.println(availabilitiesByTeacherJson);
                    break;

                case "getAvailabilitiesOfUser":
                    if(sessionCookie.getComment()!=null) {
                        System.out.println("getAvailabilitiesOfUser");
                        System.out.println(sessionCookie.getComment());
                        ArrayList<Availability> userAvailabilitiesBooked = availabilityDao.getUserBooking(parseInt(sessionCookie.getComment()));
                        String userAvailabilitiesBookedJson = gson.toJson(userAvailabilitiesBooked);
                        System.out.println("STRINGA JSON " + userAvailabilitiesBookedJson);
                        out.print(userAvailabilitiesBookedJson);
                    }
                    break;

                case "getAvailabilitiesOfUserById":
                    System.out.println("getAvailabilitiesOfUserById");
                    ArrayList<Availability> userAvailabilitiesBookedById = availabilityDao.getUserBooking(Integer.parseInt(request.getParameter("userId")));
                    String userAvailabilitiesBookedByIdJson = gson.toJson(userAvailabilitiesBookedById);
                    System.out.println("STRINGA JSON " + userAvailabilitiesBookedByIdJson);
                    out.print(userAvailabilitiesBookedByIdJson);

                    break;

                case "removeAvailability":
                    /////////////////////////////////////////////////
                    /////////////////////////////////////////////////
                    // THIS METHOD REMOVE DISPONIBILITA FROM DB
                    //TODO: DA MODIFIFICARE LA RICERCA TRAMITE DAYTIME
                    System.out.println("Removelesson----");/*
                    String remsubdJson = gson.toJson(removeLesson(request.getParameter("id_prof"), request.getParameter("id_sub"),request.getParameter("dateAv")));
                    System.out.println("STRINGA JSON " + remsubdJson);
                    out.print(remsubdJson);*/
                    break;

                case "getAvailabilitiesOfUserActive":
                    if(sessionCookie.getComment()!=null) {
                        System.out.println("getAvailabilitiesOfUserActive");
                        System.out.println(sessionCookie.getComment());
                        ArrayList<Availability> userAvailabilitiesBooked = availabilityDao.getUserBookingActive(Integer.parseInt(sessionCookie.getComment()));
                        String userAvailabilitiesBookedJson = gson.toJson(userAvailabilitiesBooked);
                        System.out.println("STRINGA JSON " + userAvailabilitiesBookedJson);
                        out.print(userAvailabilitiesBookedJson);
                    }
                    break;
                case "deleteAvailability":
                    /////////////////////////////////////////////////
                    /////////////////////////////////////////////////
                    //THIS METHOD IS CALLED BY USER IT DOESNT REMOVE AVAILABILITY FROM DB
                    System.out.println("DELETE");

                    int availabilityId2 = parseInt(request.getParameter("availabilityId"));
                    Availability a2 = availabilityDao.getAvailabilityByID(availabilityId2);
                    //prendo l'id dalla session cookie

                     int userId2= parseInt(sessionCookie.getComment());

                    System.out.println(userId2);
                    a2.setBooking("disdetta");
                    a2.setUserId(userId2);
                    System.out.println("stampa1d");
                    if(availabilityDao.updateAvailability(a2)!=0){
                        String bookedJson = gson.toJson("booked");
                        System.out.println("STRINGA JSON " + bookedJson);
                        out.print(bookedJson);
                    }else{
                        String bookedJson = gson.toJson("notBooked");
                        System.out.println("STRINGA JSON " + bookedJson);
                        out.print(bookedJson);
                    }
                    break;


                case "archiveAvailability":
                    int availabilityId3 = parseInt(request.getParameter("availabilityId"));
                    Availability a3 = availabilityDao.getAvailabilityByID(availabilityId3);
                    //prendo l'id dalla session cookie

                    int userId3= parseInt(sessionCookie.getComment());

                    System.out.println(userId3);
                    a3.setBooking("effettuata");
                    a3.setUserId(userId3);
                    System.out.println("stampa1d");
                    availabilityDao.updateAvailability(a3);
                    System.out.println("stampa1d");
                    if(availabilityDao.updateAvailability(a3)!=0){
                        String bookedJson = gson.toJson("booked");
                        System.out.println("STRINGA JSON " + bookedJson);
                        out.print(bookedJson);
                    }else{
                        String bookedJson = gson.toJson("notBooked");
                        System.out.println("STRINGA JSON " + bookedJson);
                        out.print(bookedJson);
                    }
                    break;

                case "getAdmin":

                    User u1=userDao.getByEmail(request.getParameter("email"));
                    int userId4= parseInt(sessionCookie.getComment());
                    String answerJson= gson.toJson(u1.getRole());
                    System.out.println("STRINGA JSON " + answerJson);
                    out.print(answerJson);


                    break;
                case "addProf":
                    System.out.println("addprof----");
                    String addprofdJson1 = gson.toJson(submitTeacher(request.getParameter("name"), request.getParameter("surname"), request.getParameter("email")));
                    System.out.println("STRINGA JSON " + addprofdJson1);
                    out.print(addprofdJson1);

                    break;
                case "addSub":
                    System.out.println("addsub----");
                    String addsubdJson1 = gson.toJson(submitSub(request.getParameter("name"), request.getParameter("descp")));
                    System.out.println("STRINGA JSON " + addsubdJson1);
                    out.print(addsubdJson1);
                    break;

                    /*cancellazione subject tramite nome e descrizione*/
                case "RemoveSubject":
                    System.out.println("removesub----");
                    String removesubdJson1 = gson.toJson(removeSub(request.getParameter("name"), request.getParameter("descp")));
                    System.out.println("STRINGA JSON " + removesubdJson1);
                    out.print(removesubdJson1);
                    break;

                   /* cancellazione subject tramite id */
                case "deleteSubject":
                    System.out.println("delete subject ");
                    String removesubdJson2 = gson.toJson(removeSub(Integer.parseInt(request.getParameter("subjectId"))));
                    System.out.println("STRINGA JSON " + removesubdJson2);
                    out.print(removesubdJson2);
                    break;


                case "addLesson":
                    System.out.println("addlesson----");
                    String addsubdJson4 = gson.toJson(submitLesson(request.getParameter("id_prof"),request.getParameter("day"),request.getParameter("time")));
                    System.out.println("STRINGA JSON " + addsubdJson4);
                    out.print(addsubdJson4);
                    break;

                case "deleteProf":
                    System.out.println("delete----");
                    String deleteprofdJson1 = gson.toJson(removeTeacher(request.getParameter("name"), request.getParameter("surname"), request.getParameter("email")));
                    System.out.println("STRINGA JSON " + deleteprofdJson1);
                    out.print(deleteprofdJson1);

                    break;
                case "deleteTeacher":
                    System.out.println("delete teacher");
                    String deleteprofdJson2= gson.toJson(removeTeacher(Integer.parseInt(request.getParameter("teacherId"))));

                    System.out.println("STRINGA JSON " + deleteprofdJson2);
                    out.print(deleteprofdJson2);

                    break;
                case "logout":
                    System.out.println("logout----");
                    Cookie sessionCookie = new Cookie("session_id", "");
                    sessionCookie.setMaxAge(0); // Imposta la durata del cookie a 0 per farlo scadere immediatamente
                    response.addCookie(sessionCookie);
                    response.sendRedirect("/");
                    break;




                default:
            }

        }
    }

    private ArrayList<SubjectTeacher> getTeachersBySubject(String subjectId) {
        System.out.println(subjectId);
        ArrayList<SubjectTeacher> teacherBySubject=subjectTeacherDao.getAllTeachersBySubjectId(Integer.parseInt(subjectId));
        return teacherBySubject;
    }

    //quando aggiunge una nuova availability(qui chiamata lesson?)non serve la meteria poichè e materie sono collegate al professore
    private Object submitLesson(String idProf, String idDay,String idSlot) {
        Availability a1 = new Availability(parseInt(idProf),parseInt(idDay),parseInt(idSlot));
        int row= availabilityDao.add(a1);
        if (row == 0) {
            return "notAdded";
        } else {
            return "Added";
        }
    }
    private String removeLesson(String idProf, String idSub, String idDay,String idSlot) {
        out.println(idProf+" "+idSub+" "+idDay+" "+ idSlot);
        Availability a1=availabilityDao.getByTeacherSubjectUser(parseInt(idProf),parseInt(idSub),parseInt(idDay),parseInt(idSlot));
        int row= availabilityDao.delete(a1.getAvailabilityID());
        if (row == 0) {
            return "notAdded";
        } else {
            return "Removed";
        }
    }

    private String submitRegistration(String userName, String userSurname, String userPassword, String userEmail, String userRole) {
        Service s = new Service();
        String crptpassw = Service.encryptMD5(userPassword);
        User newUser = new User(userName, userSurname, userEmail, crptpassw, userRole);
        int row = userDao.add(newUser);
        if (row == 0) {
            return "isNotSigned";
        } else {
            return "isSigned";
        }
    }
    private String submitTeacher(String name, String surname,String email)
    {
        Teacher t=new Teacher(name,surname,email);
        int row = teacherDao.add(t);
        if (row == 0) {
            return "notAdded";
        } else {
            return "Added";
        }
    }
    private String submitSub(String name, String desc)
    {
        Subject s1= new Subject(name,desc);
        int row=subjectDao.add(s1);
        if (row == 0) {
            return "notAdded";
        } else {
            return "Added";
        }
    }
    private String removeSub(String name, String desc)
    {
        Subject s1= new Subject(name,desc);
        SubjectDaoImpl sd=new SubjectDaoImpl();
        s1=sd.getByName(s1.getSubjectName());
        System.out.println("--_-----RemoveSUB"+s1.getSubjectID());
        int row=sd.delete(s1.getSubjectID());
        if (row == 0) {
            return "notAdded";
        } else {
            return "Added";
        }
    }

    private String removeSub(int id)
    {
        Subject s1= subjectDao.getById(id);
        SubjectDaoImpl sd=new SubjectDaoImpl();
        s1=sd.getByName(s1.getSubjectName());
        System.out.println("--_-----RemoveSUB"+s1.getSubjectID());
        int row=sd.delete(s1.getSubjectID());
        if (row == 0) {
            return "notAdded";
        } else {
            return "Added";
        }
    }

    private String removeTeacher(String name, String surname,String email)
    {
        Teacher t1=new Teacher(name,surname,email);
        TeacherDaoImpl td= new TeacherDaoImpl();
        int row = teacherDao.delete(td.getByEmail(t1.getEmail()).getTeacherId());
        if (row == 0) {
            return "notRemoved";
        } else {
            return "Removed";
        }
    }
    private String removeTeacher(int id)
    {
        Teacher t1=teacherDao.getById(id);

        int row = teacherDao.delete(t1.getTeacherId());
        if (row == 0) {
            return "notRemoved";
        } else {
            return "Removed";
        }
    }

    private int submitLogin(String userEmail, String userPassword, String userRole) {
        Service s = new Service();
        User u = userDao.getByEmail(userEmail);
        if (Service.checkMD5(u.getPassword(), userPassword)) {
            //LOGGED
            out.println("---------logged");
            return u.getUserID();
        } else {
            //NOT LOGGED
            out.println("---------not logged");
            return -1;
        }
    }

}