package modelo;

import java.time.LocalDate;

/**
 * Clase que representa un Colaborador en el sistema de Atención Diferenciada
 */
public class Colaborador {
    
    // Atributos
    private int legajo;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;
    private Rol rol;

    // Constructor
    public Colaborador(int legajo, String nombre, String apellido, LocalDate fechaNacimiento, LocalDate fechaIngreso, Rol rol) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
        this.rol = rol;
    }

    // Getters y Setters
    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    // Método toString para imprimir información del Colaborador
    @Override
    public String toString() {
        return "Colaborador{" +
                "legajo=" + legajo +
                ", nombre=\"" + nombre + "\"" +
                ", apellido=\"" + apellido + "\"" +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaIngreso=" + fechaIngreso +
                ", rol=" + rol +
                '}';
    }
}
