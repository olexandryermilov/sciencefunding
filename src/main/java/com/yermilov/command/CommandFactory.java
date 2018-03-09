package com.yermilov.command;

import com.yermilov.admin.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final static CommandFactory factory = new CommandFactory();
    public final static String LOGIN = "login";
    public final static String REGISTRATION = "registration";
    public final static String LOGOUT = "logout";
    public final static String USERS = "users";
    public final static String ADMIN_LOGIN="adminLogin";
    public final static String ADMIN_LOGOUT="adminLogout";
    public final static String CHANGE_STATE = "delete";
    public final static String ADD_SCIENTIST = "addScientist";
    public final static String ADD_CAMPAIGN = "addCampaign";
    public final static String CAMPAIGNS = "campaigns";
    public final static String CHANGE_CAMPAIGN_STATE = "changeCampaignState";
    public final static String WATCH_CAMPAIGN = "campaign";
    public final static String DONATE = "donate";
    private Map<String,Command> commandMap = new HashMap<>();
    private CommandFactory(){
        commandMap.put(LOGIN,new com.yermilov.command.LoginCommand());
        commandMap.put(REGISTRATION,new RegistrationCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(USERS,new UsersCommand());
        commandMap.put(ADMIN_LOGIN,new com.yermilov.admin.command.LoginCommand());
        commandMap.put(CHANGE_STATE, new ChangeUserStateCommand());
        commandMap.put(ADMIN_LOGOUT,new com.yermilov.admin.command.LogoutCommand());
        commandMap.put(ADD_SCIENTIST, new AddScientistCommand());
        commandMap.put(ADD_CAMPAIGN, new AddCampaignCommand());
        commandMap.put(CAMPAIGNS, new CampaignsCommand());
        commandMap.put(CHANGE_CAMPAIGN_STATE, new ChangeCampaignStateCommand());
        commandMap.put(WATCH_CAMPAIGN,new WatchCampaignCommand());
        commandMap.put(DONATE,new DonateCommand());
    }
    public static CommandFactory getInstance() {
        return factory;
    }

    public Command getCommand(String command){
        return commandMap.get(command);
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

}
