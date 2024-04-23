package org.example.afarm.Service;

import org.example.afarm.DTO.AiResponseDTO;
import org.example.afarm.DTO.PlantInfoDto;
import org.example.afarm.DTO.PlantManageDto;
import org.example.afarm.Repository.PlantManageRepository;
import org.example.afarm.Repository.PlantRepository;
import org.example.afarm.Repository.UserRepository;
import org.example.afarm.entity.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static java.time.Instant.now;

@Service
public class PlantManageService {
    private final PlantManageRepository plantManageRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    public PlantManageService(PlantManageRepository plantManageRepository, UserRepository userRepository, PlantRepository plantRepository) {
        this.plantManageRepository = plantManageRepository;
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
    }

    @Transactional
    public void nowPlantInfo(PlantInfoDto plantInfoDto, String username){
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity entity = plantManageRepository.findByUser(user);
        entity.setPlantTemp(plantInfoDto.getTemp());
        entity.setPollOutside(plantInfoDto.getDate());
        entity.setSoilPoll(plantInfoDto.getHumi());

        plantManageRepository.save(entity);
    }

    //성장도.
    @Transactional
    public void aiPlantRate(MultipartFile file,String username){
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);


//        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
//        param.add("image",file);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
//        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//
//        HttpEntity<?> http = new HttpEntity<>(param,httpHeaders);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 질병 예측 ai 요청. rate가 70%이상이면 질병 발생
//        ResponseEntity<AiResponseDTO> response = restTemplate.exchange("http://192.168.10.76/predict", HttpMethod.POST,http, AiResponseDTO.class);
        // 성장도 계산.
       // ResponseEntity<AiResponseDTO> response2 = restTemplate.exchange("http://192.168.10.76/predict_"+p.getPlantName(), HttpMethod.POST,http, AiResponseDTO.class);


        // 시작일과, 현재일의 일수 차이를 구한 후, 성장일과,
        // 나누어 /생각해보기-> 단계를 이용해 성장률을 구한다.
        // 1. 시작일 가져오기.
        Date startDate = p.getStartDay();
        Date today = Date.from(now());

        // 2. 계산
        long diff = startDate.getTime() - today.getTime();

        long re = diff / (24*60*60*1000);

        // 성장 일 수 가져오기.
        int grow = p.getPlantName().getPlantGrowTime();

        float percent = (float) re /grow;

        System.out.println(percent);
//        String[][] desease = response.getBody().getObjects();
//        int situation = 0;
//
//        for(String[] leaf:desease){
//            if(Float.parseFloat(leaf[4])>0.7){
//                situation = 4;
//            }
//        }



        SingleFileDto singleFileDto = SingleFileDto.builder()
                .file(file)
                .build();
        p.setGrowthRate(10);
        //p.setSituation(situation);
        plantManageRepository.save(p);
    }


    @Transactional
    public void test(String username) {
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        Date startDate = p.getStartDay();
        Date today = Date.from(now());

        // 2. 계산
        long diff = startDate.getTime() - today.getTime();

        long re = diff / (24*60*60*1000);

        // 성장 일 수 가져오기.
        int grow = p.getPlantName().getPlantGrowTime();

        float percent = (float) re /grow;

        System.out.println(percent);
    }

    public PlantInfoDto getPlantinfo(String username){
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity entity = plantManageRepository.findByUser(user);

        PlantInfoDto plantInfoDto = PlantInfoDto.builder()
                .Temp(entity.getPlantTemp())
                .Date(entity.getSoilPoll())
                .Humi(entity.getPlantTemp())
                .Situation(entity.getSituation()) // 작물의 상태 메세지를 위한 상황 전달.
                .rate(entity.getGrowthRate())
                .build();

        return plantInfoDto;

    }

    public void CreatePlantinfo(PlantManageDto plantManage, String username){

        UserEntity user = userRepository.findByUsername(username);
        PlantEntity plant = plantRepository.findByPlantName(plantManage.getPlantName());
        PlantManageEntity plantManageEntity = PlantManageEntity.builder()
                .plantName(plant)
                .user(user)
                .startDay(Date.from(now()))
                .build();
        plantManageRepository.save(plantManageEntity);

    }
}
