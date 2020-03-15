package com.example.libr.repos;

import com.example.libr.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
    Session findByIdUser(Long idUser);
}
