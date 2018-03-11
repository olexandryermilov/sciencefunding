package com.yermilov.service

import com.yermilov.dao.DAOFactory
import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class DonateServiceTest extends Specification {
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

    def 'donateMoney_createsRecordInDatabase'(){
        setup:
            Donation donation = new Donation(userList.get(0),campaignList.get(0),100, "From our table to yours")
            donationList<<donation
            donation.id=donationList.size()
        when:
            DonateService.donateService.donateMoney(userList.get(0).id,campaignList.get(0).id,100,"From our table to yours")
        then:
            donationList==DAOFactory.instance.donationDAO.donationDao.queryForAll()
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
