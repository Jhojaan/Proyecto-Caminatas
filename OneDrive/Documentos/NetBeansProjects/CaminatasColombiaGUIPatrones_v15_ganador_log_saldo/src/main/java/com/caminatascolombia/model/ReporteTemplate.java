
package com.caminatascolombia.model;

import java.util.List;

public abstract class ReporteTemplate {

    public final String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append(encabezado()).append("\n");
        sb.append(contenido()).append("\n");
        sb.append(pie()).append("\n");
        return sb.toString();
    }

    protected String encabezado() {
        return "===== Reporte Caminatas Colombia =====";
    }

    protected abstract String contenido();

    protected String pie() {
        return "===== Fin del reporte =====";
    }
}
