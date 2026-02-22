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
import ufp.esof.project.repository.CollegeRepository;
import ufp.esof.project.repository.DegreeRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CollegeService Tests")
@SuppressWarnings("unused")
class CollegeServiceTest {

    @Mock
    private CollegeRepository collegeRepository;

    @Mock
    private DegreeRepository degreeRepository;

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
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(testCollege));
        var collegeById = collegeService.getCollegeById(1L);
        assertThat(collegeById).isPresent();
        assertThat(collegeById.get().getName()).isEqualTo("Test College");
        assertThat(collegeById.get().getId()).isEqualTo(1L);
        verify(collegeRepository).findById(1L);
    }


    @Test
    @DisplayName("Should delete college when no degrees")
    void testDeleteByIdNoDegrees() {
        testCollege.setDegrees(Collections.emptySet());
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(testCollege));
        boolean deleted = collegeService.deleteCollege(1L);
        assertThat(deleted).isTrue();
        verify(collegeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete college when degrees exist")
    void testDeleteByIdWithDegrees() {
        // degree set non-empty
        testCollege.getDegrees().add(new Degree("D"));
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(testCollege));
        boolean deleted = collegeService.deleteCollege(1L);
        assertThat(deleted).isFalse();
        verify(collegeRepository, never()).deleteById(1L);
    }
}
