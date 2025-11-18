package com.cog.proyecto_olimpiadas_api.repository;

import com.cog.proyecto_olimpiadas_api.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    // Todas las sesiones de un atleta
    List<TrainingSession> findByAthlete_Id(Long athleteId);

    // Sesiones de un atleta en un rango de fechas (inclusive)
    List<TrainingSession> findByAthlete_IdAndFechaBetween(
            Long athleteId,
            LocalDate inicio,
            LocalDate fin
    );
}
