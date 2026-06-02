package view;

import controller.PersonajeController;
import model.Personaje;

import java.util.List;
import java.util.Scanner;

/**
 * View — solo muestra información y recibe input del usuario.
 * NO accede a la BD. NO contiene lógica de negocio.
 * Llama al Controller para todo lo que necesita procesar.
 */
public class MenuView {

    private final Scanner               scanner    = new Scanner(System.in);
    private final PersonajeController   controller = new PersonajeController();

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========== RPG Manager ==========");
            System.out.println("1. Crear personaje");
            System.out.println("2. Listar personajes");
            System.out.println("3. Buscar personaje por nombre");
            System.out.println("4. Actualizar nivel");
            System.out.println("5. Eliminar personaje");
            System.out.println("6. Ver habilidad especial");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearPersonaje();
                case 2 -> listarPersonajes();
                case 3 -> buscarPersonaje();
                case 4 -> actualizarNivel();
                case 5 -> eliminarPersonaje();
                case 6 -> verHabilidad();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ---- opciones del menú ----

    private void crearPersonaje() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.println("Tipo: 1-Guerrero  2-Mago  3-Arquero");
        int t = leerEntero();
        String tipo = switch (t) {
            case 1 -> "Guerrero";
            case 2 -> "Mago";
            case 3 -> "Arquero";
            default -> "Guerrero";
        };

        System.out.print("Nivel (1-100): ");
        int nivel = leerEntero();

        controller.crearPersonaje(nombre, tipo, nivel);
    }

    private void listarPersonajes() {
        List<Personaje> lista = controller.listarPersonajes();
        if (lista.isEmpty()) {
            System.out.println("No hay personajes registrados.");
        } else {
            System.out.println("\n--- Lista de personajes ---");
            lista.forEach(System.out::println);
        }
    }

    private void buscarPersonaje() {
        System.out.print("Nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        Personaje p = controller.buscarPorNombre(nombre);
        if (p != null) System.out.println("Encontrado: " + p);
    }

    private void actualizarNivel() {
        System.out.print("ID del personaje: ");
        int id = leerEntero();
        System.out.print("Nuevo nivel: ");
        int nivel = leerEntero();
        controller.actualizarNivel(id, nivel);
    }

    private void eliminarPersonaje() {
        System.out.print("ID del personaje a eliminar: ");
        int id = leerEntero();
        controller.eliminarPersonaje(id);
    }

    private void verHabilidad() {
        System.out.print("Nombre del personaje: ");
        String nombre = scanner.nextLine().trim();
        Personaje p = controller.buscarPorNombre(nombre);
        if (p != null) System.out.println(p.habilidadEspecial());
    }

    // ---- utilidad para leer enteros sin que explote ----
    private int leerEntero() {
        while (true) {
            try {
                int n = Integer.parseInt(scanner.nextLine().trim());
                return n;
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un número válido: ");
            }
        }
    }
}
