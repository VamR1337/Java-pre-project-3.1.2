package ru.kata.spring.boot_security.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servis.UserService;

import java.security.Principal;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String infoUser(Principal principal, ModelMap model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}