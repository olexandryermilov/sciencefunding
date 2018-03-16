package com.yermilov.command

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

class LogoutCommandTestSpock extends Specification {

    @Shared
    private List<User> userList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
    }

    def 'logout_deletesUserFromSession'(){
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Mock()
            httpSession.getAttribute("currentUser")>>userList.get(0)
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Stub(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher("index.jsp")>>requestDispatcher
            Command logoutCommand = CommandFactory.getInstance().getCommand(CommandFactory.LOGOUT)
        when:
            logoutCommand.execute(httpServletRequest,httpServletResponse)
        then:
            1*httpSession.removeAttribute("currentUser")
    }

    def 'logout_NotFailsWhenUserIsn\'tLogged'(){
        given:
            HttpServletRequest httpServletRequest = Mock()
            HttpServletResponse httpServletResponse = Mock()
            HttpSession httpSession = Mock()
            httpSession.getAttribute("currentUser")>>null
            httpServletRequest.getSession()>>httpSession
            RequestDispatcher requestDispatcher = Stub(RequestDispatcher.class)
            httpServletRequest.getRequestDispatcher("index.jsp")>>requestDispatcher
            Command logoutCommand = CommandFactory.getInstance().getCommand(CommandFactory.LOGOUT)
        when:
            logoutCommand.execute(httpServletRequest,httpServletResponse)
        then:
            0*httpSession.removeAttribute("currentUser")
    }
    def cleanup(){
        TableCleaner.cleanUserTable()
    }
}
