package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Mascota;
import models.Microchip;
import service.MascotaService;
import service.MicrochipService;



public class MenuHandler {
    private final Scanner scanner;
    private final MascotaService mascotaService;
    private final MicrochipService microchipService;

    public MenuHandler(Scanner scanner,
                       MascotaService mascotaService,
                       MicrochipService microchipService) {

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
        
        Mascota mascota = new Mascota(0, nombre, especie, raza, LocalDate.parse(fechaNacimiento), duenio);
        
        try {
            mascotaService.insertar(mascota);
            System.out.println("Mascota creada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear mascota: " + e.getMessage());
        }
 
    }

    public void listarMascotas() {
        System.out.println("\n--- Lista de Mascotas ---");
        try {
            List<Mascota> mascotas = mascotaService.getAll();

            String[] headers = new String[] { "ID", "NOMBRE", "ESPECIE", "RAZA", "FECHA_NAC", "DUEÑO", "MICROCHIP" };

            List<java.util.function.Function<Mascota, String>> extractors = new ArrayList<>();
            extractors.add(m -> String.valueOf(m.getId()));
            extractors.add(m -> m.getNombre());
            extractors.add(m -> m.getEspecie());
            extractors.add(m -> m.getRaza());
            extractors.add(m -> String.valueOf(m.getFechaNacimiento()));
            extractors.add(m -> m.getDuenio());
            extractors.add(m -> m.getMicrochip() != null ? m.getMicrochip().getCodigo() : "-");

            TablePrinter.printTable(mascotas, "MASCOTAS", headers, extractors);

            // presionar cualquier tecla para continuar
            esperar_tecla();

        } catch (Exception e) {
            System.out.println("Error al listar mascotas: " + e.getMessage());
            esperar_tecla();
        }
    }

    public void actualizarMascota() {
        System.out.println("\n--- Actualizar Mascota ---");

        try {
            System.out.print("ID de mascota: ");
            int id = Integer.parseInt(scanner.nextLine());
            // buscar mascota por id
            Mascota mascota = mascotaService.getById(id);
            if (mascota == null) {
                System.out.println("Mascota no encontrada.");
                return;
            }

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

            mascota.setNombre(nombre);
            mascota.setEspecie(especie);
            mascota.setRaza(raza);
            mascota.setFechaNacimiento(fechaNacimiento);
            mascota.setDuenio(duenio);

            mascotaService.actualizar(mascota);
            System.out.println("Mascota actualizada con éxito.");

        } catch (Exception e) {
            System.out.println("Error al actualizar mascota: " + e.getMessage());
        }
        
    }

    public void eliminarMascota() {
        System.out.println("\n--- Eliminar Mascota ---");

        try {
        System.out.print("ID de mascota: ");
        int id = Integer.parseInt(scanner.nextLine());

        mascotaService.eliminar(id);
        System.out.println("Mascota eliminada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al eliminar mascota: " + e.getMessage());
        }
    }

    // ---------------------------
    //        MICROCHIPS
    // ---------------------------

    public void crearMicrochip() {
        System.out.println("\n--- Crear Microchip ---");
        
        try {
            System.out.print("Código: ");
            String codigo = scanner.nextLine();

            System.out.print("Fecha implementación (YYYY-MM-DD): ");
            String fechaImpl = scanner.nextLine();

            System.out.print("Veterinaria: ");
            String vet = scanner.nextLine();

            System.out.print("Observaciones: ");
            String obs = scanner.nextLine();

            Microchip microchip = new Microchip(0, codigo, obs, vet, LocalDate.parse(fechaImpl));
            microchipService.insertar(microchip);
            System.out.println("Microchip creado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear microchip: " + e.getMessage());
        }
    }

