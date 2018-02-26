package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "campaign")
public class Campaign {
    public static final String IS_ACTIVE_FIELD_NAME="is_active";
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
    @DatabaseField(columnName = "is_active", defaultValue = "1")
    private Integer isActive;


    public Campaign() {
    }

    public Campaign(Organiser organiser, int needToRaise, String name, Domain domain, String description) {
        this.organiser = organiser;
        this.needToRaise = needToRaise;
        this.name = name;
        this.domain = domain;
        this.description = description;
        this.isActive=1;
    }

    public Integer getId() {
        return id;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", organiser=" + organiser +
                ", needToRaise=" + needToRaise +
                ", name='" + name + '\'' +
                ", domain=" + domain +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        if (needToRaise != campaign.needToRaise) return false;
        if (id != null ? !id.equals(campaign.id) : campaign.id != null) return false;
        if (organiser != null ? !organiser.getId().equals(campaign.organiser.getId()) : campaign.organiser != null) return false;
        if (name != null ? !name.equals(campaign.name) : campaign.name != null) return false;
        if (domain != null ? !domain.getId().equals(campaign.domain.getId()) : campaign.domain != null) return false;
        if (description != null ? !description.equals(campaign.description) : campaign.description != null)
            return false;
        return isActive != null ? isActive.equals(campaign.isActive) : campaign.isActive == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (organiser != null ? organiser.hashCode() : 0);
        result = 31 * result + needToRaise;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }
}
