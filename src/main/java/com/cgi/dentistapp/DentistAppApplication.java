package com.cgi.dentistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DentistAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentistAppApplication.class, args);
    }

    @Bean
    public FormValidator formValidator() {
        return new FormValidator();
    }
}
