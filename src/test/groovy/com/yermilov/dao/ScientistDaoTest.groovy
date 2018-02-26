package com.yermilov.dao

import com.yermilov.domain.Domain
import com.yermilov.domain.Organisation
import com.yermilov.domain.Scientist
import com.yermilov.domain.User
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class ScientistDaoTest extends Specification {

    @Shared
    private List<Scientist> scientistList

    @Shared
    private List<User> userList

    @Shared
    private List<Domain> domainList

    @Shared
    private List<Organisation> organisationList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
        domainList = TableCreator.initDomainTable()
        organisationList = TableCreator.initOrganisationTable()
        scientistList = TableCreator.initScientistTable()
    }
    def 'create_createsRecord'(){
        setup:
            Scientist scientist = new Scientist(userList.get(3),domainList.get(1),organisationList.get(1))
            scientistList.add(scientist)
        when:
            DAOFactory.instance.scientistDAO.create(scientist)
        then:
            scientistList==DAOFactory.instance.scientistDAO.scientistDao.queryForAll()


    }

    def 'queryForUserId_returnsScientistWhenRightId'(){
        setup:
            Scientist scientist = scientistList.get(0)
        when:
            Scientist sci = DAOFactory.instance.scientistDAO.queryForUserId(scientist.user.id)
        then:
            scientist==sci&&sci.user.email!=null&sci.domain.name!=null&&sci.organisation.name!=null

    }
    def 'queryForUserId_returnsNullWhenBadId'(){
        setup:
        when:
            Scientist sci = DAOFactory.instance.scientistDAO.queryForUserId(18)
        then:
            sci==null
        when:
            sci = DAOFactory.instance.scientistDAO.queryForUserId(-1)
        then:
            sci==null
    }
    def 'update_UpdatesRecord'(){
        setup:
            Scientist scientist = scientistList.get(0)
            scientist.setDomain(domainList.get(2-scientist.domain.id))
        when:
            DAOFactory.instance.scientistDAO.update(scientist)
        then:
            scientist==DAOFactory.instance.scientistDAO.scientistDao.queryForId(scientist.id)
    }
    def cleanup(){
        TableCleaner.cleanScientistTable()
        TableCleaner.cleanOrganisationTable()
        TableCleaner.cleanDomainTable()
        TableCleaner.cleanUserTable()
    }
}
