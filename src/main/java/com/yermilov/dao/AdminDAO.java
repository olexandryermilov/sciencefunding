package com.yermilov.dao;

import com.yermilov.domain.Admin;
import com.yermilov.exception.DAOException;

public interface AdminDAO {
    int create(Admin admin) throws DAOException;
    Admin queryForEmail(String email) throws DAOException;
}
