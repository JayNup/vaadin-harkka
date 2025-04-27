package org.hohoho.cheer.controller;

import org.hohoho.cheer.model.Measurement;
import org.hohoho.cheer.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {
    @Autowired
    private MeasurementRepository measRepo;

    @GetMapping("/person/{personId}")
    public List<Measurement> getByPerson(@PathVariable Long personId) {
        return measRepo.findByPersonId(personId);
    }

    @PostMapping
    public Measurement create(@RequestBody Measurement m) {
        return measRepo.save(m);
    }
}
