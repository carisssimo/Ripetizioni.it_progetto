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
    private static DAO<Teacher> teacherDao = new TeacherDaoImpl();
    private static DAO<Subject> subjectDao = new SubjectDaoImpl();
    private static DAO<SubjectTeacher> subjectTeacherDao = new SubjectTeacherDAOImpl();
    private static DAO<Availability> availabilityDao = new AvailabilityDAOImpl();
    private static UserDAOImpl userDao = new UserDAOImpl();

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

        String email = request.getParameter("email");
        HttpSession s = request.getSession();

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
                    ArrayList<User> users = userDao.getAll();
                    ArrayList<Teacher> teachers = teacherDao.getAll();
                    ArrayList<Subject> subjects = subjectDao.getAll();
                    ArrayList<SubjectTeacher> associations = subjectTeacherDao.getAll();
                    ArrayList<Availability> availabilities = availabilityDao.getAll();

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
                        for (Subject subs: subjects) {
                            out.println("<p>" + subs + "</p>");
                        }

                        out.println("<h2>ASSOCIAZIONE CORSO-DOCENTE</h2>");
                        for(SubjectTeacher ass: associations){
                            out.println("<p> " + ass + "</p>");
                        }

                        out.println("<h2>DISPONIBILITÀ</h2>");
                        for(Availability a: availabilities){
                            out.println("<p> " + a + "</p>");
                        }

                        out.println("<p>FINE!</p>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                    break;
                case "submitRegistration":  //vera e propria registrazione di un utente
                    submitRegistration(request.getParameter("name"),request.getParameter("surname"),request.getParameter("password"),request.getParameter("email"), request.getParameter("role"));
                    break;

                case "submitLogin":
                    submitLogin(request.getParameter("email"),request.getParameter("password"), request.getParameter("role"));
                    System.out.println(request.getParameter("email"));
                    s.setAttribute("email", email);

                    User client = userDao.getByEmail((String) s.getAttribute("email"));

                    s.setAttribute("userId", client.getUserID());
                    s.setAttribute("role", client.getRole());
                    break;

                case "pageFormBooking":  //al client torna la pagina di registrazione
                    rd = ctx.getRequestDispatcher("/formBooking.html");
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

    private void submitLogin( String userEmail,String userPassword, String userRole) {
        Service s= new Service();
        User u= userDao.getByEmail(userEmail);//userDao.getByEmail(userEmail);
        if(s.checkMD5(u.getPassword(),userPassword))
        {
            //LOGGED
            System.out.println("---------logged");
        }
        else
        {
            //NOT LOGGED
            System.out.println("---------not logged");
        }

        //userDao.add(newUser);
    }
}