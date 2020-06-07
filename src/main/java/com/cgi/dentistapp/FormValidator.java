package com.cgi.dentistapp;

import com.cgi.dentistapp.dao.entity.DentistEntity;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.DentistService;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class FormValidator implements Validator {

    @Autowired
    DentistVisitService dentistVisitService;

    @Autowired
    DentistService dentistService;

    @Override
    public boolean supports(Class<?> aClass) {
        return DentistVisitDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dentistName",
                "NotEmpty.dentistVisitDTO.dentistName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "visitTime",
                "NotNull.dentistVisitDTO.visitTime");

        DentistVisitDTO dentistVisitDTO = (DentistVisitDTO) target;
        List<DentistEntity> dentists = dentistService.getAllDentists();

        if (dentistVisitDTO.getDentistName().isEmpty()) return;

        int index = Integer.parseInt(dentistVisitDTO.getDentistName()) - 1;
        LocalDateTime selectedTime = dentistVisitDTO.getVisitTime();

        if (selectedTime == null) return;

        if (selectedTime.compareTo(LocalDateTime.now()) < 0)  {
            errors.rejectValue("visitTime", "Invalid.time.past");
        }

        for (DentistVisitEntity visit : dentistVisitService.findAll()) {
            if (visit.getDentistName().equals(dentists.get(index).getName())) {
                LocalDateTime bookedTime = visit.getVisitTime();
                if (selectedTime.compareTo(bookedTime) == 0
                        || selectedTime.compareTo(bookedTime.minusMinutes(60)) > 0
                        && selectedTime.compareTo(bookedTime.plusMinutes(60)) < 0) {
                    errors.rejectValue("visitTime", "Invalid.time.taken");
                }
            }
        }

        if (selectedTime.getHour() < 8 || selectedTime.getHour() > 16
                || selectedTime.getDayOfWeek() == DayOfWeek.SATURDAY
                || selectedTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            errors.rejectValue("visitTime", "Invalid.time.period");
        }
    }
}
