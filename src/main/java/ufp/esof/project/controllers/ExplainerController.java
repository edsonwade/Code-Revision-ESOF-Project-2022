package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.dto.ExplainerDto;
import ufp.esof.project.filters.FilterObject;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.services.ExplainerService;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "/api/v1/explainer")
@RequiredArgsConstructor
public class ExplainerController {


    private Logger logger = LoggerFactory.getLogger(ExplainerController.class);

    private final ExplainerService explainerServiceImpl;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Set<Explainer> getAllExplainer(@ModelAttribute FilterObject filterObject) {
        return explainerServiceImpl.getFilteredExplainer(filterObject);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> getExplainerById(@PathVariable("id") Long id) {
        Optional<Explainer> explainerOptional = explainerServiceImpl.getById(id);
        if (explainerOptional.isPresent())
            return ResponseEntity.ok(explainerOptional.get());
        throw new InvalidExplainerException(id);
    }

    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> createExplainer(@RequestBody ExplainerDto explainer) {
        Optional<Explainer> explainerOptional = this.explainerServiceImpl.saveExplainer(explainer);
        if (explainerOptional.isPresent())
            return ResponseEntity.ok(explainerOptional.get());
        throw new ExplainerNotCreatedException(explainer.getName());
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explainer> updateExplainer(@RequestBody ExplainerDto explainer, @PathVariable("id") Long id) {
        Optional<Explainer> explainerOptional = explainerServiceImpl.getById(id);
        if (explainerOptional.isEmpty())
            throw new InvalidExplainerException(id);

        explainerOptional = this.explainerServiceImpl.editExplainer(explainerOptional.get(), explainer, id);
        if (explainerOptional.isPresent())
            return ResponseEntity.ok(explainerOptional.get());

        throw new ExplainerNotEditedException(id);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteExplainer(@PathVariable("id") Long id) {
        boolean res = this.explainerServiceImpl.deleteById(id);
        Optional<Explainer> optionalExplainer = this.explainerServiceImpl.getById(id);
        if (optionalExplainer.isPresent())
            throw new DegreeController.DegreeNotDeletedException(optionalExplainer.get().getName());

        if (res)
            return ResponseEntity.ok("Explainer Deleted Successfully");
        throw new InvalidExplainerException(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Explainer not created")
    public static class ExplainerNotCreatedException extends RuntimeException {
        public ExplainerNotCreatedException(String name) {
            super("The explainer with name \"" + name + "\" was not created");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Explainer")
    public static class InvalidExplainerException extends RuntimeException {
        public InvalidExplainerException(Long id) {
            super("The explainer with id \"" + id + "\" does not exist");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Explainer not edited")
    public static class ExplainerNotEditedException extends RuntimeException {
        public ExplainerNotEditedException(Long id) {
            super("The explainer with id \"" + id + "\" was not edited");
        }
    }
}

