package modelo;


public class Canal {
    
    // Atributos
    private int idCanal;
    private String nombreCanal;

    // Constructor
    public Canal(int idCanal, String nombreCanal) {
        this.idCanal = idCanal;
        this.nombreCanal = nombreCanal;
    }

    // Constructor por defecto
    public Canal() {
        this.idCanal = 0;
        this.nombreCanal = "";
    }

    // Getters y Setters
    public int getIdCanal() { return idCanal; }
    public void setIdCanal(int idCanal) { this.idCanal = idCanal; }

    public String getNombreCanal() { return nombreCanal; }
    public void setNombreCanal(String nombreCanal) { this.nombreCanal = nombreCanal; }

    // Método toString para imprimir información del Canal
    @Override
    public String toString() {
        return "Canal{" +
                "idCanal=" + idCanal +
                ", nombreCanal=\"" + nombreCanal + "\"" +
                '}';
    }
}
