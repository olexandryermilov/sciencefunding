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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scientist scientist = (Scientist) o;

        if (id != null ? !id.equals(scientist.id) : scientist.id != null) return false;
        if (user != null ? !user.getId().equals(scientist.user.getId()) : scientist.user != null) return false;
        if (domain != null ? !domain.getId().equals(scientist.domain.getId()) : scientist.domain != null) return false;
        return organisation != null ? organisation.getId().equals(scientist.organisation.getId()) : scientist.organisation == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (organisation != null ? organisation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Scientist{" +
                "id=" + id +
                ", user=" + user +
                ", domain=" + domain +
                ", organisation=" + organisation +
                '}';
    }
}
