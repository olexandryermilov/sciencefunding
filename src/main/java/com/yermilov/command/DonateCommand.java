package com.yermilov.command;

import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.service.DonateService;
import com.yermilov.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DonateCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(DonateCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DonateService donateService = DonateService.getDonateService();
        long userId = ((User)(request.getSession().getAttribute("currentUser"))).getId();
        int campaignId= Integer.parseInt(request.getParameter("campaignId"));
        int money=-1;
        if(request.getParameter("money")==null){
            //request.getRequestDispatcher("donate.jsp").forward(request,response);
            return "donate";
        }
        try{
            money = Integer.parseInt(request.getParameter("money"));
        }
        catch (NumberFormatException e){
            request.setAttribute("errorMessage","Please, donate positive amount of money");
            //request.getRequestDispatcher("").forward(request,response);
            return "donate";
        }
        if(money<=0){
            request.setAttribute("errorMessage","Please, donate positive amount of money");
            //request.getRequestDispatcher("").forward(request,response);
            return "donate";
        }
        String comment = request.getParameter("description");

        try {
            if(!TransactionService.getDonateService().verifyTransaction()){
                request.setAttribute("errorMessage","Transaction problem. Please, try again");
                //request.getRequestDispatcher("").forward(request,response);
                return "donate";
            }
            donateService.donateMoney(userId,campaignId,money,comment);
            LOGGER.info("User {} successfully donated {}$ to campaign {}",userId,money,campaignId);
            //request.getRequestDispatcher("controller?command=campaign&campaignId="+String.valueOf(campaignId)).forward(request,response);
            return "controller?command=campaign&campaignId="+String.valueOf(campaignId);
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

