package com.gazatem.ekip.service;

import com.gazatem.ekip.model.Role;
import com.gazatem.ekip.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
	 User findUserByEmail(String email);
	 void saveUser(User user);
	 void createUser(User user);
	 List<User> findAll();
	 List<Role> findAllRoles();
	User findById(Integer id);
}
