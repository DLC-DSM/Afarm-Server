package org.example.afarm.Repository;

import org.example.afarm.entity.FileEntity;
import org.example.afarm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
