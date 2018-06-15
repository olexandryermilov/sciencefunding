package com.yermilov.admin.service.repository;

import com.yermilov.dao.AdminDAO;
import com.yermilov.dao.DAOFactory;
import com.yermilov.dao.IDAOFactory;
import com.yermilov.dao.springdata.AdminRepository;
import com.yermilov.domain.Admin;
import com.yermilov.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * Service for authorization into admin interface
 * @see com.yermilov.admin.command.LoginCommand
 */
@Service
public class LoginService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private AdminRepository adminRepository;
    private IDAOFactory daoFactory;
    @Autowired
    private LoginService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }
    /**
     *
     * @param email Email
     * @param password Password (not encrypted)
     * @return Admin object if there is an admin in database with such email and password, null otherwise
     * @throws DAOException Re-throws DAOException from AdminDAOORMLite
     */
    public Admin getAdmin( String email, String password) throws DAOException {
        Admin admin = adminRepository.getAdminByEmail(email);
        if(admin==null){
            return null;
        }
        LOGGER.info(email+" tried to login into admin.");
        return (password.equals(admin.getPassword()))?admin:null;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
