
package com.caminatascolombia.model;

public class EstadoFinalizada implements EstadoInscripcion {

    @Override
    public String getNombre() {
        return "FINALIZADA";
    }

    @Override
    public void avanzar(Inscripcion inscripcion) {
        // estado final
    }

    @Override
    public boolean puedeComentar() {
        return true;
    }
}
