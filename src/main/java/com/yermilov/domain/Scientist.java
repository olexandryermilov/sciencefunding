package com.yermilov.domain;

public class Scientist {
    private int id;
    private int userId;
    private int domainId;
    private int organisationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scientist scientist = (Scientist) o;

        if (id != scientist.id) return false;
        if (userId != scientist.userId) return false;
        if (domainId != scientist.domainId) return false;
        return organisationId == scientist.organisationId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + domainId;
        result = 31 * result + organisationId;
        return result;
    }

    @Override
    public String toString() {
        return "Scientist{" +
                "id=" + id +
                ", userId=" + userId +
                ", domainId=" + domainId +
                ", organisationId=" + organisationId +
                '}';
    }
}
