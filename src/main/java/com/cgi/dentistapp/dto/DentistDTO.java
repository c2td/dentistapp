package com.cgi.dentistapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DentistDTO {

    @NotNull
    @Size(min = 1, max = 50)
    String dentistName;

    public DentistDTO(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }
}
