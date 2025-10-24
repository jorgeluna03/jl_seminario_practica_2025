package modelo;

/**
 * Clase que representa una Gestión en el sistema de Atención Diferenciada
 */
public class Gestiones {
    
    // Atributos
    private int idGestion;
    private String nombreGestion;
    private int prioridad;
    private int orden;
    private boolean esDiferencial;

    // Constructor
    public Gestiones(int idGestion, String nombreGestion, int prioridad, int orden, boolean esDiferencial) {
        this.idGestion = idGestion;
        this.nombreGestion = nombreGestion;
        this.prioridad = prioridad;
        this.orden = orden;
        this.esDiferencial = esDiferencial;
    }

    // Getters y Setters
    public int getIdGestion() { return idGestion; }
    public void setIdGestion(int idGestion) { this.idGestion = idGestion; }

    public String getNombreGestion() { return nombreGestion; }
    public void setNombreGestion(String nombreGestion) { this.nombreGestion = nombreGestion; }

    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    public boolean isEsDiferencial() { return esDiferencial; }
    public void setEsDiferencial(boolean esDiferencial) { this.esDiferencial = esDiferencial; }

    // Método toString para imprimir información de la Gestión
    @Override
    public String toString() {
        return "Gestiones{" +
                "idGestion=" + idGestion +
                ", nombreGestion=\"" + nombreGestion + "\"" +
                ", prioridad=" + prioridad +
                ", orden=" + orden +
                ", esDiferencial=" + esDiferencial +
                '}';
    }
}
