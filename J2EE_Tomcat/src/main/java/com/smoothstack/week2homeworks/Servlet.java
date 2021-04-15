package com.smoothstack.week2homeworks;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "Week2Day3", value = "/W2D3")
public class Servlet extends HttpServlet {
    //The working directly is set to Tomcat's BIN folder, so something like this is necessary.
    //There might be a better way to do this, but I'm almost out of time.
    private static final String RESOURCES_ROOT = "E:\\Documents\\IdeaProjects\\demo\\src\\main\\resources\\";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        response.setContentType("text/html");
        Writer responseWriter = response.getWriter();
        try(FileReader html_file = new FileReader(RESOURCES_ROOT + "week2day3.html")) {
            int chr = 0;
            do {
                chr = html_file.read();
                if(chr > 0) {
                    responseWriter.write(chr);
                }
            } while (chr > 0);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(password.equals("verysecurepassword")) {
            response.getWriter().println("Welcome, " + username);
        }
        else {
            response.getWriter().println("go away stranger");
        }
    }
}
