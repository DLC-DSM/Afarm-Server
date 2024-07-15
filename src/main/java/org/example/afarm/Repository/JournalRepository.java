package org.example.afarm.Repository;

import org.example.afarm.entity.JournalEntity;
import org.example.afarm.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntity, Integer> {
    JournalEntity findByUserAndId(UserEntity user, int id);

    Page<JournalEntity> findAllByUser(UserEntity user, Pageable pageable);


    @Modifying
    @Query("DELETE FROM JournalEntity j WHERE j.id = :num  AND j.user.username like :user ESCAPE '#'" )
    void deleteByIdAndUser(@Param("num") Integer id, @Param("user") String user);

    List<JournalEntity> findAllByUser(UserEntity user);
}
