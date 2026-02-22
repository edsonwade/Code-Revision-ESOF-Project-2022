package ufp.esof.project.dto.degree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DegreeResponseDTO {
    private Long id;
    private String name;
    private Long collegeId;
    private String collegeName;
    private Set<CourseResponseDTO> courses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseResponseDTO {
        private Long id;
        private String name;
    }
}
