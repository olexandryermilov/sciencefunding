package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "scientist")
public class Scientist {
    @DatabaseField(generatedId =true)
    private Integer id;
    @DatabaseField(foreign = true)
    private User user;
    @DatabaseField (foreign = true)
    private Domain domain;
    @DatabaseField(foreign = true)
    private Organisation organisation;

    public Scientist() {
    }

    public Scientist(User user, Domain domain, Organisation organisation) {
        this.user = user;
        this.domain = domain;
        this.organisation = organisation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
