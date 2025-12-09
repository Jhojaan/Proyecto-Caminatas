
package com.caminatascolombia.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Singleton que centraliza usuarios, caminatas, inscripciones,
 * resenas de blog y la fecha actual del sistema.
 */
public class SistemaCaminatas {

    private static SistemaCaminatas instancia;

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Caminata> caminatas = new ArrayList<>();
    private List<Inscripcion> inscripciones = new ArrayList<>();
    private List<ResenaBlog> resenas = new ArrayList<>();

    /** Fecha "actual" del sistema, que se puede adelantar manualmente. */
    private Date fechaActual;

    private SistemaCaminatas() {
        // La fecha actual arranca en el dia de hoy,
        // y las caminatas se crean a partir de esta referencia.
        this.fechaActual = new Date();
        cargarDatosIniciales();
    }

    public static synchronized SistemaCaminatas getInstancia() {
        if (instancia == null) {
            instancia = new SistemaCaminatas();
        }
        return instancia;
    }

    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Caminata> getCaminatas() { return caminatas; }
    public List<Inscripcion> getInscripciones() { return inscripciones; }

    public Date getFechaActual() { return fechaActual; }

    public void registrarUsuario(Usuario u) { usuarios.add(u); }
    public void registrarCaminata(Caminata c) { caminatas.add(c); }

