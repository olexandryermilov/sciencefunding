package com.yermilov.admin.command;

import com.yermilov.admin.service.AddScientistService;
import com.yermilov.admin.service.ChangeUserStateService;
import com.yermilov.command.Command;
import com.yermilov.domain.User;
import com.yermilov.exception.AddScientistException;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddScientistCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddScientistCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddScientistService addScientistService = AddScientistService.getAddScientistService();
        int userid = Integer.parseInt(request.getParameter("userid"));
        try {
            addScientistService.registerAsScientist(userid);
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request,response);
            request.setAttribute("errorMessage","User is registered as scientist");
            LOGGER.info("User with userid={} is registered as scientist",userid);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        } catch (AddScientistException e) {
            LOGGER.info(e.getMessage());
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("controller?command=users&pageNumber=1").forward(request,response);
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}

