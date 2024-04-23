package org.example.afarm.Controllers;

import org.example.afarm.entity.UserEntity;
import org.example.afarm.Service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/user")
public class registerController {

    private final RegisterService registerService;

    public registerController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerController(UserEntity userEntity){
        System.out.println(userEntity.getUsername());
        registerService.registerProcess(userEntity);
        return new ResponseEntity<>("ok", HttpStatus.valueOf(200));
    }
}
