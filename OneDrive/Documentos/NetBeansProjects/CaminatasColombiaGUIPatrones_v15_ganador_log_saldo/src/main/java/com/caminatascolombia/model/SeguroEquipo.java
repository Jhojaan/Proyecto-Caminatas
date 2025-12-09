
package com.caminatascolombia.model;

public class SeguroEquipo extends SeguroDecorator {

    public SeguroEquipo(ISeguro envoltura) {
        super(envoltura);
    }

    @Override
    public double getCostoExtra(double costoBase) {
        // 5% extra
        return super.getCostoExtra(costoBase) + costoBase * 0.05;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Cobertura por perdida o daño de equipo.";
    }
}
