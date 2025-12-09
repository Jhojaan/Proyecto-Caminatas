
package com.caminatascolombia.model;

public class FachadaInscripcion {

    private PagoService pagoService = new PagoService();

    /**
     * Orquesta el proceso de inscripcion usando varios patrones:
     * - Decorator para seguros (se pueden combinar varios)
     * - State en Inscripcion
     * - Observer para notificar al usuario
     */
    public Inscripcion inscribir(Usuario usuario, Caminata caminata,
                                 boolean seguroAccidentes, boolean seguroEquipo,
                                 StringBuilder log) {
        if (usuario == null || caminata == null) {
            if (log != null) {
                log.append("Usuario o caminata no validos para la inscripcion.\n");
            }
            return null;
        }

        ISeguro seguro = new SeguroBasico();
        if (seguroAccidentes) {
            seguro = new SeguroAccidentes(seguro);
        }
        if (seguroEquipo) {
            seguro = new SeguroEquipo(seguro);
        }

        double costoBase = caminata.getCostoBase();
        double costoExtraSeguro = seguro.getCostoExtra(costoBase);
        double total = costoBase + costoExtraSeguro;

        if (log != null) {
            log.append("Descripcion del seguro: ").append(seguro.getDescripcion()).append("\n");
            log.append("Costo base caminata: ").append(costoBase).append("\n");
            log.append("Costo extra seguro: ").append(costoExtraSeguro).append("\n");
            log.append("Total a pagar: ").append(total).append("\n");
        }

        boolean pagoOk = pagoService.procesarPago(usuario, total, log);
        if (!pagoOk) {
            return null;
        }

        Inscripcion inscripcion = new Inscripcion(usuario, caminata);
        inscripcion.avanzarEstado(); // PENDIENTE -> CONFIRMADA

        // Registrar inscripcion en el singleton
        SistemaCaminatas.getInstancia().registrarInscripcion(inscripcion);

        // Observer: adaptamos el usuario como observador de la caminata
        UsuarioObservadorAdapter obs = new UsuarioObservadorAdapter(usuario);
        caminata.adjuntar(obs);
        caminata.notificar("Inscripcion confirmada en la caminata " + caminata.getNombre());

        if (log != null) {
            log.append("Estado actual de la inscripcion: ").append(inscripcion.getEstado().getNombre()).append("\n");
        }

        return inscripcion;
    }
}
