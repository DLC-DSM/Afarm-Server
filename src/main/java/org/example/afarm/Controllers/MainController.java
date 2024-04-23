package org.example.afarm.Controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
