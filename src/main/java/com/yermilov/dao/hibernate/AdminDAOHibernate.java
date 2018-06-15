package com.yermilov.dao.hibernate;

import com.yermilov.dao.AdminDAO;
import com.yermilov.domain.Admin;
import com.yermilov.exception.DAOException;
import org.hibernate.SessionFactory;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class AdminDAOHibernate implements AdminDAO {
    private EntityManagerFactory sessionFactory;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    public AdminDAOHibernate(EntityManagerFactory sessionFactory){
        this.entityManager = Persistence
                .createEntityManagerFactory("org.hibernate.tutorial.jpa")
                .createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }
    @Override
    public int create(Admin admin) throws DAOException {
        entityManager.getTransaction().begin();
        entityManager.persist(admin);
        entityManager.getTransaction().commit();
        return 1;
    }

    @Override
    public Admin queryForEmail(String email) throws DAOException {
        CriteriaQuery<Admin> criteriaQuery =criteriaBuilder.createQuery(Admin.class);
        Root<Admin> root = criteriaQuery.from(Admin.class);
        criteriaQuery.select(root);
        //criteriaQuery.where(criteriaBuilder.equal(root.get(Admin.class)))
        return null;
    }
}
