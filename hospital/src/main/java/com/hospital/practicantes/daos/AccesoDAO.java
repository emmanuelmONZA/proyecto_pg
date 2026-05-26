package com.hospital.practicantes.daos;

import com.hospital.practicantes.entities.RegistroAcceso;
import com.hospital.practicantes.interfaces.CrudDAO;
import com.hospital.practicantes.persistence.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccesoDAO implements CrudDAO<RegistroAcceso> {

    private final Persistence persistence = new Persistence();

    @Override
    public void guardar(RegistroAcceso r) {
        String sql = """
                INSERT INTO registros_acceso
                    (nombre_practicante, fecha, hora, area, tipo)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = persistence.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getPracticante());
            ps.setDate(2, Date.valueOf(r.getFecha()));
            ps.setTime(3, Time.valueOf(r.getHora() + ":00"));
            ps.setString(4, r.getServicio());
            ps.setString(5, r.getTipo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar acceso: " + e.getMessage(), e);
        }
    }

    @Override
    public List<RegistroAcceso> listar() {
        List<RegistroAcceso> lista = new ArrayList<>();
        String sql = "SELECT * FROM registros_acceso ORDER BY fecha DESC, hora DESC";
        try (Connection conn = persistence.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                RegistroAcceso r = new RegistroAcceso(
                        rs.getInt("id"),
                        rs.getString("nombre_practicante"),
                        rs.getDate("fecha").toString(),
                        rs.getTime("hora").toString().substring(0, 5),
                        rs.getString("area"),
                        rs.getString("tipo")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar accesos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM registros_acceso WHERE id = ?";
        try (Connection conn = persistence.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar acceso: " + e.getMessage(), e);
        }
    }

    public void actualizar(RegistroAcceso r) {
        String sql = """
                UPDATE registros_acceso
                SET nombre_practicante = ?, fecha = ?, hora = ?, area = ?, tipo = ?
                WHERE id = ?
                """;
        try (Connection conn = persistence.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getPracticante());
            ps.setDate(2, Date.valueOf(r.getFecha()));
            ps.setTime(3, Time.valueOf(r.getHora() + ":00"));
            ps.setString(4, r.getServicio());
            ps.setString(5, r.getTipo());
            ps.setInt(6, r.getId());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar acceso: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar acceso: " + e.getMessage(), e);
        }
    }
}