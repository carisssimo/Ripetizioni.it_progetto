package com.example.servletjdbcmaven;

import Crypt.Service;
import DAO.*;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "jdbcServlet", value = "/jdbc-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private static DAO<User> userDao = new UserDao();
    private static DAO<Teacher> teacherDao = new TeacherDao();

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
        response.setContentType("text/html;charset=UTF-8");

        request.setCharacterEncoding("UTF-8"); // per essere robusti rispetto a caratteri speciali (', etc)
        ServletContext ctx = getServletContext();
        String action = request.getParameter("action");
        System.out.printf(action);
        RequestDispatcher rd = ctx.getRequestDispatcher("/index.html");
        if (action!=null) {
            switch (action) {
                case "pageSign-in":  //al client torna la pagina di registrazione
                    rd = ctx.getRequestDispatcher("/sign-in.html");
                    break;

                case "pageLogin":    //al client torna la pagina di login
                    rd = ctx.getRequestDispatcher("/login.html");
                    break;
                case "listDB":
                    //ArrayList<User> users = userDao.getAll();
                    //ArrayList<Teacher> teachers = teacherDao.getAll();

                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet NewServlet</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");

                        //aggiunta di un utente
                        //User us = new User("ProvaNome", "ProvaCognome", "ProvaEmail@gmail.com", "ProvaPW", "Utente");
                        //System.out.println("res add: " + userDao.add(us));

                        ArrayList<User> users = userDao.getAll();

                        out.println("<h2>UTENTI:</h2>");
                        for (User u: users) {
                            out.println("<p>" + u + "</p>");
                        }
/*
                        out.println("<h3> TEST UTENTI </h3>");
                        //get di un utente passando la mail
                        User resGet = userDao.get("lapolapo@gmail.com");
                        out.println("<p> " + resGet + "</p>");

                        //update dei dati di un utente
                        User resGet = userDao.get("lapolapo@gmail.com");
                        out.println("<p> " + resGet + "</p>");
                        System.out.println("res update: " + userDao.update(resGet, "Lapo", "Elkann", "lapoelkann@modificato.it", "cocaina", "Utente"));
                        out.println("<p>Dopo update: " + resGet + "</p>");

                        //delete di un utente
                        out.println("res delete: " + userDao.delete(4));

                        //restituzione di righe che soddisfano le condizioni passate come parametro
                        ArrayList<User> usersTestParameters = userDao.getByParameters("Amministratore");
                        for (User u: usersTestParameters) {
                            out.println("<p>" + u + "</p>");
                        }
*/
                        //aggiunta di un docente
                        //Teacher tc = new Teacher("ProvaNome", "ProvaCognome", "ProvaEmail@gmail.com");
                        //System.out.println("res add: " + teacherDao.add(tc));

                        ArrayList<Teacher> teachers = teacherDao.getAll();

                        out.println("<h2>DOCENTI:</h2>");
                        for (Teacher t: teachers) {
                            out.println("<p>" + t + "</p>");
                        }

                        out.println("<h3> TEST DOCENTI </h3>");
                        //get di un docente passando la mail
                        Teacher resGet = teacherDao.get("giusesimo@gmail.com");
                        out.println("<p> " + resGet + "</p>");

                        //aggiunta availability da parte di admin
                        //Availability av = new Availability(3, 4, -1, "Giovedì 14:00 - 15:00", true, null);
                        //availabilityDAO.add(av);

                        //prenotazione di un utente
                        //Availability av = new Availability(1, 1, 1, "prova prova modifica", false, "attiva");
                        //availabilityDAO.update(1, av);

                        //cancellazione di una prenotazione da parte di un utente


                        //cancellazione da parte di admin
                        //availabilityDAO.delete(1);


                        out.println("<p>FINE!</p>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                    break;
                case "submitRegistration":  //vera e propria registrazione di un utente

                    submitRegistration(request.getParameter("name"),request.getParameter("surname"),request.getParameter("password"),request.getParameter("email"), request.getParameter("role"));
                    break;

                case "submitLogin":
                   // submitLogin(request.getParameter(""),request.getParameter("surname"),request.getParameter("password"),request.getParameter("email"), request.getParameter("role"));
                    //submitLogin( String userPassword, String userEmail, String userRole)
                    break;
                default:
            }
            rd.forward(request, response);
        }
    }

    private void submitRegistration(String userName, String userSurname, String userPassword, String userEmail, String userRole) {
        Service s= new Service();
        String crptpassw=s.encryptMD5(userPassword);
        User newUser=new User(userName, userSurname, userEmail, crptpassw, userRole);
        userDao.add(newUser);
    }

    private void submitLogin( String userPassword, String userEmail, String userRole) {

        //userDao.add(newUser);
    }
}