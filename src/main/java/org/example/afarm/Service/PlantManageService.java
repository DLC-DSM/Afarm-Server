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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public void nowPlantInfo(PlantInfoDto plantInfoDto, String username) throws IOException {
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity entity = plantManageRepository.findByUser(user);

        int setuation = 2;

        float temp = Float.parseFloat(plantInfoDto.getTemp());
        float PollOut = Float.parseFloat(plantInfoDto.getHumi());
        float SoilPoll = Float.parseFloat(plantInfoDto.getDate());

//        if(SoilPoll< 10.0){
//            setuation = 3;
//            //firebaseCloudMessageService.sendMessageTo(target,"수분부족","수분이 부족합니다. 물을 주세요.");
//        }





        entity.setPlantTemp(String.valueOf(temp));
        entity.setPollOutside(String.valueOf(PollOut));
        entity.setSoilPoll(String.valueOf(SoilPoll));
        entity.setSituation(setuation);

        plantManageRepository.save(entity);
    }

    @Transactional
    public void testRate(String username) throws IOException{
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        int situation = 2;
        int chainge = 1;

        if(chainge==1){
            p.setGrowthRate(14);
            System.out.println(p.getGrowthRate());
            chainge = 2;
        }else{
            p.setGrowthRate(32);
        }

        p.setSituation(situation);
        plantManageRepository.save(p);
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
        //param.add("width",10);
        //param.add("height",10);



        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        //httpHeaders.setContentType(MediaType.IMAGE_JPEG);

        HttpEntity<?> http = new HttpEntity<>(param,httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

         //질병 예측 ai 요청. rate가 70%이상이면 질병 발생

        int situation = 2;

        //상추
        if(p.getPlantName().getPlantName().equals("상추")) {
            ResponseEntity<AiRevggDto> response = restTemplate.exchange("http://192.168.210.76:8001/predict_vgg", HttpMethod.POST, http, AiRevggDto.class);
            situation = response.getBody().getPredicted_class();
            System.out.println(response.getHeaders());
            System.out.println(response.getBody());
        }
        // 토마토


        if(p.getPlantName().getPlantName().equals("토마토")){
            ResponseEntity<AiResponseDTO> response2 = restTemplate.exchange("http://192.168.210.76:8001/predict", HttpMethod.POST, http, AiResponseDTO.class);
            String[][] desease = response2.getBody().getObjects();


            for(String[] leaf:desease){
                if(Integer.parseInt(leaf[5])%2==0){
                    situation = 0;
                }
            }

            System.out.println(response2.getHeaders());
            System.out.println(response2.getBody());

        }

        //ResponseEntity<AiResponseDTO> rate= restTemplate.exchange("http://192.168.210.76:8001/percent", HttpMethod.POST, http, AiResponseDTO.class);




//        Date startDate = p.getStartDay();
//        Date today = Date.from(now());
//
//        // 2. 계산
//        long diff = today.getTime() - startDate.getTime();
//
//        long re = diff / (24*60*60*1000); // ms초로 하루를 나눔.
//
//        // 성장 일 수 가져오기.
//        int grow = p.getPlantName().getPlantGrowTime();
//
//        float percent = (float) (re /grow) * 100;

        //System.out.println(rate);



        p.setSituation(situation);
        plantManageRepository.save(p);
    }

//    @Transactional
//    public void aiPlant(String username) throws IOException {
//        UserEntity user = userRepository.findByUsername(username);
//        PlantManageEntity p = plantManageRepository.findByUser(user);
//
//        List list = List.of(12,13,7,15,8,12,13,9,7,9);
//        int index1 = (int) Math.random()*10;
//        int index2 = (int) Math.random()*10;
//
//        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
//        param.add("width",index1);
//        param.add("height",index2);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
//        //httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//
//        HttpEntity<?> http = new HttpEntity<>(param,httpHeaders);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<AiResponseDTO> rate= restTemplate.exchange("http://192.168.210.76:8001/percent", HttpMethod.POST, http, AiResponseDTO.class);
//
//        System.out.println(rate);
//
//    }

    @Transactional
    public void aiPlant(String username) throws IOException {
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        List<Integer> list = List.of(12, 13, 7, 15, 8, 12, 13, 9, 7, 9);
        int index1 = (int) (Math.random() * list.size());
        int index2 = (int) (Math.random() * list.size());

        Map<String, Integer> param = new HashMap<>();
        param.put("width", list.get(index1));
        param.put("height", list.get(index2));

        System.out.println(param.toString());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Integer>> http = new HttpEntity<>(param, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<AiResponseDTO> rate = restTemplate.exchange(
                "http://192.168.210.76:8001/percent",
                HttpMethod.POST,
                http,
                AiResponseDTO.class
        );

        System.out.println(rate);
        //System.out.println(Objects.requireNonNull(rate.getBody()).getGrowth_percentage());
        p.setGrowthRate(Objects.requireNonNull(rate.getBody()).getGrowth_percentage());
        System.out.println(plantManageRepository.save(p).getGrowthRate());
    }



    @Transactional
    public void test(String username) {
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        Date startDate = p.getStartDay();
        Date today = Date.from(now());

        // 2. 계산
        long diff =today.getTime() - startDate.getTime();

        long re = diff / (60*60*24*1000); // 하루 일 수 밀리초 단위.

        // 성장 일 수 가져오기.
        int grow = p.getPlantName().getPlantGrowTime();

//        LocalDate sd = p.getStartDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate td = LocalDate.now();
//        long daysBetween = ChronoUnit.DAYS.between(sd, td);
//
//        float percent = ((float) daysBetween / grow) * 100;

        float percent = (float) re /grow;

        // 100 / 5 -> 25 * a

        int a = 5; // ai에게 받을 단계
        if(a*20 < percent){
            percent = a*25;
        }

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
                .Humi(entity.getPollOutside())
                .Situation(entity.getSituation()) // 작물의 상태 메세지를 위한 상황 전달.
                .rate(String.valueOf(entity.getGrowthRate()))
                .build();

        return plantInfoDto;

    }

    @Transactional
    public void CreatePlantinfo(PlantManageDto plantManage, String username){

        UserEntity user = userRepository.findByUsername(username);
        PlantEntity plant = plantRepository.findByPlantName(plantManage.getPlantName());
        user.setPlant_name(plant);
        PlantManageEntity plantManageEntity = PlantManageEntity.builder()
                .plantName(plant)
                .user(user)
                .startDay(Date.from(now()))
                .plantTemp("0")
                .soilPoll("0")
                .pollOutside("0")
                .growthRate(0)
                .Situation(2)
                .build();
        plantManageRepository.save(plantManageEntity);
        userRepository.save(user);

    }

    @Transactional
    public void deletePlantRate(String username){
        UserEntity user = userRepository.findByUsername(username);
        PlantManageEntity p = plantManageRepository.findByUser(user);

        plantManageRepository.delete(p);
    }
}
