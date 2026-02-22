package ufp.esof.project.dto.college;

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
public class CollegeResponseDTO {
    private Long id;
    private String name;
    private Long organizationId;
    private Set<DegreeResponseDTO> degrees;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DegreeResponseDTO {
        private Long id;
        private String name;
    }
}
