/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:37
 * Version:1
 */

package ufp.esof.project.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RescheduleRequest(@NotNull(message = "New start time is required") LocalDateTime newStartTime,
                                @NotNull(message = "Student ID is required") Long studentId) {
}
