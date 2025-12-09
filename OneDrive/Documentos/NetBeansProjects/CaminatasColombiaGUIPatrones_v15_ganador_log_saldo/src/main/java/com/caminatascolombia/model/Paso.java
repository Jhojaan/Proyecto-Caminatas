
package com.caminatascolombia.model;

public class Paso implements ComponenteItinerario {

    private String descripcion;

    public Paso(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
