
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.List;

public class BusquedaPorDificultad implements IEstrategiaBusqueda {

    private Dificultad dificultad;

    public BusquedaPorDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    @Override
    public List<Caminata> buscar(List<Caminata> base) {
        List<Caminata> res = new ArrayList<>();
        for (Caminata c : base) {
            if (c.getDificultad() == dificultad) {
                res.add(c);
            }
        }
        return res;
    }
}
