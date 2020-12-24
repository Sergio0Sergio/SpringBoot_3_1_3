package com.example.demo.service;



import com.example.demo.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> listRoles();
}
