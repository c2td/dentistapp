package com.cgi.dentistapp.dao;

import com.cgi.dentistapp.dao.entity.DentistEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DentistDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(DentistEntity dentist) {
        entityManager.persist(dentist);
    }

    public List<DentistEntity> getAllDentists() {
        return entityManager.createQuery("SELECT e FROM DentistEntity e").getResultList();
    }
}
