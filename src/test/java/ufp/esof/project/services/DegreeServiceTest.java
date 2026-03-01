package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CollegeRepository;
import ufp.esof.project.repository.DegreeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DegreeService Tests")
class DegreeServiceTest {

    @Mock
    private DegreeRepository degreeRepository;

    @Mock
    private CollegeRepository collegeRepository;

    private DegreeServiceImpl degreeService;

    private Degree testDegree;

    @BeforeEach
    void setUp() {
        degreeService = new DegreeServiceImpl(degreeRepository, collegeRepository);
        testDegree = new Degree("Test Degree");
        testDegree.setId(1L);
    }

    @Test
    @DisplayName("Should find degree by id")
    void testFindById() {
        when(degreeRepository.findById(1L)).thenReturn(Optional.of(testDegree));
        var degreeServiceById = degreeService.getDegreeById(1L);
        assertThat(degreeServiceById).isPresent();
        assertThat(degreeServiceById.get().getId()).isEqualTo(1L);
        assertThat(degreeServiceById.get().getName()).isEqualTo("Test Degree");
        verify(degreeRepository, atLeastOnce()).findById(1L);

    }

    @Test
    @DisplayName("Should find degree by name")
    void testFindByName() {
        when(degreeRepository.findByName("Test Degree")).thenReturn(Optional.of(testDegree));
        var degreeByName = degreeService.getDegreeByName("Test Degree");
        assertThat(degreeByName).isPresent();
        assertThat(degreeByName.get().getId()).isEqualTo(1L);
        assertThat(degreeByName.get().getName()).isEqualTo("Test Degree");
        verify(degreeRepository, atLeastOnce()).findByName("Test Degree");
    }

    @Test
    @DisplayName("Should delete degree by id when exists")
    void testDeleteByIdExists() {
        when(degreeRepository.findById(1L)).thenReturn(Optional.of(testDegree));
        boolean deleted = degreeService.deleteDegree(1L);
        assertThat(deleted).isTrue();
        verify(degreeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete degree when not exists")
    void testDeleteByIdNotExists() {
        when(degreeRepository.findById(999L)).thenReturn(Optional.empty());
        boolean deleted = degreeService.deleteDegree(999L);
        assertThat(deleted).isFalse();
    }
}
