
package com.caminatascolombia.model;

public class PersonaNatural extends Usuario {

    private String documento;

    public PersonaNatural(String nombre, String email, String password,
                          double saldo, String documento) {
        super(nombre, email, password, saldo, "NATURAL");
        this.documento = documento;
    }

    public String getDocumento() { return documento; }
}
