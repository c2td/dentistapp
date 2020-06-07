package com.cgi.dentistapp.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "visit_time")
    private LocalDateTime visitTime;

    @NotNull
    @Column(name = "dentist_name")
    private String dentistName;

    public DentistVisitEntity() {}

    public DentistVisitEntity(String dentistName, LocalDateTime visitTime) {
        this.setDentistName(dentistName);
        this.setVisitTime(visitTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

}
