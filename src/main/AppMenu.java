package main;

import config.DatabaseConnection;
import dao.GenericDAO;
import dao.MascotaDAO;
import dao.MicrochipDAO;
import service.MascotaService;
import service.MicrochipService;
import java.sql.Connection;
import java.util.Scanner;
import models.Mascota;


/**
 * Responsabilidades:
 * - Crear y gestionar el Scanner único
 * - Inicializar servicios necesarios (MascotaServiceImpl, MicrochipServiceImpl)
 * - Instanciar MenuHandler
 * - Ejecutar el loop principal
 * - Delegar operaciones a MenuHandler
*/

public class AppMenu {
    
    private final Scanner scanner;
    private final MenuHandler menuHandler;
    private boolean running;
    
public AppMenu() {
    this.scanner = new Scanner(System.in);
    
    try {
        // Crear DAOs
        MicrochipDAO microchipDAO = new MicrochipDAO();
        GenericDAO<Mascota> mascotaDAO = new MascotaDAO();

        // Crear conexión compartida para MascotaService
        Connection connection = DatabaseConnection.getConnection();

        // Crear servicios con las dependencias correctas
        MicrochipService microchipService = new MicrochipService(microchipDAO);
        MascotaService mascotaService = new MascotaService(mascotaDAO, microchipService, connection);

        // Crear el MenuHandler
        this.menuHandler = new MenuHandler(scanner, mascotaService, microchipService);
    } catch (Exception e) {
        throw new RuntimeException("Error al inicializar servicios y DAOs: " + e.getMessage(), e);
    } 
    
    this.running = true;  
}

public static void main(String[] args) {
        AppMenu app = new AppMenu();
        app.run();
    }

public void run() {
        while (running) {
            try {
                MenuDisplay.mostrarMenuPrincipal();
                int opcion = Integer.parseInt(scanner.nextLine());
                processOption(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
        scanner.close();
    }

    private void processOption(int opcion) {

        switch (opcion) {

            // MASCOTAS
            case 1 -> menuHandler.crearMascota();
            case 2 -> menuHandler.listarMascotas();
            case 3 -> menuHandler.actualizarMascota();
            case 4 -> menuHandler.eliminarMascota();

            // MICROCHIPS
            case 5 -> menuHandler.crearMicrochip();
            case 6 -> menuHandler.listarMicrochips();
            case 7 -> menuHandler.actualizarMicrochip();
            case 8 -> menuHandler.eliminarMicrochip();

            // OPERACIONES COMPUESTAS
            case 9  -> menuHandler.asignarMicrochipAMascota();
            case 10 -> menuHandler.eliminarMicrochipDeMascota();

            case 0 -> {
                System.out.println("Saliendo...");
                running = false;
            }

            default -> System.out.println("Opción no válida.");
        }
    }
}
