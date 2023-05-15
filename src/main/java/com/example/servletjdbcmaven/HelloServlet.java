package com.example.servletjdbcmaven;

import DAO.UserDao;
import DAO.User;
import DAO.DAO;
import DAO.Teacher;
import DAO.TeacherDao;
import DAO.Subject;
import DAO.SubjectDao;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }
    public void destroy() {
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<User> utenti = userDao.getAll();

        ArrayList<Teacher> teachers = teacherDao.getAll();

        ArrayList<Subject> subjects = subjectDao.getAll();

        String userName =request.getParameter("name");
        System.out.println(userName);
        String userSurname=request.getParameter("surname");
        String userPassword=request.getParameter("password");
        String userEmail=request.getParameter("email");
        String userRole= request.getParameter("role");

        User newUser= new User(userName,userSurname,userPassword,userEmail,userRole);

        userDao.add(newUser);

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


    }
}