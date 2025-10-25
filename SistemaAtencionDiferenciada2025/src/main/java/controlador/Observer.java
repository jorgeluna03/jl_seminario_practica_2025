/*
 * Interfaz Observer - Sistema de Atención Diferenciada 2025
 * Define el contrato para el patrón Observer
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

/**
 * Interfaz Observer que define el contrato para el patrón Observer
 * Permite que los objetos sean notificados de cambios en el sistema
 */
public interface Observer {
    
    /**
     * Método que se llama cuando hay un cambio en el sistema
     * @param evento Descripción del evento que ocurrió
     */
    void actualizar(String evento);
}
