package com.cgi.dentistapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitDao dentistVisitDao;

    public void addOrUpdateVisit(DentistVisitEntity dentistVisitEntity) {
        if (dentistVisitEntity.getId() == null || findById(dentistVisitEntity.getId()) == null) {
            dentistVisitDao.create(dentistVisitEntity);
        } else {
            dentistVisitDao.update(dentistVisitEntity);
        }
    }

    public void addOrUpdateVisit(long id) {
        dentistVisitDao.update(findById(id));
    }

    public void deleteVisit(long id) {
        dentistVisitDao.deleteVisit(id);
    }

    public List<DentistVisitEntity> findAll() {
        return dentistVisitDao.getAllVisits();
    }

    public DentistVisitEntity findById(long id) {
        return dentistVisitDao.getVisit(id);
    }

    public List<DentistVisitEntity> findBySearchString(String searchString) {
        return dentistVisitDao.getVisit(searchString);
    }

}
