/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import javax.swing.JFrame;

/**
 *
 * @author m29ee
 */
public class frmInicioMenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form frmInicioMenuPrincipal
     */
    public frmInicioMenuPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Sistema de Atención Diferenciada 2025 - Menú Principal");
        configurarAtajosTeclado();
        personalizarBotones();
    }
    
    private void configurarAtajosTeclado() {
        // Atajos de teclado
        javax.swing.InputMap inputMap = getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
        javax.swing.ActionMap actionMap = getRootPane().getActionMap();
        
        // F2 - Abrir Turnero
        inputMap.put(javax.swing.KeyStroke.getKeyStroke("F2"), "abrirTurnero");
        actionMap.put("abrirTurnero", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                miTurneroActionPerformed(e);
            }
        });
        
        // F3 - Llamar Siguiente
        inputMap.put(javax.swing.KeyStroke.getKeyStroke("F3"), "llamarSiguiente");
        actionMap.put("llamarSiguiente", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                miLlamarSiguienteActionPerformed(e);
            }
        });
        
        // F4 - Nueva Atención
        inputMap.put(javax.swing.KeyStroke.getKeyStroke("F4"), "nuevaAtencion");
        actionMap.put("nuevaAtencion", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                miNuevaAtencionActionPerformed(e);
            }
        });
        
        // Ctrl+B - Buscar Cliente
        inputMap.put(javax.swing.KeyStroke.getKeyStroke("ctrl B"), "buscarCliente");
        actionMap.put("buscarCliente", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                miBuscarClienteActionPerformed(e);
            }
        });
    }
    
    /**
     * Personaliza el formato de los botones sin afectar el modo diseño
     */
    private void personalizarBotones() {
        // Personalizar botón Turnero (jButton1) - Botón principal del toolbar
        if (jButton1 != null) {
            // Color similar al desktopPane (gris claro)
            jButton1.setBackground(new java.awt.Color(192, 192, 192)); // Gris claro similar al desktopPane
            jButton1.setForeground(new java.awt.Color(51, 51, 51)); // Texto gris oscuro
            jButton1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13)); // Fuente más grande
            jButton1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createRaisedBevelBorder(),
                javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12) // Padding interno
            ));
            jButton1.setFocusPainted(false);
            jButton1.setPreferredSize(new java.awt.Dimension(120, 45)); // Tamaño más grande
            jButton1.setMinimumSize(new java.awt.Dimension(120, 45));
            jButton1.setMaximumSize(new java.awt.Dimension(120, 45));
        }
        
        // Personalizar botón Atención (jButton2) - Botón principal del toolbar
        if (jButton2 != null) {
            // Color similar al desktopPane (gris claro)
            jButton2.setBackground(new java.awt.Color(192, 192, 192)); // Gris claro similar al desktopPane
            jButton2.setForeground(new java.awt.Color(51, 51, 51)); // Texto gris oscuro
            jButton2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13)); // Fuente más grande
            jButton2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createRaisedBevelBorder(),
                javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12) // Padding interno
            ));
            jButton2.setFocusPainted(false);
            jButton2.setPreferredSize(new java.awt.Dimension(140, 45)); // Tamaño más grande
            jButton2.setMinimumSize(new java.awt.Dimension(140, 45));
            jButton2.setMaximumSize(new java.awt.Dimension(140, 45));
        }
        
        // Personalizar botones del toolbar principal (jToolBar1) - Botones secundarios
        if (jToolBar1 != null) {
            for (java.awt.Component comp : jToolBar1.getComponents()) {
                if (comp instanceof javax.swing.JButton) {
                    javax.swing.JButton btn = (javax.swing.JButton) comp;
                    btn.setBackground(new java.awt.Color(240, 240, 240)); // Gris más claro
                    btn.setForeground(new java.awt.Color(51, 51, 51)); // Gris oscuro
                    btn.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
                    btn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    btn.setFocusPainted(false);
                    btn.setPreferredSize(new java.awt.Dimension(100, 35)); // Tamaño estándar
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);
        jToolBar1.setName("toolBar"); // NOI18N
        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jDesktopPane1.setName("desktopPane"); // NOI18N

        jPanel1.setName("statusPanel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jToolBar2.setRollover(true);

        jButton1.setText("Turnero");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("btnTurnero"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jButton2.setText("Atención Clientes");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("btnAtencion"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jToolBar2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 359, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);

        jMenuBar1.setName("menuBar"); // NOI18N

        jMenu1.setMnemonic('a');
        jMenu1.setText("Archivo");
        jMenu1.setName("menuArchivo"); // NOI18N

        jMenuItem1.setText("Inicio");
        jMenuItem1.setName("miInicio"); // NOI18N
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Salir");
        jMenuItem2.setName("btnSalir"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setMnemonic('t');
        jMenu2.setText("Atención");
        jMenu2.setName("menuAtencion"); // NOI18N
        jMenuBar1.add(jMenu2);

        jMenu3.setMnemonic('c');
        jMenu3.setText("Clientes");
        jMenu3.setName("menuClientes"); // NOI18N
        jMenuBar1.add(jMenu3);

        jMenu4.setMnemonic('r');
        jMenu4.setText("Reportes");
        jMenu4.setName("menuReportes"); // NOI18N

        jMenuItem3.setText("Reporte Cola Atención");
        jMenuItem3.setName("btnReporteColaAtencion"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuBar1.add(jMenu4);

        jMenu5.setMnemonic('y');
        jMenu5.setText("Ayuda");
        jMenu5.setName("menuAyuda"); // NOI18N
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // Salir del sistema
        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea salir del sistema?", 
            "Salir del Sistema", 
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Botón Turnero - Abrir turnero
        miTurneroActionPerformed(evt);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Botón Atención - Abrir módulo de atención
        vista.frmRegistrarAtencion frm = new vista.frmRegistrarAtencion();
        frm.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // Abrir formulario de reporte de cola de atención
        vista.frmReporteColaAtencion frmReporte = new vista.frmReporteColaAtencion();
        frmReporte.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    // Métodos de funcionalidad para los menús
    private void miInicioActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Bienvenido al Sistema de Atención Diferenciada 2025", 
            "Inicio", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miTurneroActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            frmTurneroDirecto turnero = new frmTurneroDirecto();
            turnero.setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al abrir el turnero: " + e.getMessage(), 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void miNuevaAtencionActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Funcionalidad de Nueva Atención en desarrollo", 
            "Nueva Atención", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miLlamarSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Funcionalidad de Llamar Siguiente en desarrollo", 
            "Llamar Siguiente", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miABMClientesActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Funcionalidad de ABM de Clientes en desarrollo", 
            "ABM Clientes", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Funcionalidad de Búsqueda de Cliente en desarrollo", 
            "Buscar Cliente", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miReportesAtencionActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Funcionalidad de Reportes en desarrollo", 
            "Reportes", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miReportesTiemposActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Funcionalidad de Reportes de Tiempos en desarrollo", 
            "Reportes", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Sistema de Atención Diferenciada 2025\n" +
            "Versión 1.0\n" +
            "Desarrollado por Jorge Luna\n" +
            "Seminario de Práctica 2025\n\n" +
            "Formulario creado con GUI Builder de NetBeans", 
            "Acerca de...", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void miAtajosTecladoActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "ATAJOS DE TECLADO:\n\n" +
            "F2 - Abrir Turnero\n" +
            "F3 - Llamar siguiente\n" +
            "F4 - Nueva Atención\n" +
            "Ctrl+H - Inicio\n" +
            "Ctrl+B - Buscar Cliente\n" +
            "Alt+C - ABM Clientes\n" +
            "Alt+F4 - Salir", 
            "Atajos de Teclado", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Métodos de funcionalidad para los botones
    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {
        miInicioActionPerformed(evt);
    }
    
    private void btnAtencionActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Módulo de Atención abierto", 
            "Atención", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Módulo de Clientes abierto", 
            "Clientes", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void btnTurneroActionPerformed(java.awt.event.ActionEvent evt) {
        miTurneroActionPerformed(evt);
    }
    
    // ========== MÉTODOS REQUERIDOS POR CONTROLADORPRINCIPAL ==========
    
    /**
     * Obtiene el DesktopPane para el gestor de ventanas MDI
     */
    public javax.swing.JDesktopPane getDesktopPane() {
        return jDesktopPane1;
    }
    
    /**
     * Establece el controlador principal
     */
    public void setControladorPrincipal(controlador.ControladorPrincipal controlador) {
        // Implementar si es necesario
    }
    
    /**
     * Establece el controlador de turnero
     */
    public void setControladorTurnero(controlador.ControladorTurnero controlador) {
        // Implementar si es necesario
    }
    
    /**
     * Establece el controlador de cliente
     */
    public void setControladorCliente(controlador.ControladorCliente controlador) {
        // Implementar si es necesario
    }
    
    /**
     * Establece el controlador de atención
     */
    public void setControladorAtencion(controlador.ControladorAtencion controlador) {
        // Implementar si es necesario
    }
    
    /**
     * Actualiza la barra de estado con información del usuario
     */
    public void actualizarBarraEstado(String usuario, String rol, String canal, boolean conexionDB) {
        // Actualizar labels de la barra de estado si existen
        if (jPanel1.getComponentCount() > 0) {
            // Buscar labels en el panel de estado
            for (java.awt.Component comp : jPanel1.getComponents()) {
                if (comp instanceof javax.swing.JLabel) {
                    javax.swing.JLabel label = (javax.swing.JLabel) comp;
                    if (label.getName() != null && label.getName().equals("lblUsuario")) {
                        label.setText("Usuario: " + usuario + " | Rol: " + rol + " | Canal: " + canal);
                    } else if (label.getName() != null && label.getName().equals("lblEstado")) {
                        label.setText(conexionDB ? "DB: Conectado" : "DB: Desconectado");
                    }
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmInicioMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInicioMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInicioMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInicioMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInicioMenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
