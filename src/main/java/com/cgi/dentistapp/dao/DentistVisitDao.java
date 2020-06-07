package com.cgi.dentistapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DentistVisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(DentistVisitEntity visit) {
        entityManager.persist(visit);
    }

    public void update(DentistVisitEntity visit) {
        String name = visit.getDentistName();
        LocalDateTime time = visit.getVisitTime();
        long id = visit.getId();
        Query query = entityManager.createQuery("UPDATE DentistVisitEntity e SET visitTime = :time, " +
                "dentistName = :name WHERE e.id = :id");
        query.setParameter("time", time);
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<DentistVisitEntity> getAllVisits() {
        return entityManager.createQuery("SELECT e FROM DentistVisitEntity e").getResultList();
    }

    public DentistVisitEntity getVisit(long id) {
        Query query = entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE e.id = :id");
        query.setParameter("id", id);
        return (DentistVisitEntity) query.getResultList().get(0);
    }

    public List<DentistVisitEntity> getVisit(String searchString) {
        Query query = entityManager.createQuery("SELECT e FROM DentistVisitEntity e " +
                "WHERE e.dentistName LIKE :searchString");
        query.setParameter("searchString", MatchMode.ANYWHERE.toMatchString(searchString));
        return query.getResultList();
    }

    public void deleteVisit(long id) {
        Query query = entityManager.createQuery("DELETE FROM DentistVisitEntity e WHERE e.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
