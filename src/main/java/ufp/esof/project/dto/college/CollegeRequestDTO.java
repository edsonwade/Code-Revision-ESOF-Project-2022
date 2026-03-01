package ufp.esof.project.dto.college;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeRequestDTO {

    @NotBlank(message = "College name is required")
    private String name;

    private Set<Long> degreeIds;
}
