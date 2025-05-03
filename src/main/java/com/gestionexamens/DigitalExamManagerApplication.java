package com.gestionexamens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitalExamManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalExamManagerApplication.class, args);
    }
}
