package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/")
    public String homePage() {
        return "admin/users";
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> showRoles = roleService.listRoles();
        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("showRoles", showRoles);
        return "admin/users";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "/login";
    }


    @GetMapping("/user/userspace/{id}")
    public String userspace(Model model, @PathVariable("id") long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", userService.getUser(id));
        return "user/userspace";
    }
}
