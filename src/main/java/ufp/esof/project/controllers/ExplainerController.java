package ufp.esof.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ufp.esof.project.dto.explainer.ExplainerRequestDTO;
import ufp.esof.project.dto.explainer.ExplainerResponseDTO;
import ufp.esof.project.services.ExplainerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/explainers")
@RequiredArgsConstructor
@SuppressWarnings("all")
public class ExplainerController {

    private static final Logger logger = LoggerFactory.getLogger(ExplainerController.class);

    private final ExplainerService explainerService;

    @GetMapping()
    public ResponseEntity<List<ExplainerResponseDTO>> getAllExplainer() {
        logger.info("all explainers");
        return ResponseEntity.ok(explainerService.getAllExplainers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<ExplainerResponseDTO>> getExplainerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(explainerService.getExplainerById(id));

    }

    @PostMapping(value = "/create")
    public ResponseEntity<ExplainerResponseDTO> createExplainer(@Valid @RequestBody ExplainerRequestDTO explainerRequestDTO) {
        var explainer = this.explainerService.createExplainer(explainerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(explainer);


    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ExplainerResponseDTO> updateExplainer(@Valid @RequestBody ExplainerRequestDTO explainerRequestDTO,
                                                                @PathVariable("id") Long id) {
        var explainer = this.explainerService.updateExplainer(id, explainerRequestDTO);
        return ResponseEntity.ok().body(explainer);

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteExplainer(@PathVariable Long id) {
        boolean deleted = this.explainerService.deleteExplainer(id);
        if (deleted) {
            return ResponseEntity.ok("Explainer Deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Explainer Not Found");

    }


}
