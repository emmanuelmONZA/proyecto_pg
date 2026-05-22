package com.hospital.practicantes.daos;

import com.hospital.practicantes.entities.Rotacion;
import com.hospital.practicantes.interfaces.CrudDAO;
import com.hospital.practicantes.persistence.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RotacionDAO implements CrudDAO<Rotacion> {

    private final Persistence persistence = new Persistence();

    @Override
    public void guardar(Rotacion r) {
        String sql = """
                INSERT INTO rotaciones
                    (area, fecha_inicio, fecha_fin, hora_inicio, hora_fin, cupo_maximo, docente)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = persistence.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getArea());
            ps.setDate(2, Date.valueOf(r.getFechaInicio()));
            ps.setDate(3, Date.valueOf(r.getFechaFin()));
            ps.setTime(4, Time.valueOf(r.getHoraInicio() + ":00"));
            ps.setTime(5, Time.valueOf(r.getHoraFin() + ":00"));
            ps.setInt(6, r.getCupoMaximo());
            ps.setString(7, r.getDocente());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar rotacion: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Rotacion> listar() {
        List<Rotacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM rotaciones ORDER BY fecha_inicio, hora_inicio";

        try (Connection conn = persistence.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Rotacion r = new Rotacion(
                        rs.getInt("id"),
                        rs.getString("area"),
                        rs.getDate("fecha_inicio").toString(),
                        rs.getDate("fecha_fin").toString(),
                        rs.getTime("hora_inicio").toString().substring(0, 5),
                        rs.getTime("hora_fin").toString().substring(0, 5),
                        rs.getInt("cupo_maximo"),
                        rs.getString("docente")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar rotaciones: " + e.getMessage(), e);
        }
        return lista;
    }
}
