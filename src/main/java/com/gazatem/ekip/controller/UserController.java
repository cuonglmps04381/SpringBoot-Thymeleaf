package com.gazatem.ekip.controller;


import com.gazatem.ekip.model.FileInfo;
import com.gazatem.ekip.model.Role;
import com.gazatem.ekip.model.User;
import com.gazatem.ekip.payload.response.ResponseMessage;
import com.gazatem.ekip.service.FileInfoService;
import com.gazatem.ekip.service.RoleService;
import com.gazatem.ekip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FileInfoService fileInfoService;

    @RequestMapping(value = "/admin/users/list", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<User> users = userService.findAll();
            modelAndView.addObject("users", users);
            modelAndView.setViewName("admin/users/list");
        }catch (Exception e ){
            e.printStackTrace();
        }

        return modelAndView;
    }

    @RequestMapping(value = "/admin/users/teams", method = RequestMethod.GET)
    public ModelAndView teams() {
        ModelAndView modelAndView = new ModelAndView();
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        */
        modelAndView.setViewName("admin/users/list");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/users/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = userService.findAllRoles();
        modelAndView.addObject("roles", roles);
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/users/create");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/users/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("id") int id) {
        User user = userService.findById(id);
        Role role = user.getRoles().stream().findAny().orElse(null);
        List<Role> roles =  roleService.findById(role.getId());
        List<Role> lstRoles = userService.findAllRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lstRoles", lstRoles);
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/admin/users/update");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/users/delete", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView delete(@RequestParam int id) {
        User user = userService.findById(id);
        if (Objects.nonNull(user)) {
            user.setActive(false);
            userService.saveUser(user);
        }
        List<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", users);
        modelAndView.setViewName("/admin/users/list");
        return modelAndView;
    }
    @RequestMapping(value = "/admin/users/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid User user, BindingResult bindingResult, @RequestParam("pathImage") MultipartFile file) throws IOException {

        logger.info("This is a debug message");

        List<Role> roles = userService.findAllRoles();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", roles);


        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            logger.info("This is a error message"  + bindingResult.toString());
            modelAndView.setViewName("admin/users/create");
        } else {
            String message = "";
            if (file != null) {
                user.setFileInfo(new FileInfo(file.getOriginalFilename(), file.getContentType(), file.getBytes()));
                userService.createUser(user);
            }

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("admin/users/create");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/users/update", method = RequestMethod.POST)
    public ModelAndView update(@Valid User user, BindingResult bindingResult) {

        logger.info("This is a debug message");

        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            Role role = user.getRoles().stream().findAny().orElse(null);
            List<Role> roles =  roleService.findById(role.getId());
            List<Role> lstRoles = userService.findAllRoles();
            user.setActive(true);
            userService.saveUser(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("lstRoles", lstRoles);
            modelAndView.addObject("roles", roles);
        } else {
            bindingResult
                    .rejectValue("user", "error.user",
                            "can't update user");
        }
        return modelAndView;
    }



}
