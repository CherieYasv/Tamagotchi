package org.uabc2109545.Modelo;

import java.util.ArrayList;

// Colección: guarda todos los tamagotchis (mis juguetes) en un ArrayList.
public class Coleccion {
    private ArrayList<Tamagotchi> misJuguetes;

    // Crea la colección vacía
    public Coleccion() {
        misJuguetes = new ArrayList<>();
    }

    // Agrega un tamagotchi a la colección
    public void agregar(Tamagotchi t) {
        misJuguetes.add(t);
    }

    // Regresa el tamagotchi de esa posición (0 es el primero)
    public Tamagotchi obtener(int posicion) {
        return misJuguetes.get(posicion);
    }

    // Cuántos tamagotchis hay
    public int tamano() {
        return misJuguetes.size();
    }

    // true si no hay ninguno
    public boolean estaVacia() {
        return misJuguetes.isEmpty();
    }

    // true si ese tamagotchi sigue en la colección
    public boolean contiene(Tamagotchi t) {
        return misJuguetes.contains(t);
    }

    // Pasa el tiempo para todos los tamagotchis por igual
    public void pasarTiempo() {
        misJuguetes.forEach(t -> t.pasarTiempo());
    }

    // Quita a los que murieron (salud 0) y regresa el aviso de quién murió
    public String quitarMuertos() {
        String aviso = "";
        for (int i = 0; i < misJuguetes.size(); i++) {
            Tamagotchi m = misJuguetes.get(i);
            if (m.morir()) {
                aviso = aviso + m.getNombre() + " (" + m.getTipo() + ") ha muerto :(\n";
            }
        }
        misJuguetes.removeIf(t -> t.morir());
        return aviso.trim();
    }

    // Regresa el toString de todos los tamagotchis, uno tras otro
    public String listaJuguetes() {
        StringBuffer cadena = new StringBuffer();
        for (Tamagotchi t : misJuguetes) {
            cadena.append(t);
            cadena.append("\n\n");
        }
        return cadena.toString();
    }
}
