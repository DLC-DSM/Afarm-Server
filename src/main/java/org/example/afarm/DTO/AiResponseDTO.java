package org.example.afarm.DTO;

import lombok.*;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AiResponseDTO {
    private String[][] Objects;
    private int lev;
    private int growth_percentage;
}
