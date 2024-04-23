package org.example.afarm.Controllers;

import org.example.afarm.DTO.PlantInfoDto;
import org.example.afarm.DTO.PlantManageDto;
import org.example.afarm.Service.PlantManageService;
import org.example.afarm.entity.PlantEntity;
import org.example.afarm.entity.PlantManageEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/PlantM")
public class PlantManageController {
    private final PlantManageService plantManageService;

    public PlantManageController(PlantManageService plantManageService) {
        this.plantManageService = plantManageService;
    }

    @PostMapping("/connect")
    public ResponseEntity<?> nowInfoSet(@RequestBody PlantInfoDto plantInfoDto){
        plantManageService.nowPlantInfo(plantInfoDto, plantInfoDto.getUsername());
        System.out.println(plantInfoDto);
        return new ResponseEntity<>("PM_SET_OK", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/ai")
    public ResponseEntity<?> getAiService(MultipartFile file,Authentication authentication,String username){
        plantManageService.aiPlantRate(file,username);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/test2")
    public void test(String username){
        plantManageService.test(username);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPlant(@RequestBody PlantManageDto plant){
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        plantManageService.CreatePlantinfo(plant, plant.getUsername());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
