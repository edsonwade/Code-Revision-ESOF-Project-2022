/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:45
 * Version: 1
 */

package ufp.esof.project.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private String studentName;
    private String explainerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor para facilitar criação
    public ReviewResponseDTO(Long id, Integer rating, String comment, String studentName, String explainerName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.studentName = studentName;
        this.explainerName = explainerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
