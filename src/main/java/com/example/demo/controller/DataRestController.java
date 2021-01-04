package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.RoleServiceImpl;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class DataRestController {

    UserService userService;
    RoleService roleService;

    @Autowired
    public DataRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.listRoles();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.listUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public List<User> newUser(@RequestBody User user){
        fixRoles(user);
        userService.add(user);
        return userService.listUsers();
    }

    @PutMapping("/users")
    public List<User> updateUser(@RequestBody User user){
        fixRoles(user);
        userService.updateUser(user);
        return userService.listUsers();
    }

    @DeleteMapping("/users/{id}")
    public List<User> deleteUser(@PathVariable long id){
        userService.deleteUser(userService.getUser(id));
        return userService.listUsers();
    }

    private void fixRoles(User user) {
        Set<Role> receivedRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();
        for (Role r : receivedRoles) {
            roles.add(roleService.getRoleByName(r.getName()));
        }
        user.setRoles(roles);
    }
}
