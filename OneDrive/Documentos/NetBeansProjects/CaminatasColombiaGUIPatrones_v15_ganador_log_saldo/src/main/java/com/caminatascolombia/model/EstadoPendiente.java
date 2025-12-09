
package com.caminatascolombia.model;

public class EstadoPendiente implements EstadoInscripcion {

    @Override
    public String getNombre() {
        return "PENDIENTE";
    }

    @Override
    public void avanzar(Inscripcion inscripcion) {
        inscripcion.setEstado(new EstadoConfirmada());
    }

    @Override
    public boolean puedeComentar() {
        return false;
    }
}
