
package com.caminatascolombia.controller;

import com.caminatascolombia.model.*;
import com.caminatascolombia.view.Vista;

import java.util.List;
import java.util.Random;

public class CaminataController {

    private Vista vista;
    private SistemaCaminatas sistema = SistemaCaminatas.getInstancia();
    private BuscadorCaminatas buscador = new BuscadorCaminatas();
    private InvocadorComandos invocador = new InvocadorComandos();
    private Random random = new Random();

    public CaminataController(Vista vista) {
        this.vista = vista;
    }

    public void listarTodas() {
        vista.mostrarCaminatas(sistema.getCaminatas());
    }

    public void buscarPorDificultad(Dificultad dificultad) {
        buscador.setEstrategia(new BusquedaPorDificultad(dificultad));
        List<Caminata> filtradas = buscador.ejecutar(sistema.getCaminatas());
        vista.mostrarCaminatas(filtradas);
    }

    public void buscarPorTexto(String texto) {
        buscador.setEstrategia(new BusquedaPorTexto(texto));
        List<Caminata> filtradas = buscador.ejecutar(sistema.getCaminatas());
        vista.mostrarCaminatas(filtradas);
    }

    public void crearCaminataAleatoria() {
        CaminataCreator creator;
        if (random.nextBoolean()) {
            creator = new RecreativaCreator();
        } else {
            creator = new CompetenciaCreator();
        }
        invocador.ejecutarComando(new ComandoCrearCaminata(creator));
        List<Caminata> lista = sistema.getCaminatas();
        Caminata nueva = lista.get(lista.size() - 1);
        vista.mostrarMensaje("Se creo una caminata mediante Factory+Builder+Command:");
        vista.mostrarMensaje("  " + nueva.toString());
    }


    public Caminata crearCaminataManual(String nombre,
                                        String lugar,
                                        java.util.Date fecha,
                                        TipoCaminata tipo,
                                        Dificultad dificultad,
                                        double costoBase,
                                        boolean conPatrocinador,
                                        String patrocinador,
                                        double premio) {
        ICaminataBuilder builder = new CaminataBuilder();
        CaminataDirector director = new CaminataDirector(builder);
        builder.reset();
        builder.setDatosBasicos(nombre, lugar, fecha, tipo, dificultad, costoBase);
        builder.setDescripcion(
                "Descripcion configurada manualmente en la interfaz.",
                "Recomendaciones configuradas manualmente en la interfaz.");
        builder.setItinerarioDemo();
        Caminata c = builder.getResultado();
        c.setConPatrocinador(conPatrocinador);
        if (conPatrocinador) {
            c.setNombrePatrocinador(patrocinador);
            c.setPremioDinero(premio);
        }
        sistema.registrarCaminata(c);
        vista.mostrarMensaje("Se creo una caminata manual:");
        vista.mostrarMensaje("  " + c.toString());
        return c;
    }

    public void generarReporte() {
        ReporteCaminatas reporte = new ReporteCaminatas(sistema.getCaminatas());
        String texto = reporte.generarReporte();
        for (String linea : texto.split("\\n")) {
            vista.mostrarMensaje(linea);
        }
    }
}
