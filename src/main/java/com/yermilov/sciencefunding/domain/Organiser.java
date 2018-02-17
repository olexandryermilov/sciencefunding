package com.yermilov.sciencefunding.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "organiser")
public class Organiser {
    @DatabaseField(generatedId=true)
    private Integer id;
    @DatabaseField
    private int scientistId;
    @DatabaseField
    private int organisationId;

    public Organiser(int scientistId, int organisationId) {
        this.scientistId = scientistId;
        this.organisationId = organisationId;
    }

    public Organiser() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
