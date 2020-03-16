package com.example.libr.repos;

import com.example.libr.domain.Roles;
import com.example.libr.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RolesRepo extends JpaRepository<Roles, Long> {
    Roles findByRole(String role);
}
