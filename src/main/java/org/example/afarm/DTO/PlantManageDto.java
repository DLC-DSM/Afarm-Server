package org.example.afarm.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.afarm.entity.PlantEntity;
import org.example.afarm.entity.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantManageDto {
    private String username;
    private String plantName;
}
