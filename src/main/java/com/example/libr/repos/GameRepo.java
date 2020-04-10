package com.example.libr.repos;

import com.example.libr.domain.GameBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<GameBook, Integer> {
    //GameBook findById(Long id);
}
