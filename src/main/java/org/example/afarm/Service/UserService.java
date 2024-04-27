package org.example.afarm.Service;

import org.apache.catalina.User;
import org.example.afarm.DTO.UserDto;
import org.example.afarm.Repository.UserRepository;
import org.example.afarm.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public void UpdateUser(UserDto userDto, String username, MultipartFile file) throws IOException {
        UserEntity user = userRepository.findByUsername(username);
        if(userDto.getUsername()!=null)
            user.setUsername(userDto.getUsername());

        if(!file.isEmpty()){
            String filename = file.getOriginalFilename();
            String path = "/C:/Users/user/IdeaProjects/afarm/src/main/resources/photo/";
            File file1 = new File(path+filename);

            file.transferTo(file1);

            user.setPhotoPath("path"+filename);
        }


        userRepository.save(user);
    }

    @Transactional
    public void DeleteUser(String username){
        UserEntity user = userRepository.findByUsername(username);
        if(user.getUsername() != null){
            userRepository.delete(user);
        }
    }

}
