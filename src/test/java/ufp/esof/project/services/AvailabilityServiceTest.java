package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.dto.AvailabilityDto;
import ufp.esof.project.dto.availability.AvailabilityRequestDTO;
import ufp.esof.project.dto.availability.AvailabilityResponseDTO;
import ufp.esof.project.exception.ExplainerNotFoundException;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.AvailabilityRepository;
import ufp.esof.project.repository.ExplainerRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AvailabilityService Tests")
class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private ExplainerRepository explainerRepository;

    AvailabilityRequestDTO availabilityRequestDTO;

    private AvailabilityServiceImpl availabilityService;

    private Availability availability;
    private Explainer explainer;

    @BeforeEach
    void setUp() {
        availabilityService = new AvailabilityServiceImpl(availabilityRepository, explainerRepository);

        AvailabilityDto availabilityDto = new AvailabilityDto();
        explainer = new Explainer("Test");
        explainer.setId(2L);
        availabilityDto.setExplainer(explainer);
        availabilityDto.setDayOfWeek(DayOfWeek.MONDAY);
        availabilityDto.setStart(LocalTime.of(9, 0));
        availabilityDto.setEnd(LocalTime.of(12, 0));

        availabilityRequestDTO = new AvailabilityRequestDTO();
        availabilityRequestDTO.setExplainerId(1L);
        availabilityRequestDTO.setStartTime(LocalDateTime.now());
        availabilityRequestDTO.setEndTime(LocalDateTime.now().plusHours(1));

        availability = new Availability(DayOfWeek.MONDAY, LocalDateTime.of(2026, 1, 1, 9, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0));
        availability.setId(1L);
        availability.setExplainer(explainer);
    }

    @Test
    @DisplayName("Should create availability when explainer exists")
    void testCreateAvailabilitySuccess() {
        when(explainerRepository.findById(anyLong())).thenReturn(Optional.of(explainer));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);

        AvailabilityResponseDTO result = availabilityService.createAvailability(availabilityRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        // others asserts validations
        assertThat(result.getExplainerId()).isEqualTo(2L);
        verify(availabilityRepository).save(any(Availability.class));
    }

    @Test
    @DisplayName("Should return empty when explainer not found for availability creation")
    void testCreateAvailabilityExplainerNotFound() {
        when(explainerRepository.findById(anyLong())).thenReturn(Optional.empty());

        var expectedMessage = assertThrows(ExplainerNotFoundException.class, () -> availabilityService.createAvailability(availabilityRequestDTO));

        assertThat(expectedMessage).hasMessage("Explainer not found");
        verify(availabilityRepository, never()).save(any(Availability.class));
    }
}

