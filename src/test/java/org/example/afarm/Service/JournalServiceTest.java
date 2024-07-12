package org.example.afarm.Service;

import org.apache.catalina.User;
import org.example.afarm.DTO.JournalGetDto;
import org.example.afarm.Repository.JournalRepository;
import org.example.afarm.entity.JournalEntity;
import org.example.afarm.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
class JournalServiceTest {

    JournalService journalService;
    @MockBean
    static UserService userRepository;

    @MockBean
    JournalRepository journalRepository;

    static List<Object> makeSample(){
        List<Object> samples = new ArrayList<>();

        UserEntity user = UserEntity.builder()
                .username("admin")
                .password("1234")
                .photoPath(null)
                .plant_name(null)
                .build();

        JournalEntity journal = JournalEntity.builder()
                .title("this is test")
                .content("for title")
                .user((UserEntity) Mockito.when(userRepository.getUser("admin")).thenReturn(user))
                .file(null)
                .date(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        samples.add(user);
        samples.add(journal);

        return samples;
    }

    @Test
    void saveJournal() {
        JournalGetDto journalGetDto = new JournalGetDto("this is test","for test",null);

       List<?> samples = makeSample();

        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);

        ReflectionTestUtils.setField(journalGetDto,"id",5);// 더미데이터.

        Mockito.when(journalRepository.save(journal)).thenReturn(journal);
        int result = journalService.saveJournal(journalGetDto,"admin"); // 만약 id가 반환되면 test가 자동으로 id가 더미의 아이디와 같도록 자동으로 설정

        assertThat(result).isEqualTo(5);
    }

    @Test
    void deleteJournal() {
        List<?> samples = makeSample();
        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);

        ReflectionTestUtils.setField(journal,"id",108);

        int result = journalService.deleteJournal(108,"admin");
        assertThat(result).isEqualTo(108);
    }

    @Test
    void selectAllJournal() {
        List<?> samples = makeSample();
        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);

        ReflectionTestUtils.setField(journal,"id",108);
        ReflectionTestUtils.setField(journal,"id",109);
        ReflectionTestUtils.setField(journal,"id",110);
        ReflectionTestUtils.setField(journal,"id",111);

        for(int i=0;i<4;i++){
            Mockito.when(journalRepository.save(journal)).thenReturn(journal);
        }

        Pageable pageable = PageRequest.of(1,4);

        int result = (int) journalService.selectAllJournal(pageable,user.getUsername()).getTotalElements();

        assertThat(result).isEqualTo(4);
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