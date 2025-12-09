
package com.caminatascolombia.model;

public class PagoService {

    /**
     * Descuenta el valor de la caminata del saldo del usuario si es suficiente.
     * Retorna true si el pago fue exitoso.
     */
    public boolean procesarPago(Usuario usuario, double monto, StringBuilder log) {
        if (log != null) {
            log.append("Saldo actual del usuario: ").append(usuario.getSaldo()).append("\n");
            log.append("Valor a pagar: ").append(monto).append("\n");
        }

        if (usuario.getSaldo() < monto) {
            if (log != null) {
                log.append("Saldo insuficiente. No se puede realizar el pago.\n");
            }
            return false;
        }

        double nuevoSaldo = usuario.getSaldo() - monto;
        usuario.setSaldo(nuevoSaldo);

        if (log != null) {
            log.append("Pago exitoso. Nuevo saldo del usuario: ").append(nuevoSaldo).append("\n");
        }
        return true;
    }
}
