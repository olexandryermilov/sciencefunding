package com.yermilov.command;

import com.yermilov.domain.Campaign;
import com.yermilov.domain.Domain;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.exception.LoginException;
import com.yermilov.services.AddCampaignService;
import com.yermilov.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddCampaignCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddCampaignCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String needToRaiseString = request.getParameter("needToRaise");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String domainIdString = "1";
        if (needToRaiseString == null||name==null||description==null||domainIdString==null) {
            request.setAttribute("errorMessageLogin","You should fill all fields");
            LOGGER.info("Empty fields");
            request.getRequestDispatcher(CommandFactory.ADD_CAMPAIGN+".jsp").forward(request, response);
            return;
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
            request.getRequestDispatcher(CommandFactory.ADD_CAMPAIGN+".jsp").forward(request, response);
            return;
        }
        AddCampaignService addCampaignService = AddCampaignService.getAddCampaignService();
        try {
            addCampaignService.addCampaign((User) request.getSession().getAttribute("currentUser"),needToRaise,name,domainId,description);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}

