package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.models.College;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CollegeRepo;
import ufp.esof.project.repository.DegreeRepo;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CollegeService Tests")
class CollegeServiceTest {

    @Mock
    private CollegeRepo collegeRepo;

    @Mock
    private DegreeRepo degreeRepo;

    @InjectMocks
    private CollegeService collegeService;

    private College testCollege;

    @BeforeEach
    void setUp() {
        testCollege = new College("Test College");
        testCollege.setId(1L);
    }

    @Test
    @DisplayName("Should find college by id")
    void testFindById() {
        when(collegeRepo.findById(1L)).thenReturn(Optional.of(testCollege));
        Optional<College> opt = collegeService.findById(1L);
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("Should find college by name")
    void testFindByName() {
        when(collegeRepo.findByName("Test College")).thenReturn(Optional.of(testCollege));
        Optional<College> opt = collegeService.findByName("Test College");
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("Should delete college when no degrees")
    void testDeleteByIdNoDegrees() {
        testCollege.setDegrees(Collections.emptySet());
        when(collegeRepo.findById(1L)).thenReturn(Optional.of(testCollege));
        boolean deleted = collegeService.deleteById(1L);
        assertThat(deleted).isTrue();
        verify(collegeRepo).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete college when degrees exist")
    void testDeleteByIdWithDegrees() {
        // degree set non-empty
        testCollege.getDegrees().add(new Degree("D"));
        when(collegeRepo.findById(1L)).thenReturn(Optional.of(testCollege));
        boolean deleted = collegeService.deleteById(1L);
        assertThat(deleted).isFalse();
        verify(collegeRepo, never()).deleteById(1L);
    }
}
