package modelo;

/**
 * Representa un Box de atención dentro del sistema.
 * Permite asociar (asignar) un colaborador a un box.
 */
public class BoxAtencion {
    
    private int idBox;
    private String nombre;
    private String estado; // LIBRE / OCUPADO (u otros estados si se requieren)
    private Colaborador colaboradorAsignado; // puede ser null si no hay colaborador asignado
    
    public BoxAtencion() {
        this.idBox = 0;
        this.nombre = "";
        this.estado = "LIBRE";
        this.colaboradorAsignado = null;
    }
    
    public BoxAtencion(int idBox, String nombre) {
        this.idBox = idBox;
        this.nombre = nombre;
        this.estado = "LIBRE";
        this.colaboradorAsignado = null;
    }
    
    public int getIdBox() { return idBox; }
    public void setIdBox(int idBox) { this.idBox = idBox; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Colaborador getColaboradorAsignado() { return colaboradorAsignado; }
    public void setColaboradorAsignado(Colaborador colaboradorAsignado) { this.colaboradorAsignado = colaboradorAsignado; }
    
    @Override
    public String toString() {
        return "BoxAtencion{" +
                "idBox=" + idBox +
                ", nombre=\"" + nombre + "\"" +
                ", estado=\"" + estado + "\"" +
                ", colaboradorAsignado=" + (colaboradorAsignado != null ? colaboradorAsignado.toString() : "null") +
                '}';
    }
}


