package com.gazatem.ekip.service;

import com.gazatem.ekip.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface RoleService {
    List<Role> findById(Integer id);
}
