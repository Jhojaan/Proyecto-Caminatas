
package com.caminatascolombia.model;

public interface Sujeto {
    void adjuntar(Observador obs);
    void desadjuntar(Observador obs);
    void notificar(String mensaje);
}
