package com.yermilov.admin.service;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.UserDAO;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;
import com.yermilov.tableworkers.TableCleaner;
import com.yermilov.tableworkers.TableCreator;
import com.yermilov.transaction.DatabaseConnector;
import com.yermilov.transaction.H2ConnectionPool;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChangeStateServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource());
    }

    @Test
    public void deleteUser_ChangesUserState() throws SQLException, DAOException {
        List<User> userList= TableCreator.initUserTable();
        ChangeStateService changeStateService = ChangeStateService.getChangeStateService();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        for(User user : userList){
            int state = user.getIsActive();
            changeStateService.deleteUser(user.getId());
            userDAO.getUserDao().refresh(user);
            assertEquals(1-state, user.getIsActive());
        }
        TableCleaner.cleanUserTable();
    }

}
