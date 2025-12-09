
package com.caminatascolombia.model;

import java.util.Calendar;
import java.util.Date;

public class CaminataDirector {

    private ICaminataBuilder builder;

    public CaminataDirector(ICaminataBuilder builder) {
        this.builder = builder;
    }

    private Date fechaRelativa(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    public Caminata construirCaminataRecreativa(String nombre, String lugar,
                                                int diasDesdeHoy, Dificultad dif,
                                                double costoBase) {
        builder.reset();
        builder.setDatosBasicos(nombre, lugar, fechaRelativa(diasDesdeHoy),
                TipoCaminata.RECREATIVA, dif, costoBase);
        builder.setDescripcion(
                "Caminata recreativa ideal para disfrutar del paisaje y compartir en grupo.",
                "Llevar ropa comoda, hidratacion y protector solar.");
        builder.setItinerarioDemo();
        return builder.getResultado();
    }

    public Caminata construirCaminataEntrenamiento(String nombre, String lugar,
                                                   int diasDesdeHoy, Dificultad dif,
                                                   double costoBase) {
        builder.reset();
        builder.setDatosBasicos(nombre, lugar, fechaRelativa(diasDesdeHoy),
                TipoCaminata.DEPORTIVA_ENTRENAMIENTO, dif, costoBase);
        builder.setDescripcion(
                "Caminata de entrenamiento fisico con tramos exigentes.",
                "Se recomienda buen estado fisico, botas de montana y bastones.");
        builder.setItinerarioDemo();
        return builder.getResultado();
    }

    public Caminata construirCaminataCompetencia(String nombre, String lugar,
                                                 int diasDesdeHoy, Dificultad dif,
                                                 double costoBase) {
        builder.reset();
        builder.setDatosBasicos(nombre, lugar, fechaRelativa(diasDesdeHoy),
                TipoCaminata.DEPORTIVA_COMPETENCIA, dif, costoBase);
        builder.setDescripcion(
                "Evento de competencia cronometrado con clasificacion por categorias.",
                "Uso obligatorio de equipo de seguridad y certificado medico.");
        builder.setItinerarioDemo();
        return builder.getResultado();
    }
}
