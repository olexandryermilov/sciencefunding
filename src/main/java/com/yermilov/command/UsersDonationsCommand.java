package com.yermilov.command;

import com.yermilov.admin.service.dao.DonationService;
import com.yermilov.domain.Donation;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersDonationsCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(UsersDonationsCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            long userId = ((User)req.getSession().getAttribute("currentUser")).getId();
            List<Donation> donations = donationService.getDonations((pageNum-1)*pageSize,pageSize,userId);
            req.setAttribute("pageAmount",((donationService.getTableSize(userId)+pageSize-1)/pageSize));
            req.setAttribute("donations",donations);
            //req.getRequestDispatcher("my_donations.jsp").forward(req,resp);
        }
        catch(NumberFormatException e){
            LOGGER.error(e.getMessage());
            req.setAttribute("errorMessage","Page number and page size must be a positive integer number.");
            //req.getRequestDispatcher("my_donations.jsp").forward(req,resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            return "error";
        }
        return "my_donations";
    }
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
