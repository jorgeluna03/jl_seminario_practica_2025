/*
 * Formulario MDI para Reportes - Sistema de Atención Diferenciada 2025
 * Formulario placeholder para gestión de reportes
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario MDI para gestión de reportes
 */
public class frmReportesMDI extends javax.swing.JInternalFrame {
    
    /**
     * Creates new form frmReportesMDI
     */
    public frmReportesMDI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        
        setTitle("Gestión de Reportes");
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        
        // Panel de contenido
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Gestión de Reportes", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        
        // Configurar tamaño
        setSize(600, 400);
        setLocation(200, 200);
    }
}

