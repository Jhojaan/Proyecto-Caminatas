
package com.caminatascolombia.model;

public class EstadoConfirmada implements EstadoInscripcion {

    @Override
    public String getNombre() {
        return "CONFIRMADA";
    }

    @Override
    public void avanzar(Inscripcion inscripcion) {
        inscripcion.setEstado(new EstadoFinalizada());
    }

    @Override
    public boolean puedeComentar() {
        return false;
    }
}
