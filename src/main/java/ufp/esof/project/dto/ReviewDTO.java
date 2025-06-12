/**
 * Author: vanilson muhongo
 * Date:09/06/2025
 * Time:18:41
 * Version:1
 */

package ufp.esof.project.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    @NotBlank(message = "Comment cannot be blank")
    @Size(min = 10, max = 500, message = "Comment must be between 10 and 500 characters")
    private String comment;

    private Long appointmentId;

    private Long studentId;

    private Long explainerId;

    private String studentName;

    private String explainerName;

    // Timestamp fields
    private String createdAt;
    private String updatedAt;
}