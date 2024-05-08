package org.example.afarm.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.ContentType;
import org.example.afarm.DTO.PlantInfoDto;
import org.example.afarm.DTO.PlantInfoDto2;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/PlantM")
public class PlantManageController {
    private final PlantManageService plantManageService;

    public PlantManageController(PlantManageService plantManageService) {
        this.plantManageService = plantManageService;
    }

    @PostMapping("/connect")
    public ResponseEntity<?> nowInfoSet(@RequestBody PlantInfoDto plantInfoDto) throws IOException {
        String tar = "fMylLbYeRNq8u4tGAyfq5F:APA91bHRHPY50OxIO5Fn9teRIJnzK8R23q2YBwn_OUsFpaiXAn0pH3ZbGybaRItIlX95Gp5QbhcspZqW9t7MolRWUCEuznPA7wi-oDbmWz96blRt9_v_dNXs9nr0k_6M9SU58YSxnEvj";
        System.out.println(plantInfoDto);
        plantManageService.nowPlantInfo(plantInfoDto,"admin",tar);

        return new ResponseEntity<>("PM_SET_OK", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/connect2")
    public ResponseEntity<?> nowInfo(@RequestBody PlantInfoDto plantInfoDto){
        System.out.println(plantInfoDto+"2");
        return new ResponseEntity<>("PM_SET_OK", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/rate")
    public ResponseEntity<?> rateSet(Authentication authentication) throws IOException {
        plantManageService.rate(authentication.getName());
        return new ResponseEntity<>("rateOk",HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @PostMapping("/ai")
    public ResponseEntity<?> getAiService(MultipartFile file,Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        plantManageService.aiPlantRate(file, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/test2")
    public void test(String username){
        plantManageService.test(username);
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> createPlant(@RequestBody PlantManageDto plant){
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        plantManageService.CreatePlantinfo(plant, plant.getUsername());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping("/GetInfo")
    public ResponseEntity<?> getPlantManage(Authentication authentication, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(request);
        PlantInfoDto p = plantManageService.getPlantinfo(userDetails.getUsername());
        return new ResponseEntity<PlantInfoDto>(p,HttpStatusCode.valueOf(200));
    }

//    @ResponseBody
//    @DeleteMapping("/delete")

}
