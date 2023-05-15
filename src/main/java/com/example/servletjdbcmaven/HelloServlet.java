package com.example.servletjdbcmaven;

import DAO.UserDao;
import DAO.User;
import DAO.DAO;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "jdbcServlet", value = "/jdbc-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private static DAO<User> userDao=new UserDao();

    public void init() {
        message = "Hello World!";
        DAO.registerDriver();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    public void destroy() {
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<User> utenti = userDao.getAll();

        /*User u = new User(4, "Luana", "Biasi", "luanabiasi@gmail.com", "we", "Utente", "Studente" );
        userDao.add(u);*/

        //System.out.println("FINE!");
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
            out.println("<p>FINE!</p>");
            out.println("</body>");
            out.println("</html>");
        }


    }
}