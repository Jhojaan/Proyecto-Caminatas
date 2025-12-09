
package com.caminatascolombia.controller;

import com.caminatascolombia.model.*;
import com.caminatascolombia.view.Vista;

public class InscripcionController {

    private Vista vista;
    private FachadaInscripcion fachada = new FachadaInscripcion();

    public InscripcionController(Vista vista) {
        this.vista = vista;
    }

    public void inscribirUsuarioEnCaminata(Usuario usuario, Caminata caminata,
                                           boolean seguroAccidentes, boolean seguroEquipo) {
        StringBuilder log = new StringBuilder();
        Inscripcion ins = fachada.inscribir(usuario, caminata, seguroAccidentes, seguroEquipo, log);
        for (String linea : log.toString().split("\\n")) {
            if (!linea.isEmpty()) {
                vista.mostrarMensaje(linea);
            }
        }
        if (ins == null) {
            vista.mostrarMensaje("No se pudo completar la inscripcion.");
        } else {
            vista.mostrarMensaje("Inscripcion creada. Estado: " + ins.getEstado().getNombre());
        }
    }
}
