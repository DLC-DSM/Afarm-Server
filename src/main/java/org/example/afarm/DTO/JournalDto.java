package org.example.afarm.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.afarm.entity.JournalEntity;
import org.example.afarm.entity.FileEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class JournalDto {
    private int id;
    private String user_id;
    private String title;
    private String content;
    private LocalDateTime time;
    private List<String> imageUrls;

    @Builder
    public JournalDto(JournalEntity journal){
        this.id = journal.getId();
        this.user_id = journal.getUser().getUsername();
        this.title = journal.getTitle();
        this.content = journal.getContent();
        this.time = journal.getDate();
        this.imageUrls = journal.getFile().stream()
                .map(FileEntity::getSave_path)
                .collect(Collectors.toList());
    }
}
