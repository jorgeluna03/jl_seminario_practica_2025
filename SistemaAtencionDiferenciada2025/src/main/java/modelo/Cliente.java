package modelo;


import java.time.LocalDate;

/**
 * Clase que representa un Cliente en el sistema de Atención Diferenciada
 */
public class Cliente {
    
    // Atributos
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String dni;
    private LocalDate fechaIngreso;

    // Constructor
    public Cliente(String nombre, String apellido, LocalDate fechaNacimiento, String dni, LocalDate fechaIngreso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
    }

    public Cliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    // Método toString para imprimir información del Cliente
    @Override
 
	public String toString() {
		return "Cliente{" +
				"nombre=\"" + nombre + "\"" +
				", apellido=\"" + apellido + "\"" +
				", fechaNacimiento=" + fechaNacimiento +
				", dni=\"" + dni + "\"" +
				", fechaIngreso=" + fechaIngreso +
				'}';
	}

    public int getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setId(int idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
