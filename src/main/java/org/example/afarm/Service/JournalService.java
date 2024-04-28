package org.example.afarm.Service;

import org.example.afarm.DTO.JournalDto;
import org.example.afarm.DTO.JournalGetDto;
import org.example.afarm.Repository.FileRepository;
import org.example.afarm.Repository.UserRepository;
import org.example.afarm.entity.FileEntity;
import org.example.afarm.entity.JournalEntity;
import org.example.afarm.Repository.JournalRepository;
import org.example.afarm.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.LocalTime.now;

@Service
public class JournalService {
    private final JournalRepository journalRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public JournalService(JournalRepository journalRepository, UserRepository userRepository, FileRepository fileRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }


    @Transactional
    public int saveJournal(JournalGetDto journalDto, String username) {
        UserEntity entity = userRepository.findByUsername(username);

        JournalEntity journal = JournalEntity.builder()
                .title(journalDto.getTitle())
                .content(journalDto.getContent())
                .date(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .user(entity)
                .build();

        journalRepository.save(journal);

        if(journalDto.getFiles() != null && !journalDto.getFiles().isEmpty()){
            for(MultipartFile file1 : journalDto.getFiles()){
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid+"_"+file1.getOriginalFilename();

                String path = "C:/Users/user/IdeaProjects/afarm/src/main/resources/photo/";
                //File destinationFile = new File("/path/"+imageFileName);

                File destinationFile = new File(path+imageFileName);

                try {
                    file1.transferTo(destinationFile);
                }catch (IOException e){
                    System.out.println("put error");
                    throw new RuntimeException("Err_init_file");
                }

                FileEntity image = FileEntity.builder()
                        //.save_path("/path/"+imageFileName)
                        .save_path(path+imageFileName)
                        .journal(journal)
                        .build();

                fileRepository.save(image);

            }
        }

        return 5;
    }

    @Transactional
    public void deleteJournal(Integer id, String username){
        UserEntity user = userRepository.findByUsername(username);
        JournalEntity journal = journalRepository.findByUserAndId(user,id);

        System.out.println(journal.toString());
        if(journal==null){
            throw new NullPointerException("NOT_EXIST_BOARD");
        }

        journalRepository.deleteByIdAndUser(id,user.getUsername());
    }

    @Transactional
    public Page<JournalEntity> selectAllJournal(Pageable pageable, String username){
        UserEntity user = userRepository.findByUsername(username);
        Page<JournalEntity> journals = journalRepository.findAllByUser(user, pageable);
        if(journals == null){
            throw new NullPointerException();
        }
        else{
            return journals;
        }
    }

    @Transactional
    public JournalDto selectOne(String username, Integer id){
        UserEntity user = userRepository.findByUsername(username);
        JournalEntity journal = journalRepository.findByUserAndId(user,id);
        JournalDto pageJournal = JournalDto.builder()
                .journal(journal)
                .build();
        return pageJournal;
    }

    public List<byte[]> getFile(JournalDto journal) throws IOException {
        List<byte[]> bytefile = new ArrayList<>();
        for (String url:journal.getImageUrls()){
            System.out.println(url);
            File file = new File(url);
            bytefile.add(FileCopyUtils.copyToByteArray(file));
        }

        return bytefile;
    }

    @Transactional
    public JournalEntity updateOne(String username, Integer id, JournalDto journal){
        UserEntity user = userRepository.findByUsername(username);
        JournalEntity oldJournal = journalRepository.findByUserAndId(user,id);
        oldJournal.update(journal.getTitle(), journal.getContent());
        journalRepository.save(oldJournal);
        return oldJournal;
    }

}
