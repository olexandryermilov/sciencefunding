package com.yermilov.service

import com.yermilov.dao.DAOFactory
import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class AddCampaignServiceTest extends Specification {
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

    @Shared
    private List<Campaign> campaignList

    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
        domainList = TableCreator.initDomainTable()
        organisationList = TableCreator.initOrganisationTable()
        scientistList = TableCreator.initScientistTable()
        organiserList = TableCreator.initOrganiserTable()
        campaignList = TableCreator.initCampaignTable()
    }

    def 'addCampaign_CreatesRecord'(){
        setup:
            Campaign campaign = new Campaign(organiserList.get(0), 4000, "BestCampaign", domainList.get(0), "Fund us plz")
            campaign.setId(campaignList.size()+1)
            campaignList.add(campaign)
        when:
            AddCampaignService.addCampaignService.addCampaign(organiserList.get(0).getScientist().user,
            4000,"BestCampaign",domainList.get(0).id,campaign.description)
        then:
            campaignList==DAOFactory.instance.campaignDAO.campaignDao.queryForAll()
    }

    def cleanup(){
        TableCleaner.cleanCampaignTable()
        TableCleaner.cleanOrganiserTable()
        TableCleaner.cleanScientistTable()
        TableCleaner.cleanOrganisationTable()
        TableCleaner.cleanDomainTable()
        TableCleaner.cleanUserTable()
    }
}
