package com.example.libr.repos;

import com.example.libr.domain.BookQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepo extends JpaRepository<BookQueue, Long> {
    BookQueue findByUseridAndBookid(Long userid, Long bookid);

    BookQueue findFirstByBookid(Long bookid);
}