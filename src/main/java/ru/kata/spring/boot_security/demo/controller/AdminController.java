package ru.kata.spring.boot_security.demo.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.UsernameAlreadyExistsException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servis.RoleService;
import ru.kata.spring.boot_security.demo.servis.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
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
		model.addAttribute("allRoles", roleService.findAll()); // Передаем все роли в модель
		return "add";
	}

	@PostMapping("/add")
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Invalid input. Please check the values.");
			model.addAttribute("allRoles", roleService.findAll()); // Передаем все роли в модель
			return "add";
		}
		try {
			userService.add(user);
			return "redirect:/admin/";
		} catch (UsernameAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("allRoles", roleService.findAll()); // Передаем все роли в модель
			return "add";
		}
	}

	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") Long id) {
		userService.delete(id);
		return "redirect:/admin/";
	}

	@GetMapping(value = "/update")
	public String showUpdateUserForm(@RequestParam("id") Long id, Model model) {
		model.addAttribute("updateUser", userService.getUserById(id));
		model.addAttribute("allRoles", roleService.findAll()); // Передаем все роли в модель
		return "up";
	}

	@PostMapping("/up")
	public String updateUser(@ModelAttribute("updateUser") @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Invalid input. Please check the values.");
			model.addAttribute("allRoles", roleService.findAll()); // Передаем все роли в модель
			return "up";
		}
		try {
			userService.update(user);
			return "redirect:/admin/";
		} catch (UsernameAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("allRoles", roleService.findAll()); // Передаем все роли в модель
			return "up";
		}
	}
}