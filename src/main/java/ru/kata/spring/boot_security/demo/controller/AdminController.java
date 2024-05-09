package ru.kata.spring.boot_security.demo.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servis.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<User> users = userService.listUsers();
		model.addAttribute("user", users);
		return "admin";
	}

	@GetMapping("/addNew")
	public String showAddUserForm(Model model) {
		model.addAttribute("user", new User());
		return "add";
	}

	@PostMapping("/add")
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Invalid input. Please check the values.");
			return "add";
		}
		userService.add(user);
		return "redirect:/admin/";
	}

	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") Long id) {
		userService.delete(id);
		return "redirect:/admin/";
	}

	@GetMapping(value = "/update")
	public String showUpdateUserForm(@RequestParam("id") Long id, Model model) {
		model.addAttribute("updateUser", userService.getUserById(id));
		return "up";
	}

	@PostMapping("/up")
	public String updateUser(@ModelAttribute("updateUser") @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Invalid input. Please check the values.");
			return "up";
		}
		userService.update(user);
		return "redirect:/admin/";
	}
}