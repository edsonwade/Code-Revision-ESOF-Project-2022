package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.dto.AvailabilityDto;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.AvailabilityRepository;
import ufp.esof.project.repository.ExplainerRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AvailabilityService Tests")
class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private ExplainerRepository explainerRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    private AvailabilityDto availabilityDto;
    private Availability availability;
    private Explainer explainer;

    @BeforeEach
    void setUp() {
        availabilityDto = new AvailabilityDto();
        explainer = new Explainer("Test");
        explainer.setId(2L);
        availabilityDto.setExplainer(explainer);
        availabilityDto.setDayOfWeek(DayOfWeek.MONDAY);
        availabilityDto.setStart(LocalTime.of(9,0));
        availabilityDto.setEnd(LocalTime.of(12,0));

        availability = new Availability(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(12,0));
        availability.setId(1L);
        availability.setExplainer(explainer);
    }

    @Test
    @DisplayName("Should create availability when explainer exists")
    void testCreateAvailabilitySuccess() {
        when(explainerRepository.findByName(anyString())).thenReturn(Optional.of(explainer));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);

        Optional<Availability> result = availabilityService.createAvailability(availabilityDto);

        assertThat(result).isPresent();
        verify(availabilityRepository).save(any(Availability.class));
    }

    @Test
    @DisplayName("Should return empty when explainer not found for availability creation")
    void testCreateAvailabilityExplainerNotFound() {
        when(explainerRepository.findByName(anyString())).thenReturn(Optional.empty());

        Optional<Availability> result = availabilityService.createAvailability(availabilityDto);

        assertThat(result).isEmpty();
        verify(availabilityRepository, never()).save(any(Availability.class));
    }
}

