package com.example.libr.repos;

import com.example.libr.domain.Relations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRepo extends JpaRepository<Relations, Long> {
    List<Relations> findAllByUserid(Long userid);

    Relations findByUseridAndBooksId(Long userid, Long book_id);
    Relations findByBooksId(Long book_id);
    List<Relations> findAllByBooksId(Long book_id);
}