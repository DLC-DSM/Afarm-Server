package org.example.afarm.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantInfoDto {
    private String username;
    private String Date;
    private String Temp;
    private String Humi;
    private String rate;
    private int Situation;
}
