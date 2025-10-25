package com.mycompany.sistemaatenciondiferenciada2025;

import java.util.Scanner;
import modelo.Cliente;
import modelo.Turnero;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Sistema de Atención Diferenciada 2025
 * Clase principal que maneja el menú y la lógica del sistema
 * 
 * @author Jorge Luna
 * @version 1.0
 */
public class SistemaAtencionDiferenciada2025 {
    
    private static Scanner scanner = new Scanner(System.in);
    private static int contadorTurnos = 1;
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   SISTEMA DE ATENCIÓN DIFERENCIADA 2025");
        System.out.println("==========================================");
        
        mostrarMenuPrincipal();
    }
    
    /**
     * Muestra el menú principal del sistema
     */
    public static void mostrarMenuPrincipal() {
        int opcion;
        
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar ingreso a Sucursal");
            System.out.println("2. Gestionar Skills de Colaboradores");
            System.out.println("3. Gestionar Criticidad de Gestiones");
            System.out.println("4. Consultar Atención Diferenciada");
            System.out.println("5. Gestionar Modelo LTV");
            System.out.println("6. Consultar Llamadas Derivadas");
            System.out.println("7. Registrar Llamada Telefónica");
            System.out.println("8. Gestionar Turnero");
            System.out.println("0. Salir del Sistema");
            System.out.print("\nSeleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        registrarIngresoSucursal();
                        break;
                    case 2:
                        System.out.println("Funcionalidad en desarrollo: Gestionar Skills de Colaboradores");
                        break;
                    case 3:
                        System.out.println("Funcionalidad en desarrollo: Gestionar Criticidad de Gestiones");
                        break;
                    case 4:
                        System.out.println("Funcionalidad en desarrollo: Consultar Atención Diferenciada");
                        break;
                    case 5:
                        System.out.println("Funcionalidad en desarrollo: Gestionar Modelo LTV");
                        break;
                    case 6:
                        System.out.println("Funcionalidad en desarrollo: Consultar Llamadas Derivadas");
                        break;
                    case 7:
                        System.out.println("Funcionalidad en desarrollo: Registrar Llamada Telefónica");
                        break;
                    case 8:
                        System.out.println("Funcionalidad en desarrollo: Gestionar Turnero");
                        break;
                    case 0:
                        System.out.println("¡Gracias por usar el Sistema de Atención Diferenciada!");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione una opción del 0 al 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                opcion = -1;
            }
        } while (opcion != 0);
    }
    
    /**
     * Implementa el caso de uso 9.2.6: Registrar ingreso a Sucursal
     * Permite a un cliente registrarse para ser atendido en la sucursal
     */
    public static void registrarIngresoSucursal() {
        System.out.println("\n=== REGISTRAR INGRESO A SUCURSAL ===");
        
        try {
            // Paso 1: Solicitar DNI del cliente
            System.out.print("Ingrese el DNI del cliente: ");
            String dni = scanner.nextLine().trim();
            
            if (dni.isEmpty()) {
                System.out.println("Error: El DNI no puede estar vacío.");
                return;
            }
            
            // Validar formato básico de DNI (solo números)
            if (!dni.matches("\\d{7,8}")) {
                System.out.println("Error: El DNI debe contener entre 7 y 8 dígitos numéricos.");
                return;
            }
            
            // Paso 2: Consultar modelo LTV (simulado)
            System.out.println("Consultando valor del cliente en el modelo LTV...");
            String valorLTV = consultarModeloLTV(dni);
            
            // Paso 3: Determinar prioridad y registrar en lista de espera
            String tipoLista;
            if ("Alto".equals(valorLTV)) {
                tipoLista = "Lista de espera PRIORIZADA";
                System.out.println("✓ Cliente con valor LTV ALTO - Prioridad alta asignada");
            } else {
                tipoLista = "Lista de espera NORMAL";
                System.out.println("✓ Cliente con valor LTV BAJO - Lista normal asignada");
            }
            
            // Paso 4: Generar número de turno
            String codigoTurno = generarCodigoTurno(valorLTV);
            
            // Paso 5: Crear objetos del modelo
            Cliente cliente = crearCliente(dni);
            Turnero turnero = new Turnero(contadorTurnos++, LocalDate.now(), cliente, codigoTurno);
            
            // Paso 6: Mostrar comprobante
            mostrarComprobante(turnero, tipoLista, valorLTV);
            
        } catch (Exception e) {
            System.out.println("Error durante el registro: " + e.getMessage());
        }
    }
    
    /**
     * Simula la consulta al modelo LTV
     * @param dni DNI del cliente
     * @return Valor LTV (Alto/Bajo)
     */
    private static String consultarModeloLTV(String dni) {
        // Simulación: clientes con DNI terminado en número par = Alto, impar = Bajo
        int ultimoDigito = Character.getNumericValue(dni.charAt(dni.length() - 1));
        return (ultimoDigito % 2 == 0) ? "Alto" : "Bajo";
    }
    
    /**
     * Genera código de turno basado en el valor LTV
     * @param valorLTV Valor del cliente
     * @return Código de turno generado
     */
    private static String generarCodigoTurno(String valorLTV) {
        String prefijo = "Alto".equals(valorLTV) ? "P" : "N"; // P=Prioritario, N=Normal
        return prefijo + String.format("%04d", contadorTurnos);
    }
    
    /**
     * Crea un objeto Cliente con datos básicos
     * @param dni DNI del cliente
     * @return Objeto Cliente creado
     */
    private static Cliente crearCliente(String dni) {
        // Datos simulados para el prototipo
        return new Cliente(
            "Cliente", 
            "Ejemplo", 
            LocalDate.of(1990, 1, 1), 
            dni, 
            LocalDate.now()
        );
    }
    
    /**
     * Muestra el comprobante de turno generado
     * @param turnero Objeto Turnero creado
     * @param tipoLista Tipo de lista asignada
     * @param valorLTV Valor LTV del cliente
     */
    private static void mostrarComprobante(Turnero turnero, String tipoLista, String valorLTV) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        COMPROBANTE DE TURNO");
        System.out.println("=".repeat(50));
        System.out.println("Fecha: " + turnero.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Código de Turno: " + turnero.getCodigoTurno());
        System.out.println("DNI Cliente: " + turnero.getCliente().getDni());
        System.out.println("Valor LTV: " + valorLTV);
        System.out.println("Tipo de Atención: " + tipoLista);
        System.out.println("=".repeat(50));
        System.out.println("¡Su turno ha sido registrado exitosamente!");
        System.out.println("Por favor, espere a ser llamado por su número de turno.");
        System.out.println("=".repeat(50));
    }
}
