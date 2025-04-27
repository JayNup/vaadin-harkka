package org.hohoho.cheer.repository;

import org.hohoho.cheer.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByPersonId(Long personId); // Hae mittaukset henkilön mukaan
}
