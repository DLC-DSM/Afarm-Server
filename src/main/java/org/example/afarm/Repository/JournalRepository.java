package org.example.afarm.Repository;

import org.example.afarm.DTO.JournalDto;
import org.example.afarm.entity.JournalEntity;
import org.example.afarm.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<JournalEntity, Integer> {
    JournalEntity findByUserAndId(UserEntity user, int id);

    Page<JournalDto> findAllByUser(UserEntity user, Pageable pageable);

}
