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
import ufp.esof.project.dto.CollegeRequestDTO;
import ufp.esof.project.dto.college.CollegeResponseDTO;
import ufp.esof.project.exception.college_exception.CollegeBadRequestException;
import ufp.esof.project.exception.college_exception.CollegeNotFoundException;
import ufp.esof.project.services.CollegeService;

import java.util.List;

@RestController
@RequestMapping("/colleges")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping
    public ResponseEntity<List<CollegeResponseDTO>> getAllColleges() {
        return ResponseEntity.ok(collegeService.getAllColleges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeResponseDTO> getCollegeById(@PathVariable Long id) {
        return collegeService.getCollegeById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CollegeNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<CollegeResponseDTO> createCollege(@Valid @RequestBody CollegeRequestDTO request) {
        return collegeService.createCollege(request)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElseThrow(() -> new CollegeBadRequestException(request.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollegeResponseDTO> updateCollege(
            @PathVariable Long id,
            @Valid @RequestBody
            CollegeRequestDTO request) {
        return collegeService.updateCollege(id, request)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CollegeNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        try {
            boolean deleted = collegeService.deleteCollege(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            throw new CollegeNotFoundException(id);
        } catch (CollegeBadRequestException e) {
            throw new CollegeBadRequestException(id);
        }
    }

}
