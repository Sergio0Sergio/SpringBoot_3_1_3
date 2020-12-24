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

    @GetMapping("/admin/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        model.addAttribute(userService.getUser(id));
        return "admin/show";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        User user = new User();
        List<Role> showRoles = roleService.listRoles();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        model.addAttribute("showRoles", showRoles);
        return "admin/new";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("showRoles", roleService.listRoles());
        return "admin/edit";
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

    @GetMapping("/admin/adminspace/{id}")
    public String adminspace(Model model, @PathVariable("id") long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", userService.getUser(id));
        return "/admin/users";
    }

    @PostMapping("/admin/new")
    public String createUser(Model model, @ModelAttribute("user") User user) {


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("users", userService.listUsers());
        return "admin/users";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUser(Model model, @ModelAttribute("user") User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.updateUser(user);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.listUsers());
        return "admin/users";
    }

    @PostMapping("/admin/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") long id) {
        userService.deleteUser(userService.getUser(id));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = null;
        if (principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }
        User currentUser = userService.getUserByName(userName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.listUsers());
        return "admin/users";
    }
}
