package com.yermilov.service;

import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.exception.RegistrationException;
import com.yermilov.tableworkers.TableCleaner;
import com.yermilov.tableworkers.TableCreator;
import com.yermilov.transaction.DatabaseConnector;
import com.yermilov.transaction.H2ConnectionPool;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegistrationServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource());
    }
    @Test(expected = RegistrationException.class)
    public void register_throwsException_whenEmailOccupied() throws DAOException, RegistrationException, SQLException {
        final String TEST_EMAIL = "myemail@gmail.com";
        IDAOFactory daoFactory = mock(IDAOFactory.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.queryForEmail(TEST_EMAIL)).thenReturn(new User(TEST_EMAIL,null,null,null));
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        RegistrationService.getRegistrationService().setDaoFactory(daoFactory);
        RegistrationService.getRegistrationService().register(TEST_EMAIL,"","","");
    }

    @Test(expected = RegistrationException.class)
    public void register_throwsRegistrationException_WhenUserDAOThrowsRegistrationException() throws DAOException, RegistrationException, SQLException {
        final String TEST_EMAIL = "myemail@gmail.com";
        IDAOFactory daoFactory = mock(IDAOFactory.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.queryForEmail(TEST_EMAIL)).thenReturn(null);
        when(userDAO.create(any(User.class))).thenThrow(new DAOException("smth went wrong"));
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        RegistrationService.getRegistrationService().setDaoFactory(daoFactory);
        RegistrationService.getRegistrationService().register(TEST_EMAIL,"","","");
    }

    @Test
    public void register_CreatesRecordInDatabase_WhenRightData() throws RegistrationException, DAOException, SQLException, InterruptedException {
        List<User> allUsers = TableCreator.initUserTable();
        User toAdd = new User("antonnalivaychenko@gmail.com","pasqwers","Anton","Nalivaychenko");
        toAdd.setId(5L);
        allUsers.add(toAdd);
        RegistrationService.getRegistrationService().setDaoFactory(new DAOFactory());
        RegistrationService.getRegistrationService().register(toAdd.getEmail(), toAdd.getPassword(),toAdd.getName(),toAdd.getSurname());
        assertEquals(allUsers,DAOFactory.getInstance().getUserDAO().getUserDao().queryForAll());
        TableCleaner.cleanUserTable();
    }

}
