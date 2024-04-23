package org.example.afarm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "journal")
public class JournalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "journal_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_user_id")
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @Column(name = "Date")
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_id_file",referencedColumnName = "journal_id")
    private List<FileEntity> file;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}

