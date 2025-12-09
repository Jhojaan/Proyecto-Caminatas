
package com.caminatascolombia.model;

public class RecreativaCreator extends CaminataCreator {

    @Override
    public Caminata crearCaminata() {
        return director.construirCaminataRecreativa(
                "Caminata recreativa especial",
                "Destino por definir",
                10,
                Dificultad.MEDIA,
                100000
        );
    }
}
