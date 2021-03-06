package com.yermilov.service;

import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.exception.LoginException;
import com.yermilov.tableworkers.TableCleaner;
import com.yermilov.tableworkers.TableCreator;
import com.yermilov.transaction.DatabaseConnector;
import com.yermilov.transaction.H2ConnectionPool;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class LoginServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource());
    }
    @Test
    public void getUser_returnsUser_whenRightData_mockingDAO() throws DAOException, LoginException {
        final String TEST_EMAIL="abs@gmail.com",TEST_PASSWORD="qweerty";
        final User RIGHT_ANSWER = new User(TEST_EMAIL,TEST_PASSWORD,null,null);
        IDAOFactory daoFactory = mock(IDAOFactory.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.queryForEmail(TEST_EMAIL)).thenReturn(RIGHT_ANSWER);
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        LoginService loginService = LoginService.getLoginService();
        loginService.setDaoFactory(daoFactory);
        User answer = loginService.getUser(TEST_EMAIL,TEST_PASSWORD);
        assertEquals(RIGHT_ANSWER,answer);
    }
    @Test
    public void getUser_returnsNull_whenBadData_mockingDAO() throws DAOException, LoginException {
        final String TEST_EMAIL="abs@gmail.com",TEST_PASSWORD="qweerty";
        final User RIGHT_ANSWER = new User(TEST_EMAIL,TEST_PASSWORD,null,null);
        IDAOFactory daoFactory = mock(IDAOFactory.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.queryForEmail(TEST_EMAIL)).thenReturn(RIGHT_ANSWER);
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        LoginService loginService = LoginService.getLoginService();
        loginService.setDaoFactory(daoFactory);
        User answer = loginService.getUser(TEST_EMAIL,TEST_PASSWORD+"1");
        assertNull(answer);
    }

    @Test
    public void getUser_returnsUser_WhenRightData() throws SQLException, DAOException, LoginException {
        List<User> allUsers = TableCreator.initUserTable();
        LoginService loginService = LoginService.getLoginService();
        loginService.setDaoFactory(DAOFactory.getInstance());
        final User USER = allUsers.get(0);
        assertEquals(USER,loginService.getUser(USER.getEmail(),USER.getPassword()));
        TableCleaner.cleanUserTable();
    }

    @Test
    public void getUser_returnsNull_WhenBadPassword() throws SQLException, DAOException, LoginException {
        List<User> allUsers = TableCreator.initUserTable();
        LoginService loginService = LoginService.getLoginService();
        final User USER = allUsers.get(0);
        assertNull(loginService.getUser(USER.getEmail(),USER.getPassword()+"1"));
        TableCleaner.cleanUserTable();
    }

    @Test
    public void getUser_returnsNull_WhenBadEmail() throws SQLException, DAOException, LoginException {
        List<User> allUsers = TableCreator.initUserTable();
        LoginService loginService = LoginService.getLoginService();
        final User USER = allUsers.get(0);
        assertNull(loginService.getUser(USER.getEmail()+"1",USER.getPassword()));
        TableCleaner.cleanUserTable();
    }
    @Test(expected = LoginException.class)
    public void getUser_throwsException_WhenDeletedUser() throws SQLException, DAOException, LoginException {
        LoginService.getLoginService().setDaoFactory(DAOFactory.getInstance());
        List<User> allUsers = TableCreator.initUserTable();
        LoginService loginService = LoginService.getLoginService();
        final User USER = allUsers.get(2);
        try{
            loginService.getUser(USER.getEmail(),USER.getPassword());
        }
        catch (LoginException e){
            throw new LoginException(e.getMessage());
        }
        finally {
            TableCleaner.cleanUserTable();
        }
    }
}
