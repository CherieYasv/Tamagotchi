package org.uabc2109545.Modelo;

// Las acciones básicas que todo tamagotchi debe saber hacer.
// Cada accion devuelve el texto de como reacciono
public interface TamagotchiActions {

    // Darle de comer. Alimento es 1, 2 o 3
    String comer(int alimento);

    // Jugar. Juego es 1, 2 o 3
    String jugar(int juego);

    // Dormir restaura energía según las horas
    String dormir(int horasDeSueno);

    // true si el promedio de sus estadísticas llegó a 0
    boolean morir();
}
