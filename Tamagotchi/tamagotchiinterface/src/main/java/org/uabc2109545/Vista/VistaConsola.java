package org.uabc2109545.Vista;

import java.util.Scanner;

import org.uabc2109545.Modelo.Coleccion;
import org.uabc2109545.Modelo.Tamagotchi;

// Vista de terminal solo muestra cosas y lee lo que escribe el usuario
public class VistaConsola {
    private Scanner teclado = new Scanner(System.in);

    // Muestra un texto
    public void mostrarDatos(String cadena) {
        System.out.println(cadena);
    }

    // Pide un texto
    public String leerCadenas(String cadena) {
        System.out.println(cadena);
        return teclado.nextLine();
    }

    // Pide un número, si escriben otra cosa regresa cero (NumberFormatException es eso, la forma rapida de evaluar una falla)
    public int leerNumeros(String cadena) {
        System.out.println(cadena);
        try {
            return Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Menú de inicio
    public int mostrarMenuInicial() {
        System.out.println();
        System.out.println("---- TAMAGOTCHIS ----");
        System.out.println("1. Elegir un tamagotchi");
        System.out.println("2. Salir");
        return leerNumeros("Elige una opción:");
    }

    // Muestra una lista de las operaciones
    public int elegirDeLista(String titulo, String[] opciones) {
        System.out.println();
        System.out.println(titulo);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        System.out.println("0. Cancelar");
        return leerNumeros("Elige una opción:");
    }

    // Muestra el estado del tamagotchi, animo y mensaje
    public void mostrarEstado(Tamagotchi t) {
        System.out.println();
        System.out.println("------------------------------------");
        System.out.println(t.toString());
        System.out.println("Animo: " + t.getAnimo());
        System.out.println();
        System.out.println("Mensaje: " + t.getMensaje());
    }

    // Menú de acciones del tamagotchi activo y abajo aparecen todos los tamagotchis disponibles
    public int mostrarMenuAcciones(Tamagotchi activo, Coleccion coleccion) {
        String[] especiales = activo.getAcciones();

        System.out.println();
        System.out.println("---- Acciones de " + activo.getNombre() + " ----");
        System.out.println("1. Dar de comer");
        System.out.println("2. Jugar");
        System.out.println("3. Dormir");
        System.out.println("4. " + especiales[0]);
        System.out.println("5. " + especiales[1]);
        System.out.println("6. Esperar un rato");

        System.out.println();
        System.out.println("---- Tus tamagotchis (> es el que estás usando) ----");
        for (int i = 0; i < coleccion.tamano(); i++) {
            Tamagotchi m = coleccion.obtener(i);
            String marca = "   ";
            if (m == activo) {
                marca = " > ";
            }
            System.out.println(marca + m.getNombre() + " (" + m.getTipo() + ") - " + m.getAnimo());
        }
        System.out.println("7. Cambiar de tamagotchi");
        System.out.println("8. Crear otro tamagotchi");
        System.out.println("9. Modo trampa");
        System.out.println("10. Ver todos mis tamagotchis");
        System.out.println("0. Salir");
        return leerNumeros("Elige una opción:");
    }
}
