package com.yermilov.domain;

public class Donation {
    private int id;
    private int fromUserId;
    private int toCampaignId;
    private int value;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToCampaignId() {
        return toCampaignId;
    }

    public void setToCampaignId(int toCampaignId) {
        this.toCampaignId = toCampaignId;
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

        if (id != donation.id) return false;
        if (fromUserId != donation.fromUserId) return false;
        if (toCampaignId != donation.toCampaignId) return false;
        if (value != donation.value) return false;
        return comment != null ? comment.equals(donation.comment) : donation.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + fromUserId;
        result = 31 * result + toCampaignId;
        result = 31 * result + value;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", fromUserId=" + fromUserId +
                ", toCampaignId=" + toCampaignId +
                ", value=" + value +
                ", comment='" + comment + '\'' +
                '}';
    }
}
