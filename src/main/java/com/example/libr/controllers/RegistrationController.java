package com.example.libr.controllers;

import com.example.libr.domain.MyUser;
import com.example.libr.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }}
   /* @GetMapping("/login")
    public String login() {
        return "login";
    }*/

   /* @PostMapping("/registration")
    public String addUser(final MyUser user, final Map<String, Object> model) {
        MyUser userFromDB = userRepo.findByLogin(user.getLogin());
        if (userFromDB != null) {
            model.put("message", "User Exist!");
            return "registration";
        }

        userRepo.save(user);
        return "redirect:/login";
    }
}*/
