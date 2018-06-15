package com.yermilov.command;

import com.yermilov.domain.Campaign;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.service.AddCampaignService;
import com.yermilov.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WatchCampaignCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(WatchCampaignCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id  = Integer.parseInt(request.getParameter("campaignId"));

        CampaignService campaignService = CampaignService.getCampaignService();
        try {
            Campaign campaign = campaignService.getCampaign(id);
            int moneyRaised = campaignService.getRaisedMoneyForCampaign(id);
            request.setAttribute("campaign",campaign);
            request.setAttribute("moneyRaised",moneyRaised);
            //request.getRequestDispatcher("campaign.jsp").forward(request, response);
            return "campaign";
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

