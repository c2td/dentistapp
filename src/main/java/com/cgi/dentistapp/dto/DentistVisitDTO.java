package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.*;

public class DentistVisitDTO {

    private long id;

    @Size(min = 1, max = 50)
    String dentistName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    LocalDateTime visitTime;

    public DentistVisitDTO() {}

    public DentistVisitDTO(String dentistName, LocalDateTime visitTime) {
        this.dentistName = dentistName;
        this.visitTime = visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
