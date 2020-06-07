package com.cgi.dentistapp.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "dentist")
public class DentistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    public DentistEntity() { }

    public DentistEntity(String name) {
        this.setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
