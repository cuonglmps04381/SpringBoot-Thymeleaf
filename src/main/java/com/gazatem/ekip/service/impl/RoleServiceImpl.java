package com.gazatem.ekip.service.impl;

import com.gazatem.ekip.model.Role;
import com.gazatem.ekip.repository.RoleRepository;
import com.gazatem.ekip.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
}
