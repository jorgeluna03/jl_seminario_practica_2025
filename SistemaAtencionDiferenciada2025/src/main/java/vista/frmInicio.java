/*
 * Formulario de inicio del Sistema de Atención Diferenciada 2025
 * Punto de entrada principal con interfaz gráfica
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario de inicio del sistema
 * Inicia directamente la interfaz gráfica del sistema
 */
public class frmInicio extends JFrame {
    
    private JButton btnInterfazGrafica;
    private JButton btnSalir;
    private JLabel lblTitulo;
    private JLabel lblDescripcion;
    
    /**
     * Creates new form frmInicio
     */
    public frmInicio() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Atención Diferenciada 2025 - Inicio");
        setResizable(false);
        
        // Configurar layout
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Título
        lblTitulo = new JLabel("Sistema de Atención Diferenciada 2025");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Descripción
        lblDescripcion = new JLabel("<html><center>Sistema de Atención Diferenciada 2025<br><br>" +
                                   "Sistema completo con interfaz gráfica, menús, formularios MDI<br>" +
                                   "y patrones de diseño implementados<br><br>" +
                                   "<b>El sistema se iniciará automáticamente en 3 segundos...</b><br><br>" +
                                   "Desarrollado por Jorge Luna - Seminario de Práctica 2025</center></html>");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        // Botón Iniciar Sistema
        btnInterfazGrafica = new JButton("Iniciar Sistema");
        btnInterfazGrafica.setFont(new Font("Arial", Font.BOLD, 16));
        btnInterfazGrafica.setPreferredSize(new Dimension(200, 50));
        btnInterfazGrafica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirInterfazGrafica();
            }
        });
        
        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setPreferredSize(new Dimension(150, 40));
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        
        panelBotones.add(btnInterfazGrafica);
        panelBotones.add(btnSalir);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(lblDescripcion);
        panelPrincipal.add(panelBotones);
        
        // Agregar panel principal al frame
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Configurar tamaño y centrar
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        // Auto-iniciar después de 3 segundos
        Timer autoInicio = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirInterfazGrafica();
            }
        });
        autoInicio.setRepeats(false);
        autoInicio.start();
    }
    
    /**
     * Abre la interfaz gráfica principal
     */
    private void abrirInterfazGrafica() {
        try {
            // Ocultar ventana de inicio
            setVisible(false);
            
            // Abrir menú principal
            frmMenuPrincipal menuPrincipal = new frmMenuPrincipal();
            menuPrincipal.setVisible(true);
            
            // Cerrar ventana de inicio
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al abrir la interfaz gráfica: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            setVisible(true);
        }
    }
    
    /**
     * Sale del sistema
     */
    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea salir del sistema?", 
            "Salir del Sistema", 
            JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInicio().setVisible(true);
            }
        });
    }
}
