package com.example.libr.repos;

import com.example.libr.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<MyUser, Long> {
    MyUser findByLogin(String login);
}
