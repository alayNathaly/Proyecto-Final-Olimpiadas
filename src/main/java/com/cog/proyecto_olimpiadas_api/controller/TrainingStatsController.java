package com.cog.proyecto_olimpiadas_api.controller;

import com.cog.proyecto_olimpiadas_api.dto.TrainingStatsDto;
import com.cog.proyecto_olimpiadas_api.service.TrainingStatsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
@CrossOrigin // por si acaso, para que el index.html pueda llamar
public class TrainingStatsController {

    private final TrainingStatsService service;

    public TrainingStatsController(TrainingStatsService service) {
        this.service = service;
    }

    @GetMapping("/athlete/{id}")
    public TrainingStatsDto statsForAthlete(@PathVariable Long id) {
        return service.statsForAthlete(id);
    }
}
