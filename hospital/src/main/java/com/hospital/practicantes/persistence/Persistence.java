package com.hospital.practicantes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexion JDBC a PostgreSQL
 */
public class Persistence {

    private static final String URL      = "jdbc:postgresql://localhost:5432/hospital_practicantes";
    private static final String USUARIO  = "postgres";
    private static final String PASSWORD = "POSGRESS";
    // ──────────────────────────────────────────────────────────────────

    /**
     * Retorna una conexion activa a la base de datos
     */
    public Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("[DB] Conexion exitosa a PostgreSQL");
            return conn;
        } catch (SQLException e) {
            System.err.println("[DB] Error al conectar: " + e.getMessage());
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
    }
}
