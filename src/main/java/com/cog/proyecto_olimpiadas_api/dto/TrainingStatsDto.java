package com.cog.proyecto_olimpiadas_api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TrainingStatsDto {

    public static class SesionDto {
        public Long id;
        public LocalDate fecha;
        public String tipo;
        public double valor;
        public String unidad;
        public String nota;
        public String ubicacion;
        public String pais;
    }

    public static class TipoStats {
        public String tipo;
        public String unidad;
        public double promedio;
        public double mejorValor;
        public LocalDate fechaMejor;
    }

    public Long athleteId;
    public String athleteNombre;

    // Historial completo
    public List<SesionDto> historial;

    // Estadísticas por tipo (clave = tipo de entrenamiento)
    public Map<String, TipoStats> porTipo;
}
