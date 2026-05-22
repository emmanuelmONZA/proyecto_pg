package com.hospital.practicantes.daos;

import com.hospital.practicantes.entities.RegistroAcceso;
import com.hospital.practicantes.interfaces.CrudDAO;

import java.util.ArrayList;
import java.util.List;

public class AccesoDAO implements CrudDAO<RegistroAcceso> {

    private final ArrayList<RegistroAcceso> registros = new ArrayList<>();

    public AccesoDAO() {
        registros.add(new RegistroAcceso(1, "Ana Torres",
                "2026-05-17", "07:05", "Urgencias", "Entrada"));
        registros.add(new RegistroAcceso(2, "Mateo Gomez",
                "2026-05-17", "07:08", "Urgencias", "Entrada"));
        registros.add(new RegistroAcceso(3, "Ana Torres",
                "2026-05-17", "09:12", "Urgencias", "Salida"));
    }

    @Override
    public void guardar(RegistroAcceso registro) {
        registros.add(registro);
    }

    @Override
    public List<RegistroAcceso> listar() {
        return registros;
    }
}
