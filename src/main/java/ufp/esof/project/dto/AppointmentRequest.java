/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:36
 * Version:1
 */

package ufp.esof.project.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AppointmentRequest {
    public Long studentId;
    public Long explainerId;
    public LocalDateTime startTime;
}
