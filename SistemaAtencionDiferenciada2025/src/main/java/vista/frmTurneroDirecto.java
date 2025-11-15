/*
 * Formulario de Turnero Directo - Sistema de Atención Diferenciada 2025
 * Abre directamente tu formulario frmPantallaTurnero
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario que abre directamente tu formulario frmPantallaTurnero
 */
public class frmTurneroDirecto extends JFrame {
    
    private frmPantallaTurnero turneroPanel;
    private controlador.ControladorTurnero controladorTurnero;
    private controlador.ControladorPrincipal controladorPrincipal;
    private JPanel panelTipoTurno;
    private JRadioButton rbConsultaGeneral;
    private JRadioButton rbSolicitudPrestamo;
    private JRadioButton rbReclamo;
    private JRadioButton rbOtros;
    private ButtonGroup grupoTipoTurno;
    

    public frmTurneroDirecto() {
        initComponents();
    }

 
    private void initComponents() {
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Turnero - Sistema de Atención Diferenciada 2025");
        setResizable(true);
        
        crearSelectorTipoTurno();
        
        // Crear el panel del turnero
        turneroPanel = new frmPantallaTurnero();
        
        // Crear controlador principal
        controladorPrincipal = new controlador.ControladorPrincipal();
        
        // Crear controlador del turnero con referencia al principal
        controladorTurnero = new controlador.ControladorTurnero(controladorPrincipal);
        
        // Conectar controlador con el panel
        turneroPanel.setControladorTurnero(controladorTurnero);
        
        // Interceptar el botón Entrar del panel para manejar el registro desde aquí
        configurarBotonEntrar();
        
        getContentPane().add(panelTipoTurno, BorderLayout.WEST);
        getContentPane().add(turneroPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        
        crearMenu();
    }
    
    /**
     * Crea el menú de la ventana
     */
    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Operaciones
        JMenu menuOperaciones = new JMenu("Operaciones");
        
        JMenuItem itemLlamarSiguiente = new JMenuItem("Llamar Siguiente");
        itemLlamarSiguiente.addActionListener(e -> {
            if (controladorTurnero != null) {
                controladorTurnero.llamarSiguiente();
            }
        });
        
        JMenuItem itemReasignarTurno = new JMenuItem("Reasignar Turno");
        itemReasignarTurno.addActionListener(e -> {
            if (controladorTurnero != null) {
                controladorTurnero.reasignarTurno();
            }
        });
        
        JMenuItem itemEstadisticas = new JMenuItem("Estadísticas");
        itemEstadisticas.addActionListener(e -> {
            if (controladorTurnero != null) {
                controladorTurnero.mostrarEstadisticas();
            }
        });
        
        menuOperaciones.add(itemLlamarSiguiente);
        menuOperaciones.add(itemReasignarTurno);
        menuOperaciones.addSeparator();
        menuOperaciones.add(itemEstadisticas);
        
        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Sistema de Atención Diferenciada 2025\n" +
                "Desarrollado por Jorge Luna\n" +
                "Seminario de Práctica 2025\n\n" +
                "Formulario de Turnero con teclado numérico personalizado",
                "Acerca de", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        menuAyuda.add(itemAcercaDe);
        
        // Agregar menús a la barra
        menuBar.add(menuOperaciones);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Crea el panel superior con el selector de tipo de turno
     */
    private void crearSelectorTipoTurno() {
        panelTipoTurno = new JPanel();
        panelTipoTurno.setLayout(new BoxLayout(panelTipoTurno, BoxLayout.Y_AXIS));
        panelTipoTurno.setBorder(BorderFactory.createTitledBorder("Tipo de turno"));
        
        rbConsultaGeneral = new JRadioButton("Consulta general");
        rbSolicitudPrestamo = new JRadioButton("Solicitud Préstamo");
        rbReclamo = new JRadioButton("Reclamo");
        rbOtros = new JRadioButton("Otros");
        
        rbConsultaGeneral.setAlignmentX(LEFT_ALIGNMENT);
        rbSolicitudPrestamo.setAlignmentX(LEFT_ALIGNMENT);
        rbReclamo.setAlignmentX(LEFT_ALIGNMENT);
        rbOtros.setAlignmentX(LEFT_ALIGNMENT);
        
        grupoTipoTurno = new ButtonGroup();
        grupoTipoTurno.add(rbConsultaGeneral);
        grupoTipoTurno.add(rbSolicitudPrestamo);
        grupoTipoTurno.add(rbReclamo);
        grupoTipoTurno.add(rbOtros);
        
        rbConsultaGeneral.setSelected(true);
        
        panelTipoTurno.add(rbConsultaGeneral);
        panelTipoTurno.add(rbSolicitudPrestamo);
        panelTipoTurno.add(rbReclamo);
        panelTipoTurno.add(rbOtros);
        panelTipoTurno.add(Box.createVerticalGlue());
    }
    
    /**
     * Obtiene el tipo de turno seleccionado
     * @return String con el tipo de turno seleccionado
     */
    private String getTipoTurnoSeleccionado() {
        if (rbReclamo.isSelected()) {
            return "Reclamo";
        } else if (rbConsultaGeneral.isSelected()) {
            return "Consulta general";
        } else if (rbSolicitudPrestamo.isSelected()) {
            return "Solicitud Prestamo";
        } else if (rbOtros.isSelected()) {
            return "Otros";
        }
        return "Consulta general"; // Por defecto
    }
    
    /**
     * Configura el botón Entrar del panel para que llame al método de registro en esta clase
     */
    private void configurarBotonEntrar() {
        // Buscar el botón Entrar en el panel usando reflexión o método auxiliar
        javax.swing.JButton btnEntrar = buscarBotonEntrar();
        if (btnEntrar != null) {
            // Remover todos los listeners existentes
            for (java.awt.event.ActionListener listener : btnEntrar.getActionListeners()) {
                btnEntrar.removeActionListener(listener);
            }
            // Agregar nuestro listener que maneja el registro desde aquí
            btnEntrar.addActionListener(e -> registrarTurno());
        }
    }
    
    /**
     * Busca el botón Entrar en el panel del turnero
     * @return JButton del botón Entrar o null si no se encuentra
     */
    private javax.swing.JButton buscarBotonEntrar() {
        if (turneroPanel == null) {
            return null;
        }
        
        // Buscar el botón por nombre
        java.awt.Component[] components = turneroPanel.getComponents();
        for (java.awt.Component comp : components) {
            javax.swing.JButton btn = buscarBotonEnComponente(comp, "btnEntrar");
            if (btn != null) {
                return btn;
            }
        }
        return null;
    }
    
    /**
     * Busca recursivamente un botón por nombre en un componente y sus hijos
     */
    private javax.swing.JButton buscarBotonEnComponente(java.awt.Component comp, String nombre) {
        if (comp instanceof javax.swing.JButton) {
            javax.swing.JButton btn = (javax.swing.JButton) comp;
            if ("btnEntrar".equals(btn.getName()) || "Entrar".equals(btn.getText())) {
                return btn;
            }
        }
        
        if (comp instanceof java.awt.Container) {
            java.awt.Container container = (java.awt.Container) comp;
            for (java.awt.Component child : container.getComponents()) {
                javax.swing.JButton btn = buscarBotonEnComponente(child, nombre);
                if (btn != null) {
                    return btn;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Método que maneja el registro del turno cuando se presiona el botón Entrar
     */
    private void registrarTurno() {
        if (controladorTurnero == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: Controlador no inicializado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Obtener DNI del panel
        String dni = turneroPanel.getDniIngresado();
        
        if (dni == null || dni.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese un DNI válido.", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener tipo de turno seleccionado
        String tipoTurno = getTipoTurnoSeleccionado();
        
        // Llamar al controlador con DNI y tipo de turno
        modelo.Turnero turno = controladorTurnero.registrarTurno(dni, tipoTurno);
        
        if (turno != null) {
            String mensaje = String.format("""
                                           Turno registrado exitosamente:
                                           
                                           C\u00f3digo: %s
                                           Cliente: %s %s""",
                turno.getCodigoTurno(),
                turno.getCliente().getNombre(),
                turno.getCliente().getApellido()
            );
            
            JOptionPane.showMessageDialog(this, 
                mensaje, 
                "Turno Registrado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campo de DNI
            turneroPanel.limpiarCampo();
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
            java.util.logging.Logger.getLogger(frmTurneroDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTurneroDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTurneroDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTurneroDirecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTurneroDirecto().setVisible(true);
            }
        });
    }
}
