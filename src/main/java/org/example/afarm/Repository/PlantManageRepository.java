package org.example.afarm.Repository;

import org.example.afarm.DTO.PlantInfoDto;
import org.example.afarm.entity.PlantManageEntity;
import org.example.afarm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantManageRepository extends JpaRepository<PlantManageEntity, UserEntity> {
    PlantManageEntity findByUser(UserEntity user);
}
