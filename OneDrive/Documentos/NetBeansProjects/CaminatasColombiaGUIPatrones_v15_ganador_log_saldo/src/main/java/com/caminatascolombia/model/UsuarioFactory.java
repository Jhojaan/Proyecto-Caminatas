
package com.caminatascolombia.model;

public class UsuarioFactory {

    public static PersonaNatural crearPersonaNatural(String nombre, String email,
                                                     String password, double saldo,
                                                     String documento) {
        return new PersonaNatural(nombre, email, password, saldo, documento);
    }

    public static PersonaJuridica crearPersonaJuridica(String nombre, String email,
                                                       String password, double saldo,
                                                       String nit, String representante,
                                                       int numeroParticipantes) {
        return new PersonaJuridica(nombre, email, password, saldo, nit, representante, numeroParticipantes);
    }
}
