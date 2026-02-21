package ufp.esof.project.mapper;

import org.springframework.stereotype.Component;
import ufp.esof.project.dto.request.CreateStudentRequest;
import ufp.esof.project.dto.response.StudentResponse;
import ufp.esof.project.models.Student;

/**
 * Mapper for converting between Student entities and DTOs.
 */
@Component
public class StudentMapper {

    /**
     * Convert CreateStudentRequest DTO to Student entity.
     *
     * @param request the request DTO
     * @return the student entity
     */
    public Student toEntity(CreateStudentRequest request) {
        if (request == null) {
            return null;
        }

        Student student = new Student(request.getName());
        student.setEmail(request.getEmail());
        return student;
    }

    /**
     * Convert Student entity to StudentResponse DTO.
     *
     * @param student the student entity
     * @return the response DTO
     */
    public StudentResponse toResponse(Student student) {
        if (student == null) {
            return null;
        }

        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .role(student.getRole())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}

