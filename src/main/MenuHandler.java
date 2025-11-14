package main;

import java.util.List;
import java.util.Scanner;


public class MenuHandler {
    private final Scanner scanner;
    private final MascotaServiceImpl mascotaService;
    private final MicrochipServiceImpl microchipService;

    public MenuHandler(Scanner scanner,
                       MascotaServiceImpl mascotaService,
                       MicrochipServiceImpl microchipService) {

        this.scanner = scanner;
        this.mascotaService = mascotaService;
        this.microchipService = microchipService;
    }

    // ---------------------------
    //        MASCOTAS
    // ---------------------------

    public void crearMascota() {
        System.out.println("\n--- Crear Mascota ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Especie: ");
        String especie = scanner.nextLine();

        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();

        System.out.print("Dueño: ");
        String duenio = scanner.nextLine();

        mascotaService.crearMascota(nombre, especie, raza, fechaNacimiento, duenio);
        System.out.println("Mascota creada con éxito.");
    }

    public void listarMascotas() {
        System.out.println("\n--- Lista de Mascotas ---");
        mascotaService.listarMascotas();
    }

    public void actualizarMascota() {
        System.out.println("\n--- Actualizar Mascota ---");

        System.out.print("ID de mascota: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva especie: ");
        String especie = scanner.nextLine();

        System.out.print("Nueva raza: ");
        String raza = scanner.nextLine();

        System.out.print("Nueva fecha de nacimiento: ");
        String fechaNacimiento = scanner.nextLine();

        System.out.print("Nuevo dueño: ");
        String duenio = scanner.nextLine();

        mascotaService.actualizarMascota(id, nombre, especie, raza, fechaNacimiento, duenio);
    }

    public void eliminarMascota() {
        System.out.println("\n--- Eliminar Mascota ---");

        System.out.print("ID de mascota: ");
        int id = Integer.parseInt(scanner.nextLine());

        mascotaService.eliminarMascota(id);
    }

    // ---------------------------
    //        MICROCHIPS
    // ---------------------------

    public void crearMicrochip() {
        System.out.println("\n--- Crear Microchip ---");

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Fecha implementación (YYYY-MM-DD): ");
        String fechaImpl = scanner.nextLine();

        System.out.print("Veterinaria: ");
        String vet = scanner.nextLine();

        System.out.print("Observaciones: ");
        String obs = scanner.nextLine();

        microchipService.crearMicrochip(codigo, fechaImpl, vet, obs);
    }

    public void listarMicrochips() {
        System.out.println("\n--- Lista de Microchips ---");
        microchipService.listarMicrochips();
    }

    public void actualizarMicrochip() {
        System.out.println("\n--- Actualizar Microchip ---");

        System.out.print("ID del microchip: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nuevo código: ");
        String codigo = scanner.nextLine();

        System.out.print("Nueva fecha de implementación: ");
        String fecha = scanner.nextLine();

        System.out.print("Nueva veterinaria: ");
        String vet = scanner.nextLine();

        System.out.print("Nuevas observaciones: ");
        String obs = scanner.nextLine();

        microchipService.actualizarMicrochip(id, codigo, fecha, vet, obs);
    }

    public void eliminarMicrochip() {
        System.out.println("\n--- Eliminar Microchip ---");

        System.out.print("ID del microchip: ");
        int id = Integer.parseInt(scanner.nextLine());

        microchipService.eliminarMicrochip(id);
    }

    // ---------------------------
    //      OPERACIONES MIXTAS
    // ---------------------------

    public void asignarMicrochipAMascota() {
        System.out.println("\n--- Asignar Microchip a Mascota ---");

        System.out.print("ID mascota: ");
        int idMascota = Integer.parseInt(scanner.nextLine());

        System.out.print("ID microchip: ");
        int idMicrochip = Integer.parseInt(scanner.nextLine());

        mascotaService.asignarMicrochip(idMascota, idMicrochip);
    }

    public void eliminarMicrochipDeMascota() {
        System.out.println("\n--- Eliminar Microchip de Mascota ---");

        System.out.print("ID mascota: ");
        int idMascota = Integer.parseInt(scanner.nextLine());

        mascotaService.eliminarMicrochip(idMascota);
    }
    
}
