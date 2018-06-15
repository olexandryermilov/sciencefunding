package com.yermilov.dao.springdata;

import com.yermilov.domain.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin,Long> {
    Admin getAdminByEmail(String email);
}
