
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.List;

public class InvocadorComandos {

    private List<Comando> historial = new ArrayList<>();

    public void ejecutarComando(Comando comando) {
        comando.ejecutar();
        historial.add(comando);
    }
}
