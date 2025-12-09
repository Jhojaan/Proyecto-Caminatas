
package com.caminatascolombia.model;

public class PersonaJuridica extends Usuario {

    private String nit;
    private String representante;
    private int numeroParticipantes;

    public PersonaJuridica(String nombre, String email, String password,
                           double saldo, String nit, String representante,
                           int numeroParticipantes) {
        super(nombre, email, password, saldo, "JURIDICA");
        this.nit = nit;
        this.representante = representante;
        this.numeroParticipantes = numeroParticipantes;
    }

    public String getNit() { return nit; }
    public String getRepresentante() { return representante; }
    public int getNumeroParticipantes() { return numeroParticipantes; }
}
