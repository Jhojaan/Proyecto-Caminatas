
package com.caminatascolombia.model;

public interface EstadoInscripcion {
    String getNombre();
    void avanzar(Inscripcion inscripcion);
    boolean puedeComentar();
}
