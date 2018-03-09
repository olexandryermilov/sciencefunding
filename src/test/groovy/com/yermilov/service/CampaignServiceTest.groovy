package com.yermilov.service

import com.yermilov.dao.DAOFactory
import com.yermilov.domain.Campaign
import com.yermilov.domain.Domain
import com.yermilov.domain.Donation
import com.yermilov.domain.Organisation
import com.yermilov.domain.Organiser
import com.yermilov.domain.Scientist
import com.yermilov.domain.User
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class CampaignServiceTest extends Specification {
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

    @Shared
    private List<Donation> donationList
    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
        userList = TableCreator.initUserTable()
        domainList = TableCreator.initDomainTable()
        organisationList = TableCreator.initOrganisationTable()
        scientistList = TableCreator.initScientistTable()
        organiserList = TableCreator.initOrganiserTable()
        campaignList = TableCreator.initCampaignTable()
        donationList = TableCreator.initDonationTable()
    }

    def 'getCampaign_returnsRightCampaigns'(){
        setup:
        for(campaign in campaignList){
            when:
                Campaign ans = CampaignService.campaignService.getCampaign(campaign.id)
            then:
                campaign==ans
        }
    }

    def 'queryForId_returnsNull_WhenThereIsNoSuchCampaign'(){
        setup:
            List<Integer> badIds = new ArrayList<Integer>(){{add(0);add(-1);add(15)}}
        for(id in badIds){
            when:
                Campaign ans = CampaignService.campaignService.getCampaign(id)
            then:
                null==ans
        }
    }

    def 'getMoneyForCampaign_returnsRightAnswer'(){
        when:
            int sum = CampaignService.campaignService.getRaisedMoneyForCampaign(campaignId)
            for(Donation donation: donationList){
                if(campaignId==donation.toCampaign.id){
                    expectedAns+=donation.value
                }
            }
        then:
            sum==expectedAns
        where:
            campaignId<<(1..4)
            expectedAns = 0
    }

    def cleanup(){
        TableCleaner.cleanCampaignTable()
        TableCleaner.cleanOrganiserTable()
        TableCleaner.cleanScientistTable()
        TableCleaner.cleanOrganisationTable()
        TableCleaner.cleanDomainTable()
        TableCleaner.cleanUserTable()
        TableCleaner.cleanDonationTable()
    }
}
