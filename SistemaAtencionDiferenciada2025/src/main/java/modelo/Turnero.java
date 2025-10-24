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

    // Constructor
    public Turnero(int idTurnero, LocalDate fecha, Cliente cliente, String codigoTurno) {
        this.idTurnero = idTurnero;
        this.fecha = fecha;
        this.cliente = cliente;
        this.codigoTurno = codigoTurno;
    }

    public Turnero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    // Método toString para imprimir información del Turnero
    @Override
    public String toString() {
        return "Turnero{" +
                "idTurnero=" + idTurnero +
                ", fecha=" + fecha +
                ", cliente=" + cliente +
                ", codigoTurno=\"" + codigoTurno + "\"" +
                '}';
    }
}
