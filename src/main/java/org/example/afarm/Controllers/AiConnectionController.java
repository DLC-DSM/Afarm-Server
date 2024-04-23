package org.example.afarm.Controllers;


import org.example.afarm.DTO.AiResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai")
public class AiConnectionController {

    private final AiResponseDTO aiResponseDTO;

    public AiConnectionController(AiResponseDTO aiResponseDTO) {
        this.aiResponseDTO = aiResponseDTO;
    }

//    @GetMapping("/testConnection")
//    public ResponseEntity<?> jpgConnecction(){
//        WebClient.builder()
//                .baseUrl("")
//                .build().get()
//                .uri("/fdfefdf")
//                .header()
//    }

}
