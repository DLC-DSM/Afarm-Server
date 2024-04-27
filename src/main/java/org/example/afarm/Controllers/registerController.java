package org.example.afarm.Controllers;

import org.example.afarm.entity.UserEntity;
import org.example.afarm.Service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class registerController {

    private final RegisterService registerService;

    public registerController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerController(@RequestBody UserEntity userEntity){
        System.out.println(userEntity.getUsername());
        System.out.println(userEntity.getPessword());
        registerService.registerProcess(userEntity);
        return new ResponseEntity<String>("ok", HttpStatus.valueOf(200));
    }
}
