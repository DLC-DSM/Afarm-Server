package org.example.afarm.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String username;
    @Column(name = "user_pwd")
    private String password;

    @Column(name = "photo")
    private String photoPath;

    @OneToOne
    @JoinColumn(name = "plant_name",referencedColumnName = "plant_name_pl")
    private PlantEntity plant_name;

}

