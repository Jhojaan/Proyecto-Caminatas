
package com.caminatascolombia.controller;

import com.caminatascolombia.model.*;
import com.caminatascolombia.view.Vista;

import java.util.List;

/**
 * Controlador para la seccion de Blog.
 * Usa el Singleton SistemaCaminatas como repositorio
 * y el patron Command para publicar nuevas resenas.
 */
public class BlogController {

    private Vista vista;
    private SistemaCaminatas sistema = SistemaCaminatas.getInstancia();
    private InvocadorComandos invocador = new InvocadorComandos();

    public BlogController(Vista vista) {
        this.vista = vista;
    }

    public List<ResenaBlog> obtenerResenas() {
        return sistema.getResenas();
    }

    public List<Caminata> obtenerCaminatasRealizadas(Usuario usuario) {
        return sistema.getCaminatasRealizadasPorUsuario(usuario);
    }

    public void publicarResena(Usuario autor,
                               Caminata caminata,
                               int calificacion,
                               String texto) {
        if (autor == null) {
            vista.mostrarMensaje("Debe haber un usuario autenticado para publicar una resena.");
            return;
        }
        if (caminata == null) {
            vista.mostrarMensaje("Debe seleccionar una caminata realizada.");
            return;
        }
        if (!sistema.usuarioPuedeResenar(autor, caminata)) {
            vista.mostrarMensaje("Solo puede resenar caminatas que ya haya completado.");
            return;
        }
        if (texto == null || texto.trim().isEmpty()) {
            vista.mostrarMensaje("El texto de la resena no puede estar vacio.");
            return;
        }
        if (calificacion < 1 || calificacion > 5) {
            vista.mostrarMensaje("La calificacion debe estar entre 1 y 5.");
            return;
        }

        ResenaBlog resena = new ResenaBlog(autor, caminata, texto.trim(), calificacion);
        Comando comando = new ComandoCrearResena(sistema, resena);
        invocador.ejecutarComando(comando);
        vista.mostrarMensaje("Resena publicada para la caminata " + caminata.getNombre());
    }
}
