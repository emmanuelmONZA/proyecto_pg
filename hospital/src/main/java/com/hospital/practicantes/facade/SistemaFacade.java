package com.hospital.practicantes.facade;

import com.hospital.practicantes.daos.AccesoDAO;
import com.hospital.practicantes.daos.PracticanteDAO;
import com.hospital.practicantes.daos.RotacionDAO;
import com.hospital.practicantes.entities.Practicante;
import com.hospital.practicantes.entities.RegistroAcceso;
import com.hospital.practicantes.entities.Rotacion;

import java.util.List;

public class SistemaFacade {

    private static final SistemaFacade INSTANCIA = new SistemaFacade();

    private final PracticanteDAO practicanteDAO;
    private final RotacionDAO rotacionDAO;
    private final AccesoDAO accesoDAO;

    private SistemaFacade() {
        practicanteDAO = new PracticanteDAO();
        rotacionDAO = new RotacionDAO();
        accesoDAO = new AccesoDAO();
    }

    public static SistemaFacade getInstancia() {
        return INSTANCIA;
    }

    // Eliminar practicante
    public void eliminarPracticante(int id) {
        practicanteDAO.eliminar(id);
    }

    // Eliminar acceso
    public void eliminarAcceso(int id) {
        accesoDAO.eliminar(id);
    }

    // Practicantes

    public void registrarPracticante(String nombre, String documento,
            String universidad, String semestre,
            String programa, String docente,
            boolean induccion, boolean arl,
            String estado) {
        int id = practicanteDAO.listar().size() + 1;
        Practicante p = new Practicante(id, nombre, documento,
                universidad, semestre, programa, docente, induccion, arl, estado);
        practicanteDAO.guardar(p);
    }

    public List<Practicante> listarPracticantes() {
        return practicanteDAO.listar();
    }

    // Rotaciones

    public void registrarRotacion(String area, String fechaInicio, String fechaFin,
            String horaInicio, String horaFin,
            int cupoMaximo, String docente) {
        int id = rotacionDAO.listar().size() + 1;
        Rotacion r = new Rotacion(id, area, fechaInicio, fechaFin,
                horaInicio, horaFin, cupoMaximo, docente);
        rotacionDAO.guardar(r);
    }

    public List<Rotacion> listarRotaciones() {
        return rotacionDAO.listar();
    }

    // Accesos

    public void registrarAcceso(String practicante, String fecha,
            String hora, String servicio, String tipo) {
        int id = accesoDAO.listar().size() + 1;
        RegistroAcceso ra = new RegistroAcceso(id, practicante, fecha, hora, servicio, tipo);
        accesoDAO.guardar(ra);
    }

    public List<RegistroAcceso> listarAccesos() {
        return accesoDAO.listar();
    }

    public void actualizarPracticante(Practicante p) {
        practicanteDAO.actualizar(p);
    }

    public void actualizarRotacion(Rotacion r) {
        rotacionDAO.actualizar(r);
    }

    public void actualizarAcceso(RegistroAcceso r) {
        accesoDAO.actualizar(r);
    }
}
