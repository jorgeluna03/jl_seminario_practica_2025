package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    // Nuevos atributos para compatibilidad
    private String tipoAtencion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String resultado;

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

    // Constructor por defecto
    public RegistrarAtencion() {
        this.idAtencion = 0;
        this.fecha = LocalDate.now();
        this.cliente = new Cliente();
        this.canal = new Canal();
        this.segmento = new ModeloAtencionDiferenciada();
        this.legajo = new Colaborador(0, "", "", LocalDate.now(), LocalDate.now(), new Rol());
        this.observaciones = "";
        this.gestiones = new Gestiones();
        this.tipoAtencion = "";
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = null;
        this.estado = "Iniciada";
        this.resultado = "";
    }
    
    // Nuevo constructor para compatibilidad con controladores
    public RegistrarAtencion(int idAtencion, Cliente cliente, String tipoAtencion, LocalDateTime fechaInicio, String estado) {
        this.idAtencion = idAtencion;
        this.cliente = cliente;
        this.tipoAtencion = tipoAtencion;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.fecha = fechaInicio.toLocalDate();
        this.canal = new Canal();
        this.segmento = new ModeloAtencionDiferenciada();
        this.legajo = new Colaborador(0, "", "", LocalDate.now(), LocalDate.now(), new Rol());
        this.observaciones = "";
        this.gestiones = new Gestiones();
        this.fechaFin = null;
        this.resultado = "";
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
    
    // Getters y Setters para nuevos atributos
    public String getTipoAtencion() { return tipoAtencion; }
    public void setTipoAtencion(String tipoAtencion) { this.tipoAtencion = tipoAtencion; }
    
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

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
