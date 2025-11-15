/*
 * Formulario MDI para el Turnero - Sistema de Atención Diferenciada 2025
 * Wrapper para frmPantallaTurnero que permite abrirlo dentro del JDesktopPane
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario MDI que contiene el panel del turnero
 * Permite abrir el turnero dentro del sistema MDI
 */
public class frmTurneroMDI extends javax.swing.JInternalFrame {
    
    private frmPantallaTurnero turneroPanel;
    
    /**
     * Creates new form frmTurneroMDI
     */
    public frmTurneroMDI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        
        setTitle("Turnero");
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        
        // Crear el panel del turnero
        turneroPanel = new frmPantallaTurnero();
        
        // Agregar el panel al contenido
        getContentPane().add(turneroPanel, BorderLayout.CENTER);
        
        // Configurar tamaño
        setSize(450, 400);
        setLocation(50, 50);
    }
    
    /**
     * Obtiene el panel del turnero
     * @return Panel del turnero
     */
    public frmPantallaTurnero getTurneroPanel() {
        return turneroPanel;
    }
    
    /**
     * Establece el controlador del turnero
     * @param controlador Controlador del turnero
     */
    public void setControlador(controlador.ControladorTurnero controlador) {
        // Conectar el controlador con el panel del turnero
        if (turneroPanel != null) {
            turneroPanel.setControladorTurnero(controlador);
        }
    }
}





