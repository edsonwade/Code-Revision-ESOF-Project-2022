package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.DegreeRepo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DegreeService Tests")
class DegreeServiceTest {

    @Mock
    private DegreeRepo degreeRepo;

    @Mock
    private CollegeService collegeService;

    @InjectMocks
    private DegreeService degreeService;

    private Degree testDegree;

    @BeforeEach
    void setUp() {
        testDegree = new Degree("Test Degree");
        testDegree.setId(1L);
    }

    @Test
    @DisplayName("Should find degree by id")
    void testFindById() {
        when(degreeRepo.findById(1L)).thenReturn(Optional.of(testDegree));
        Optional<Degree> opt = degreeService.findById(1L);
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("Should find degree by name")
    void testFindByName() {
        when(degreeRepo.findByName("Test Degree")).thenReturn(Optional.of(testDegree));
        Optional<Degree> opt = degreeService.findByName("Test Degree");
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("Should delete degree by id when exists")
    void testDeleteByIdExists() {
        when(degreeRepo.findById(1L)).thenReturn(Optional.of(testDegree));
        boolean deleted = degreeService.deleteById(1L);
        assertThat(deleted).isTrue();
        verify(degreeRepo).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete degree when not exists")
    void testDeleteByIdNotExists() {
        when(degreeRepo.findById(999L)).thenReturn(Optional.empty());
        boolean deleted = degreeService.deleteById(999L);
        assertThat(deleted).isFalse();
    }
}
