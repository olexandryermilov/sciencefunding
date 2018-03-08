package com.yermilov.dao

import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class CampaignDaoTest extends Specification {

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
    def 'create_createsRecord'(){
        setup:
            Campaign campaign = new Campaign(organiserList.get(0), 4000, "BestCampaign", domainList.get(0), "Fund us plz")
            campaign.setId(campaignList.size()+1)
            campaignList.add(campaign)
        when:
            DAOFactory.instance.campaignDAO.create(campaign)
        then:
            campaignList==DAOFactory.instance.campaignDAO.campaignDao.queryForAll()


    }

    def 'getSize_returnsRightSize'(){
        setup:
            int RIGHT_ANS =campaignList.size()
        when:
            int size = DAOFactory.instance.campaignDAO.getSize()
        then:
            RIGHT_ANS==size
    }

    def 'getSize_ReturnsRightSize_AfterCreatingNewCampaign'(){
        setup:
            Campaign campaign = new Campaign(organiserList.get(0), 4000, "BestCampaign", domainList.get(0), "Fund us plz")
            campaignList.add(campaign)
            DAOFactory.instance.campaignDAO.create(campaign)
        when:
            int size = DAOFactory.instance.campaignDAO.getSize()
        then:
            campaignList.size()==size
    }

    def 'getSize_ReturnsRightSize_AfterDeletingCampaign'(){
        setup:
            DAOFactory.instance.campaignDAO.campaignDao.delete(campaignList.get(0))
            campaignList.remove(campaignList.get(1))
        when:
            int size = DAOFactory.instance.campaignDAO.getSize()
        then:
            campaignList.size()==size
    }

    def 'getLimitedAmountOfCampaigns_returnsRightAmount'(){
        setup:
            List<Campaign> list = campaignList.subList(1,3)
        when:
            List<Campaign> ans = DAOFactory.instance.campaignDAO.getLimitedAmountOfCampaigns(2,1)
        then:
            list==ans
    }

    def 'getLimitedAmountOfActiveCampaigns_returnsRightAmount'(){
        setup:
            List<Campaign> list = campaignList.subList(2,4)
        when:
            List<Campaign> ans = DAOFactory.instance.campaignDAO.getLimitedAmountOfActiveCampaigns(2,1)
        then:
            list==ans
    }

    def 'changeCampaignState_changesFromOneToZero'(){
        setup:
            campaignList.get(0).isActive=0
        when:
            DAOFactory.instance.campaignDAO.changeCampaignState(campaignList.get(0).id)
        then:
            campaignList.get(0)==DAOFactory.instance.campaignDAO.campaignDao.queryForId(campaignList.get(0).id)
    }
    def 'changeCampaignState_changesFromZeroToOne'(){
        setup:
            campaignList.get(1).isActive=1
        when:
            DAOFactory.instance.campaignDAO.changeCampaignState(campaignList.get(1).id)
        then:
            campaignList.get(1)==DAOFactory.instance.campaignDAO.campaignDao.queryForId(campaignList.get(1).id)
    }

    def 'queryForId_returnsRightCampaigns'(){
        setup:
        for(campaign in campaignList){
            when:
                Campaign ans = DAOFactory.instance.campaignDAO.queryForId(campaign.id)
            then:
                campaign==ans
        }
    }

    def 'queryForId_returnsNull_WhenThereIsNoSuchCampaign'(){
        setup:
        List<Integer> badIds = new ArrayList<Integer>(){{add(0);add(-1);add(15)}}
        for(id in badIds){
            when:
                Campaign ans = DAOFactory.instance.campaignDAO.queryForId(id)
            then:
                null==ans
        }
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
