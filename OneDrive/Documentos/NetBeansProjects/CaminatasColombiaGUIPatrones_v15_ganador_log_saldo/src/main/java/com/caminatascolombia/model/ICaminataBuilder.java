
package com.caminatascolombia.model;

import java.util.Date;

public interface ICaminataBuilder {
    void reset();
    void setDatosBasicos(String nombre, String lugar, Date fecha,
                         TipoCaminata tipo, Dificultad dificultad, double costoBase);
    void setDescripcion(String descripcion, String recomendaciones);
    void setItinerarioDemo();
    Caminata getResultado();
}
