package com.yermilov.dao;

import com.yermilov.domain.Scientist;

public interface IDAOFactory {
    UserDAO getUserDAO();
    AdminDAO getAdminDAO();
    ScientistDAO getScientistDAO();
}
