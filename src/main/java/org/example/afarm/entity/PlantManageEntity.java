package org.example.afarm.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plant_management")
@Builder
public class PlantManageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "plant_name", referencedColumnName = "plant_name_pl")
    private PlantEntity plantName;

    @Column(name = "plant_temp")
    private String plantTemp = "0";

    @Column(name = "soil_pollination")
    private String soilPoll = "0";

    @Column(name = "pollination")
    private String pollOutside = "0";

    @Column(name = "plant_growth_rate")
    private Integer growthRate = 0;

    @Column
    private Integer Situation = 0;

    @Column(name = "start_date")
    private Date startDay;

}
