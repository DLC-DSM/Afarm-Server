package org.example.afarm.Controllers;

import org.example.afarm.DTO.UserDto;
import org.example.afarm.Service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserManageController {

    private final UserService userService;

    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> UserUpdate(Authentication authentication, UserDto User, MultipartFile file) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.UpdateUser(User,userDetails.getUsername(),file);
        return new ResponseEntity<>("User_Up_OK", HttpStatusCode.valueOf(200));
    }

}
