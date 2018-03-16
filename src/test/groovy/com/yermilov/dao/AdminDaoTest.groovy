package com.yermilov.dao

import com.yermilov.domain.Admin
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class AdminDaoTest extends Specification {

    @Shared
    private List<Admin> adminList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        adminList = TableCreator.initAdminTable()
    }
    def 'create_createsRecord'(){
        setup:
            final String EMAIL = "olyer@gmail.com"
            final String PASSWORD = "root1"
            Admin admin = new Admin(EMAIL,PASSWORD)
            adminList.add(admin)
        when:
            DAOFactory.instance.adminDAO.create(admin)
        then:
            adminList==DAOFactory.instance.adminDAO.adminDao.queryForAll()

    }

    def 'queryForEmail_returnsAdminWhenRightEmail'(){
        setup:
            Admin ADMIN = adminList.get(0)
        when:
            Admin ans = DAOFactory.instance.adminDAO.queryForEmail(ADMIN.email)
        then:
            ADMIN==ans

    }
    def 'queryForEmail_returnsNull_WhenBadEmail'(){
        setup:
            final String EMAIL = adminList.get(0).email+'o'
        when:
            Admin admin = DAOFactory.instance.adminDAO.queryForEmail(EMAIL)
        then:
            admin==null
    }
    def cleanup(){
        TableCleaner.cleanAdminTable()
    }
}
