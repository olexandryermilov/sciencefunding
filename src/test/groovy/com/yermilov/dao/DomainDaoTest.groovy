package com.yermilov.dao

import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class DomainDaoTest extends Specification {

    @Shared
    private List<Domain> domainList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        domainList = TableCreator.initDomainTable()
    }
    def 'create_createsRecord'(){
        setup:
            Domain domain = new Domain("Programming")
            domain.setId(domainList.size()+1)
            domainList.add(domain)
        when:
            DAOFactory.instance.domainDAO.create(domain)
        then:
            domainList==DAOFactory.instance.domainDAO.domainDao.queryForAll()
    }

    def 'queryForId_returnsRightDomains'(){
        setup:
        for(domain in domainList){
            when:
                Domain ans = DAOFactory.instance.domainDAO.queryForId(domain.id)
            then:
                domain==ans
        }
    }

    def 'queryForId_returnsNull_WhenThereIsNoSuchDomain'(){
        setup:
            List<Integer> badIds = new ArrayList<Integer>(){{add(0);add(-1);add(15)}}
        for(id in badIds){
            when:
                Domain ans = DAOFactory.instance.domainDAO.queryForId(id)
            then:
                null==ans
        }
    }

    def cleanup(){
        TableCleaner.cleanDomainTable()
    }
}
