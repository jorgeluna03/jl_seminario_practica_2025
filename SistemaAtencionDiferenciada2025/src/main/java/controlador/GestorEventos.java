/*
 * Gestor de Eventos - Sistema de Atención Diferenciada 2025
 * Implementa el patrón Observer para la comunicación entre componentes
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Gestor de Eventos que implementa el patrón Observer
 * Permite la comunicación desacoplada entre componentes del sistema
 */
public class GestorEventos {
    
    // Lista de observadores (usando CopyOnWriteArrayList para thread-safety)
    private List<Observer> observers;
    
    // Lista de eventos pendientes para procesamiento asíncrono
    private List<String> eventosPendientes;
    
    // Flag para indicar si el gestor está activo
    private boolean activo;
    
    /**
     * Constructor del Gestor de Eventos
     */
    public GestorEventos() {
        this.observers = new CopyOnWriteArrayList<>();
        this.eventosPendientes = new ArrayList<>();
        this.activo = true;
    }
    
    /**
     * Agrega un observador al gestor de eventos
     * @param observer Observador a agregar
     */
    public void agregarObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer agregado: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Remueve un observador del gestor de eventos
     * @param observer Observador a remover
     */
    public void removerObserver(Observer observer) {
        if (observer != null) {
            observers.remove(observer);
            System.out.println("Observer removido: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Notifica un cambio a todos los observadores registrados
     * @param evento Evento a notificar
     */
    public void notificarCambio(String evento) {
        if (!activo) {
            return;
        }
        
        System.out.println("Notificando evento: " + evento);
        
        // Notificar a todos los observadores
        for (Observer observer : observers) {
            try {
                observer.actualizar(evento);
            } catch (Exception e) {
                System.err.println("Error al notificar al observer " + 
                    observer.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * Notifica un cambio con datos adicionales
     * @param evento Evento a notificar
     * @param datos Datos adicionales del evento
     */
    public void notificarCambio(String evento, Object datos) {
        if (!activo) {
            return;
        }
        
        System.out.println("Notificando evento con datos: " + evento);
        
        // Notificar a todos los observadores
        for (Observer observer : observers) {
            try {
                if (observer instanceof ObserverConDatos) {
                    ((ObserverConDatos) observer).actualizar(evento, datos);
                } else {
                    observer.actualizar(evento);
                }
            } catch (Exception e) {
                System.err.println("Error al notificar al observer " + 
                    observer.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * Agrega un evento a la cola de eventos pendientes
     * @param evento Evento a agregar
     */
    public void agregarEventoPendiente(String evento) {
        if (activo) {
            eventosPendientes.add(evento);
            System.out.println("Evento agregado a la cola: " + evento);
        }
    }
    
    /**
     * Procesa todos los eventos pendientes
     */
    public void procesarEventosPendientes() {
        if (!activo || eventosPendientes.isEmpty()) {
            return;
        }
        
        System.out.println("Procesando " + eventosPendientes.size() + " eventos pendientes");
        
        // Crear una copia de la lista para evitar modificaciones concurrentes
        List<String> eventosACopiar = new ArrayList<>(eventosPendientes);
        eventosPendientes.clear();
        
        // Procesar cada evento
        for (String evento : eventosACopiar) {
            notificarCambio(evento);
        }
    }
    
    /**
     * Obtiene la lista de observadores registrados
     * @return Lista de observadores
     */
    public List<Observer> getObservers() {
        return new ArrayList<>(observers);
    }
    
    /**
     * Obtiene el número de observadores registrados
     * @return Número de observadores
     */
    public int getCantidadObservers() {
        return observers.size();
    }
    
    /**
     * Obtiene la lista de eventos pendientes
     * @return Lista de eventos pendientes
     */
    public List<String> getEventosPendientes() {
        return new ArrayList<>(eventosPendientes);
    }
    
    /**
     * Obtiene el número de eventos pendientes
     * @return Número de eventos pendientes
     */
    public int getCantidadEventosPendientes() {
        return eventosPendientes.size();
    }
    
    /**
     * Verifica si el gestor está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     * Activa o desactiva el gestor de eventos
     * @param activo true para activar, false para desactivar
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
        System.out.println("Gestor de eventos " + (activo ? "activado" : "desactivado"));
    }
    
    /**
     * Limpia todos los observadores registrados
     */
    public void limpiarObservers() {
        observers.clear();
        System.out.println("Todos los observers han sido removidos");
    }
    
    /**
     * Limpia todos los eventos pendientes
     */
    public void limpiarEventosPendientes() {
        eventosPendientes.clear();
        System.out.println("Todos los eventos pendientes han sido limpiados");
    }
    
    /**
     * Obtiene estadísticas del gestor de eventos
     * @return String con estadísticas
     */
    public String obtenerEstadisticas() {
        return String.format(
            "ESTADÍSTICAS DEL GESTOR DE EVENTOS\n\n" +
            "Observers registrados: %d\n" +
            "Eventos pendientes: %d\n" +
            "Estado: %s",
            getCantidadObservers(),
            getCantidadEventosPendientes(),
            activo ? "Activo" : "Inactivo"
        );
    }
    
    /**
     * Cierra el gestor de eventos de manera segura
     */
    public void cerrar() {
        System.out.println("Cerrando gestor de eventos...");
        
        // Desactivar el gestor
        setActivo(false);
        
        // Procesar eventos pendientes
        procesarEventosPendientes();
        
        // Limpiar recursos
        limpiarObservers();
        limpiarEventosPendientes();
        
        System.out.println("Gestor de eventos cerrado");
    }
}
