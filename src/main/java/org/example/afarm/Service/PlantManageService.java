package org.example.afarm.Service;

import org.example.afarm.DTO.AiResponseDTO;
import org.example.afarm.DTO.AiRevggDto;
import org.example.afarm.DTO.PlantInfoDto;
import org.example.afarm.DTO.PlantManageDto;
import org.example.afarm.Repository.PlantManageRepository;
import org.example.afarm.Repository.PlantRepository;
import org.example.afarm.Repository.UserRepository;
import org.example.afarm.entity.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        int setuation = 2;

        float temp = Float.parseFloat(plantInfoDto.getTemp());
        float PollOut = Float.parseFloat(plantInfoDto.getDate());
        float SoilPoll = Float.parseFloat(plantInfoDto.getHumi());

        if(SoilPoll< 30.0){
            setuation = 3;
        }


        entity.setPlantTemp(String.valueOf(temp));
        entity.setPollOutside(String.valueOf(PollOut));
        entity.setSoilPoll(String.valueOf(SoilPoll));
        entity.setSituation(setuation);

        plantManageRepository.save(entity);
    }

    //질병예측
    @Transactional
    public void aiPlantRate(MultipartFile file,String username) throws IOException {
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        ByteArrayResource body = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("file",body);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        //httpHeaders.setContentType(MediaType.IMAGE_JPEG);

        HttpEntity<?> http = new HttpEntity<>(param,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        // 질병 예측 ai 요청. rate가 70%이상이면 질병 발생

        int situation = 2;

        //상추
        if(p.getPlantName().getPlantName().equals("상추")) {
            ResponseEntity<AiRevggDto> response = restTemplate.exchange("http://192.168.10.76:8080/predict_vgg", HttpMethod.POST, http, AiRevggDto.class);
            situation = response.getBody().getPredicted_class();
            System.out.println(response.getHeaders());
            System.out.println(response.getBody());
        }
        // 토마토


        if(p.getPlantName().getPlantName().equals("토마토")){
            ResponseEntity<AiResponseDTO> response2 = restTemplate.exchange("http://192.168.10.76:8080/predict", HttpMethod.POST, http, AiResponseDTO.class);
            String[][] desease = response2.getBody().getObjects();


            for(String[] leaf:desease){
                if(Integer.parseInt(leaf[5])%2==0){
                    situation = 0;
                }
            }

            System.out.println(response2.getHeaders());
            System.out.println(response2.getBody());

        }






        Date startDate = p.getStartDay();
        Date today = Date.from(now());

        // 2. 계산
        long diff = today.getTime() - startDate.getTime();

        long re = diff / (24*60*60*1000);

        // 성장 일 수 가져오기.
        int grow = p.getPlantName().getPlantGrowTime();

        float percent = (float) (re /grow);

        System.out.println(percent);



        p.setGrowthRate((int)percent);
        p.setSituation(situation);
        plantManageRepository.save(p);
    }


    @Transactional
    public void test(String username) {
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        Date startDate = p.getStartDay();
        Date today = Date.from(now());

        // 2. 계산
        long diff =today.getTime() - startDate.getTime();

        long re = diff / (24*60*60*1000);

        // 성장 일 수 가져오기.
        int grow = p.getPlantName().getPlantGrowTime();

        float percent = (float) re /grow;

        System.out.println(percent);
    }

    @Transactional
    public PlantInfoDto getPlantinfo(String username){
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity entity = plantManageRepository.findByUser(user);

        PlantInfoDto plantInfoDto = PlantInfoDto.builder()
                .username(entity.getUser().getUsername())
                .Temp(entity.getPlantTemp())
                .Date(entity.getSoilPoll())
                .Humi(entity.getPlantTemp())
                .Situation(entity.getSituation()) // 작물의 상태 메세지를 위한 상황 전달.
                .rate(String.valueOf(entity.getGrowthRate()))
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
