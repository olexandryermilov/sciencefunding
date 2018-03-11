package com.yermilov.command

import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.RequestDispatcher
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class CampaignsCommandTestSpock extends Specification {
    @Shared
    private List<Organiser> organiserList

    @Shared
    private List<Organisation> organisationList

    @Shared
    private List<Scientist> scientistList

    @Shared
    private List<Domain> domainList

    @Shared
    private List<User> userList

    @Shared
    private List<Campaign> campaignList


    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
        domainList = TableCreator.initDomainTable()
        organisationList = TableCreator.initOrganisationTable()
        scientistList = TableCreator.initScientistTable()
        organiserList = TableCreator.initOrganiserTable()
        campaignList = TableCreator.initCampaignTable()
    }

    def 'execute_WorksGreat_WhenRightData'() throws ServletException, IOException {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Stub()
            httpServletRequest.getParameter("pageNumber")>>"1"
            httpServletRequest.getParameter("pageSize")>>"2"
            httpServletRequest.getSession()>>httpSession
            httpServletRequest.getRequestURI()>>"sciencefunding/campaigns.jsp?pageNumber=1&pageSize=2"
            RequestDispatcher requestDispatcher = Mock(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher(CommandFactory.CAMPAIGNS+".jsp")>>requestDispatcher
            Command campaignsCommand = CommandFactory.getInstance().getCommand(CommandFactory.CAMPAIGNS)
            List<Campaign> campaigns = campaignList.subList(0,2)
        when:
            campaignsCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1* httpServletRequest.getRequestDispatcher(CommandFactory.CAMPAIGNS+".jsp").forward(httpServletRequest,httpServletResponse)
            1*httpServletRequest.setAttribute("campaigns",[campaignList.get(0),campaignList.get(2)])
            //1* httpServletRequest.setAttribute({name,attr ->name.equals("campaigns")&&attr.equals([campaignList.get(0),campaignList.get(2)])})
    }

    def cleanup(){
        TableCleaner.cleanCampaignTable()
        TableCleaner.cleanOrganiserTable()
        TableCleaner.cleanScientistTable()
        TableCleaner.cleanOrganisationTable()
        TableCleaner.cleanDomainTable()
        TableCleaner.cleanUserTable()
    }
}
