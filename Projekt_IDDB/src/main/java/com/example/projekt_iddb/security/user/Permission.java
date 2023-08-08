package com.example.projekt_iddb.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    PATIENT_READ("patient:read"),
    PATIENT_UPDATE("patient:update"),
    PATIENT_CREATE("patient:create"),
    PATIENT_DELETE("patient:delete"),
    DOCTOR_READ("doctor:read"),
    DOCTOR_UPDATE("doctor:update"),
    DOCTOR_CREATE("doctor:create"),
    DOCTOR_DELETE("doctor:delete")
    ;

    @Getter
    private final String permission;
}
