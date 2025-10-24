package modelo;

import java.time.LocalDate;

/**
 * Clase que representa el registro de una Atención en el sistema
 */
public class RegistrarAtencion {
    
    // Atributos
    private int idAtencion;
    private LocalDate fecha;
    private Cliente cliente;
    private Canal canal;
    private ModeloAtencionDiferenciada segmento;
    private Colaborador legajo;
    private String observaciones;
    private Gestiones gestiones;

    // Constructor
    public RegistrarAtencion(int idAtencion, LocalDate fecha, Cliente cliente, Canal canal, ModeloAtencionDiferenciada segmento, Colaborador legajo, String observaciones, Gestiones gestiones) {
        this.idAtencion = idAtencion;
        this.fecha = fecha;
        this.cliente = cliente;
        this.canal = canal;
        this.segmento = segmento;
        this.legajo = legajo;
        this.observaciones = observaciones;
        this.gestiones = gestiones;
    }

    // Getters y Setters
    public int getIdAtencion() { return idAtencion; }
    public void setIdAtencion(int idAtencion) { this.idAtencion = idAtencion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Canal getCanal() { return canal; }
    public void setCanal(Canal canal) { this.canal = canal; }

    public ModeloAtencionDiferenciada getSegmento() { return segmento; }
    public void setSegmento(ModeloAtencionDiferenciada segmento) { this.segmento = segmento; }

    public Colaborador getLegajo() { return legajo; }
    public void setLegajo(Colaborador legajo) { this.legajo = legajo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Gestiones getGestiones() { return gestiones; }
    public void setGestiones(Gestiones gestiones) { this.gestiones = gestiones; }

    // Método toString para imprimir información del Registro de Atención
    @Override
    public String toString() {
        return "RegistrarAtencion{" +
                "idAtencion=" + idAtencion +
                ", fecha=" + fecha +
                ", cliente=" + cliente +
                ", canal=" + canal +
                ", segmento=" + segmento +
                ", legajo=" + legajo +
                ", observaciones=\"" + observaciones + "\"" +
                ", gestiones=" + gestiones +
                '}';
    }
}
