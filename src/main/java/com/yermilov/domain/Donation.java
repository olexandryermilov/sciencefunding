package com.yermilov.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "donation")
public class Donation {
    @DatabaseField(generatedId=true)
    private Integer id;
    @DatabaseField(foreign = true, columnName = "from_user_id")
    private User fromUser;
    @DatabaseField(foreign = true, columnName = "to_campaign_id")
    private Campaign toCampaign;
    @DatabaseField
    private int value;
    @DatabaseField
    private String comment;

    public Donation() {
    }

    public Donation(User fromUser, Campaign toCampaign, int value, String comment) {
        this.fromUser = fromUser;
        this.toCampaign = toCampaign;
        this.value = value;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public Campaign getToCampaign() {
        return toCampaign;
    }

    public void setToCampaign(Campaign toCampaign) {
        this.toCampaign = toCampaign;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Donation donation = (Donation) o;

        if (value != donation.value) return false;
        if (id != null ? !id.equals(donation.id) : donation.id != null) return false;
        if (fromUser != null ? !fromUser.getId().equals(donation.fromUser.getId()) : donation.fromUser != null) return false;
        if (toCampaign != null ? !toCampaign.getId().equals(donation.toCampaign.getId()) : donation.toCampaign != null) return false;
        return comment != null ? comment.equals(donation.comment) : donation.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fromUser != null ? fromUser.hashCode() : 0);
        result = 31 * result + (toCampaign != null ? toCampaign.hashCode() : 0);
        result = 31 * result + value;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", fromUser=" + fromUser +
                ", toCampaign=" + toCampaign +
                ", value=" + value +
                ", comment='" + comment + '\'' +
                '}';
    }
    Donation plus(Donation other) {
        return new Donation(fromUser,toCampaign,value+other.value,comment);
    }
}
