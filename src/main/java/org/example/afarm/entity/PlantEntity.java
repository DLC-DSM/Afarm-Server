package org.example.afarm.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plant")
public class PlantEntity {
    @Id
    @Column(name = "plant_name_pl")
    private String plantName;

    @Column(name = "plant_growth_time")
    private int plantGrowTime;
}
