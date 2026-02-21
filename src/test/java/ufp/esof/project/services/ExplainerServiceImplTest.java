package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.dto.ExplainerDto;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.CourseRepo;
import ufp.esof.project.repository.ExplainerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExplainerServiceImpl Tests")
class ExplainerServiceImplTest {

    @Mock
    private ExplainerRepository explainerRepository;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private ExplainerServiceImpl explainerService;

    private Explainer explainer;

    @BeforeEach
    void setUp() {
        explainer = new Explainer("Test");
        explainer.setId(2L);
    }

    @Test
    @DisplayName("Should return explainer by id when exists")
    void testGetById() {
        when(explainerRepository.findById(2L)).thenReturn(Optional.of(explainer));
        Optional<Explainer> opt = explainerService.getById(2L);
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("Should delete explainer by id when exists")
    void testDeleteByIdExists() {
        when(explainerRepository.findById(2L)).thenReturn(Optional.of(explainer));
        boolean result = explainerService.deleteById(2L);
        assertThat(result).isTrue();
        verify(explainerRepository).deleteById(2L);
    }

    @Test
    @DisplayName("Should return empty when saving explainer with existing name")
    void testSaveExplainerExistingName() {
        Explainer e = new Explainer("Test");
        when(explainerRepository.findByName("Test")).thenReturn(Optional.of(e));
        Optional<Explainer> result = explainerService.saveExplainer(e);
        assertThat(result).isEmpty();
    }
}

