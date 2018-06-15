package com.yermilov.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("currentUser")!=null)session.removeAttribute("currentUser");
        //request.getRequestDispatcher("index.jsp").forward(request, response);
        return "index";
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
