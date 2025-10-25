/*
 * Controlador Principal del Sistema de Atención Diferenciada 2025
 * Implementa el patrón MVC (Model-View-Controller)
 * 
 * @author Jorge Luna
 * @version 1.0
 */
package controlador;

import vista.frmMenuPrincipal;
import modelo.ModeloAtencionDiferenciada;
import controlador.GestorEventos;
import controlador.GestorVentanasMDI;
import controlador.ControladorTurnero;
import controlador.ControladorCliente;
import controlador.ControladorAtencion;
import controlador.FabricaFormularios;

/**
 * Controlador Principal que coordina todos los controladores del sistema
 * Implementa el patrón MVC como controlador central
 */
public class ControladorPrincipal {
    
    // Referencias a los componentes del patrón MVC
    private frmMenuPrincipal vista;
    private ModeloAtencionDiferenciada modelo;
    
    // Controladores específicos (patrón Command)
    private ControladorTurnero controladorTurnero;
    private ControladorCliente controladorCliente;
    private ControladorAtencion controladorAtencion;
    
    // Gestores del sistema (patrones Observer, Factory, Facade)
    private GestorEventos gestorEventos;
    private GestorVentanasMDI gestorVentanas;
    private FabricaFormularios fabricaFormularios;
    
    // Estado del sistema
    private String usuarioLogueado;
    private String rolUsuario;
    private String canalActual;
    private boolean conexionDB;
    
    /**
     * Constructor del Controlador Principal
     * Inicializa todos los controladores y gestores
     */
    public ControladorPrincipal() {
        // Inicializar modelo
        this.modelo = new ModeloAtencionDiferenciada();
        
        // Inicializar gestores
        this.gestorEventos = new GestorEventos();
        this.fabricaFormularios = new FabricaFormularios();
        
        // Inicializar vista
        this.vista = new frmMenuPrincipal();
        
        // Inicializar gestor de ventanas con el desktop pane de la vista
        this.gestorVentanas = new GestorVentanasMDI(vista.getDesktopPane());
        
        // Inicializar controladores específicos
        inicializarControladores();
        
        // Configurar eventos y dependencias
        configurarSistema();
        
        // Configurar estado inicial
        configurarEstadoInicial();
    }
    
    /**
     * Inicializa todos los controladores específicos
     */
    private void inicializarControladores() {
        this.controladorTurnero = new ControladorTurnero(this);
        this.controladorCliente = new ControladorCliente(this);
        this.controladorAtencion = new ControladorAtencion(this);
    }
    
    /**
     * Configura el sistema completo con todas las dependencias
     */
    private void configurarSistema() {
        // Configurar controladores en la vista
        vista.setControladorPrincipal(this);
        vista.setControladorTurnero(controladorTurnero);
        vista.setControladorCliente(controladorCliente);
        vista.setControladorAtencion(controladorAtencion);
        
        // Configurar gestores
        gestorVentanas.setFabricaFormularios(fabricaFormularios);
        gestorVentanas.setGestorEventos(gestorEventos);
        
        // Registrar observadores
        gestorEventos.agregarObserver(controladorTurnero);
        gestorEventos.agregarObserver(controladorCliente);
        gestorEventos.agregarObserver(controladorAtencion);
    }
    
    /**
     * Configura el estado inicial del sistema
     */
    private void configurarEstadoInicial() {
        this.usuarioLogueado = "j.luna";
        this.rolUsuario = "Supervisor";
        this.canalActual = "Sucursal Centro";
        this.conexionDB = true;
        
        // Actualizar vista con estado inicial
        vista.actualizarBarraEstado(usuarioLogueado, rolUsuario, canalActual, conexionDB);
    }
    
    /**
     * Inicia el sistema principal
     */
    public void iniciarSistema() {
        vista.setVisible(true);
        gestorEventos.notificarCambio("SISTEMA_INICIADO");
    }
    
    /**
     * Cierra el sistema de manera controlada
     */
    public void cerrarSistema() {
        gestorEventos.notificarCambio("SISTEMA_CERRANDO");
        
        // Cerrar todas las ventanas abiertas
        gestorVentanas.cerrarTodasVentanas();
        
        // Cerrar vista principal
        vista.dispose();
        
        gestorEventos.notificarCambio("SISTEMA_CERRADO");
    }
    
    /**
     * Cambia el usuario logueado
     * @param nuevoUsuario Nuevo usuario
     * @param nuevoRol Nuevo rol
     */
    public void cambiarUsuario(String nuevoUsuario, String nuevoRol) {
        this.usuarioLogueado = nuevoUsuario;
        this.rolUsuario = nuevoRol;
        
        vista.actualizarBarraEstado(usuarioLogueado, rolUsuario, canalActual, conexionDB);
        gestorEventos.notificarCambio("USUARIO_CAMBIADO");
    }
    
    /**
     * Cambia el canal actual
     * @param nuevoCanal Nuevo canal
     */
    public void cambiarCanal(String nuevoCanal) {
        this.canalActual = nuevoCanal;
        
        vista.actualizarBarraEstado(usuarioLogueado, rolUsuario, canalActual, conexionDB);
        gestorEventos.notificarCambio("CANAL_CAMBIADO");
    }
    
    /**
     * Actualiza el estado de conexión a la base de datos
     * @param conectado Estado de conexión
     */
    public void actualizarConexionDB(boolean conectado) {
        this.conexionDB = conectado;
        
        vista.actualizarBarraEstado(usuarioLogueado, rolUsuario, canalActual, conexionDB);
        gestorEventos.notificarCambio("CONEXION_ACTUALIZADA");
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public frmMenuPrincipal getVista() {
        return vista;
    }
    
    public ModeloAtencionDiferenciada getModelo() {
        return modelo;
    }
    
    public ControladorTurnero getControladorTurnero() {
        return controladorTurnero;
    }
    
    public ControladorCliente getControladorCliente() {
        return controladorCliente;
    }
    
    public ControladorAtencion getControladorAtencion() {
        return controladorAtencion;
    }
    
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }
    
    public GestorVentanasMDI getGestorVentanas() {
        return gestorVentanas;
    }
    
    public FabricaFormularios getFabricaFormularios() {
        return fabricaFormularios;
    }
    
    public String getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    public String getRolUsuario() {
        return rolUsuario;
    }
    
    public String getCanalActual() {
        return canalActual;
    }
    
    public boolean isConexionDB() {
        return conexionDB;
    }
}
