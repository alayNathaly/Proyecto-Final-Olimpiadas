package com.cog.proyecto_olimpiadas_api.service;

import com.cog.proyecto_olimpiadas_api.dto.TrainingStatsDto;
import com.cog.proyecto_olimpiadas_api.dto.TrainingStatsDto.SesionDto;
import com.cog.proyecto_olimpiadas_api.dto.TrainingStatsDto.TipoStats;
import com.cog.proyecto_olimpiadas_api.entity.Athlete;
import com.cog.proyecto_olimpiadas_api.entity.TrainingSession;
import com.cog.proyecto_olimpiadas_api.repository.AthleteRepository;
import com.cog.proyecto_olimpiadas_api.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainingStatsService {

    private final AthleteRepository athleteRepository;
    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingStatsService(AthleteRepository athleteRepository,
                                TrainingSessionRepository trainingSessionRepository) {
        this.athleteRepository = athleteRepository;
        this.trainingSessionRepository = trainingSessionRepository;
    }

    public TrainingStatsDto statsForAthlete(Long athleteId) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Atleta no encontrado"));

        List<TrainingSession> sesiones =
                trainingSessionRepository.findByAthlete_Id(athleteId);

        // Ordenar por fecha
        sesiones.sort(Comparator.comparing(TrainingSession::getFecha));

        TrainingStatsDto dto = new TrainingStatsDto();
        dto.athleteId = athlete.getId();
        dto.athleteNombre = athlete.getNombre();

        // Historial
        dto.historial = sesiones.stream().map(s -> {
            SesionDto sd = new SesionDto();
            sd.id = s.getId();
            sd.fecha = s.getFecha();
            sd.tipo = s.getTipo();
            sd.valor = s.getValor();
            sd.unidad = s.getUnidad();
            sd.nota = s.getNota();
            sd.ubicacion = s.getUbicacion();
            sd.pais = s.getPais();
            return sd;
        }).collect(Collectors.toList());

        // Agrupar por tipo para calcular promedio, mejor marca y evolución
        Map<String, List<TrainingSession>> porTipo =
                sesiones.stream().collect(Collectors.groupingBy(TrainingSession::getTipo,
                        LinkedHashMap::new, Collectors.toList()));

        Map<String, TipoStats> mapaStats = new LinkedHashMap<>();

        for (Map.Entry<String, List<TrainingSession>> entry : porTipo.entrySet()) {
            String tipo = entry.getKey();
            List<TrainingSession> lista = entry.getValue();

            double suma = lista.stream().mapToDouble(TrainingSession::getValor).sum();
            double promedio = lista.isEmpty() ? 0 : suma / lista.size();

            // Mejor marca:
            // - Si unidad es "seg": menor es mejor
            // - Si unidad es "kg": mayor es mejor
            TrainingSession mejor = lista.get(0);
            for (TrainingSession s : lista) {
                boolean esTiempo = "seg".equalsIgnoreCase(s.getUnidad());
                boolean mejorEsTiempo = "seg".equalsIgnoreCase(mejor.getUnidad());
                if (esTiempo && mejorEsTiempo) {
                    if (s.getValor() < mejor.getValor()) mejor = s;
                } else if (!esTiempo && !mejorEsTiempo) {
                    if (s.getValor() > mejor.getValor()) mejor = s;
                }
            }

            TipoStats ts = new TipoStats();
            ts.tipo = tipo;
            ts.unidad = lista.get(0).getUnidad();
            ts.promedio = promedio;
            ts.mejorValor = mejor.getValor();
            ts.fechaMejor = mejor.getFecha();

            mapaStats.put(tipo, ts);
        }

        dto.porTipo = mapaStats;
        return dto;
    }
}
