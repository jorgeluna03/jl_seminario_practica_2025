/*
 * Formulario de inicio directo - Sistema de Atención Diferenciada 2025
 * Va directamente a la interfaz gráfica sin pantalla de selección
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
 * Formulario de inicio directo que va inmediatamente a la interfaz gráfica
 */
public class frmInicioDirecto extends JFrame {
    
    private JLabel lblTitulo;
    private JLabel lblCargando;
    private JProgressBar progressBar;
    private Timer timer;
    private int contador = 0;
    
    /**
     * Creates new form frmInicioDirecto
     */
    public frmInicioDirecto() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Atención Diferenciada 2025 - Iniciando...");
        setResizable(false);
        
        // Configurar layout
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Título
        lblTitulo = new JLabel("Sistema de Atención Diferenciada 2025");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        lblTitulo.setForeground(new Color(25, 25, 112));
        
        // Mensaje de carga
        lblCargando = new JLabel("Iniciando sistema...");
        lblCargando.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCargando.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCargando.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        lblCargando.setForeground(new Color(70, 130, 180));
        
        // Barra de progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Cargando...");
        progressBar.setPreferredSize(new Dimension(300, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Información del sistema
        JLabel lblInfo = new JLabel("<html><center>" +
                                   "Sistema completo con interfaz gráfica<br>" +
                                   "Menús, formularios MDI y patrones de diseño<br><br>" +
                                   "Desarrollado por Jorge Luna - Seminario de Práctica 2025</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInfo.setForeground(new Color(105, 105, 105));
        
        // Agregar componentes al panel principal
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(lblCargando);
        panelPrincipal.add(progressBar);
        panelPrincipal.add(lblInfo);
        
        // Agregar panel principal al frame
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Configurar tamaño y centrar
        setSize(500, 350);
        setLocationRelativeTo(null);
        
        // Iniciar animación de carga
        iniciarAnimacionCarga();
    }
    
    /**
     * Inicia la animación de carga
     */
    private void iniciarAnimacionCarga() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contador++;
                int progreso = (contador * 100) / 60; // 3 segundos = 60 * 50ms
                
                if (progreso > 100) {
                    progreso = 100;
                }
                
                progressBar.setValue(progreso);
                
                // Cambiar mensajes durante la carga
                if (contador < 20) {
                    lblCargando.setText("Iniciando sistema...");
                } else if (contador < 40) {
                    lblCargando.setText("Cargando controladores...");
                } else if (contador < 50) {
                    lblCargando.setText("Configurando interfaz...");
                } else {
                    lblCargando.setText("¡Sistema listo!");
                }
                
                // Cuando termine la carga
                if (contador >= 60) {
                    timer.stop();
                    abrirInterfazGrafica();
                }
            }
        });
        timer.start();
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
            java.util.logging.Logger.getLogger(frmInicioDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInicioDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInicioDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInicioDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInicioDirecto().setVisible(true);
            }
        });
    }
}
