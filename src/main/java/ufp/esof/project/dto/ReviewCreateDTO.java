/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:45
 * Version:
 */

package ufp.esof.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO para criação da ‘review’ (entrada)
@Data
public class ReviewCreateDTO {

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    @NotBlank(message = "Comment cannot be blank")
    @Size(min = 10, max = 500, message = "Comment must be between 10 and 500 characters")
    private String comment;

    @NotNull(message = "Appointment ID cannot be null")
    private Long appointmentId;

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Explainer ID cannot be null")
    private Long explainerId;

    // Getters e setters
}
