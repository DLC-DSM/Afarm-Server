package org.example.afarm.Service;

import org.example.afarm.DTO.JournalGetDto;
import org.example.afarm.Repository.JournalRepository;
import org.example.afarm.entity.JournalEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class JournalServiceTest {

    JournalService journalService;
    UserService userService;

    @MockBean
    JournalRepository journalRepository;

    @Test
    void saveJournal() {
        JournalGetDto journalGetDto = new JournalGetDto("this is test","for test",null);
        JournalEntity journal = JournalEntity.builder()
                .title("this is test")
                .content("for title")
                .user(userService.getUser("admin"))
                .file(null)
                .date(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        ReflectionTestUtils.setField(journalGetDto,"id",5);// 더미데이터.

        Mockito.when(journalRepository.save(journal)).thenReturn(journal);
        int result = journalService.saveJournal(journalGetDto,"admin"); // 만약 id가 반환되면 test가 자동으로 id가 더미의 아이디와 같도록 자동으로 설정

        assertThat(result).isEqualTo(5);
    }

    @Test
    void deleteJournal() {
        journalService.deleteJournal(108,"admin");
    }

    @Test
    void selectAllJournal() {
    }

    @Test
    void selectOne() {
    }

    @Test
    void getFile() {
    }

    @Test
    void updateOne() {
    }
}