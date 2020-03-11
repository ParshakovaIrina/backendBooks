package com.example.libr.repos;

import com.example.libr.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepo extends JpaRepository<Books, Long> {
    Books findByName(String name);
}

