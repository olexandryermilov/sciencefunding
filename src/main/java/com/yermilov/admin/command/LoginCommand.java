package com.yermilov.admin.command;

import com.yermilov.command.Command;
import com.yermilov.domain.Admin;
import com.yermilov.exception.DAOException;
import com.yermilov.admin.service.dao.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(com.yermilov.command.LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("admin")!=null){
            //request.getRequestDispatcher("index.jsp").forward(request, response);
            return "index";
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null) {
            request.setAttribute("errorMessageLogin", "You should fill email");
            //request.getRequestDispatcher("login.html").forward(request,response);
            LOGGER.info("Empty email");
            return "/admin/login";
        }
        if (password == null) {
            request.setAttribute("errorMessageLogin", "You should fill password");
            //request.getRequestDispatcher("login.html").forward(request,response);
            LOGGER.info("Empty password");
            return "/admin/login";
        }
        LoginService loginService = LoginService.getLoginService();
        try {
            Admin admin = loginService.getAdmin(email,password);
            if (admin!=null) {
                LOGGER.info("User {} logged into admin.",email);
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                //request.getRequestDispatcher("index.jsp").forward(request, response);
                return "/admin/index";
            } else {
                LOGGER.info("User {} couldn't log into admin.",email);
                request.setAttribute("errorMessageLogin", "Login or password incorrect");
                //request.getRequestDispatcher(CommandFactory.LOGIN+".jsp").forward(request, response);
                return "/admin/login";
            }
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return "error";
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}

