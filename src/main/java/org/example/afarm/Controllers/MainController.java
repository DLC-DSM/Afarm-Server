package org.example.afarm.Controllers;

import org.example.afarm.DTO.RequestDto;
import org.example.afarm.Service.FirebaseCloudMessageService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class MainController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    public MainController(FirebaseCloudMessageService firebaseCloudMessageService) {
        this.firebaseCloudMessageService = firebaseCloudMessageService;
    }


    @PostMapping("/send/message")
    public ResponseEntity pushMessage(@RequestBody RequestDto requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    public ResponseEntity<?> main(String name){
        return new ResponseEntity<>(name, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(String name){
        return new ResponseEntity<>(name, HttpStatusCode.valueOf(200));
    }


}
