package main;

public class MenuDisplay {
    public static void mostrarMenuPrincipal() {
        System.out.println("\n==============================");
        System.out.println("        MENÚ PRINCIPAL");
        System.out.println("==============================");

        System.out.println("---- MASCOTAS ----");
        System.out.println("1. Crear mascota");
        System.out.println("2. Listar mascotas");
        System.out.println("3. Actualizar mascota");
        System.out.println("4. Eliminar mascota");

        System.out.println("\n---- MICROCHIPS ----");
        System.out.println("5. Crear microchip");
        System.out.println("6. Listar microchips");
        System.out.println("7. Actualizar microchip");
        System.out.println("8. Eliminar microchip");

        System.out.println("\n---- OPERACIONES COMPUESTAS ----");
        System.out.println("9. Asignar microchip a mascota");
        System.out.println("10. Eliminar microchip de mascota");

        System.out.println("\n0. Salir");
        System.out.print("Seleccione una opción: ");
    }
}
