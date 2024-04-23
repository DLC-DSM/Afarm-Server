package org.example.afarm.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileDto {
    private List<MultipartFile> files;
}
