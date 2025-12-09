
package com.caminatascolombia.controller;

import com.caminatascolombia.model.*;
import com.caminatascolombia.view.Vista;

public class UsuarioController {

    private Vista vista;
    private SistemaCaminatas sistema = SistemaCaminatas.getInstancia();

    public UsuarioController(Vista vista) {
        this.vista = vista;
    }

    public Usuario iniciarSesion(String email, String password) {
        for (Usuario u : sistema.getUsuarios()) {
            if (u.getEmail().equalsIgnoreCase(email) &&
                u.getPassword().equals(password)) {
                vista.mostrarMensaje("Inicio de sesion exitoso: " + u.getNombre());
                return u;
            }
        }
        vista.mostrarMensaje("Credenciales invalidas.");
        return null;
    }

    public Usuario registrarPersonaNatural(String nombre, String email,
                                           String documento, String password,
                                           double saldoInicial) {
        Usuario u = UsuarioFactory.crearPersonaNatural(nombre, email, password, saldoInicial, documento);
        sistema.registrarUsuario(u);
        vista.mostrarMensaje("Usuario registrado: " + u.toString());
        return u;
    }
}
