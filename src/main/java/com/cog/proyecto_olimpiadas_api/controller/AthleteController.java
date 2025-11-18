package com.cog.proyecto_olimpiadas_api.controller;

import com.cog.proyecto_olimpiadas_api.entity.Athlete;
import com.cog.proyecto_olimpiadas_api.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletes")
public class AthleteController {

    @Autowired
    private AthleteRepository repository;

    // 👉 POST: Crear atleta
    @PostMapping
    public Athlete create(@RequestBody Athlete athlete) {
        return repository.save(athlete);
    }

    // 👉 GET: Listar todos los atletas
    @GetMapping
    public List<Athlete> getAll() {
        return repository.findAll();
    }

    // 👉 GET: Obtener atleta por ID
    @GetMapping("/{id}")
    public Athlete getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
