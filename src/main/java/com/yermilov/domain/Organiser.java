package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "organiser")
public class Organiser {
    @DatabaseField(generatedId=true)
    private Integer id;
    @DatabaseField(foreign = true)
    private Scientist scientist;
    @DatabaseField(foreign = true)
    private Organisation organisation;

    public Organiser(Scientist scientist) {
        this.scientist = scientist;
        this.organisation = null;
    }

    public Organiser(Organisation organisation){
        this.scientist=null;
        this.organisation=organisation;
    }
    public Organiser() {
    }

    public Scientist getScientist() {
        return scientist;
    }

    public void setScientist(Scientist scientist) {
        this.scientist = scientist;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organiser organiser = (Organiser) o;

        if (id != null ? !id.equals(organiser.id) : organiser.id != null) return false;
        if (scientist != null ? !scientist.equals(organiser.scientist) : organiser.scientist != null) return false;
        return organisation != null ? organisation.equals(organiser.organisation) : organiser.organisation == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scientist != null ? scientist.hashCode() : 0);
        result = 31 * result + (organisation != null ? organisation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organiser{" +
                "id=" + id +
                ", scientist=" + scientist +
                ", organisation=" + organisation +
                '}';
    }
}
