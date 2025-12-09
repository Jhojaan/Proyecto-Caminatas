
package com.caminatascolombia.model;

import java.util.List;

public class ReporteCaminatas extends ReporteTemplate {

    private List<Caminata> caminatas;

    public ReporteCaminatas(List<Caminata> caminatas) {
        this.caminatas = caminatas;
    }

    @Override
    protected String contenido() {
        StringBuilder sb = new StringBuilder();
        for (Caminata c : caminatas) {
            sb.append("- ").append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
