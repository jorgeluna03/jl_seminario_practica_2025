package modelo;

/**
 * Clase que representa un Modelo en el sistema de Atención Diferenciada
 */
public class Modelo {
    
    // Atributos
    private int idModelo;
    private String periodo;
    private String rangoFrecuencia;
    private String rangoRecencia;
    private String rangoMonto;
    private int cantSegmentos;
    private String rangoSegmentos;

    // Constructor
    public Modelo(int idModelo, String periodo, String rangoFrecuencia, String rangoRecencia, String rangoMonto, int cantSegmentos, String rangoSegmentos) {
        this.idModelo = idModelo;
        this.periodo = periodo;
        this.rangoFrecuencia = rangoFrecuencia;
        this.rangoRecencia = rangoRecencia;
        this.rangoMonto = rangoMonto;
        this.cantSegmentos = cantSegmentos;
        this.rangoSegmentos = rangoSegmentos;
    }

    // Constructor por defecto
    public Modelo() {
        this.idModelo = 0;
        this.periodo = "";
        this.rangoFrecuencia = "";
        this.rangoRecencia = "";
        this.rangoMonto = "";
        this.cantSegmentos = 0;
        this.rangoSegmentos = "";
    }

    // Getters y Setters
    public int getIdModelo() { return idModelo; }
    public void setIdModelo(int idModelo) { this.idModelo = idModelo; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getRangoFrecuencia() { return rangoFrecuencia; }
    public void setRangoFrecuencia(String rangoFrecuencia) { this.rangoFrecuencia = rangoFrecuencia; }

    public String getRangoRecencia() { return rangoRecencia; }
    public void setRangoRecencia(String rangoRecencia) { this.rangoRecencia = rangoRecencia; }

    public String getRangoMonto() { return rangoMonto; }
    public void setRangoMonto(String rangoMonto) { this.rangoMonto = rangoMonto; }

    public int getCantSegmentos() { return cantSegmentos; }
    public void setCantSegmentos(int cantSegmentos) { this.cantSegmentos = cantSegmentos; }

    public String getRangoSegmentos() { return rangoSegmentos; }
    public void setRangoSegmentos(String rangoSegmentos) { this.rangoSegmentos = rangoSegmentos; }

    // Método toString para imprimir información del Modelo
    @Override
    public String toString() {
        return "Modelo{" +
                "idModelo=" + idModelo +
                ", periodo=\"" + periodo + "\"" +
                ", rangoFrecuencia=\"" + rangoFrecuencia + "\"" +
                ", rangoRecencia=\"" + rangoRecencia + "\"" +
                ", rangoMonto=\"" + rangoMonto + "\"" +
                ", cantSegmentos=" + cantSegmentos +
                ", rangoSegmentos=\"" + rangoSegmentos + "\"" +
                '}';
    }
}
