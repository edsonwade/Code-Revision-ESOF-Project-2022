///**
// * Author: vanilson muhongo
// * Date:16/06/2025
// * Time:16:09
// * Version:1
// */
//
//package ufp.esof.project.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import ufp.esof.project.models.Appointment;
//import ufp.esof.project.models.Student;
//import ufp.esof.project.repository.AppointmentRepository;
//import ufp.esof.project.repository.ExplainerRepository;
//import ufp.esof.project.repository.StudentRepository;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("test")
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class StudentControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper; // para JSON
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private AppointmentRepository appointmentRepository;
//
//    @Autowired
//    private ExplainerRepository explainerRepository;
//
//
//
//    @BeforeEach
//    void setup() {
//        appointmentRepository.deleteAll();
//        studentRepository.deleteAll();
//        explainerRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("GET /api/v1/student returns empty list when no students")
//    void getAllStudents_EmptyList() throws Exception {
//        mockMvc.perform(get("/api/v1/student"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"));
//    }
//
//    @Test
//    @DisplayName("POST /api/v1/student creates a new student")
//    void createStudent_Success() throws Exception {
//        Student student = new Student();
//        student.setName("John Doe");
//
//        mockMvc.perform(post("/api/v1/student")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(student)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("John Doe"))
//                .andExpect(jsonPath("$.id").isNumber());
//    }
//
//    @Test
//    @DisplayName("GET /api/v1/student/{id} returns student")
//    void getStudentById_Success() throws Exception {
//        Student student = new Student();
//        student.setName("Jane");
//        student = studentRepository.save(student);
//
//        mockMvc.perform(get("/api/v1/student/{id}", student.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Jane"))
//                .andExpect(jsonPath("$.id").value(student.getId()));
//    }
//
//    @Test
//    @DisplayName("PUT /api/v1/student/{id} updates student")
//    void updateStudent_Success() throws Exception {
//        Student student = new Student();
//        student.setName("Old Name");
//        student = studentRepository.save(student);
//
//        Student updated = new Student();
//        updated.setName("New Name");
//
//        mockMvc.perform(put("/api/v1/student/{id}", student.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updated)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("New Name"));
//    }
//
//    @Test
//    @DisplayName("DELETE /api/v1/student/{id} deletes student")
//    void deleteStudent_Success() throws Exception {
//        Student student = new Student();
//        student.setName("To Delete");
//        student = studentRepository.save(student);
//
//        mockMvc.perform(delete("/api/v1/student/{id}", student.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Student deleted successfully"));
//
//        assertThat(studentRepository.existsById(student.getId())).isFalse();
//    }
//
//    @Test
//    @DisplayName("POST /api/v1/student - Duplicate student name returns conflict status")
//    void createStudent_DuplicateName_ReturnsConflict() throws Exception {
//        Student existing = new Student();
//        existing.setName("DuplicateName");
//        studentRepository.save(existing);
//
//        Student newStudent = new Student();
//        newStudent.setName("DuplicateName");
//
//        mockMvc.perform(post("/api/v1/student")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newStudent)))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value("Student with name 'DuplicateName' already exists"));
//    }
//
//    @Test
//    @DisplayName("PUT /api/v1/student/{id} - Updating with duplicate name returns conflict")
//    void updateStudent_DuplicateName_ReturnsConflict() throws Exception {
//        Student student1 = new Student();
//        student1.setName("Student1");
//        student1 = studentRepository.save(student1);
//
//        Student student2 = new Student();
//        student2.setName("Student2");
//        student2 = studentRepository.save(student2);
//
//        Student updated = new Student();
//        updated.setName("Student2"); // Name already used by student2
//
//        mockMvc.perform(put("/api/v1/student/{id}", student1.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updated)))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value("Another student with name 'Student2' already exists"));
//    }
//
//    @Test
//    @DisplayName("DELETE /api/v1/student/{id} - Deleting student with appointments returns bad request")
//    void deleteStudent_WithAppointments_ReturnsBadRequest() throws Exception {
//        Student student = new Student();
//        student.setName("HasAppointments");
//        student = studentRepository.save(student);
//
//        // Create a dummy appointment linked to the student
//        Appointment appointment = new Appointment();
//        appointment.setStudent(student);
//        appointmentRepository.save(appointment);
//
//        mockMvc.perform(delete("/api/v1/student/{id}", student.getId()))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Student with ID" + student.getId() + " has appointment"));
//    }
//
//    @Test
//    @DisplayName("GET /api/v1/student/{id} - Nonexistent student returns 404")
//    void getStudentById_NotFound() throws Exception {
//        mockMvc.perform(get("/api/v1/student/{id}", 999L))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Student with ID 999 not found"));
//    }
//
//    @Test
//    @DisplayName("GET /api/v1/student/search?name=Nonexistent - Nonexistent student by name returns 404")
//    void getStudentByName_NotFound() throws Exception {
//        mockMvc.perform(get("/api/v1/student/search")
//                        .param("name", "Nonexistent"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Student with NAME Nonexistent not found"));
//    }
//
//}
