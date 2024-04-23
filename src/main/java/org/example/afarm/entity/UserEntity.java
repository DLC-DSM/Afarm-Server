package org.example.afarm.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;


@Getter
@Setter
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
    private String pessword;

    @Column(name = "photo")
    private String photoPath;

    @OneToOne
    @JoinColumn(name = "plant_name",referencedColumnName = "plant_name")
    private PlantManageEntity plant_name;

    @OneToMany(mappedBy = "user")
    private List<JournalEntity> journal;
}
