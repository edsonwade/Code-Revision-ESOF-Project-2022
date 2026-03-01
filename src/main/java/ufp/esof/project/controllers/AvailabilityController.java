package ufp.esof.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufp.esof.project.dto.availability.AvailabilityRequestDTO;
import ufp.esof.project.dto.availability.AvailabilityResponseDTO;
import ufp.esof.project.services.AvailabilityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/availabilities")
@RequiredArgsConstructor
@SuppressWarnings("all")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping()
    public ResponseEntity<List<AvailabilityResponseDTO>> getAllAvailabilities() {
        return ResponseEntity.ok(this.availabilityService.findAllAvailabilities());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<AvailabilityResponseDTO>> getAvailabilityById(@PathVariable Long id) {
        var availabilityResponseDTO = this.availabilityService.findAvailabilityById(id);
        if (availabilityResponseDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(availabilityResponseDTO);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAvailability(@PathVariable Long id) {
        var deletedByAvailabilityId = this.availabilityService.deleteByAvailabilityId(id);
        if (!deletedByAvailabilityId) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Availability with id " + deletedByAvailabilityId + " deleted successfully!");
    }

    @PostMapping()
    public ResponseEntity<AvailabilityResponseDTO> createAvailability(@Valid @RequestBody AvailabilityRequestDTO availabilityRequestDTO) {
        var availabilityResponseDTO = this.availabilityService.createAvailability(availabilityRequestDTO);
        if (availabilityResponseDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(availabilityResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AvailabilityResponseDTO> updateAvailability(@PathVariable Long id,
                                                                      @Valid @RequestBody AvailabilityRequestDTO availabilityRequestDTO) {
        var availabilityResponseDTO = this.availabilityService.updateAvailability(id, availabilityRequestDTO);
        if (availabilityResponseDTO == null || availabilityResponseDTO.getId() != id) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(availabilityResponseDTO);
    }

}
