package com.yermilov.command

import com.yermilov.domain.Campaign
import com.yermilov.domain.Domain
import com.yermilov.domain.Organisation
import com.yermilov.domain.Organiser
import com.yermilov.domain.Scientist
import com.yermilov.domain.User
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



class LoginCommandTestSpock extends Specification {

    @Shared
    private List<User> userList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
    }

    def 'execute_FailsLogin_WhenBadData'() throws ServletException, IOException {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Stub()
            httpSession.getAttribute("currentUser")>>null
            httpServletRequest.getParameter("email")>>"bademail@gmail.com"
            httpServletRequest.getParameter("password")>>"password"
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Stub(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher(CommandFactory.LOGIN+".jsp")>>requestDispatcher
            Command loginCommand = CommandFactory.getInstance().getCommand("login")
        when:
            loginCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1* httpServletRequest.setAttribute("errorMessageLogin","Login or password incorrect")
    }
    def 'execute_FailsLogin_WhenEmailIsNull'() throws ServletException, IOException {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Stub()
            httpSession.getAttribute("currentUser")>>null
            httpServletRequest.getParameter("email")>>null
            httpServletRequest.getParameter("password")>>"password"
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Stub(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher(CommandFactory.LOGIN+".jsp")>>requestDispatcher
            Command loginCommand = CommandFactory.getInstance().getCommand("login")
        when:
            loginCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1*httpServletRequest.setAttribute("errorMessageLogin","You should fill email")
    }

    def 'execute_FailsLogin_WhenPasswordIsNull'() throws ServletException, IOException {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Stub()
            httpSession.getAttribute("currentUser")>>null
            httpServletRequest.getParameter("email")>>"email@gmail.com"
            httpServletRequest.getParameter("password")>>null
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Stub(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher(CommandFactory.LOGIN+".jsp")>>requestDispatcher
            Command loginCommand = CommandFactory.getInstance().getCommand("login")
        when:
            loginCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1*httpServletRequest.setAttribute("errorMessageLogin","You should fill password")
    }

    def 'execute_LogsIn_WhenGoodData'() throws ServletException, IOException {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Mock()
            httpSession.getAttribute("currentUser")>>null
            httpServletRequest.getParameter("email")>>userList.get(0).email
            httpServletRequest.getParameter("password")>>userList.get(0).password
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Stub(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher("index.jsp")>>requestDispatcher
            Command loginCommand = CommandFactory.getInstance().getCommand("login")
        when:
            loginCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1*httpSession.setAttribute("currentUser", userList.get(0))
    }

    def 'execute_FailsLogIn_WhenAlreadyLoggedIn'() throws ServletException, IOException {
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Stub()
            httpSession.getAttribute("currentUser")>>userList.get(0)
            httpServletRequest.getParameter("email")>>userList.get(0).email
            httpServletRequest.getParameter("password")>>userList.get(0).password
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Mock()
            httpServletRequest.getRequestDispatcher("index.jsp")>>requestDispatcher
            Command loginCommand = CommandFactory.getInstance().getCommand("login")
        when:
            loginCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1*requestDispatcher.forward(httpServletRequest,httpServletResponse)
    }
    def cleanup(){
        TableCleaner.cleanUserTable()
    }
}
