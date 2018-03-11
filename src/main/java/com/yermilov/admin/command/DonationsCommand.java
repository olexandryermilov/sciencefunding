package com.yermilov.admin.command;

import com.yermilov.admin.service.DonationService;
import com.yermilov.command.Command;
import com.yermilov.domain.Donation;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DonationsCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(DonationsCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DonationService donationService = DonationService.getDonationService();
        try{
            String pageNumberParam = req.getParameter("pageNumber");
            String pageSizeParam = req.getParameter("pageSize");
            if(pageNumberParam==null){
                pageNumberParam="1";
            }
            if(pageSizeParam==null)pageSizeParam="5";
            int pageNum = Integer.parseInt(pageNumberParam);
            int pageSize = Integer.parseInt(pageSizeParam);
            List<Donation> donations = donationService.getDonations((pageNum-1)*pageSize,pageSize);
            req.setAttribute("pageAmount",((donationService.getTableSize()+pageSize-1)/pageSize));
            req.setAttribute("donations",donations);
            req.getRequestDispatcher("donations.jsp").forward(req,resp);
        }
        catch(NumberFormatException e){
            LOGGER.error(e.getMessage());
            req.setAttribute("errorMessage","Page number and page size must be a positive integer number.");
            req.getRequestDispatcher("donations.jsp").forward(req,resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
