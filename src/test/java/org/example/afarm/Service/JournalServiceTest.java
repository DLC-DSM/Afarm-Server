package org.example.afarm.Service;

import org.apache.catalina.User;
import org.example.afarm.DTO.JournalDto;
import org.example.afarm.DTO.JournalGetDto;
import org.example.afarm.Repository.FileRepository;
import org.example.afarm.Repository.JournalRepository;
import org.example.afarm.Repository.UserRepository;
import org.example.afarm.entity.FileEntity;
import org.example.afarm.entity.JournalEntity;
import org.example.afarm.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
class JournalServiceTest {


    JournalService journalService;
    @MockBean
    UserRepository userRepository;

    @MockBean
    JournalRepository journalRepository;

    @MockBean
    FileRepository fileRepository;

    @BeforeEach
    void setUp(){
        journalService = new JournalService(journalRepository,userRepository,fileRepository);
    }

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
                .content("for test")
                .user(user)
                .file(null)
                .date(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        FileEntity file = FileEntity.builder()
                .journal(1)
                .save_path("C:/Users/user/IdeaProjects/afarm/src/main/resources/photo/b7fb6405-ed28-470d-8964-a80f9ce214db_저자.jpg")
                .build();

        samples.add(user);
        samples.add(journal);
        samples.add(file);

        return samples;
    }

    @Test
    void saveJournal() {
        JournalGetDto journalGetDto = new JournalGetDto("this is test","for test",null);

       List<?> samples = makeSample();

        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);

        ReflectionTestUtils.setField(journal,"id",5);// 더미데이터.

        Mockito.when(journalRepository.save(Mockito.any(JournalEntity.class))).thenReturn(journal);
        int result = journalService.saveJournal(journalGetDto,"admin"); // 만약 id가 반환되면 test가 자동으로 id가 더미의 아이디와 같도록 자동으로 설정

        assertThat(result).isEqualTo(5);
    }

    @Test
    void deleteJournal() {
        List<?> samples = makeSample();
        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);

        ReflectionTestUtils.setField(journal,"id",108);

        Mockito.when(journalRepository.findByUserAndId(user, 108)).thenReturn(journal);
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(user); // 해당 명령이 실행되면 다음 객체를 반환함.

        int result = journalService.deleteJournal(108,"admin");
        assertThat(result).isEqualTo(108);
    }

    @Test
    void selectAllJournal() {
        List<?> samples = makeSample();
        UserEntity user = (UserEntity) samples.get(0);

        // 여러 개의 JournalEntity 객체 생성 및 ID 설정
        JournalEntity journal1 = (JournalEntity) samples.get(1);
        JournalEntity journal2 = (JournalEntity) samples.get(1);
        JournalEntity journal3 = (JournalEntity) samples.get(1);
        JournalEntity journal4 = (JournalEntity) samples.get(1);

        ReflectionTestUtils.setField(journal1, "id", 108);
        ReflectionTestUtils.setField(journal2, "id", 109);
        ReflectionTestUtils.setField(journal3, "id", 110);
        ReflectionTestUtils.setField(journal4, "id", 111);

        List<JournalEntity> journals = Arrays.asList(journal1, journal2, journal3, journal4);
        Page<JournalEntity> journalPage = new PageImpl<>(journals);

        // Mock 설정: findAll(Pageable) 호출 시 페이지 객체를 반환하도록 설정


        Pageable pageable = PageRequest.of(0, 4);

        Mockito.when(journalRepository.findAllByUser(user,pageable)).thenReturn(journalPage);
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(user);

        int result = (int) journalService.selectAllJournal(pageable, user.getUsername()).getTotalElements();

        assertThat(result).isEqualTo(4);
    }


    @Test
    void selectOne() {
        List<Object> samples = makeSample();

        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);
        ReflectionTestUtils.setField(journal,"id",1);

        Mockito.when(userRepository.findByUsername("admin")).thenReturn(user);
        Mockito.when(journalRepository.findByUserAndId(user,1)).thenReturn(journal);

        int result = journalService.selectOne("admin",1).getId();

        assertThat(result).isEqualTo(1);

    }

    @Test
    void getFile() throws IOException {
        List<Object> samples = makeSample();

        UserEntity user = (UserEntity) samples.get(0);
        JournalEntity journal = (JournalEntity) samples.get(1);
        FileEntity file = (FileEntity) samples.get(2);
        FileEntity file1 = (FileEntity) samples.get(2);
        FileEntity file2 = (FileEntity) samples.get(2);

        ReflectionTestUtils.setField(file,"id",1);
        ReflectionTestUtils.setField(file1,"id",2);
        ReflectionTestUtils.setField(file2,"id",3);

        List<FileEntity> fileEntities =  Arrays.asList(file,file1,file2);

        journal.setFile(fileEntities);

        JournalDto journalDto = JournalDto.builder()
                .journal(journal)
                .build();

        List<byte[]> fileRes = journalService.getFile(journalDto);

        assertThat(fileRes.size()).isEqualTo(3);
    }

    @Test
    void updateOne() {
        List<Object> samples = makeSample();

        UserEntity user = (UserEntity) samples.get(0);

        JournalEntity journal = (JournalEntity) samples.get(1);
        JournalEntity uJournal =  JournalEntity.builder()
                .title("this is test")
                .content("just test")
                .user(user)
                .file(null)
                .date(journal.getDate())
                .build();
        JournalDto j = JournalDto.builder()
                .journal(uJournal)
                .build();

        ReflectionTestUtils.setField(journal,"id",108);

        Mockito.when(journalRepository.findByUserAndId(user, 108)).thenReturn(journal);
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(user); // 해당 명령이 실행되면 다음 객체를 반환함.
        Mockito.when(journalRepository.save(uJournal)).thenReturn(uJournal);

        String content = journalService.updateOne("admin",108,j).getContent();
        assertThat(content).isEqualTo("just test");
    }
}