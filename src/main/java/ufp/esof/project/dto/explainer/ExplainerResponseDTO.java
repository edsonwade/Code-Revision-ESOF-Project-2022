package ufp.esof.project.dto.explainer;

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
public class ExplainerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Set<AvailabilityResponseDTO> availabilities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvailabilityResponseDTO {
        private Long id;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
