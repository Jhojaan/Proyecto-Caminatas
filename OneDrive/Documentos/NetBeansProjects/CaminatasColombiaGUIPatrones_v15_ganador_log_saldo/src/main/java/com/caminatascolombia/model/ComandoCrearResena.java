
package com.caminatascolombia.model;

/**
 * Command para crear una resena de blog.
 * Encapsula la accion de agregar la resena al SistemaCaminatas.
 */
public class ComandoCrearResena implements Comando {

    private SistemaCaminatas sistema;
    private ResenaBlog resena;

    public ComandoCrearResena(SistemaCaminatas sistema, ResenaBlog resena) {
        this.sistema = sistema;
        this.resena = resena;
    }

    @Override
    public void ejecutar() {
        sistema.agregarResena(resena);
    }
}
