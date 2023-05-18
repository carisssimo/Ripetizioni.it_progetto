package com.example.servletjdbcmaven;
import Crypt.Service;
import DAO.UserDao;
import DAO.User;
import DAO.DAO;
import DAO.Teacher;
import DAO.TeacherDao;
import DAO.Subject;
import DAO.SubjectDao;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.*;

@WebServlet(name = "jdbcServlet", value = "/jdbc-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private static DAO<User> userDao=new UserDao();
    private static DAO<Teacher> teacherDao=new TeacherDao();

    private static DAO<Subject> subjectDao=new SubjectDao();

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
        ArrayList<User> utenti = userDao.getAll();

        ArrayList<Teacher> teachers = teacherDao.getAll();

        ArrayList<Subject> subjects = subjectDao.getAll();

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
                case "listDB":    // torna la pagina di test del database


                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet NewServlet</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
                        for (User ut: utenti) {
                            out.println("<p>" + ut + "</p>");
                        }

                        for (Teacher t: teachers) {
                            out.println("<p>" + t + "</p>");
                        }

                        for (Subject s: subjects) {
                            out.println("<p>" + s + "</p>");
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