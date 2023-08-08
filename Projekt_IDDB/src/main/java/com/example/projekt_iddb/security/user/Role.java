package com.example.projekt_iddb.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  Permission.ADMIN_READ,
                  Permission.ADMIN_UPDATE,
                  Permission.ADMIN_DELETE,
                  Permission.ADMIN_CREATE,
                  Permission.PATIENT_READ,
                  Permission.PATIENT_UPDATE,
                  Permission.PATIENT_DELETE,
                  Permission.PATIENT_CREATE,
                  Permission.DOCTOR_READ,
                  Permission.DOCTOR_UPDATE,
                  Permission.DOCTOR_DELETE,
                  Permission.DOCTOR_CREATE
          )
  ),

  PATIENT(
          Set.of(
                  Permission.PATIENT_READ,
                  Permission.PATIENT_UPDATE,
                  Permission.PATIENT_DELETE,
                  Permission.PATIENT_CREATE
          )
  ),
  DOCTOR(
          Set.of(
          Permission.DOCTOR_READ,
          Permission.DOCTOR_UPDATE,
          Permission.DOCTOR_DELETE,
          Permission.DOCTOR_CREATE
          )
  )
  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
