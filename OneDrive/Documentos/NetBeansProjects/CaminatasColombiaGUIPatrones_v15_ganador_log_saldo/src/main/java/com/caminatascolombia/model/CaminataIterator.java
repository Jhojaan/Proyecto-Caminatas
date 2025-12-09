
package com.caminatascolombia.model;

import java.util.Iterator;
import java.util.List;

public class CaminataIterator implements Iterator<Caminata> {

    private List<Caminata> lista;
    private int indice = 0;

    public CaminataIterator(List<Caminata> lista) {
        this.lista = lista;
    }

    @Override
    public boolean hasNext() {
        return indice < lista.size();
    }

    @Override
    public Caminata next() {
        return lista.get(indice++);
    }
}
