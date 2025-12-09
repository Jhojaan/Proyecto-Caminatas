
package com.caminatascolombia.model;

import java.util.Date;

public class CaminataBuilder implements ICaminataBuilder {

    private Caminata caminata;

    @Override
    public void reset() {
        this.caminata = null;
    }

    @Override
    public void setDatosBasicos(String nombre, String lugar, Date fecha,
                                TipoCaminata tipo, Dificultad dificultad, double costoBase) {
        this.caminata = new Caminata(nombre, lugar, fecha, tipo, dificultad, costoBase);
    }

    @Override
    public void setDescripcion(String descripcion, String recomendaciones) {
        if (caminata != null) {
            caminata.setDescripcion(descripcion);
            caminata.setRecomendaciones(recomendaciones);
        }
    }

    @Override
    public void setItinerarioDemo() {
        if (caminata != null) {
            Etapa e1 = new Etapa("Inicio");
            e1.agregar(new Paso("Reunion en el punto acordado"));
            e1.agregar(new Paso("Charla de seguridad e instrucciones generales"));

            Etapa e2 = new Etapa("Recorrido");
            e2.agregar(new Paso("Ascenso por el sendero principal"));
            e2.agregar(new Paso("Paradas para hidratacion y fotografias"));

            Etapa e3 = new Etapa("Retorno");
            e3.agregar(new Paso("Descenso controlado"));
            e3.agregar(new Paso("Cierre y recomendaciones finales"));

            caminata.setItinerario(e1); // se puede extender a una estructura mas compleja
        }
    }

    @Override
    public Caminata getResultado() {
        return caminata;
    }
}
