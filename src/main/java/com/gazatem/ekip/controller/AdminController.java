package com.gazatem.ekip.controller;

import com.gazatem.ekip.model.FileInfo;
import com.gazatem.ekip.model.User;
import com.gazatem.ekip.payload.response.ResponseFile;
import com.gazatem.ekip.service.FileInfoService;
import com.gazatem.ekip.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            modelAndView.setViewName("login");
            return modelAndView;
        }
        User user = userService.findUserByEmail(auth.getName());
        FileInfo files = fileInfoService.getFile(user.getFileInfo().getId());
            String fileDownloadUri = "";
                fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/admin/files/")
                        .path(files.getId().toString())
                        .toUriString();


        modelAndView.addObject("files", fileDownloadUri);
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/home");

        return modelAndView;
    }
    @GetMapping("/admin/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        FileInfo file = fileInfoService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

}