    public void registrarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
        actualizarEstadosInscripciones();
    }

    public void agregarResena(ResenaBlog resena) {
        resenas.add(resena);
    }

    public List<ResenaBlog> getResenas() {
        return new ArrayList<>(resenas);
    }

    /**
     * Permite adelantar el reloj logico del sistema.
     * Si pasa la fecha de alguna caminata, sus inscripciones
     * confirmadas cambian a estado FINALIZADA.
     */
    public void adelantarDias(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaActual);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        fechaActual = cal.getTime();
        actualizarEstadosInscripciones();
    }

    private void actualizarEstadosInscripciones() {
        for (Inscripcion ins : inscripciones) {
            Caminata c = ins.getCaminata();
            if (c.getFecha().before(fechaActual) &&
                !(ins.getEstado() instanceof EstadoFinalizada)) {
                if (ins.getEstado() instanceof EstadoConfirmada) {
                    ins.avanzarEstado();
                }
            }
        }
        asignarGanadoresCompetenciasPatrocinadas();
    }

    /**
     * Selecciona al azar un ganador entre las inscripciones finalizadas
     * de cada caminata de competencia con patrocinador y asigna el premio
     * a su saldo. La asignacion se hace una unica vez por caminata.
     */
    private void asignarGanadoresCompetenciasPatrocinadas() {
        java.util.Random random = new java.util.Random();
        for (Caminata c : caminatas) {
            if (c.getTipo() == TipoCaminata.DEPORTIVA_COMPETENCIA &&
                c.isConPatrocinador() &&
                c.getGanadorCompetencia() == null &&
                c.getFecha().before(fechaActual)) {

                List<Inscripcion> finalizadas = new ArrayList<>();
                for (Inscripcion ins : inscripciones) {
                    if (ins.getCaminata() == c &&
                        ins.getEstado() instanceof EstadoFinalizada) {
                        finalizadas.add(ins);
                    }
                }

                if (!finalizadas.isEmpty()) {
                    int idx = random.nextInt(finalizadas.size());
                    Inscripcion insGanadora = finalizadas.get(idx);
                    Usuario ganador = insGanadora.getUsuario();
                    c.setGanadorCompetencia(ganador);
                    ganador.setSaldo(ganador.getSaldo() + c.getPremioDinero());
                }
            }
        }
    }

    /**
     * Caminatas aun programables (fecha >= fechaActual).
     */
    public List<Caminata> getCaminatasProgramables() {
        List<Caminata> resultado = new ArrayList<>();
        for (Caminata c : caminatas) {
            if (!c.getFecha().before(fechaActual)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     * Caminatas que el usuario ya realizo (inscripcion FINALIZADA).
     */
    public List<Caminata> getCaminatasRealizadasPorUsuario(Usuario usuario) {
        List<Caminata> resultado = new ArrayList<>();
        for (Inscripcion ins : inscripciones) {
            if (ins.getUsuario().equals(usuario) &&
                ins.getEstado() instanceof EstadoFinalizada) {
                Caminata c = ins.getCaminata();
                if (!resultado.contains(c)) {
                    resultado.add(c);
                }
            }
        }
        return resultado;
    }

    /**
     * Verifica si el usuario tiene una inscripcion finalizada
     * para la caminata dada, lo que le permite publicar resena.
     */
    public boolean usuarioPuedeResenar(Usuario usuario, Caminata caminata) {
        for (Inscripcion ins : inscripciones) {
            if (ins.getUsuario().equals(usuario) &&
                ins.getCaminata().equals(caminata) &&
                ins.getEstado() instanceof EstadoFinalizada) {
                return true;
            }
        }
        return false;
    }

    private Date fechaRelativa(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaActual);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    private void cargarDatosIniciales() {
        // 5 personas naturales
        registrarUsuario(UsuarioFactory.crearPersonaNatural(
                "Ana", "ana@demo.com", "1234", 200000, "1111"));
        registrarUsuario(UsuarioFactory.crearPersonaNatural(
                "Bruno", "bruno@demo.com", "1234", 150000, "2222"));
        registrarUsuario(UsuarioFactory.crearPersonaNatural(
                "Carla", "carla@demo.com", "1234", 300000, "3333"));
        registrarUsuario(UsuarioFactory.crearPersonaNatural(
                "Diego", "diego@demo.com", "1234", 50000, "4444"));
        registrarUsuario(UsuarioFactory.crearPersonaNatural(
                "Elena", "elena@demo.com", "1234", 120000, "5555"));

        // 5 personas juridicas (empresas)
        registrarUsuario(UsuarioFactory.crearPersonaJuridica(
                "Empresa Andina", "contacto@andina.com", "emp1", 500000,
                "900111222", "Carlos Perez", 20));
        registrarUsuario(UsuarioFactory.crearPersonaJuridica(
                "EcoTours SAS", "info@ecotours.com", "emp2", 350000,
                "900333444", "Maria Lopez", 15));
        registrarUsuario(UsuarioFactory.crearPersonaJuridica(
                "Aventuras del Norte", "contacto@norte.com", "emp3", 420000,
                "900555666", "Juan Rodriguez", 30));
        registrarUsuario(UsuarioFactory.crearPersonaJuridica(
                "Senderos Urbanos", "info@senderosur.com", "emp4", 280000,
                "900777888", "Laura Gomez", 10));
        registrarUsuario(UsuarioFactory.crearPersonaJuridica(
                "Montana Viva", "contacto@montanaviva.com", "emp5", 600000,
                "900999000", "Andres Garcia", 25));

        // 5 caminatas por defecto usando Builder/Director directo
        CaminataBuilder builder = new CaminataBuilder();
        CaminataDirector director = new CaminataDirector(builder);

        Caminata c1 = director.construirCaminataEntrenamiento(
                "Nevado del Ruiz",
                "Parque Nacional Natural Los Nevados",
                10,
                Dificultad.ALTA,
                180000);
        c1.setFotoPrincipal("img/nevado.png");
        c1.setFecha(fechaRelativa(10));
        registrarCaminata(c1);

        Caminata c2 = director.construirCaminataRecreativa(
                "Cascadas de Choachi",
                "Choachi, Cundinamarca",
                5,
                Dificultad.MEDIA,
                90000);
        c2.setFotoPrincipal("img/choachi.png");
        c2.setFecha(fechaRelativa(5));
        registrarCaminata(c2);

        Caminata c3 = director.construirCaminataRecreativa(
                "Sendero Chicaque",
                "Parque Natural Chicaque",
                3,
                Dificultad.MEDIA,
                110000);
        c3.setFotoPrincipal("img/chicaque.png");
        c3.setFecha(fechaRelativa(3));
        registrarCaminata(c3);

        Caminata c4 = director.construirCaminataEntrenamiento(
                "Paramo de Sumapaz",
                "Sumapaz, Bogota",
                15,
                Dificultad.ALTA,
                200000);
        c4.setFotoPrincipal("img/sumapaz.png");
        c4.setFecha(fechaRelativa(15));
        registrarCaminata(c4);

        Caminata c5 = director.construirCaminataRecreativa(
                "Laguna de Guatavita",
                "Guatavita, Cundinamarca",
                7,
                Dificultad.BAJA,
                80000);
        c5.setFotoPrincipal("img/guatavita.png");
        c5.setFecha(fechaRelativa(7));
        registrarCaminata(c5);
    }
}
