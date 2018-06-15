package com.yermilov.dao;

import com.yermilov.domain.Campaign;
import com.yermilov.domain.User;
import com.yermilov.exception.DAOException;

import java.util.List;

public interface CampaignDAO {
    int create(Campaign campaign) throws DAOException;
    int create(User user, int needToRaise, String name, int domainId, String description) throws DAOException;
    long getSize(boolean isActive) throws DAOException;
    List<Campaign> getLimitedAmountOfActiveCampaigns(int limit, int skip) throws DAOException;
    List<Campaign> getLimitedAmountOfCampaigns(int limit, int skip) throws DAOException;
    int changeCampaignState(int campaignid) throws DAOException;
    Campaign queryForId(int campaignId) throws DAOException;
    List<Campaign> getCampaignsLike(String text, boolean isActive) throws DAOException;
}
