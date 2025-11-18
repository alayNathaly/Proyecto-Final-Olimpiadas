package com.cog.proyecto_olimpiadas_api.repository;

import com.cog.proyecto_olimpiadas_api.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    // Para buscar atletas por nombre (como en tu programa original)
    Optional<Athlete> findByNombre(String nombre);
}
