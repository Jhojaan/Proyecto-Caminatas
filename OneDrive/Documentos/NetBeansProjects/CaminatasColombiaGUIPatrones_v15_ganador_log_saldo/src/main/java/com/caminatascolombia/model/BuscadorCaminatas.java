
package com.caminatascolombia.model;

import java.util.List;

public class BuscadorCaminatas {

    private IEstrategiaBusqueda estrategia;

    public void setEstrategia(IEstrategiaBusqueda estrategia) {
        this.estrategia = estrategia;
    }

    public List<Caminata> ejecutar(List<Caminata> base) {
        if (estrategia == null) return base;
        return estrategia.buscar(base);
    }
}
