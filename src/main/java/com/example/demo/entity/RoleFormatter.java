package com.example.demo.entity;


import com.example.demo.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class RoleFormatter implements Formatter<Role> {


    RoleDao roleDao;
    @Autowired
    public RoleFormatter(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role parse(String s, Locale locale) throws ParseException {
        return roleDao.getRole(Long.parseLong(s));
    }

    @Override
    public String print(Role role, Locale locale) {
        return String.valueOf(role.getId());
    }
}