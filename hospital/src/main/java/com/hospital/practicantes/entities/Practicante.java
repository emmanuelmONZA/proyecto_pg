package com.hospital.practicantes.entities;

/**
 * Representa a un estudiante en practica en el hospital.
 * Campos requeridos por el hospital segun entrevista:
 * nombre, documento, universidad, semestre, programa,
 * docente responsable, estado de induccion y estado de ARL.
 */
public class Practicante {
    private int id;
    private String nombre;
    private String documento;
    private String universidad;
    private String semestre;
    private String programa;
    private String docente;
    private boolean inducionRealizada;
    private boolean arlVigente;
    private String estado; // Activo | Pendiente docs | En practica

    public Practicante() {
    }

    public Practicante(int id, String nombre, String documento,
                       String universidad, String semestre, String programa,
                       String docente, boolean inducionRealizada,
                       boolean arlVigente, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.universidad = universidad;
        this.semestre = semestre;
        this.programa = programa;
        this.docente = docente;
        this.inducionRealizada = inducionRealizada;
        this.arlVigente = arlVigente;
        this.estado = estado;
    }

    // ---- Getters y setters ----

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getUniversidad() { return universidad; }
    public void setUniversidad(String universidad) { this.universidad = universidad; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public String getDocente() { return docente; }
    public void setDocente(String docente) { this.docente = docente; }

    public boolean isInducionRealizada() { return inducionRealizada; }
    public void setInducionRealizada(boolean inducionRealizada) { this.inducionRealizada = inducionRealizada; }

    public boolean isArlVigente() { return arlVigente; }
    public void setArlVigente(boolean arlVigente) { this.arlVigente = arlVigente; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() { return nombre; }
}
