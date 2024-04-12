package org.example.afarm.DTO;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AiResponseDTO {
    private String dieasase;
}
