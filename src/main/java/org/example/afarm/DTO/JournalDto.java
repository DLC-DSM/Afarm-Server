package org.example.afarm.DTO;

import lombok.*;
import org.example.afarm.entity.JournalEntity;
import org.example.afarm.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JournalDto {
    private int id;
    private String user_id;
    private String Title;
    private String Content;
    private LocalDateTime time;
    private List<String> imageUrls;
    private List<MultipartFile> files;

    @Builder
    public JournalDto(JournalEntity journal){
        this.id = journal.getId();
        this.user_id = journal.getUser().getUsername();
        this.Title = journal.getTitle();
        this.Content = journal.getContent();
        this.time = journal.getDate();
        if(journal.getFile() != null) {
            this.imageUrls = journal.getFile().stream()
                    .map(FileEntity::getSave_path)
                    .collect(Collectors.toList());
        }
    }
}
