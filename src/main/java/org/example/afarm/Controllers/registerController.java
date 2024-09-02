package org.example.afarm.Controllers;

import org.example.afarm.entity.UserEntity;
import org.example.afarm.Service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:3000")
public class registerController {

    private final RegisterService registerService;

    public registerController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerController(@RequestBody UserEntity userEntity){
        System.out.println(userEntity.getUsername());
        System.out.println(userEntity.getPassword());
        registerService.registerProcess(userEntity);
        return new ResponseEntity<String>("ok", HttpStatus.valueOf(200));
    }

//    @GetMapping("/plantCho/{plantName}")
//    public ResponseEntity<?> plantChoice(@RequestParam String plantName){
//        return new ResponseEntity<>("plantName_Ok", HttpStatusCode.valueOf(200));
//    }
}
