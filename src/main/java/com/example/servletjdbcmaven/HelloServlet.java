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

import static java.lang.System.out;

@WebServlet(name = "jdbcServlet", value = "/jdbc-servlet")
public class HelloServlet extends HttpServlet {
    HttpSession session;
    private static final DAO<Teacher> teacherDao = new TeacherDaoImpl();
    private static final DAO<Subject> subjectDao = new SubjectDaoImpl();
    private static final DAO<SubjectTeacher> subjectTeacherDao = new SubjectTeacherDAOImpl();
    private static final AvailabilityDAOImpl availabilityDao = new AvailabilityDAOImpl();
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
        ArrayList<User> users = userDao.getAll();
        ArrayList<Teacher> teachers = teacherDao.getAll();
        ArrayList<Subject> subjects = subjectDao.getAll();
        ArrayList<SubjectTeacher> associations = subjectTeacherDao.getAll();
        ArrayList<Availability> availabilities = availabilityDao.getAll();

        Gson gson = new Gson();

        if (action != null) {
            switch (action) {

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

                    System.out.println("Siamo get Alla Availabilities available");
                    ArrayList<Availability> availabilitiesAvailable = availabilityDao.getAllAvailabilityAvailable();
                    String availabilityAvailableJson = gson.toJson(availabilitiesAvailable);
                    System.out.println("STRINGA JSON " + availabilityAvailableJson);
                    out.print(availabilityAvailableJson);
                    break;


                case "bookingAvailability":

                    System.out.println("Sono in booking Availability");
                    int availabilityId = Integer.parseInt(request.getParameter("availabilityId"));
                    Availability a = availabilityDao.getAvailabilityByID(availabilityId);

                    //prendo l'id dalla session cookie
                    int userId= Integer.parseInt(sessionCookie.getComment());
                    System.out.println(userId);
                    a.setBooking("attiva");
                    a.setUserId(userId);
                    System.out.println("stampa1");
                    availabilityDao.updateAvailability(a);
                    System.out.println("stampa1");
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
                case "getAvailabilitiesOfUser":
                    if(sessionCookie.getComment()!=null) {
                        System.out.println("getAvailabilitiesOfUser");
                        System.out.println(sessionCookie.getComment());
                        ArrayList<Availability> userAvailabilitiesBooked = availabilityDao.getUserBooking(Integer.parseInt(sessionCookie.getComment()));
                        String userAvailabilitiesBookedJson = gson.toJson(userAvailabilitiesBooked);
                        System.out.println("STRINGA JSON " + userAvailabilitiesBookedJson);
                        out.print(userAvailabilitiesBookedJson);
                    }
                    break;
                case "logout":
                    System.out.println("logout----");
                    /*cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");*/

                    break;

                default:
            }

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