package com.yermilov.dao

import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

import javax.persistence.Table
import java.util.function.Predicate
import java.util.function.ToIntFunction

class DonationDaoTest extends Specification {

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
    def 'create_createsRecord'(){
        setup:
            Donation donation = new Donation(userList.get(0),campaignList.get(0),100, "From our table to yours")
            donationList<<donation
            donation.id=donationList.size()
        when:
            DAOFactory.instance.donationDAO.create(donation)
        then:
            donationList==DAOFactory.instance.donationDAO.donationDao.queryForAll()
    }
    def 'createSecond_createsRecord'(){
        setup:
            Donation donation = new Donation(userList.get(0),campaignList.get(0),100, "From our table to yours")
            donationList<<donation
            donation.id=donationList.size()
        when:
            DAOFactory.instance.donationDAO.create(userList.get(0).id,campaignList.get(0).id,100, "From our table to yours")
        then:
            donationList==DAOFactory.instance.donationDAO.donationDao.queryForAll()
    }

    def 'getMoneyForCampaign_returnsRightAnswer'(){
        when:
            int sum = DAOFactory.instance.donationDAO.getMoneyForCampaign(campaignId)
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

    def 'getSize_returnsRightSize'(){
        setup:
            int RIGHT_ANS =donationList.size()
        when:
            int size = DAOFactory.instance.donationDAO.getSize()
        then:
            RIGHT_ANS==size
    }

    def 'getSize_ReturnsRightSize_AfterCreatingNewDonation'(){
        setup:
            Donation donation = new Donation(userList.get(0),campaignList.get(0),400,"TY")
            donationList.add(donation)
            DAOFactory.instance.donationDAO.create(donation)
        when:
            int size = DAOFactory.instance.donationDAO.getSize()
        then:
            donationList.size()==size
    }

    def 'getSize_ReturnsRightSize_AfterDeletingDonation'(){
        setup:
            DAOFactory.instance.donationDAO.donationDao.delete(donationList.get(0))
            donationList.remove(donationList.get(0))
        when:
            int size = DAOFactory.instance.donationDAO.getSize()
        then:
            donationList.size()==size
    }

    def 'getLimitedAmountOfDonations_returnsRightAmount'(){
        setup:
            List<Donation> list = donationList.subList(1,3)
        when:
            List<Donation> ans = DAOFactory.instance.donationDAO.getLimitedAmountOfDonations(2,1)
        then:
            list==ans
    }
    def 'getLimitedAmountOfDonations_returnsRightAmount_WithUserIdSpecified'(){
        setup:
            List<Donation> list = donationList.stream().filter({ (it.fromUser.id == userList.get(1).id) })
                    .collect().toList()
        when:
            List<Donation> ans = DAOFactory.instance.donationDAO.getLimitedAmountOfDonations(2,0,userList.get(1).id)
        then:
            list==ans
    }

    def 'getSize_returnsRightSize_WithUserIdSpecified'(){
        when:
            int size = DAOFactory.instance.donationDAO.getSize(userId)
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
        TableCleaner.cleanDonationTable()
        TableCleaner.cleanCampaignTable()
        TableCleaner.cleanOrganiserTable()
        TableCleaner.cleanScientistTable()
        TableCleaner.cleanOrganisationTable()
        TableCleaner.cleanDomainTable()
        TableCleaner.cleanUserTable()
    }
}
