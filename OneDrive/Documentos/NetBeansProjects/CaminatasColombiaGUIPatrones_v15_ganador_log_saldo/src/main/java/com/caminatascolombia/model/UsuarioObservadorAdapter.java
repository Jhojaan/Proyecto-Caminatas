
package com.caminatascolombia.model;

public class UsuarioObservadorAdapter implements Observador {

    private Usuario usuario;

    public UsuarioObservadorAdapter(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void actualizar(String mensaje) {
        // Por ahora solo mostramos por consola,
        // la vista grafica vera las notificaciones por otros canales si se desea.
        System.out.println("Notificacion para " + usuario.getEmail() + ": " + mensaje);
    }
}
