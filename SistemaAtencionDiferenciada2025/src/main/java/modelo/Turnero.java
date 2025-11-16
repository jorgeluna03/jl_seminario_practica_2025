package modelo;


import java.time.LocalDate;

/**
 * Clase que representa un Turnero en el sistema de Atención Diferenciada
 */
public class Turnero {
    
    // Atributos
    private int idTurnero;
    private LocalDate fecha;
    private Cliente cliente;
    private String codigoTurno;
    private String estado; 
    private Integer idBox; 
    private Integer orden; // Orden de atención dentro del box o cola 
    private Integer idGestion; // FK a gestiones (tipo de gestión: Consulta general=2, Solicitud Prestamo=3, Reclamo=1, Otros=4)
    private Integer prioridad; // Prioridad de la gestión (1 = más prioritario, viene de gestiones.prioridad)
    private Integer segmentoScore; // Score del segmento del cliente (ALTO=1, MEDIO ALTO=2, MEDIO=3, MEDIO BAJO=4, BAJO=5)
    private Integer score; // Score combinado para ordenamiento (mayor = más prioritario)

    // Constructor
    public Turnero(int idTurnero, LocalDate fecha, Cliente cliente, String codigoTurno) {
        this.idTurnero = idTurnero;
        this.fecha = fecha;
        this.cliente = cliente;
        this.codigoTurno = codigoTurno;
        this.estado = "EN_ESPERA";
        this.idBox = null;
        this.orden = null;
        this.idGestion = null;
        this.prioridad = null;
        this.segmentoScore = null;
        this.score = null;
    }

    // Constructor por defecto
    public Turnero() {
        this.idTurnero = 0;
        this.fecha = LocalDate.now();
        this.cliente = new Cliente();
        this.codigoTurno = "";
        this.estado = "EN_ESPERA";
        this.idBox = null;
        this.orden = null;
        this.idGestion = null;
        this.prioridad = null;
        this.segmentoScore = null;
        this.score = null;
    }

    // Getters y Setters
    public int getIdTurnero() { return idTurnero; }
    public void setIdTurnero(int idTurnero) { this.idTurnero = idTurnero; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getCodigoTurno() { return codigoTurno; }
    public void setCodigoTurno(String codigoTurno) { this.codigoTurno = codigoTurno; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Integer getIdBox() { return idBox; }
    public void setIdBox(Integer idBox) { this.idBox = idBox; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    
    public Integer getIdGestion() { return idGestion; }
    public void setIdGestion(Integer idGestion) { this.idGestion = idGestion; }
    
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }
    
    public Integer getSegmentoScore() { return segmentoScore; }
    public void setSegmentoScore(Integer segmentoScore) { this.segmentoScore = segmentoScore; }
    
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    // Método toString para imprimir información del Turnero
    @Override
    public String toString() {
        return "Turnero{" +
                "idTurnero=" + idTurnero +
                ", fecha=" + fecha +
                ", cliente=" + cliente +
                ", codigoTurno=\"" + codigoTurno + "\"" +
                ", estado=\"" + estado + "\"" +
                ", idBox=" + idBox +
                ", orden=" + orden +
                ", idGestion=" + idGestion +
                ", prioridad=" + prioridad +
                ", segmentoScore=" + segmentoScore +
                ", score=" + score +
                '}';
    }
}
