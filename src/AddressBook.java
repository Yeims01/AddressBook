import java.io.*;
import java.util.*;

import static java.lang.System.*;

public class AddressBook {
    private Map<String, String> contacts;
    private String filename;

    public AddressBook(String filename) {
        this.filename = filename;
        this.contacts = new HashMap<>();
        load();
    }

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            err.println("Error loading contacts: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            err.println("Error saving contacts: " + e.getMessage());
        }
    }

    public void list() {
        out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
    }

    public void delete(String number) {
        contacts.remove(number);
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contacts.csv");

        Scanner scanner = new Scanner(in);
        boolean running = true;
        while (running) {
            out.println("\nMenú:");
            out.println("1. Listar contactos");
            out.println("2. Crear contacto");
            out.println("3. Borrar contacto");
            out.println("4. Guardar y salir");
            out.print("Seleccione una opción: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    out.print("Ingrese el número telefónico: ");
                    String number = scanner.nextLine();
                    out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    out.print("Ingrese el número telefónico a borrar: ");
                    String numberToDelete = scanner.nextLine();
                    addressBook.delete(numberToDelete);
                    break;
                case 4:
                    addressBook.save();
                    out.println("Contactos guardados. Saliendo...");
                    running = false;
                    break;
                default:
                    out.println("Opción inválida. Por favor, seleccione nuevamente.");
            }
        }
        scanner.close();
    }
}
