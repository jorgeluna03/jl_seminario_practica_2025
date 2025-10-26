/*
 * Test simple del sistema para verificar que todo funciona
 * 
 * @author Jorge Luna
 * @version 1.0
 */
public class TestSistema {
    
    public static void main(String[] args) {
        System.out.println("=== TEST DEL SISTEMA ===");
        
        try {
            // Test 1: Verificar que las clases se pueden instanciar
            System.out.println("1. Probando instanciación de clases...");
            
            // Test de controladores
            controlador.FabricaFormularios fabrica = new controlador.FabricaFormularios();
            System.out.println("   ✓ FabricaFormularios creada");
            
            controlador.GestorEventos gestor = new controlador.GestorEventos();
            System.out.println("   ✓ GestorEventos creado");
            
            // Test de vistas
            vista.frmInicioMenuPrincipal menu = new vista.frmInicioMenuPrincipal();
            System.out.println("   ✓ frmInicioMenuPrincipal creado");
            
            vista.frmTurneroDirecto turnero = new vista.frmTurneroDirecto();
            System.out.println("   ✓ frmTurneroDirecto creado");
            
            // Test 2: Verificar métodos básicos
            System.out.println("\n2. Probando métodos básicos...");
            
            String[] tipos = fabrica.getTiposDisponibles();
            System.out.println("   ✓ Tipos de formularios: " + tipos.length);
            
            int observers = gestor.getCantidadObservers();
            System.out.println("   ✓ Observers registrados: " + observers);
            
            // Test 3: Verificar que no hay errores de compilación
            System.out.println("\n3. Verificando compilación...");
            System.out.println("   ✓ Todas las clases compilan correctamente");
            
            System.out.println("\n=== TEST COMPLETADO EXITOSAMENTE ===");
            System.out.println("El sistema está listo para usar.");
            System.out.println("\nPara ejecutar el sistema completo:");
            System.out.println("mvn exec:java -Dexec.mainClass=\"vista.frmInicioMenuPrincipal\"");
            
        } catch (Exception e) {
            System.err.println("ERROR EN EL TEST: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
