
package com.caminatascolombia.model;

public class CompetenciaCreator extends CaminataCreator {

    @Override
    public Caminata crearCaminata() {
        return director.construirCaminataCompetencia(
                "Caminata de competencia especial",
                "Destino por definir",
                20,
                Dificultad.ALTA,
                180000
        );
    }
}
