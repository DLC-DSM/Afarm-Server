package org.example.afarm.Service;

import org.example.afarm.entity.UserEntity;
import org.example.afarm.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void registerProcess(UserEntity userEntity){

        String username = userEntity.getUsername();
        String password = userEntity.getPessword();

        Boolean isExist = userRepository.existsByUsername(username);;

        if(isExist){
            return;
        }
        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPessword(bCryptPasswordEncoder.encode(password));

        userRepository.save(data);
    }
}
