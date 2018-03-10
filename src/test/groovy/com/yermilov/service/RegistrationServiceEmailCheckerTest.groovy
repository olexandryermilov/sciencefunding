package com.yermilov.service

import com.yermilov.domain.*
import com.yermilov.tableworkers.TableCleaner
import com.yermilov.tableworkers.TableCreator
import com.yermilov.transaction.DatabaseConnector
import com.yermilov.transaction.H2ConnectionPool
import spock.lang.Shared
import spock.lang.Specification

class RegistrationServiceEmailCheckerTest extends Specification {

    private List<Campaign> campaignList

    @Shared
    private List<Donation> donationList
    def setup(){
        DatabaseConnector.getInstance().setORMLiteConnectionSource(H2ConnectionPool.getInstance().getConnectionSource())
    }

    def 'isEmailCorrect'(){
        when:
            boolean isCorrect = RegistrationService.registrationService.isCorrectEmail(email)
        then:
            isCorrect == expectedAnswer
        where:
            email                                      | expectedAnswer
            "olexandr.yermilov@gmail.com"              | true
            "abc@gmail."                               | false
            "olex@@gmail.com"                          | false
            "test.test@subdomain.subdomain.domain.com" | true
            "email@gmail.com"                          | true
            "o@lex@gmail.com"                          | false

    }
}
