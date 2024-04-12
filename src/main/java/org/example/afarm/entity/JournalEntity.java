package org.example.afarm.DTO;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "journal")
public class JournalDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "journal_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id")
    private UserDto user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "journal" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_id_file",referencedColumnName = "journal_id")
    private List<fileEntity> file;

}

