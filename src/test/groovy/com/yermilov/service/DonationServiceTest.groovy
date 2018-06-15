package com.yermilov.service

import com.yermilov.admin.service.dao.DonationService
import com.yermilov.dao.DAOFactory
import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class DonationServiceTest extends Specification {
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

    def 'getTableSize_returnsRightSize'(){
        setup:
            int RIGHT_ANS =donationList.size()
        when:
            int size = DonationService.donationService.getTableSize()
        then:
            RIGHT_ANS==size
    }

    def 'getTableSize_ReturnsRightSize_AfterCreatingNewDonation'(){
        setup:
            Donation donation = new Donation(userList.get(0),campaignList.get(0),400,"TY")
            donationList.add(donation)
            DAOFactory.instance.donationDAO.create(donation)
        when:
            int size = DonationService.donationService.getTableSize()
        then:
            donationList.size()==size
    }

    def 'getTableSize_ReturnsRightSize_AfterDeletingDonation'(){
        setup:
            DAOFactory.instance.donationDAO.donationDao.delete(donationList.get(0))
            donationList.remove(donationList.get(0))
        when:
            int size = DonationService.donationService.getTableSize()
        then:
            donationList.size()==size
    }

    def 'getLimitedAmountOfDonations_returnsRightAmount'(){
        setup:
            List<Donation> list = donationList.subList(1,2)
        when:
            List<Donation> ans = DonationService.donationService.getDonations(1,1)
        then:
            list==ans
    }

    def 'getDonations_returnsRightAmount_WithUserIdSpecified'(){
        setup:
            List<Donation> list = donationList.stream().filter({ (it.fromUser.id == userList.get(1).id) })
                .collect().toList()
        when:
            List<Donation> ans = DonationService.donationService.getDonations(0,2,userList.get(1).id)
        then:
            list==ans
    }

    def 'getTableSize_returnsRightSize_WithUserIdSpecified'(){
        when:
            int size = DonationService.donationService.getTableSize(userId)
            for(Donation donation: donationList){
            if(userId==donation.fromUser.id){
                expectedAns++
            }
        }
        then:
            expectedAns==size
        where:
            userId <<(1..userList.size())
            expectedAns=0

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
