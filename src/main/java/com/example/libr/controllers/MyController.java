package com.example.libr.controllers;

import com.example.libr.domain.Message;
import com.example.libr.domain.MyUser;
import com.example.libr.domain.Session;
import com.example.libr.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin()
@RestController
public class MyController {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SessionRepo sessionRepo;

    public static void main(String[] args) {
        SpringApplication.run(MyController.class, args);
    }


    @CrossOrigin()
    @PostMapping("message/{idUser}")
    public List<Message> addMessage(@PathVariable Long idUser, @RequestBody String message) {
        if (checkSession(idUser)) {
            Message newMessage = new Message();
            MyUser user = (userRepo.findById(idUser).get());
            newMessage.setLogin(user.getLogin());
            newMessage.setText(message);
            messageRepo.save(newMessage);
            List<Message> messages = messageRepo.findAll();
            return messages;
        } else {
            return null;
        }
    }

    @CrossOrigin()
    @GetMapping("message/{idUser}")
    public List<Message> showMessage(@PathVariable Long idUser) {
        if (checkSession(idUser)) {
            List<Message> messages = messageRepo.findAll();
            return messages;
        } else {
            return null;
        }
    }

    public Boolean checkSession(Long idUser) {
        Session mySession = sessionRepo.findByIdUser(idUser);
        if (mySession == null) {
            return false;
        } else {
            LocalDateTime MyData = mySession.getMyTime();
            LocalDateTime NowData = LocalDateTime.now();
            if (MyData.compareTo(NowData) > 0) {
                return true;
            } else {
                sessionRepo.delete(mySession);
                return false;
            }
        }
    }


}
