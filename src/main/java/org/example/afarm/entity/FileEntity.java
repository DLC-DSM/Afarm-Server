package org.example.afarm.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//  @ManyToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "journal_id_file")
    @Column(name = "journal_id_file")
    private int journal;

    @Column(name = "save_path")
    private String save_path;

    @Column(name = "name")
    private String name;
}
