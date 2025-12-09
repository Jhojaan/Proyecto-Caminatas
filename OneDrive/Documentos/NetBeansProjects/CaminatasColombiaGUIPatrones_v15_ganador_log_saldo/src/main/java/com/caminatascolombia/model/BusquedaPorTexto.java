
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.List;

public class BusquedaPorTexto implements IEstrategiaBusqueda {

    private String texto;

    public BusquedaPorTexto(String texto) {
        this.texto = texto == null ? "" : texto.toLowerCase();
    }

    @Override
    public List<Caminata> buscar(List<Caminata> base) {
        List<Caminata> res = new ArrayList<>();
        for (Caminata c : base) {
            String desc = c.getDescripcion() == null ? "" : c.getDescripcion().toLowerCase();
            String lugar = c.getLugar() == null ? "" : c.getLugar().toLowerCase();
            if (desc.contains(texto) || lugar.contains(texto)) {
                res.add(c);
            }
        }
        return res;
    }
}
