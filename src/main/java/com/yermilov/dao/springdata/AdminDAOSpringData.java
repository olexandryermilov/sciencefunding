package com.yermilov.dao.springdata;

import com.yermilov.dao.AdminDAO;
import com.yermilov.domain.Admin;
import com.yermilov.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@org.springframework.stereotype.Repository
public class AdminDAOSpringData implements AdminDAO {

    private AdminRepository adminRepository;

    @Autowired
    public AdminDAOSpringData(AdminRepository adminRepository){
        this.adminRepository=adminRepository;
    }

    @Override
    public int create(Admin admin) throws DAOException {
        adminRepository.save(admin);
        return 1;
    }

    @Override
    public Admin queryForEmail(String email) throws DAOException {
        return adminRepository.getAdminByEmail(email);
    }
}
