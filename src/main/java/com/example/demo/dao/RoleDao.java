package com.example.demo.dao;



import com.example.demo.entity.Role;

import java.util.List;

public interface RoleDao {
    List<Role> listRoles();
    Role getRole(long id);
}
