package com.example.servletjdbcmaven;

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
    private static DAO<User> userDao=new UserDao();
    private static DAO<Teacher> teacherDao=new TeacherDao();
    private static DAO<Subject> subjectDao=new SubjectDao();
    private static DAO<Availability> availabilityDAO= new AvailabilityDAO();
    private static DAO<SubjectTeacher> subjectTeacherDAO= new SubjectTeacherDAO();

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
                case "pageSign-in":
                    rd = ctx.getRequestDispatcher("/sign-in.html");
                    break;

                case "pageLogin":
                    rd = ctx.getRequestDispatcher("/login.html");
                    break;
                case "listDB":
                    ArrayList<User> users = userDao.getAll();
                    ArrayList<Teacher> teachers = teacherDao.getAll();
                    ArrayList<Subject> subjects = subjectDao.getAll();
                    //ArrayList<Availability> availabilities = availabilityDAO.getAll();
                    ArrayList<SubjectTeacher> associations = subjectTeacherDAO.getAll();

                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet NewServlet</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
                        out.println("<h2>UTENTI:</h2>");
                        for (User u: users) {
                            out.println("<p>" + u + "</p>");
                        }

                        out.println("<h2>DOCENTI:</h2>");
                        for (Teacher t: teachers) {
                            out.println("<p>" + t + "</p>");
                        }

                        out.println("<h2>CORSI:</h2>");
                        for (Subject s: subjects) {
                            out.println("<p>" + s + "</p>");
                        }

                        out.println("<h2>ASSOCIAZIONE CORSO-DOCENTE</h2>");
                        for(SubjectTeacher ass: associations){
                            out.println("<p> " + ass + "</p>");
                        }

                        //aggiunta availability da parte di admin
                        //Availability av = new Availability(3, 4, -1, "Giovedì 14:00 - 15:00", true, null);
                        //availabilityDAO.add(av);

                        //prenotazione di un utente
                        //Availability av = new Availability(1, 1, 1, "prova prova modifica", false, "attiva");
                        //availabilityDAO.update(1, av);

                        //cancellazione di una prenotazione da parte di un utente


                        //cancellazione da parte di admin
                        //availabilityDAO.delete(1);

                        ArrayList<Availability> availabilities = availabilityDAO.getAll();
                        out.println("<h2>DISPONIBILITÀ</h2>");
                        for(Availability a: availabilities){
                            out.println("<p> " + a + "</p>");
                        }

                        out.println("<p>FINE!</p>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                    break;
                case "submitRegistration":
                    String userName =request.getParameter("name");
                    System.out.println(userName);
                    String userSurname=request.getParameter("surname");
                    String userPassword=request.getParameter("password");
                    String userEmail=request.getParameter("email");
                    String userRole= request.getParameter("role");
                    User newUser=new User(userName,userSurname,userPassword,userEmail,userRole);
                    userDao.add(newUser);
                    break;
                default:
            }
            rd.forward(request, response);
        }
    }
}