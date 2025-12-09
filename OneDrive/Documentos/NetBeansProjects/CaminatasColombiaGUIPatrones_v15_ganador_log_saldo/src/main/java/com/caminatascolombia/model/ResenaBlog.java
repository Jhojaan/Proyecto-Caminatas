
package com.caminatascolombia.model;

import java.util.Date;

/**
 * Representa una resena de blog sobre una caminata ya realizada.
 * Se usa en la seccion de Blog y se guarda dentro del Singleton SistemaCaminatas.
 */
public class ResenaBlog {

    private Usuario autor;
    private Caminata caminata;
    private String texto;
    private int calificacion; // 1 a 5
    private Date fecha;

    public ResenaBlog(Usuario autor, Caminata caminata, String texto, int calificacion) {
        this.autor = autor;
        this.caminata = caminata;
        this.texto = texto;
        this.calificacion = calificacion;
        this.fecha = new Date();
    }

    public Usuario getAutor() { return autor; }
    public Caminata getCaminata() { return caminata; }
    public String getTexto() { return texto; }
    public int getCalificacion() { return calificacion; }
    public Date getFecha() { return fecha; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(fecha).append("] ")
          .append(autor.getNombre())
          .append(" sobre ").append(caminata.getNombre())
          .append(" (calificacion ").append(calificacion).append("/5):\n")
          .append(texto);
        if (caminata.isConPatrocinador() && caminata.getGanadorCompetencia() != null) {
            sb.append("\nGanador competencia patrocinada: ")
              .append(caminata.getGanadorCompetencia().getNombre())
              .append(" (ID ").append(caminata.getGanadorCompetencia().getId()).append(")")
              .append(" premio $").append(caminata.getPremioDinero());
        }
        return sb.toString();
    }
}
