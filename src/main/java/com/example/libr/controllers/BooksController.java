package com.example.libr.controllers;

import com.example.libr.domain.Books;
import com.example.libr.domain.Message;
import com.example.libr.domain.Session;
import com.example.libr.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin()
@RestController
public class BooksController {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private BooksRepo booksRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private RelationRepo relationsRepo;


}
