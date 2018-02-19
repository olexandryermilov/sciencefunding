package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "campaign")
public class Campaign {
    @DatabaseField(generatedId=true)
    private Integer id;
    @DatabaseField(foreign = true)
    private Organiser organiser;
    @DatabaseField(columnName = "need_to_raise")
    private int needToRaise;
    @DatabaseField
    private String name;
    @DatabaseField(foreign = true)
    private Domain domain;
    @DatabaseField
    private String description;

    public Campaign() {
    }

    public Campaign(Organiser organiser, int needToRaise, String name, Domain domain, String description) {
        this.organiser = organiser;
        this.needToRaise = needToRaise;
        this.name = name;
        this.domain = domain;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNeedToRaise() {
        return needToRaise;
    }

    public void setNeedToRaise(int needToRaise) {
        this.needToRaise = needToRaise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
