package com.yermilov.command;

import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.service.AddCampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCampaignCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddCampaignCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String needToRaiseString = request.getParameter("needToRaise");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String domainIdString = "1";
        if (needToRaiseString == null||name==null||description==null||domainIdString==null) {
            request.setAttribute("errorMessageLogin","You should fill all fields");
            LOGGER.info("Empty fields");
            //request.getRequestDispatcher(CommandFactory.ADD_CAMPAIGN+".jsp").forward(request, response);
            return CommandFactory.ADD_CAMPAIGN;
        }
        int domainId=-1;
        int needToRaise=-1;
        try{
            domainId = Integer.parseInt(domainIdString);
            needToRaise = Integer.parseInt(needToRaiseString);
        }
        catch (NumberFormatException e){
            LOGGER.error(e.getMessage());
            request.setAttribute("errorMessageLogin","Numbers should be numbers");
            //request.getRequestDispatcher(CommandFactory.ADD_CAMPAIGN+".jsp").forward(request, response);
            return CommandFactory.ADD_CAMPAIGN;
        }
        AddCampaignService addCampaignService = AddCampaignService.getAddCampaignService();
        try {
            addCampaignService.addCampaign((User) request.getSession().getAttribute("currentUser"),needToRaise,name,domainId,description);
            //request.getRequestDispatcher("index.jsp").forward(request, response);
            return "index";
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

