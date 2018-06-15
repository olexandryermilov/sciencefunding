package com.yermilov.controller

import com.yermilov.admin.service.dao.AddScientistService
import com.yermilov.admin.service.dao.ChangeStateService
import com.yermilov.admin.service.dao.DonationService
import com.yermilov.admin.service.dao.UsersService
import com.yermilov.command.CommandFactory
import com.yermilov.domain.Admin
import com.yermilov.domain.Campaign
import com.yermilov.domain.Donation
import com.yermilov.domain.User
import com.yermilov.exception.AddScientistException
import com.yermilov.exception.DAOException
import com.yermilov.exception.LoginException
import com.yermilov.exception.RegistrationException
import com.yermilov.service.AddCampaignService
import com.yermilov.service.CampaignService
import com.yermilov.service.DonateService
import com.yermilov.service.LoginService
import com.yermilov.service.RegistrationService
import com.yermilov.service.SearchService
import com.yermilov.service.TransactionService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import javax.servlet.http.HttpSession
import java.nio.file.Path

@Controller
@RequestMapping(value = "/")
class MainController {

    private Admin admin
    private User currentUser
    //@Autowired
    //Model model
    private static final LOGGER = LoggerFactory.getLogger(MainController.class)
    @RequestMapping(value="/")
    String indexController(){
        return "index"
    }

    @GetMapping(value = "/addCampaign")
    String addCampaign(){
        return CommandFactory.ADD_CAMPAIGN
    }

    @PostMapping(value = "/addCampaign")
    String addCampaign(Model model,HttpSession session, @RequestParam String needToRaiseS, @RequestParam String description, @RequestParam String domainidS, @RequestParam String name){
        if (needToRaiseS == null||name==null||description==null||domainidS==null) {
            model.addAttribute("errorMessageLogin","You should fill all fields")
            LOGGER.info("Empty fields")
            //request.getRequestDispatcher(CommandFactory.ADD_CAMPAIGN+".jsp").forward(request, response);
            return CommandFactory.ADD_CAMPAIGN
        }
        int domainId=-1
        int needToRaise=-1
        try{
            domainId = Integer.parseInt(domainidS)
            needToRaise = Integer.parseInt(needToRaiseS)
        }
        catch (NumberFormatException e){
            LOGGER.error(e.getMessage())
            model.addAttribute("errorMessageLogin","Numbers should be numbers")
            //request.getRequestDispatcher(CommandFactory.ADD_CAMPAIGN+".jsp").forward(request, response);
            return CommandFactory.ADD_CAMPAIGN
        }
        if(needToRaise<1){
            LOGGER.error("Need to Raise smaller or equal to 0")
            model.addAttribute("errorMessageLogin","Money to raise should be bigger than 0")
            return CommandFactory.ADD_CAMPAIGN
        }
        AddCampaignService addCampaignService = AddCampaignService.getAddCampaignService()
        try {
            addCampaignService.addCampaign(currentUser,needToRaise,name,domainId,description)
            model.addAttribute("message","Campaign added successfully")
            session.setAttribute("message","Campaign added successfully")
            //request.getRequestDispatcher("index.jsp").forward(request, response);
            return "redirect:/"
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }

    @GetMapping(value="/login")
    String getPage(HttpSession session){
        session.removeAttribute("justRegistered")
        if(session.getAttribute("currentUser")!=null){
            return "redirect:/"
        }
        return CommandFactory.LOGIN
    }
    @PostMapping(value = "/login")
    String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model){
        session.removeAttribute("justRegistered")
        if(session.getAttribute("currentUser")!=null){
            return "redirect:/"
        }
        if (email == null) {
            model.addAttribute("errorMessageLogin","You should fill email")
            LOGGER.info("Empty email")
            return CommandFactory.LOGIN
        }
        if (password == null) {
            model.addAttribute("errorMessageLogin","You should fill password")
            LOGGER.info("Empty password")
            return CommandFactory.LOGIN
        }
        LoginService loginService = LoginService.getLoginService()
        try {
            User user = loginService.getUser(email,password)
            if (user!=null) {
                LOGGER.info("User {} logged in.",email)
                model.addAttribute("currentUser",user)
                session.setAttribute("currentUser", user)
                currentUser=user
                return "redirect:/"
            } else {
                LOGGER.info("User {} couldn't log in.",email)
                model.addAttribute("errorMessageLogin", "Login or password incorrect")
                return CommandFactory.LOGIN
            }
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        } catch (LoginException e) {
            LOGGER.info(e.getMessage())
            model.addAttribute("errorMessageLogin", e.getMessage())
            return CommandFactory.LOGIN
        }
    }
    @RequestMapping(value = "/campaign/{id}")
    String campaignList(@RequestParam int id, Model model){
        model.addAttribute("campaign",CampaignService.campaignService.getCampaign(id))
        return "campaign"
    }

