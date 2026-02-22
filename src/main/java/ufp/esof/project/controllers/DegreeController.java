package ufp.esof.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufp.esof.project.dto.degree.DegreeRequestDTO;
import ufp.esof.project.dto.degree.DegreeResponseDTO;
import ufp.esof.project.exception.DegreeNotFoundException;
import ufp.esof.project.services.DegreeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/degrees")
@RequiredArgsConstructor
@SuppressWarnings("all")
public class DegreeController {

    private final DegreeService degreeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DegreeResponseDTO>> getAllDegrees() {
        return ResponseEntity.ok(this.degreeService.getAllDegrees());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<DegreeResponseDTO>> getDegreeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.degreeService.getDegreeById(id));

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DegreeResponseDTO> createDegree(@Valid @RequestBody DegreeRequestDTO degreeRequestDTO) {
        var createdDegree = this.degreeService.createDegree(degreeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDegree);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DegreeResponseDTO> updateDegree(@PathVariable Long id,
                                                          @Valid @RequestBody DegreeRequestDTO degreeRequestDTO) {
        var updatedDegree = this.degreeService.updateDegree(id, degreeRequestDTO);
        return ResponseEntity.ok(updatedDegree);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteDegree(@PathVariable Long id) {
        boolean deleted = this.degreeService.deleteDegree(id);
        if (deleted) {
            return ResponseEntity.ok("Degree deleted successfully!");
        }
        throw new DegreeNotFoundException(id);
    }
}
