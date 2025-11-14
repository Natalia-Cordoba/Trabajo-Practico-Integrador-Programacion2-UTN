package main;

import models.Mascota;
import models.Microchip;
import service.MascotaServiceImpl;
import service.MicrochipServiceImpl;
import dao.MascotaDAO;
import dao.MicrochipDAO;

import java.util.Scanner;
import java.util.List;

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

    MicrochipServiceImpl microchipService = new MicrochipServiceImpl();
    MascotaServiceImpl mascotaService = new MascotaServiceImpl(microchipService);

    this.menuHandler = new MenuHandler(scanner, mascotaService, microchipService);
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
