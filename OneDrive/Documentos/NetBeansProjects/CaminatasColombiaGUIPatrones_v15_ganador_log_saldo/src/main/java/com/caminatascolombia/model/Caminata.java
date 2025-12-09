
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Caminata implements Sujeto {

    private String nombre;
    private String lugar;
    private Date fecha;
    private TipoCaminata tipo;
    private Dificultad dificultad;
    private double costoBase;
    private String descripcion;
    private String recomendaciones;
    private String fotoPrincipal; // espacio para que el usuario configure la ruta de la foto
    private ComponenteItinerario itinerario;

    // Datos extra para competencias con patrocinador
    private boolean conPatrocinador;
    private String nombrePatrocinador;
    private double premioDinero;
    private Usuario ganadorCompetencia;

    private List<Observador> observadores = new ArrayList<>();

    public Caminata(String nombre, String lugar, Date fecha,
                    TipoCaminata tipo, Dificultad dificultad, double costoBase) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.costoBase = costoBase;
        this.descripcion = "";
        this.recomendaciones = "";
        this.fotoPrincipal = "FOTO_PENDIENTE"; // placeholder
        this.conPatrocinador = false;
        this.nombrePatrocinador = "";
        this.premioDinero = 0.0;
        this.ganadorCompetencia = null;
    }

    public String getNombre() { return nombre; }
    public String getLugar() { return lugar; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public TipoCaminata getTipo() { return tipo; }
    public Dificultad getDificultad() { return dificultad; }
    public double getCostoBase() { return costoBase; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }

    public String getFotoPrincipal() { return fotoPrincipal; }
    public void setFotoPrincipal(String fotoPrincipal) { this.fotoPrincipal = fotoPrincipal; }

    public ComponenteItinerario getItinerario() { return itinerario; }
    public void setItinerario(ComponenteItinerario itinerario) { this.itinerario = itinerario; }

        public boolean isConPatrocinador() { return conPatrocinador; }
    public void setConPatrocinador(boolean conPatrocinador) { this.conPatrocinador = conPatrocinador; }

    public String getNombrePatrocinador() { return nombrePatrocinador; }
    public void setNombrePatrocinador(String nombrePatrocinador) { this.nombrePatrocinador = nombrePatrocinador; }

    public double getPremioDinero() { return premioDinero; }
    public void setPremioDinero(double premioDinero) { this.premioDinero = premioDinero; }

    public Usuario getGanadorCompetencia() { return ganadorCompetencia; }
    public void setGanadorCompetencia(Usuario ganadorCompetencia) { this.ganadorCompetencia = ganadorCompetencia; }

@Override
    public String toString() {
        return nombre + " (" + tipo + ", " + dificultad + ") - " + lugar + " - $" + costoBase;
    }

    // Observer
    @Override
    public void adjuntar(Observador obs) {
        observadores.add(obs);
    }

    @Override
    public void desadjuntar(Observador obs) {
        observadores.remove(obs);
    }

    @Override
    public void notificar(String mensaje) {
        for (Observador o : observadores) {
            o.actualizar(mensaje);
        }
    }
}
