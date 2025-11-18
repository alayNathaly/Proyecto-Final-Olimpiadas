package com.cog.proyecto_olimpiadas_api.controller;

import com.cog.proyecto_olimpiadas_api.entity.TrainingSession;
import com.cog.proyecto_olimpiadas_api.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    // POST /sessions  -> crear sesión
    @PostMapping
    public TrainingSession create(@RequestBody TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    // GET /sessions  -> ver todas (solo para probar)
    @GetMapping
    public List<TrainingSession> getAll() {
        return trainingSessionRepository.findAll();
    }

    // GET /sessions/athlete/{id}  -> todas las sesiones de ese atleta
    @GetMapping("/athlete/{athleteId}")
    public List<TrainingSession> getByAthlete(@PathVariable Long athleteId) {
        return trainingSessionRepository.findByAthlete_Id(athleteId);
    }

    // GET /sessions/athlete/{id}/range?inicio=yyyy-MM-dd&fin=yyyy-MM-dd
    @GetMapping("/athlete/{athleteId}/range")
    public List<TrainingSession> getByAthleteAndRange(
            @PathVariable Long athleteId,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin")     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin
    ) {
        return trainingSessionRepository.findByAthlete_IdAndFechaBetween(athleteId, inicio, fin);
    }
}
