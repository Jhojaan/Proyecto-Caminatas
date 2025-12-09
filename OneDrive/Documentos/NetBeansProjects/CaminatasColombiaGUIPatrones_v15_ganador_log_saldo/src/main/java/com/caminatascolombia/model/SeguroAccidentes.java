
package com.caminatascolombia.model;

public class SeguroAccidentes extends SeguroDecorator {

    public SeguroAccidentes(ISeguro envoltura) {
        super(envoltura);
    }

    @Override
    public double getCostoExtra(double costoBase) {
        // 10% extra
        return super.getCostoExtra(costoBase) + costoBase * 0.10;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Cobertura por accidentes personales.";
    }
}
