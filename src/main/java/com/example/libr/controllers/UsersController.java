package com.example.libr.controllers;

import com.example.libr.domain.Message;
import com.example.libr.domain.MyUser;
import com.example.libr.domain.Roles;
import com.example.libr.domain.Session;
import com.example.libr.repos.MessageRepo;
import com.example.libr.repos.RolesRepo;
import com.example.libr.repos.SessionRepo;
import com.example.libr.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin()
@RestController
public class UsersController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private MessageRepo messageRepo;
    @CrossOrigin()
    @PostMapping("/registration")
    public boolean addUser(@RequestBody MyUser user) {
        MyUser addUser = userRepo.findByLogin(user.getLogin());
        if (addUser == null) {
            userRepo.save(user);
            Message newMessage = new Message();
            newMessage.setLogin("admin");
            newMessage.setText("Новый пользователь "+user.getLogin());
            messageRepo.save(newMessage);
            return true;
        } else {
            return false;
        }
    }

    @CrossOrigin()
    @PostMapping("/login")
    public MyUser loginUser(@RequestBody MyUser user) {
        MyUser tyu = userRepo.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (tyu == null) {
            return null;
        } else {
            Session mySess = sessionRepo.findByIdUser(tyu.getId());
            if (mySess == null) {
                Session session = new Session();
                session.setIdUser(tyu.getId());
                session.setMyTime(LocalDateTime.now().plusMinutes(20));
                sessionRepo.save(session);
                return tyu;
            } else {
                mySess.setMyTime(LocalDateTime.now().plusMinutes(20));
                sessionRepo.save(mySess);
                return tyu;
            }
        }
    }

    @CrossOrigin()
    @GetMapping("/roles/{idUser}")
    public Roles getRole(@PathVariable Long idUser) {
        MyUser my = userRepo.findById(idUser).get();
        Roles myRole = rolesRepo.findByRole(my.getRole());
        return myRole;
    }

    @CrossOrigin()
    @GetMapping("/admin-page")
    public List<MyUser> getUsers() {
        List<MyUser> users = userRepo.findAll();
        return users;
    }

    @CrossOrigin()
    @DeleteMapping("admin-page/{id}")
    public List<MyUser> deleteUser(@PathVariable Long id) {
        MyUser user = userRepo.findById(id).get();
        userRepo.delete(user);
        List<MyUser> users = userRepo.findAll();
        return users;
    }

    @CrossOrigin()
    @PutMapping("admin-page/{id}")
    public List<MyUser> updateUser(@PathVariable Long id,
                                   @Valid @RequestBody MyUser userDetail) {
        MyUser user = userRepo.findById(id).get();
        user.setLogin(userDetail.getLogin());
        user.setPassword(userDetail.getPassword());
        user.setRole(userDetail.getRole());
        userRepo.save(user);
        List<MyUser> users = userRepo.findAll();
        return users;
    }
}
