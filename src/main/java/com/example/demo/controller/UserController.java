package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(){
        return "/users";
    }

    @GetMapping("/users")
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "/users";
    }

    @GetMapping("/login")
    public String loginPage(ModelMap model) {
        return "/login";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        model.addAttribute(userService.getUser(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @GetMapping("/userspace")
    public String userspace(){
        return "/userspace";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        user.setRole(2, "USER");
        userService.add(user);
        return "redirect:/users";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }
    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteUser(userService.getUser(id));
        return "redirect:/users";
    }
}
//final