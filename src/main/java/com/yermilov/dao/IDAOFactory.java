package com.yermilov.dao;

public interface IDAOFactory {
    UserDAO getUserDAO();
    AdminDAO getAdminDAO();
}
