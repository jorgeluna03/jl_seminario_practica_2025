/*
 * Interfaz ObserverConDatos - Sistema de Atención Diferenciada 2025
 * Extiende Observer para manejar eventos con datos adicionales
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

/**
 * Interfaz ObserverConDatos que extiende Observer
 * Permite manejar eventos con datos adicionales
 */
public interface ObserverConDatos extends Observer {
    
    /**
     * Método que se llama cuando hay un cambio en el sistema con datos adicionales
     * @param evento Descripción del evento que ocurrió
     * @param datos Datos adicionales del evento
     */
    void actualizar(String evento, Object datos);
}
