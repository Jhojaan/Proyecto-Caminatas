
package com.caminatascolombia.model;

public abstract class SeguroDecorator implements ISeguro {

    protected ISeguro envoltura;

    public SeguroDecorator(ISeguro envoltura) {
        this.envoltura = envoltura;
    }

    @Override
    public double getCostoExtra(double costoBase) {
        return envoltura.getCostoExtra(costoBase);
    }

    @Override
    public String getDescripcion() {
        return envoltura.getDescripcion();
    }
}
