
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.List;

public class BusquedaPorTipo implements IEstrategiaBusqueda {

    private TipoCaminata tipo;

    public BusquedaPorTipo(TipoCaminata tipo) {
        this.tipo = tipo;
    }

    @Override
    public List<Caminata> buscar(List<Caminata> base) {
        List<Caminata> res = new ArrayList<>();
        for (Caminata c : base) {
            if (c.getTipo() == tipo) {
                res.add(c);
            }
        }
        return res;
    }
}
