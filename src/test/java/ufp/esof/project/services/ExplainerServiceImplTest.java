package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.dto.explainer.ExplainerRequestDTO;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.CourseRepository;
import ufp.esof.project.repository.ExplainerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExplainerServiceImpl Tests")
@SuppressWarnings("unused")
class ExplainerServiceImplTest {

    @Mock
    private ExplainerRepository explainerRepository;

    @Mock
    private CourseRepository courseRepository;

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
        boolean result = explainerService.deleteExplainer(2L);
        assertThat(result).isTrue();
        verify(explainerRepository).deleteById(2L);
    }

    @Test
    @DisplayName("Should throw exception when saving explainer with existing name")
    void testSaveExplainerExistingName() {
        Explainer e = new Explainer("Test");

        ExplainerRequestDTO explainerResponseDTO = new ExplainerRequestDTO();
        explainerResponseDTO.setName("Test");
        explainerResponseDTO.setEmail("test@gmail.com");

        when(explainerRepository.findByName("Test")).thenReturn(Optional.of(e));
        
        assertThatThrownBy(() -> explainerService.createExplainer(explainerResponseDTO))
                .hasMessage("Explainer with name 'Test' already exists");
        verify(explainerRepository).findByName("Test");
    }
}

