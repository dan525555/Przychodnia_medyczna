package com.example.projekt_iddb;

import com.example.projekt_iddb.security.auth.AuthenticationService;
import com.example.projekt_iddb.security.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.projekt_iddb.security.user.Role.*;

@SpringBootApplication
public class ProjektIddbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektIddbApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var patient = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("patient@mail.com")
                    .password("password")
                    .role(PATIENT)
                    .build();
            System.out.println("Patient token: " + service.register(patient).getAccessToken());

            var doctor = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("doctor@mail.com")
                    .password("password")
                    .role(DOCTOR)
                    .build();
            System.out.println("Doctor token: " + service.register(doctor).getAccessToken());

        };
    }
}
