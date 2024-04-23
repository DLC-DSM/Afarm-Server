package org.example.afarm.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String username;
    private String pwd;
    private String plant_name;
}
