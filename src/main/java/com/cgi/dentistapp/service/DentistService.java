package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistDao;
import com.cgi.dentistapp.dao.entity.DentistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DentistService {

    @Autowired
    private DentistDao dentistDao;

    public void addDentist(String dentistName) {
        DentistEntity dentist = new DentistEntity(dentistName);
        dentistDao.create(dentist);
    }

    public List<DentistEntity> getAllDentists() {
        return dentistDao.getAllDentists();
    }

}
