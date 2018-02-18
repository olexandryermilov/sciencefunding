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
    public final static String DELETE = "delete";
    private Map<String,Command> commandMap = new HashMap<>();
    private CommandFactory(){
        commandMap.put(LOGIN,new com.yermilov.command.LoginCommand());
        commandMap.put(REGISTRATION,new RegistrationCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(USERS,new UsersCommand());
        commandMap.put(ADMIN_LOGIN,new com.yermilov.admin.command.LoginCommand());
        commandMap.put(DELETE, new DeleteCommand());
        commandMap.put(ADMIN_LOGOUT,new com.yermilov.admin.command.LogoutCommand());
    }
    public static CommandFactory getInstance() {
        return factory;
    }

    public Command getCommand(String command){
        return commandMap.get(command);
    }
}
