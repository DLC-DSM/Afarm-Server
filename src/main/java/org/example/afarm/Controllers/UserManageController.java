package org.example.afarm.Controllers;

import org.example.afarm.DTO.UserDto;
import org.example.afarm.Service.UserService;
import org.example.afarm.entity.UserEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@ResponseBody
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserManageController {

    private final UserService userService;

    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userUp")
    public ResponseEntity<?> UserUpdate(Authentication authentication, UserDto User, MultipartFile file) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.UpdateUser(User,userDetails.getUsername(),file);
        return new ResponseEntity<>("User_Up_OK", HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/userDelete")
    public ResponseEntity<?> userDelete(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userService.DeleteUser(userDetails.getUsername());
        return new ResponseEntity<>("Del_OK",HttpStatusCode.valueOf(200));
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> userInfomation(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = userService.getUser(userDetails.getUsername());
        UserDto userInfo = UserDto.builder()
                .username(user.getUsername())
                .plant_name(user.getPlant_name().getPlantName())
                .build();
        return new ResponseEntity<>(userInfo,HttpStatusCode.valueOf(200));
    }

}
