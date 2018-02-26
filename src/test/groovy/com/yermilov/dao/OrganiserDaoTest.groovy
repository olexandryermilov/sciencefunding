package com.yermilov.dao

import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class OrganiserDaoTest extends Specification {

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
    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
        domainList = TableCreator.initDomainTable()
        organisationList = TableCreator.initOrganisationTable()
        scientistList = TableCreator.initScientistTable()
        organiserList = TableCreator.initOrganiserTable()
    }
    def 'create_createsRecord'(){
        setup:
            Scientist scientist = new Scientist(userList.get(3),domainList.get(1),organisationList.get(1));
            scientistList.add(scientist)
            Organiser organiser = new Organiser(scientist)
            organiserList.add(organiser)
        when:
            DAOFactory.instance.organiserDAO.create(organiser)
            DAOFactory.instance.organiserDAO.organiserDao.refresh(organiser)
        then:
            organiserList==DAOFactory.instance.organiserDAO.organiserDao.queryForAll()


    }

    def cleanup(){
        TableCleaner.cleanOrganiserTable()
        TableCleaner.cleanScientistTable()
        TableCleaner.cleanOrganisationTable()
        TableCleaner.cleanDomainTable()
        TableCleaner.cleanUserTable()
    }
}
