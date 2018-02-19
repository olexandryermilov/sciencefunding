package com.yermilov.configuration;

import com.yermilov.command.Command;
import com.yermilov.command.CommandFactory;

import java.util.HashMap;
import java.util.Map;

public class SecurityConfiguration {
    private Map<String,String> grant = new HashMap<>();
    private final static SecurityConfiguration configuration = new SecurityConfiguration();
    public final static String ALL = "ALL";
    public final static String AUTH = "AUTH";
    public final static String ADMIN = "ADMIN";
    public final static String NO_ACCESS = "NO_ACCESS";
    private SecurityConfiguration(){
        grant.put(CommandFactory.LOGIN,ALL);
        grant.put(CommandFactory.REGISTRATION,ALL);
        grant.put(CommandFactory.LOGOUT,AUTH);
        grant.put(CommandFactory.USERS,ADMIN);
        grant.put(CommandFactory.CHANGE_STATE,ADMIN);
        grant.put(CommandFactory.ADMIN_LOGIN,ALL);
        grant.put(CommandFactory.ADD_SCIENTIST,ADMIN);
        grant.put(CommandFactory.ADMIN_LOGOUT,ALL);
        grant.put(CommandFactory.ADD_CAMPAIGN,AUTH);
        grant.put(CommandFactory.CAMPAIGNS,AUTH);
        grant.put(CommandFactory.CHANGE_CAMPAIGN_STATE,ADMIN);
        grant.put("/",ALL);
        grant.put("registration.jsp",ALL);
        grant.put("login.jsp",ALL);
        grant.put("index.jsp",ALL);
        grant.put("admin",ALL);
        grant.put("users.jsp",ADMIN);
        grant.put(CommandFactory.ADD_CAMPAIGN+".jsp",AUTH);
        grant.put("header.jsp",NO_ACCESS);
        grant.put("main.jsp",NO_ACCESS);
        grant.put(CommandFactory.CAMPAIGNS+".jsp",AUTH);

    }

    public static SecurityConfiguration getInstance() {
        return configuration;
    }

    public String security(String command){
        return grant.getOrDefault(command,ALL);
    }
}
