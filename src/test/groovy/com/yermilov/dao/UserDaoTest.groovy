package com.yermilov.dao

import com.yermilov.domain.User
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class UserDaoTest extends Specification {

    @Shared
    private List<User> userList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
    }
    def 'create_createsRecord'(){
        setup:
            User user = new User("olyer1@gmail.com","root1","Ol","Yer")
            user.id=5
            userList.add(user)
        when:
            DAOFactory.instance.userDAO.create(user)
        then:
            userList==DAOFactory.instance.userDAO.userDao.queryForAll()
    }

    def 'queryForEmail_returnsUserWhenRightEmail'(){
        setup:
            User user = userList.get(0)
        when:
            User ans = DAOFactory.instance.userDAO.queryForEmail(user.email)
        then:
            user==ans

    }

    def 'getSize_returnsRightSize'(){
        setup:
            int RIGHT_ANS =userList.size()
        when:
            int size = DAOFactory.instance.userDAO.getSize()
        then:
            RIGHT_ANS==size
    }

    def 'queryForEmail_returnsNull_WhenBadEmail'(){
        setup:
            final String EMAIL = userList.get(0).email+'o'
        when:
            User user = DAOFactory.instance.userDAO.queryForEmail(EMAIL)
        then:
            user==null
    }

    def 'getSize_ReturnsRightSize_AfterCreatingNewUser'(){
        setup:
            User user = new User("olyer1@gmail.com","root1","Ol","Yer")
            user.id=5
            userList.add(user)
            DAOFactory.instance.userDAO.create(user)
        when:
            int size = DAOFactory.instance.userDAO.getSize()
        then:
            userList.size()==size
    }

    def 'getSize_ReturnsRightSize_AfterDeletingUser'(){
        setup:
            DAOFactory.instance.userDAO.userDao.delete(userList.get(3))
            userList.remove(userList.get(3))
        when:
            int size = DAOFactory.instance.userDAO.getSize()
        then:
            userList.size()==size
    }

    def 'getLimitedAmountOfUsers_returnsRightAmount'(){
        setup:
            List<User> list = userList.subList(1,3)
        when:
            List<User> ans = DAOFactory.instance.userDAO.getLimitedAmountOfUsers(2,1)
        then:
            list==ans
    }

    def 'changeUserState_changesFromOneToZero'(){
        setup:
            userList.get(0).isActive=0
        when:
            DAOFactory.instance.userDAO.changeUserState(userList.get(0).id)
        then:
            userList.get(0)==DAOFactory.instance.userDAO.userDao.queryForId(userList.get(0).id)
    }
    def 'changeUserState_changesFromZeroToOne'(){
        setup:
            userList.get(2).isActive=1
        when:
            DAOFactory.instance.userDAO.changeUserState(userList.get(2).id)
        then:
            userList.get(2)==DAOFactory.instance.userDAO.userDao.queryForId(userList.get(2).id)
    }

    def 'queryForId_returnsRightUser'(){
        setup:
            for(User user: userList){
                when:
                    User ans = DAOFactory.instance.userDAO.queryForId(user.id)
                then:
                    user==ans
            }
    }
    def 'queryForId_returnsNullWhenBadData'(){
        setup:
        when:
            User ans = DAOFactory.instance.userDAO.queryForId(userList.size()+1)
        then:
            null==ans
    }
    def cleanup(){
        TableCleaner.cleanUserTable()
    }
}
