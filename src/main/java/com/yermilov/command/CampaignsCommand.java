package com.yermilov.command;

import com.yermilov.domain.Campaign;
import com.yermilov.exception.DAOException;
import com.yermilov.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CampaignsCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(CampaignsCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CampaignService campaignService = CampaignService.getCampaignService();
        try{
            String pageNumberParam = req.getParameter("pageNumber");
            String pageSizeParam = req.getParameter("pageSize");
            if(pageNumberParam==null){
                pageNumberParam="1";
            }
            if(pageSizeParam==null)pageSizeParam="5";
            int pageNum = Integer.parseInt(pageNumberParam);
            int pageSize = Integer.parseInt(pageSizeParam);
            boolean neededOnlyActive = !req.getRequestURI().contains("admin");
            List<Campaign> allCampaigns = campaignService.getCampaigns((pageNum-1)*pageSize,pageSize,neededOnlyActive);
            req.setAttribute("pageAmount",((campaignService.getTableSize()+pageSize-1)/pageSize));
            req.setAttribute("campaigns",allCampaigns);
            req.getRequestDispatcher("campaigns.jsp").forward(req,resp);
        }
        catch(NumberFormatException e){
            LOGGER.error(e.getMessage());
            req.setAttribute("errorMessage","Page number and page size must be a positive integer number.");
            req.getRequestDispatcher("campaigns.jsp").forward(req,resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
