package com.hospital.practicantes.interfaces;

import java.util.List;

/**
 * Contrato CRUD minimo para los DAO del sistema.
 *
 * @param <T> tipo de entidad que maneja el DAO.
 */
public interface CrudDAO<T> {
    void guardar(T objeto);
    List<T> listar();
    void eliminar(int id);
}
