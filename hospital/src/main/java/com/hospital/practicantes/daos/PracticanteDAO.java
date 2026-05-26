package com.hospital.practicantes.daos;

import com.hospital.practicantes.entities.Practicante;
import com.hospital.practicantes.interfaces.CrudDAO;
import com.hospital.practicantes.persistence.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PracticanteDAO implements CrudDAO<Practicante> {

    private final Persistence persistence = new Persistence();

    @Override
    public void guardar(Practicante p) {
        String sql = """
                INSERT INTO practicantes
                    (nombre, documento, universidad, semestre, programa,
                     docente, induccion_realizada, arl_vigente, estado)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = persistence.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDocumento());
            ps.setString(3, p.getUniversidad());
            ps.setString(4, p.getSemestre());
            ps.setString(5, p.getPrograma());
            ps.setString(6, p.getDocente());
            ps.setBoolean(7, p.isInducionRealizada());
            ps.setBoolean(8, p.isArlVigente());
            ps.setString(9, p.getEstado());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar practicante: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Practicante> listar() {
        List<Practicante> lista = new ArrayList<>();
        String sql = "SELECT * FROM practicantes ORDER BY nombre";

        try (Connection conn = persistence.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Practicante p = new Practicante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("documento"),
                        rs.getString("universidad"),
                        rs.getString("semestre"),
                        rs.getString("programa"),
                        rs.getString("docente"),
                        rs.getBoolean("induccion_realizada"),
                        rs.getBoolean("arl_vigente"),
                        rs.getString("estado")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar practicantes: " + e.getMessage(), e);
        }
        return lista;
    }

    public void eliminar(int id) {
    String sql = "DELETE FROM practicantes WHERE id = ?";
    try (Connection conn = persistence.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Error al eliminar practicante: " + e.getMessage(), e);
    }
}

public void actualizar(Practicante p) {
    String sql = """
            UPDATE practicantes
            SET nombre = ?, documento = ?, universidad = ?, semestre = ?,
                programa = ?, docente = ?, induccion_realizada = ?, arl_vigente = ?, estado = ?
            WHERE id = ?
            """;
    try (Connection conn = persistence.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getDocumento());
        ps.setString(3, p.getUniversidad());
        ps.setString(4, p.getSemestre());
        ps.setString(5, p.getPrograma());
        ps.setString(6, p.getDocente());
        ps.setBoolean(7, p.isInducionRealizada());
        ps.setBoolean(8, p.isArlVigente());
        ps.setString(9, p.getEstado());
        ps.setInt(10, p.getId());
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Error al actualizar practicante: " + e.getMessage(), e);
    }
}
}
