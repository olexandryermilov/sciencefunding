package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "campaign")
public class Campaign {
    @DatabaseField(generatedId=true)
    private Integer id;
    //@DatabaseField
    private String organiserId;
    @DatabaseField(foreign = true)
    private Organiser organiser;
    @DatabaseField
    private int needToRaise;
    @DatabaseField
    private String name;
    //@DatabaseField
    private int domainId;
    @DatabaseField(foreign = true)
    private Domain domain;
    @DatabaseField
    private String description;

    public Campaign() {
    }

    public Campaign(String organiserId, int needToRaise, String name, int domainId, String description) {
        this.organiserId = organiserId;
        this.needToRaise = needToRaise;
        this.name = name;
        this.domainId = domainId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
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

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        if (id != campaign.id) return false;
        if (needToRaise != campaign.needToRaise) return false;
        if (domainId != campaign.domainId) return false;
        if (!organiserId.equals(campaign.organiserId)) return false;
        if (!name.equals(campaign.name)) return false;
        return description.equals(campaign.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + organiserId.hashCode();
        result = 31 * result + needToRaise;
        result = 31 * result + name.hashCode();
        result = 31 * result + domainId;
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", organiserId='" + organiserId + '\'' +
                ", needToRaise=" + needToRaise +
                ", name='" + name + '\'' +
                ", domainId=" + domainId +
                ", description='" + description + '\'' +
                '}';
    }
}
