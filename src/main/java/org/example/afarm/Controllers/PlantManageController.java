package org.example.afarm.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.ContentType;
import org.example.afarm.DTO.PlantInfoDto;
import org.example.afarm.DTO.PlantInfoDto2;
import org.example.afarm.DTO.PlantManageDto;
import org.example.afarm.Service.PlantManageService;
import org.example.afarm.entity.PlantEntity;
import org.example.afarm.entity.PlantManageEntity;
import org.example.afarm.filter.CustomUserDetails;
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
@RequestMapping("/plant_m")
@CrossOrigin(origins = "http://localhost:3000")
public class PlantManageController {
    private final PlantManageService plantManageService;

    public PlantManageController(PlantManageService plantManageService) {
        this.plantManageService = plantManageService;
    }

    @PostMapping("/connect")
    public ResponseEntity<?> nowInfoSet(@RequestBody PlantInfoDto plantInfoDto) throws IOException {
        // 구현해야 할 것. -> 임베디드 장치 블루투스 or wifi로 연결 설정 기능.
        // 임베디드 코드에 유저 id 반환 및 저장 필요.
        // 그 후, 인증 진행. 만약, 회원 탈퇴시, 임베 장치도 자동해제. 추후 개선사항.
        //String tar = "fMylLbYeRNq8u4tGAyfq5F:APA91bHRHPY50OxIO5Fn9teRIJnzK8R23q2YBwn_OUsFpaiXAn0pH3ZbGybaRItIlX95Gp5QbhcspZqW9t7MolRWUCEuznPA7wi-oDbmWz96blRt9_v_dNXs9nr0k_6M9SU58YSxnEvj";
        System.out.println(plantInfoDto);
        plantManageService.nowPlantInfo(plantInfoDto,"admin");

        return new ResponseEntity<>("PM_SET_OK", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/connect2")
    public ResponseEntity<?> nowInfo(@RequestBody PlantInfoDto2 plantInfoDto) throws IOException{
        System.out.println(plantInfoDto+"2");
        //plantManageService.nowPlantInfo(plantInfoDto,"admin");
        return new ResponseEntity<>("PM_SET_OK", HttpStatusCode.valueOf(200));
    }
//
//    @PostMapping("/rate")
//    public ResponseEntity<?> rateSet(Authentication authentication) throws IOException {
//        plantManageService.testRate(authentication.getName());
//        return new ResponseEntity<>("rateOk",HttpStatusCode.valueOf(200));
//    }

    @ResponseBody
    @PostMapping("/ai")
    public ResponseEntity<?> getAiService(MultipartFile file,Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("dmdmdd");
        System.out.println(file);
        plantManageService.aiPlantRate(file, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @PostMapping("/ai2")
    public ResponseEntity<?> AiService(Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        plantManageService.aiPlant(userDetails.getUsername());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

//    @GetMapping("/test2")
//    public void test(String username){
//        plantManageService.test(username);
//    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> createPlant(@RequestBody PlantManageDto plant){
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        plantManageService.CreatePlantinfo(plant, plant.getUsername());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping("/get_info")
    public ResponseEntity<?> getPlantManage(Authentication authentication, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(request);
        PlantInfoDto p = plantManageService.getPlantinfo(userDetails.getUsername());
        return new ResponseEntity<PlantInfoDto>(p,HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @DeleteMapping("/delete") // 구현예정
    public ResponseEntity<?> deletePlant(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        plantManageService.deletePlantRate(userDetails.getUsername());
        return  new ResponseEntity<>("okDP",HttpStatusCode.valueOf(200));
    }


    //PlantManageUpdate 구현예정

}
