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
import java.util.UUID;

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

    String jsessionID=null;
    int id_sessione=0;
    Cookie sessionCookie =null;
   // Cookie c=null;
    int timeSessionMinutes=25;

    public void init() {
        message = "Hello World!";
        DAO.registerDriver();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        out.println("Attenzione POST arrivata");
        String username = request.getParameter("email");

        String token = UUID.randomUUID().toString();

        // Salva il token di sessione nell'utente


        // Imposta il cookie di sessione
        response.addCookie(new Cookie("session_token", token));

        // Ridirige l'utente alla pagina principale

        /*PrintWriter out1 = response.getWriter();
        String userName = request.getParameter("email");
        String sessionID = request.getParameter("sessione");
        HttpSession s1 = request.getSession();
        jsessionID = s1.getId(); // estraggo il session ID
        System.out.println("JSessionID:" + jsessionID);
        System.out.println("sessionID ricevuto:" + sessionID);
        System.out.println("userName ricevuto:" + userName);
        if (userName != null) {
            s1.setAttribute("userName", userName); // salvo dei dati in sessione...
        }
        if (sessionID!=null && jsessionID.equals(sessionID)) {
            //System.out.println("sessione riconosciuta!");
            out.print("sessione riconosciuta!");
        } else {
            //System.out.println(jsessionID);
            out.print(jsessionID);
        }*/
        processRequest(request, response);
    }

    public void destroy() {
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");

        if(sessionCookie!=null)
        {
            System.out.println("================= L'id sessione è "+sessionCookie.getComment());
        }
        if(jsessionID==null)
        {
            System.out.println("================= la sessione utente non è attiva");
        }
        else
        {
            System.out.println("================= la sessione utente è attiva, ha ID:"+jsessionID);
        }
        request.setCharacterEncoding("UTF-8"); // per essere robusti rispetto a caratteri speciali (', etc)
        ServletContext ctx = getServletContext();
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        ArrayList<User> users = userDao.getAll();
        ArrayList<Teacher> teachers = teacherDao.getAll();
        ArrayList<Subject> subjects = subjectDao.getAll();
        ArrayList<SubjectTeacher> associations = subjectTeacherDao.getAll();
        ArrayList<Availability> availabilities = availabilityDao.getAll();
        ArrayList<Day> days = dayDao.getAll();
        ArrayList<Slot> slots = slotDao.getAll();
        HttpSession s;

        Gson gson = new Gson();

        if (action != null) {
            switch (action) {
                case "getSession":
                    PrintWriter out1 = response.getWriter();
                    String userName = request.getParameter("email");
                    String sessionID = request.getParameter("sessione");
                    HttpSession s1 = request.getSession();
                    jsessionID = s1.getId(); // estraggo il session ID
                    System.out.println("JSessionID:" + jsessionID);
                    System.out.println("sessionID ricevuto:" + sessionID);
                    System.out.println("userName ricevuto:" + userName);
                    if (userName != null) {
                        s1.setAttribute("userName", userName); // salvo dei dati in sessione...
                    }
                    if (sessionID!=null && jsessionID.equals(sessionID)) {
                        //System.out.println("sessione riconosciuta!");
                        out.print("sessione riconosciuta!");
                    } else {
                        //System.out.println(jsessionID);
                        out.print(jsessionID);
                    }
                    break;

                case "getAllDays":
                    System.out.println("Siamo su getAllDays");

                    String daysJson = gson.toJson(days);
                    System.out.println("STRINGA JSON " + daysJson);
                    out.print(daysJson);
                    out.flush();
                    break;
                case "getAllSlots":
                    System.out.println("Siamo su getAllSlots");

                    String slotsJson = gson.toJson(slots);
                    System.out.println("STRINGA JSON " + slotsJson);
                    out.print(slotsJson);
                    out.flush();
                    break;
                case "getAllAvailabilities":
                    System.out.println("Siamo su getAllAvailabilities");

                    String availabilitiesJson = gson.toJson(availabilities);
                    System.out.println("STRINGA JSON " + availabilitiesJson);
                    out.print(availabilitiesJson);
                    out.flush();
                    break;
                case "getAllUsers":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {

                        System.out.println("Siamo su getAllUsers");
                        String usersJson = gson.toJson(userDao.getAll());
                        System.out.println("STRINGA JSON " + usersJson);
                        out.print(usersJson);
                        out.flush();
                    }
                    else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        out.flush();
                    }
                    break;

                case "getAllTeacher":
                    System.out.println("Siamo su getAllTeacher");

                    String teachersJson = gson.toJson(teachers);
                    System.out.println("STRINGA JSON " + teachersJson);
                    out.print(teachersJson);
                    out.flush();
                    break;
                case "getAllSubjects":    //al client torna la pagina di login

                    System.out.println("Siamo su getAllSubjects");
                    String subjectsJson = gson.toJson(subjects);
                    System.out.println("STRINGA JSON " + subjectsJson);
                    out.print(subjectsJson);
                    out.flush();
                    break;

                case "getAllAssociations":    //al client torna la pagina di login
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0 )
                    {
                        System.out.println("Siamo su getAllAssociations");
                        String associationsJson = gson.toJson(associations);
                        System.out.println("STRINGA JSON " + associationsJson);
                        out.print(associationsJson);
                        out.flush();
                    }
                    else
                    {
                        String alertJson = gson.toJson("invalidSession");
                        out.print(alertJson);
                        out.flush();
                    }
                    break;

                case "submitRegistration":  //vera e propria registrazione di un utente
                    System.out.println("Siamo su submit Registration");
                    String signedJson = gson.toJson(submitRegistration(request.getParameter("name"), request.getParameter("surname"), request.getParameter("password"), request.getParameter("email"), request.getParameter("role")));
                    System.out.println("STRINGA JSON " + signedJson);
                    out.print(signedJson);
                    out.flush();
                    break;
                case "getTeachersBySubject":
                    //TODO:aggiunta controlli
                    System.out.println("Siamo su getTeachersBySubject");
                    String teachersBySubjectJson=gson.toJson(getTeachersBySubject(request.getParameter("subjectId")));
                    System.out.println("STRINGA JSON " + teachersBySubjectJson);
                    out.print(teachersBySubjectJson);
                    out.flush();
                    break;

                case "submitLogin":
                    //isLoggen-token-time
                    ArrayList<Object> customResponse=new ArrayList<>();
                    System.out.println("9999999999999999 siamo SU SUBMIT LOGIN");
                    int id =-1;
                    id = submitLogin(request.getParameter("email"), request.getParameter("password"), request.getParameter("role"));
                    String loggedJson=null;

                    //request.getSession().setAttribute("isLogged", true);

                    sessionCookie = new Cookie("JSESSIONID", UUID.randomUUID().toString());
                    sessionCookie.setComment(String.valueOf(id));
                    String token=sessionCookie.getValue();

                    System.out.println("?????????????? L'email risulta essere "+request.getParameter("email"));
                    System.out.println("?????????????? IL commento del cookie e "+sessionCookie.getComment());
                    System.out.println("?????????????? il token è "+token);
                    //response.setHeader("Set-Cookie", "JSESSIONID=" + sessionIdCookie.getValue());
                    sessionCookie.setMaxAge(36000);
                    response.addCookie(sessionCookie);


                    System.out.println(sessionCookie.getComment());
                    if(id!=-1){
                        sessionCookie.setComment(String.valueOf(id));
                        customResponse.add("isLogged");
                        customResponse.add(token);
                        customResponse.add(timeSessionMinutes);
                        loggedJson = gson.toJson(customResponse);

                    }else{
                        loggedJson = gson.toJson("notLogged");
                    }

                    System.out.println("STRINGA JSON " + loggedJson);
                    out.print(loggedJson);
                    out.flush();


                    break;
                case "getAllAvailabilitiesAvailable":
                    //TODO:aggiunta controlli

                    int userId1= parseInt(sessionCookie.getComment());
                    String userRole=userDao.getById(userId1).getRole();
                    System.out.println("Siamo get Alla Availabilities available");
                    ArrayList<Availability> availabilitiesAvailable = availabilityDao.getAllAvailabilityAvailable();
                    String availabilityAvailableJson = gson.toJson(availabilitiesAvailable);
                    System.out.println("STRINGA JSON " + availabilityAvailableJson);
                    out.print(availabilityAvailableJson);
                    out.flush();
                    break;


                case "bookingAvailability":
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(sessionCookie.getMaxAge());
                    System.out.println(sessionCookie.getValue());
                    System.out.println(request.getParameter("token"));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0)
                    {
                        System.out.println("booking!!!!!");
                        System.out.println("Sono in booking Availability");
                        int availabilityId = Integer.parseInt(request.getParameter("availabilityId"));
                        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                        Availability a = availabilityDao.getAvailabilityByID(availabilityId);

                        System.out.println(availabilityId);
                        System.out.println(subjectId);
                        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                        System.out.println(sessionCookie.getComment());
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
                            out.flush();
                        }else {
                            String bookedJson = gson.toJson("notBooked");
                            System.out.println("STRINGA JSON " + bookedJson);
                            out.print(bookedJson);
                            out.flush();
                        }
                    }
                    else
                        {
                            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
                            String userAvailabilitiesBookedJson = gson.toJson("invalidSession");
                            System.out.println("invalidSession");
                            out.print(userAvailabilitiesBookedJson);
                            out.flush();
                        }
                    break;
                case "getAvailabilitiesByProfessor":
                    //TODO:aggiunta controlli
                    System.out.println("Siamo in get AvailabilitiesBProfessor");
                    int teacherId= Integer.parseInt(request.getParameter("teacherId"));
                    ArrayList<Availability> availabilitiesByProfessor= availabilityDao.getByTeacherId(teacherId);
                    String availabilitiesByTeacherJson=gson.toJson(availabilitiesByProfessor);
                    System.out.println("STRINGA JSON " + availabilitiesByTeacherJson);
                    out.println(availabilitiesByTeacherJson);
                    out.flush();
                    break;

                case "getAvailabilitiesOfUser":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0)
                    {
                            if(sessionCookie.getComment()!=null ) {
                                System.out.println("getAvailabilitiesOfUser");
                                System.out.println(sessionCookie.getComment());
                                ArrayList<Availability> userAvailabilitiesBooked = availabilityDao.getUserBooking(parseInt(sessionCookie.getComment()));
                                String userAvailabilitiesBookedJson = gson.toJson(userAvailabilitiesBooked);
                                System.out.println("STRINGA JSON " + userAvailabilitiesBookedJson);
                                out.print(userAvailabilitiesBookedJson);
                                out.flush();
                                }
                    }
                    else
                    {
                        String userAvailabilitiesBookedJson = gson.toJson("invalidSession");
                        out.print(userAvailabilitiesBookedJson);
                        System.out.println("invalidSession");
                        out.flush();
                    }
                    break;

                case "getAvailabilitiesOfUserById":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0)
                    {
                    System.out.println("getAvailabilitiesOfUserById");
                    ArrayList<Availability> userAvailabilitiesBookedById = availabilityDao.getUserBooking(Integer.parseInt(request.getParameter("userId")));
                    String userAvailabilitiesBookedByIdJson = gson.toJson(userAvailabilitiesBookedById);
                    System.out.println("STRINGA JSON " + userAvailabilitiesBookedByIdJson);
                    out.print(userAvailabilitiesBookedByIdJson);
                    out.flush();
                    }
                    else
                    {
                        String userAvailabilitiesBookedJson = gson.toJson("invalidSession");
                        out.print(userAvailabilitiesBookedJson);
                        System.out.println("invalidSession");
                        out.flush();
                    }

                    break;


                case "getAvailabilitiesOfUserActive":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0)
                    {
                        if(sessionCookie.getComment()!=null) {
                            System.out.println("getAvailabilitiesOfUserActive");
                            System.out.println(sessionCookie.getComment());
                            ArrayList<Availability> userAvailabilitiesBooked = availabilityDao.getUserBookingActive(Integer.parseInt(sessionCookie.getComment()));
                            String userAvailabilitiesBookedJson = gson.toJson(userAvailabilitiesBooked);
                            System.out.println("STRINGA JSON " + userAvailabilitiesBookedJson);
                            out.print(userAvailabilitiesBookedJson);
                            out.flush();
                        }
                    }
                    else
                    {
                        String userAvailabilitiesBookedJson = gson.toJson("invalidSession");
                        out.print(userAvailabilitiesBookedJson);
                        System.out.println("invalidSession");
                        out.flush();
                    }
                    break;
                case "deleteAvailability":
                    /////////////////////////////////////////////////
                    /////////////////////////////////////////////////
                    //THIS METHOD IS CALLED BY USER IT DOESNT REMOVE AVAILABILITY FROM DB
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0 ) {
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
                            out.flush();
                        }else{
                            String bookedJson = gson.toJson("notBooked");
                            System.out.println("STRINGA JSON " + bookedJson);
                            out.print(bookedJson);
                            out.flush();
                        }
                    }
                    else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }
                    break;


                case "archiveAvailability":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0)
                    {
                        System.out.println("siamo su archieveAvailabilietes");
                        int availabilityId3 = parseInt(request.getParameter("availabilityId"));
                        Availability a3 = availabilityDao.getAvailabilityByID(availabilityId3);
                        //prendo l'id dalla session cookie

                       /* int userId3= parseInt(sessionCookie.getComment());*/

                        /*System.out.println(userId3);*/
                        a3.setBooking("effettuata");
                        /*a3.setUserId(userId3);*/
                        System.out.println("stampa1d");
                        availabilityDao.updateAvailability(a3);
                        System.out.println("stampa1d");
                        if(availabilityDao.updateAvailability(a3)!=0){
                            String bookedJson = gson.toJson("booked");
                            System.out.println("STRINGA JSON " + bookedJson);
                            out.print(bookedJson);
                            out.flush();
                        }else{
                            String bookedJson = gson.toJson("notBooked");
                            System.out.println("STRINGA JSON " + bookedJson);
                            out.print(bookedJson);
                            out.flush();
                        }
                    }
                    else
                    {
                        String userAvailabilitiesBookedJson = gson.toJson("invalidSession");
                        out.print(userAvailabilitiesBookedJson);
                        out.flush();
                    }
                    break;

                case "getAdmin":

                    User u1=userDao.getByEmail(request.getParameter("email"));
                   /* int userId4= parseInt(sessionCookie.getComment());*/
                    String answerJson= gson.toJson(u1.getRole());
                    System.out.println("STRINGA JSON " + answerJson);
                    out.print(answerJson);


                    break;
                case "addProf":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                        System.out.println("addprof----");
                        String addProfJson1 = gson.toJson(submitTeacher(request.getParameter("name"), request.getParameter("surname"), request.getParameter("email")));
                        System.out.println("STRINGA JSON " + addProfJson1);
                        out.print(addProfJson1);
                    }else
                        {
                            String alertJson = gson.toJson("notAdmin");
                            out.print(alertJson);
                            System.out.println("notAdmin");
                            out.flush();
                        }

                    break;
                case "addSub":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                    System.out.println("addsub----");
                    String addsubdJson1 = gson.toJson(submitSub(request.getParameter("name"), request.getParameter("descp")));
                    System.out.println("STRINGA JSON " + addsubdJson1);
                    out.print(addsubdJson1);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }
                    break;

                    /*cancellazione subject tramite nome e descrizione*/
                case "RemoveSubject":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                    System.out.println("removesub----");
                    String removesubdJson1 = gson.toJson(removeSub(request.getParameter("name"), request.getParameter("descp")));
                    System.out.println("STRINGA JSON " + removesubdJson1);
                    out.print(removesubdJson1);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }
                    break;

                   /* cancellazione subject tramite id */
                case "deleteSubject":
                    if(sessionCookie.getValue().equals(request.getParameter("token"))) {
                    System.out.println("delete subject ");
                    String removesubdJson2 = gson.toJson(removeSub(Integer.parseInt(request.getParameter("subjectId"))));
                    System.out.println("STRINGA JSON " + removesubdJson2);
                    out.print(removesubdJson2);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }
                    break;

                case "removeAssociation":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && sessionCookie.getMaxAge()>0) {
                    System.out.println("delete association ");
                    String removeAssociationJson = gson.toJson(removeAssociation(Integer.parseInt(request.getParameter("id"))));
                    System.out.println("STRINGA JSON " + removeAssociationJson);
                    out.print(removeAssociationJson);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }
                    break;


                case "addLesson":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                    System.out.println("addlesson----");
                    String addsubdJson4 = gson.toJson(submitLesson(request.getParameter("id_prof"),request.getParameter("day"),request.getParameter("time")));
                    System.out.println("STRINGA JSON " + addsubdJson4);
                    out.print(addsubdJson4);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }
                    break;

                case "addAssociation":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                        System.out.println("addAssociation----");
                        String addAssociationJson4 = gson.toJson(submitAssociation(request.getParameter("id_prof"), request.getParameter("id_sub")));
                        System.out.println("STRINGA JSON " + addAssociationJson4);
                        out.print(addAssociationJson4);
                    }else {
                            String alertJson = gson.toJson("notAdmin");
                            out.print(alertJson);
                            System.out.println("notAdmin");
                            out.flush();
                        }
                    break;


                case "deleteProf":
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                    System.out.println("delete----");
                    String deleteprofdJson1 = gson.toJson(removeTeacher(request.getParameter("name"), request.getParameter("surname"), request.getParameter("email")));
                    System.out.println("STRINGA JSON " + deleteprofdJson1);
                    out.print(deleteprofdJson1);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }

                    break;
                case "deleteTeacher":
                    /////////
                    //  *****************************************
                    //  *                                       *
                    //  * USE THIS FOR FRONT END IMPLEMENTATION *
                    //  *                                       *
                    //  *****************************************
                    if(sessionCookie.getValue().equals(request.getParameter("token")) && isAdmin(Integer.parseInt(sessionCookie.getComment()))) {
                    System.out.println("delete teacher");
                    removeTeacher(Integer.parseInt(request.getParameter("teacherId")));
                    String deleteprofdJson2= gson.toJson("Removed");

                    System.out.println("STRINGA JSON " + deleteprofdJson2);
                    out.print(deleteprofdJson2);
                    }else
                    {
                        String alertJson = gson.toJson("notAdmin");
                        out.print(alertJson);
                        System.out.println("notAdmin");
                        out.flush();
                    }

                    break;
                case "logout":
                    System.out.println("logout----");
                    Cookie sessionCookie = new Cookie("session_id", "");
                    sessionCookie.setMaxAge(0); // Imposta la durata del cookie a 0 per farlo scadere immediatamente
                    response.addCookie(sessionCookie);
                    response.sendRedirect("/");
                    break;
                default:
                    break;
            }

        }
    }

    private Object removeAssociation(int id) {
        int row=subjectTeacherDao.delete(id);
        if(row==0){
            return "notRemoved";
        }else{
            return "Removed";
        }
    }

    private Object submitAssociation(String idProf, String idSub) {
        out.println(idProf);
        out.println(idSub);
        SubjectTeacher association= new SubjectTeacher(Integer.parseInt(idProf),Integer.parseInt(idSub));
        int row=subjectTeacherDao.add(association);
        if(row==0){
            return "notAdded";
        }else{
            return "Added";
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
        ArrayList<Day> days=dayDao.getAll();
        int row = teacherDao.addAvailabilitiesToo(t,dayDao.getAll().size(),slotDao.getAll().size());
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
        SubjectTeacherDAOImpl sTi =new SubjectTeacherDAOImpl();
        AvailabilityDAOImpl aDi= new AvailabilityDAOImpl();
        System.out.println("le righe rimosse sono :"+aDi.deleteFromTeacher(id));
        System.out.println("le righe rimosse sono :"+sTi.deleteFromTeacher(id));

        int row = teacherDao.delete(t1.getTeacherId());
        if (row == 0) {
            return "notRemoved";
        } else {
            return "Removed";
        }
    }

    private boolean isAdmin(int id)
    {
        UserDAOImpl userDAO= new UserDAOImpl();
        User user=userDao.getById(id);
        if(user.getRole()=="admin")
            return true;
        return false;

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