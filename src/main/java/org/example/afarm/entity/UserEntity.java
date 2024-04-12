package org.example.afarm.DTO;

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
public class UserDto {
    @Id
    @Column(name = "user_id")
    private String username;
    @Column(name = "user_pwd")
    private String pessword;
    @Column(name = "plant_name")
    private String plant_name;

    @OneToMany
    @JoinColumn(name = "user_user_id",referencedColumnName = "user_id")
    private List<JournalDto> journal;
}
