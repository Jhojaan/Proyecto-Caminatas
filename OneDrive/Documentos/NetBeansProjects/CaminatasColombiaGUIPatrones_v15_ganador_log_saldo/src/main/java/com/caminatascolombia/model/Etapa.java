
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.List;

public class Etapa implements ComponenteItinerario {

    private String nombre;
    private List<ComponenteItinerario> pasos = new ArrayList<>();

    public Etapa(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(ComponenteItinerario comp) {
        pasos.add(comp);
    }

    @Override
    public String getDescripcion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Etapa: ").append(nombre).append("\n");
        for (ComponenteItinerario c : pasos) {
            sb.append("  - ").append(c.getDescripcion()).append("\n");
        }
        return sb.toString();
    }
}
