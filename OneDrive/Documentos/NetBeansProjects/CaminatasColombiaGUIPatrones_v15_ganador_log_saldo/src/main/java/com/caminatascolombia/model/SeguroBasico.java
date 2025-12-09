
package com.caminatascolombia.model;

public class SeguroBasico implements ISeguro {

    @Override
    public double getCostoExtra(double costoBase) {
        return 0; // incluido en el paquete
    }

    @Override
    public String getDescripcion() {
        return "Seguro basico incluido.";
    }
}
