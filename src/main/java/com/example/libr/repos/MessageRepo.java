package com.example.libr.repos;

import com.example.libr.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findAll();

   // List<Message> findAllByIdBook(Long idBook);

}