    public void listarMicrochips() {
        System.out.println("\n--- Lista de Microchips ---");
        try {
            List<Microchip> lista = microchipService.getAll();

            String[] headers = new String[] { "ID", "CODIGO", "FECHA_IMPL", "VETERINARIA", "OBSERVACIONES" };
            List<java.util.function.Function<Microchip, String>> extractors = new ArrayList<>();
            extractors.add(m -> String.valueOf(m.getId()));
            extractors.add(m -> m.getCodigo());
            extractors.add(m -> String.valueOf(m.getFechaImplantacion()));
            extractors.add(m -> m.getVeterinaria());
            extractors.add(m -> m.getObservaciones());

            TablePrinter.printTable(lista, "MICROCHIPS", headers, extractors);

            esperar_tecla();

        } catch (Exception e) {
            System.out.println("Error al listar microchips: " + e.getMessage());
            esperar_tecla();
        }
    }

    public void actualizarMicrochip() {
        System.out.println("\n--- Actualizar Microchip ---");

        try {
            System.out.print("ID del microchip: ");
            int id = Integer.parseInt(scanner.nextLine());
            Microchip microchip = microchipService.getById(id);
            if (microchip == null) {
                System.out.println("Microchip no encontrado.");
                return;
            }

            System.out.print("Nuevo código: ");
            String codigo = scanner.nextLine();

            System.out.print("Nueva fecha de implementación: ");
            String fecha = scanner.nextLine();

            System.out.print("Nueva veterinaria: ");
            String vet = scanner.nextLine();

            System.out.print("Nuevas observaciones: ");
            String obs = scanner.nextLine();

            microchip.setCodigo(codigo);
            microchip.setFechaImplantacion(fecha);
            microchip.setVeterinaria(vet);
            microchip.setObservaciones(obs);

            microchipService.actualizar(microchip);
            System.out.println("Microchip actualizado con éxito.");
            esperar_tecla();

        } catch (Exception e) {
            System.out.println("Error al actualizar microchip: " + e.getMessage());
            esperar_tecla();
        }
    }

    public void eliminarMicrochip() {
        System.out.println("\n--- Eliminar Microchip ---");

        try {
            System.out.print("ID del microchip: ");
            int id = Integer.parseInt(scanner.nextLine());
            microchipService.eliminar(id);
            System.out.println("Microchip eliminado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al eliminar microchip: " + e.getMessage());
        }
    }

    // ---------------------------
    //      OPERACIONES MIXTAS
    // ---------------------------

    public void asignarMicrochipAMascota() {
        System.out.println("\n--- Asignar Microchip a Mascota ---");

        try {
            System.out.print("ID mascota: ");
            int idMascota = Integer.parseInt(scanner.nextLine());

            System.out.print("ID microchip: ");
            int idMicrochip = Integer.parseInt(scanner.nextLine());

            Mascota mascota = mascotaService.getById(idMascota);
            Microchip microchip = microchipService.getById(idMicrochip);
           
            if (mascota == null) {
                System.out.println("Mascota no encontrada.");
                return;
            }

            if (microchip == null) {
                System.out.println("Microchip no encontrado.");
                return;
            }

            mascota.setMicrochip(microchip);
            mascotaService.actualizar(mascota);

            System.out.println("Microchip asignado a la mascota correctamente.");
        } catch (Exception e) {
            System.out.println("Error al asignar microchip: " + e.getMessage());
        }  
    }

    public void eliminarMicrochipDeMascota() {
        System.out.println("\n--- Eliminar Microchip de Mascota ---");

        try {
            System.out.print("ID mascota: ");
            int idMascota = Integer.parseInt(scanner.nextLine());

            Mascota mascota = mascotaService.getById(idMascota);
            
            if (mascota == null) {
                System.out.println("Mascota no encontrada.");
                return;
            }

            mascota.setMicrochip(null);
            mascotaService.actualizar(mascota);

            System.out.println("Microchip eliminado de la mascota correctamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar microchip de la mascota: " + e.getMessage());
        }
    }

    // ---------------------------
    //      FUNCION AUXILIAR PARA LA CONSOLA: espera que el usuario presione alguna tecla
    // ---------------------------

    public void esperar_tecla() {
        System.out.print("Presione una tecla para continuar...");
        scanner.nextLine();
    }

    
}
