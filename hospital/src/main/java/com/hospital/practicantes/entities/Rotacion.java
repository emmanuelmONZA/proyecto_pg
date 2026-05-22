package com.hospital.practicantes.entities;

/**
 * Rotacion mensual de un grupo de practicantes en un area del hospital.
 */
public class Rotacion {
    private int id;
    private String area;           // Urgencias, Pediatria, Hospitalizacion
    private String fechaInicio;    // yyyy-MM-dd
    private String fechaFin;       // yyyy-MM-dd
    private String horaInicio;     // HH:mm 
    private String horaFin;        // HH:mm  
    private int cupoMaximo;        // capacidad instalada
    private String docente;        // nombre del docente acompañante

    public Rotacion() {
    }

    public Rotacion(int id, String area, String fechaInicio, String fechaFin,
                    String horaInicio, String horaFin, int cupoMaximo, String docente) {
        this.id = id;
        this.area = area;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cupoMaximo = cupoMaximo;
        this.docente = docente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public String getDocente() { return docente; }
    public void setDocente(String docente) { this.docente = docente; }
}
