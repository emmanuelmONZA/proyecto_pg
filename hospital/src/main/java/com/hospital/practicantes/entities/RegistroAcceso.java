package com.hospital.practicantes.entities;

/**
 * Registro de entrada o salida de un practicante.
 * 
 */
public class RegistroAcceso {
    private int id;
    private String practicante;
    private String fecha;      // yyyy-MM-dd
    private String hora;       // HH:mm
    private String servicio;   // Urgencias, Pediatria
    private String tipo;       // Entrada | Salida

    public RegistroAcceso() {
    }

    public RegistroAcceso(int id, String practicante, String fecha,
                          String hora, String servicio, String tipo) {
        this.id = id;
        this.practicante = practicante;
        this.fecha = fecha;
        this.hora = hora;
        this.servicio = servicio;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPracticante() { return practicante; }
    public void setPracticante(String practicante) { this.practicante = practicante; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
