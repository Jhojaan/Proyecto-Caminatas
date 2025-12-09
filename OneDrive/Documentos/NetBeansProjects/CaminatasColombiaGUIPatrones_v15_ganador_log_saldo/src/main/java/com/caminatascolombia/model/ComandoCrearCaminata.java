
package com.caminatascolombia.model;

public class ComandoCrearCaminata implements Comando {

    private CaminataCreator creator;
    private SistemaCaminatas sistema = SistemaCaminatas.getInstancia();

    public ComandoCrearCaminata(CaminataCreator creator) {
        this.creator = creator;
    }

    @Override
    public void ejecutar() {
        Caminata c = creator.crearCaminata();
        sistema.registrarCaminata(c);
    }
}
