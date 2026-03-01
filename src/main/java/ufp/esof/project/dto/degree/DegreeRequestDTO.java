package ufp.esof.project.dto.degree;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DegreeRequestDTO {

    @NotBlank(message = "Degree name is required")
    private String name;

    private Long collegeId;
}
