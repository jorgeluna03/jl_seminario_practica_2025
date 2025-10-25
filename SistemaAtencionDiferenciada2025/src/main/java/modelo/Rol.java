package modelo;


/**
 * Clase que representa un Rol en el sistema de Atención Diferenciada
 */
public class Rol {
    
    // Atributos
    private int idRol;
    private String descripcionRol;

    // Constructor
    public Rol(int idRol, String descripcionRol) {
        this.idRol = idRol;
        this.descripcionRol = descripcionRol;
    }

    // Constructor por defecto
    public Rol() {
        this.idRol = 0;
        this.descripcionRol = "";
    }

    // Getters y Setters
    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public String getDescripcionRol() { return descripcionRol; }
    public void setDescripcionRol(String descripcionRol) { this.descripcionRol = descripcionRol; }

    // Método toString para imprimir información del Rol
    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", descripcionRol=\"" + descripcionRol + "\"" +
                '}';
    }
}
