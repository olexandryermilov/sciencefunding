package com.yermilov.domain;

public class Organiser {
    private int id;
    private int scientistId;
    private int organisationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScientistId() {
        return scientistId;
    }

    public void setScientistId(int scientistId) {
        this.scientistId = scientistId;
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

        Organiser organiser = (Organiser) o;

        if (id != organiser.id) return false;
        if (scientistId != organiser.scientistId) return false;
        return organisationId == organiser.organisationId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + scientistId;
        result = 31 * result + organisationId;
        return result;
    }

    @Override
    public String toString() {
        return "Organiser{" +
                "id=" + id +
                ", scientistId=" + scientistId +
                ", organisationId=" + organisationId +
                '}';
    }
}
