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


       /* Availability a1=new Availability(1,1,1,"Martedi 18:00 - 19:00","attiva");
        availabilityDao.add(a1);
        a1.setBooking("disdetta");
        availabilityDao.updateAvailability(a1);*/
        //HttpSession s=null;
        //System.out.println((String)s.getAttribute("email")+"333333333333333333333333333333333333333333333333");






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
                /*case "pageSign-in":  //al client torna la pagina di registrazione
                    rd = ctx.getRequestDispatcher("/sign-in.html");
                    break;
                case "pageLogin":    //al client torna la pagina di login
                    rd = ctx.getRequestDispatcher("/login.html");
                    break;*/
                case "getAllTeacher":    //al client torna la pagina di login
                    System.out.println("Siamo su getAllTeacher");


                    // creo oggetto JSON con oggetto Coppia
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
                    String signedJson = gson.toJson(submitRegistration(request.getParameter("name"), request.getParameter("surname"), request.getParameter("password"), request.getParameter("email"), request.getParameter("role")));
                    System.out.println("STRINGA JSON " + signedJson);
                    out.print(signedJson);
                    break;

                case "submitLogin":

                    String result = submitLogin(request.getParameter("email"), request.getParameter("password"), request.getParameter("role"));

                    String email = request.getParameter("email");

                    //session.setAttribute("email", email);
                    sessionCookie.setComment(email);
                    response.addCookie(sessionCookie);

                    //session = request.getSession(false);
                    /*synchronized (s)
                    //{
                        s.setAttribute("email", email);

                        User client = userDao.getByEmail((String) s.getAttribute("email"));

                        s.setAttribute("userId", client.getUserID());
                        s.setAttribute("name", client.getName());
                        s.setAttribute("role", client.getRole());
                        System.out.println(s.getAttribute("userId")+"Sessione uteneeeeeeeeeeeeeeeeeeeeeeeeee");
                    //}*/
                    // System.out.println(session.getId());
                    //String email2 = (String) session.getAttribute("email");
                     //System.out.println(email2+" AAAAAAAAAAA    AAAAAAAA    3333333333333333333333333");
                   // User client = userDao.getByEmail(email2);

                   /* if (client != null && session!=null) {
                        session.setAttribute("userId", client.getUserID());
                        session.setAttribute("name", client.getName());
                        session.setAttribute("role", client.getRole());
                        System.out.println("uuuuuuuuu43h52u35h2u5hu23h5u23h5u2h3u5hn23uh52u3");
                    } else {
                        System.out.println("Impossibile trovare l'utente corrispondente all'email specificata");
                    }*/

                    // creo oggetto JSON con oggetto Coppia
                    String loggedJson = gson.toJson(result);
                    System.out.println("STRINGA JSON " + loggedJson);
                    out.print(loggedJson);

                    break;
                case "getAllAvailabilitiesAvailable":


                    ArrayList<Availability> availabilitiesAvailable = availabilityDao.getAllAvailabilityAvailable();
                    String availabilityAvailableJson = gson.toJson(availabilitiesAvailable);
                    System.out.println("STRINGA JSON " + availabilityAvailableJson);
                    out.print(availabilityAvailableJson);
                    break;

                /*case "pageFormBooking":  //al client torna la pagina di registrazione
                    rd = ctx.getRequestDispatcher("/formBooking.html");
                    break;*/
                case "bookingAvailability":

                    System.out.println(session.getAttribute("userId") + " Sessione utente!!!!!!!!!!!!!!!!!!!!!!!");
                    int availabilityId = Integer.parseInt(request.getParameter("availabilityId"));
                    int sessionUserId = (int) session.getAttribute("userId");
                    Availability a = availabilityDao.getAvailabilityByID(availabilityId);
                    a.setUserId(sessionUserId);
                    a.setBooking("attiva");
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
                    System.out.println("getAvailabilitiesOfUser");
                    if(session!=null)
                    System.out.println("shrek");

                    System.out.println(sessionCookie.getComment());

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

    private String submitLogin(String userEmail, String userPassword, String userRole) {
        Service s = new Service();
        User u = userDao.getByEmail(userEmail);//userDao.getByEmail(userEmail);
        out.println(u);
        if (Service.checkMD5(u.getPassword(), userPassword)) {
            //LOGGED
            out.println("---------logged");
            return "isLogged";
        } else {
            //NOT LOGGED
            out.println("---------not logged");
            return "isNotLogged";
        }
    }
}