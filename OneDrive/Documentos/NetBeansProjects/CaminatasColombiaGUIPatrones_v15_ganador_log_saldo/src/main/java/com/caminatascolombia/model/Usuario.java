
package com.caminatascolombia.model;

public abstract class Usuario {

    private static int nextId = 1;

    protected int id;
    protected String nombre;
    protected String email;
    protected String password;
    protected double saldo;
    protected String tipo; // NATURAL o JURIDICA

    public Usuario(String nombre, String email, String password, double saldo, String tipo) {
        this.id = nextId++;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return tipo + " #" + id + ": " + nombre + " <" + email + "> (Saldo=" + saldo + ")";
    }
}
