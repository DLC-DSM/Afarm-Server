package org.example.afarm.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plant_management")
public class PlantEntity {
    @Id
    @OneToOne
    private UserEntity user_id;

    
}
