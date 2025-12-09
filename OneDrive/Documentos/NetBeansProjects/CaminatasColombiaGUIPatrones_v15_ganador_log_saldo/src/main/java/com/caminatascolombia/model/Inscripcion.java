
package com.caminatascolombia.model;

public class Inscripcion {

    private Usuario usuario;
    private Caminata caminata;
    private EstadoInscripcion estado;

    public Inscripcion(Usuario usuario, Caminata caminata) {
        this.usuario = usuario;
        this.caminata = caminata;
        this.estado = new EstadoPendiente();
    }

    public Usuario getUsuario() { return usuario; }
    public Caminata getCaminata() { return caminata; }
    public EstadoInscripcion getEstado() { return estado; }

    public void avanzarEstado() {
        estado.avanzar(this);
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public boolean puedeComentar() {
        return estado.puedeComentar();
    }
}