    @RequestMapping(value = ["/admin/index","/admin"])
    String getAdmin(){
        return "admin/index"
    }
    @GetMapping(value = "/admin/login.html")
    String getAdminLogin(HttpSession session){
        if(session.getAttribute("admin")!=null){
            return "admin/index"
        }
        return "admin/login"
    }

    @PostMapping(value = "/admin/login")
    String adminLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session){
        if(session.getAttribute("admin")!=null){
            return "admin/index"
        }
        if (email == null) {
            model.addAttribute("errorMessageLogin","You should fill email")
            LOGGER.info("Empty email")
            return "/admin/login"
        }
        if (password == null) {
            model.addAttribute("errorMessageLogin","You should fill password")
            LOGGER.info("Empty password")
            return "/admin/login"
        }
        com.yermilov.admin.service.dao.LoginService loginService = com.yermilov.admin.service.dao.LoginService.getLoginService()
        try {
            Admin admin = loginService.getAdmin(email,password)
            if (admin!=null) {
                LOGGER.info("Admin {} logged in.",email)
                session.setAttribute("admin", admin)
                this.admin=admin
                return "redirect:/admin/index"
            } else {
                LOGGER.info("Admin {} couldn't log in.",email)
                model.addAttribute("errorMessageLogin", "Login or password incorrect")
                return "admin/login"
            }
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        } catch (LoginException e) {
            LOGGER.info(e.getMessage())
            model.addAttribute("errorMessageLogin", e.getMessage())
            return "/admin/login"
        }
    }
    @GetMapping(value ="/admin/users/{page}")
    String getUsers(@PathVariable int page, Model model){
        UsersService usersService = UsersService.getUsersService()
        try{
            int pageSize = 5
            int pageNum = page
            model.addAttribute("page",page)
            List<User> allUsers = usersService.getUsers((pageNum-1)*pageSize,pageSize)
            model.addAttribute("pageAmount",((usersService.getTableSize()+pageSize-1).intdiv(pageSize)))
            model.addAttribute("users",allUsers)
            model.addAttribute("pageNumber",page)
            println allUsers
            return "admin/users"
        }
        catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }

    @PostMapping(value = "/admin/users/{page}")
    String updateUser(@PathVariable long page,@RequestParam String userid,@RequestParam String command, Model model, @RequestParam String text){
        int id = Integer.parseInt(userid)
        int pageSize = 5
        int pageNum = page

        if(command.equals(CommandFactory.CHANGE_STATE)){
            ChangeStateService changeStateService = ChangeStateService.getChangeStateService()
            changeStateService.changeUserState(id)
        }
        else{
            if(command.equals(CommandFactory.ADD_SCIENTIST)) {
                AddScientistService addScientistService = AddScientistService.getAddScientistService()
                try {
                    addScientistService.registerAsScientist(id)
                }
                catch (AddScientistException e) {
                    model.addAttribute("errorMessage")
                }
            }
            else{
                if(command.equals("search")){
                    SearchService service = SearchService.registrationService
                    List<User> toDisplay =  service.searchUser(text.toLowerCase())
                    model.addAttribute("users",toDisplay)
                    model.addAttribute("pageAmount",1)
                    model.addAttribute("pageNumber",1)
                }
            }
        }
        UsersService usersService = UsersService.getUsersService()
        if(text==null||text.length()==0) {
            model.addAttribute("page", page)
            List<User> allUsers = usersService.getUsers((pageNum - 1) * pageSize, pageSize)
            model.addAttribute("pageAmount", ((usersService.getTableSize() + pageSize - 1).intdiv(pageSize)))
            model.addAttribute("users", allUsers)
            model.addAttribute("pageNumber", page)
            println(allUsers)
            model.asMap().put("users", allUsers)
        }
        return "admin/users"
    }


    @GetMapping(value="admin/campaigns/{page}")
    String getCampaigns(@PathVariable int page, Model model){
        CampaignService campaignService = CampaignService.getCampaignService()
        try{
            int pageNum = page
            int pageSize = 5
            List<Campaign> allCampaigns = campaignService.getCampaigns((pageNum-1)*pageSize,pageSize,false)
            model.addAttribute("pageAmount",((campaignService.getTableSize(false)+pageSize-1).intdiv(pageSize)))
            model.addAttribute("campaigns",allCampaigns)
            model.addAttribute("pageNumber",page)
            //req.getRequestDispatcher("campaigns.jsp").forward(req,resp);
            return "admin/campaigns"
        }
        catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }

    @PostMapping(value = "admin/campaigns/{page}")
    String updateCampaign(@PathVariable int page, @RequestParam String campaignid, @RequestParam String command,
                          @RequestParam String text,Model model){
        if(command.equals("search")){
            try{
                List<Campaign> allCampaigns = SearchService.registrationService.searchCampaign(text.toLowerCase(),false)
                model.addAttribute("pageAmount",1)
                model.addAttribute("campaigns",allCampaigns)
                model.addAttribute("pageNumber",1)
                return "/admin/campaigns"
            }
            catch (DAOException e) {
                LOGGER.error(e.getMessage())
                return "error"
            }
        }

        ChangeStateService changeStateService = ChangeStateService.getChangeStateService()
        int idToDelete = Integer.parseInt(campaignid)
        LOGGER.info("Trying to change state of next campaign: userid={}",idToDelete)
        try {
            changeStateService.changeCampaignState(idToDelete)
            LOGGER.info("Successfully changed state")
            return getCampaigns(page,model)
            //return "admin/campaigns"
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }

    @GetMapping(value = "admin/donations/{page}")
    String getDonations(@PathVariable int page, Model model){
        DonationService donationService = DonationService.getDonationService()
        try{
            int pageNum = page
            int pageSize = 5
            List<Donation> donations = donationService.getDonations((pageNum-1)*pageSize,pageSize)
            model.addAttribute("pageAmount",((donationService.getTableSize()+pageSize-1).intdiv(pageSize)))
            model.addAttribute("donations",donations)
            model.addAttribute("pageNumber",page)
            return "admin/donations"
        }catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }
    @PostMapping(value = "/admin/index")
    String logoutAdmin(HttpSession session){
        admin=null
        session.removeAttribute("admin")
        return "admin/index"
    }

    @GetMapping(value="/registration")
    String getRegister(){
        if(currentUser!=null){
            return "redirect:/"
        }
        return "registration"
    }

    @PostMapping(value ="/registration")
    String postRegister(HttpSession session,@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname){
        LOGGER.info("User typed next values: email :{} {} {}",email,name,surname)
        if (email == null || password == null || name == null || surname == null) {
            LOGGER.info("Tried to register with empty fields.")
            session.setAttribute("errorMessage", "All fields must be filled")
            //req.getRequestDispatcher(CommandFactory.REGISTRATION+".jsp").forward(req, resp);
            return CommandFactory.REGISTRATION
        }
        RegistrationService registrationService = RegistrationService.getRegistrationService()
        try {
            registrationService.register(email, password, name, surname)
            LOGGER.info("Registered new user: email :{} {} {}",email,name,surname)
            session.setAttribute("justRegistered",true)
            //req.getRequestDispatcher("index.jsp").forward(req,resp);
            return "redirect:/"
        } catch (RegistrationException e) {
            LOGGER.error(e.getMessage());
            session.setAttribute("errorMessage", e.getMessage())
            //req.getRequestDispatcher(CommandFactory.REGISTRATION+".jsp").forward(req, resp);
            return CommandFactory.REGISTRATION
        }
    }

    @GetMapping(value="/campaigns/{page}")
    String getCampaignsForUsers(@PathVariable int page, Model model){
        CampaignService campaignService = CampaignService.getCampaignService()
        try{
            int pageNum = page
            int pageSize = 5
            List<Campaign> allCampaigns = campaignService.getCampaigns((pageNum-1)*pageSize,pageSize,true)
            model.addAttribute("pageAmount",((campaignService.getTableSize(true)+pageSize-1).intdiv(pageSize)))
            model.addAttribute("campaigns",allCampaigns)
            model.addAttribute("pageNumber",pageNum)
            println ((campaignService.getTableSize(true)+pageSize-1)/pageSize)
            //req.getRequestDispatcher("campaigns.jsp").forward(req,resp);
            return "campaigns"
        }
        catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }

    @PostMapping(value="campaigns/{id}")
    String searchActiveCampaigns(@RequestParam String text, Model model){
        try{
            List<Campaign> allCampaigns = SearchService.registrationService.searchCampaign(text.toLowerCase(),true)
            model.addAttribute("pageAmount",1)
            model.addAttribute("campaigns",allCampaigns)
            model.addAttribute("pageNumber",1)
            return "campaigns"
        }
        catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }

    @PostMapping(value="myDonations/{id}")
    String searchUsersDonations(@RequestParam String text,Model model){
        List<Donation> donations = SearchService.registrationService.searchDonation(text.toLowerCase(),currentUser)
        model.addAttribute("pageAmount",1)
        model.addAttribute("donations",donations)
        model.addAttribute("pageNumber",1)
        return "myDonations"
    }
    @PostMapping(value="/admin/donations/{id}")
    String searchAdminsDonations(@RequestParam String text,Model model){
        List<Donation> donations = SearchService.registrationService.searchDonation(text.toLowerCase(),null)
        model.addAttribute("pageAmount",1)
        model.addAttribute("donations",donations)
        model.addAttribute("pageNumber",1)
        return "myDonations"
    }

    @GetMapping(value = "campaign/{id}")
    String getCampaign(@PathVariable int id, Model model, HttpSession session){
        CampaignService campaignService = CampaignService.getCampaignService()
        try {
            Campaign campaign = campaignService.getCampaign(id)
            int moneyRaised = campaignService.getRaisedMoneyForCampaign(id)
            model.addAttribute("campaign",campaign)
            model.addAttribute("moneyRaised",moneyRaised)
            session.setAttribute("campaign",campaign)
            session.setAttribute("moneyRaised",moneyRaised)
            //request.getRequestDispatcher("campaign.jsp").forward(request, response);
            return "campaign"
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
    }


    @GetMapping(value="donate/{id}")
    String getDonate(){
        return "donate"
    }

    @PostMapping(value="donate/{id}")
    String getDonate(@PathVariable id, @RequestParam String money, @RequestParam String comment, @RequestParam int campaignId, HttpSession session, Model model){
        DonateService donateService = DonateService.getDonateService()
        long userId = currentUser.id
        int moneyInt=-1
        if(money==null){
            session.setAttribute("errorMessage","Input amount of money")
            LOGGER.error("Money is null")
            //request.getRequestDispatcher("donate.jsp").forward(request,response);
            return "donate"
        }
        try{
            moneyInt = Integer.parseInt(money)
        }
        catch (NumberFormatException e){
            LOGGER.error("Money is not integer")
            session.setAttribute("errorMessage","Please, donate positive amount of money")
            return "donate"
        }
        if(moneyInt<=0){
            LOGGER.error("Money is not positive")
            session.setAttribute("errorMessage","Please, donate positive amount of money")
            //request.getRequestDispatcher("").forward(request,response);
            return "donate"
        }
        try {
            if(!TransactionService.getDonateService().verifyTransaction()){
                session.setAttribute("errorMessage","Transaction problem. Please, try again")
                LOGGER.error("Transaction")
                //request.getRequestDispatcher("").forward(request,response);
                return "donate"
            }
            donateService.donateMoney(userId,campaignId,moneyInt,comment)
            LOGGER.info("User {} successfully donated {}\$ to campaign {}",userId,money,campaignId)
            //request.getRequestDispatcher("controller?command=campaign&campaignId="+String.valueOf(campaignId)).forward(request,response);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
        session.setAttribute("message","Donated successfully")
        return "redirect:/"
    }

    @GetMapping(value="/myDonations/{page}")
    String getMyDonations(Model model, @PathVariable int page){
        DonationService donationService = DonationService.getDonationService()
        try{
            int pageNum = page
            int pageSize = 5
            long userId = currentUser.id
            List<Donation> donations = donationService.getDonations((pageNum-1)*pageSize,pageSize,userId)
            model.addAttribute("pageAmount",((donationService.getTableSize(userId)+pageSize-1).intdiv(pageSize)))
            model.addAttribute("donations",donations)
            model.addAttribute("pageNumber",page)
            //req.getRequestDispatcher("my_donations.jsp").forward(req,resp);
        } catch (DAOException e) {
            LOGGER.error(e.getMessage())
            return "error"
        }
        return "myDonations"
    }

    @GetMapping(value="/logout")
    String logoutUser(HttpSession session){
        currentUser=null
        session.removeAttribute("currentUser")
        return "redirect:/"
    }

}