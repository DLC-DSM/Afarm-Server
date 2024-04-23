package org.example.afarm.Repository;

import org.example.afarm.entity.PlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<PlantEntity, String> {

    PlantEntity findByPlantName(String username);
}
