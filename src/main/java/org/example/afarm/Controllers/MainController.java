package org.example.afarm.Controllers;

import org.example.afarm.DTO.RequestDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class MainController {

    @GetMapping("/")
    public ResponseEntity<?> main(String name){
        return new ResponseEntity<>(name, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(String name){
        return new ResponseEntity<>(name, HttpStatusCode.valueOf(200));
    }


}
