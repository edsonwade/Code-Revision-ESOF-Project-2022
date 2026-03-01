package ufp.esof.project.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {
    private Long id;
    private String name;
    private Long degreeId;
    private String degreeName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
