package modelo;


/**
 * Clase que representa un Modelo de Atención Diferenciada en el sistema
 */
public class ModeloAtencionDiferenciada {
    
    // Atributos
    private int frecuencia;
    private int recencia;
    private double monto;
    private Modelo idModelo;
    private Cliente cliente;
    private String segmento;

    // Constructor
    public ModeloAtencionDiferenciada(int frecuencia, int recencia, double monto, Modelo idModelo, Cliente cliente, String segmento) {
        this.frecuencia = frecuencia;
        this.recencia = recencia;
        this.monto = monto;
        this.idModelo = idModelo;
        this.cliente = cliente;
        this.segmento = segmento;
    }

    // Constructor por defecto
    public ModeloAtencionDiferenciada() {
        this.frecuencia = 0;
        this.recencia = 0;
        this.monto = 0.0;
        this.idModelo = new Modelo();
        this.cliente = new Cliente();
        this.segmento = "";
    }

    // Getters y Setters
    public int getFrecuencia() { return frecuencia; }
    public void setFrecuencia(int frecuencia) { this.frecuencia = frecuencia; }

    public int getRecencia() { return recencia; }
    public void setRecencia(int recencia) { this.recencia = recencia; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Modelo getIdModelo() { return idModelo; }
    public void setIdModelo(Modelo idModelo) { this.idModelo = idModelo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getSegmento() { return segmento; }
    public void setSegmento(String segmento) { this.segmento = segmento; }

    // Método toString para imprimir información del Modelo de Atención Diferenciada
    @Override
    public String toString() {
        return "ModeloAtencionDiferenciada{" +
                "frecuencia=" + frecuencia +
                ", recencia=" + recencia +
                ", monto=" + monto +
                ", idModelo=" + idModelo +
                ", cliente=" + cliente +
                ", segmento=\"" + segmento + "\"" +
                '}';
    }
}
