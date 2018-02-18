package com.yermilov.admin.command;

import com.yermilov.admin.service.ChangeUserStateService;
import com.yermilov.command.Command;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeUserStateCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChangeUserStateCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChangeUserStateService changeUserStateService = ChangeUserStateService.getChangeUserStateService();
        int idToDelete = Integer.parseInt(request.getParameter("userid"));
        LOGGER.info("Trying to delete next user: userid={}",idToDelete);
        try {
            changeUserStateService.delete(idToDelete);
            LOGGER.info("Successfully changed state");
            request.getRequestDispatcher("controller?command=users&pageNumber=1"/*+request.getParameter("pageNumber")*/).forward(request,response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
