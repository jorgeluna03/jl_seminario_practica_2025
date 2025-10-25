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

    // Constructor por defecto
    public Cliente() {
        this.nombre = "";
        this.apellido = "";
        this.fechaNacimiento = LocalDate.now();
        this.dni = "";
        this.fechaIngreso = LocalDate.now();
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
    
    // Método de compatibilidad para getFechaRegistro
    public LocalDate getFechaRegistro() { return fechaIngreso; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaIngreso = fechaRegistro; }

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

    // Métodos adicionales para compatibilidad
    public int getId() {
        return 0; // Valor por defecto para el prototipo
    }

    public void setId(int idCliente) {
        // Método vacío para compatibilidad
    }

}
