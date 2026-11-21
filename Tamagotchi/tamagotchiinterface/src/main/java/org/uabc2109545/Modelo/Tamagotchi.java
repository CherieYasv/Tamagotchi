package org.uabc2109545.Modelo;

// Clase padre guarda lo que todos los tamagotchis tienen en común.
// Todos los niveles van de 0 a 100, 100 significa bien. En hambre 100 significa lleno
public abstract class Tamagotchi implements TamagotchiActions {
    protected String nombre;
    protected int hambre;
    protected int energia;
    protected int felicidad;
    protected int salud;//<---- promedio de todas las estadísticas (lo calcula cada hijo)
    protected boolean dormido;

    // Crea el tamagotchi con sus niveles iniciales
    public Tamagotchi(String nombre, int hambre, int energia, int felicidad) {
        this.nombre = nombre;
        this.hambre = hambre;
        this.energia = energia;
        this.felicidad = felicidad;
        this.dormido = false;
    }

    // Gettes y setters:
    public String getNombre() {
        return nombre;
    }

    public int getHambre() {
        return hambre;
    }

    public int getEnergia() {
        return energia;
    }

    public int getFelicidad() {
        return felicidad;
    }

    public int getSalud() {
        return salud;
    }

    public boolean isDormido() {
        return dormido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Aqui va la parte abstracte. Cada subclase lo llama a su manera.

    // Nombre del tipo, por ejemplo: ("Dragón", "Pulpo"), etc
    public abstract String getTipo();

    // Nombres de los 3 alimentos que acepta
    public abstract String[] getAlimentos();

    // Nombres de los 3 juegos que tiene
    public abstract String[] getJuegos();

    // Nombres de sus 2 acciones especiales
    public abstract String[] getAcciones();

    // Nombres de todas sus estadísticas (para el modo trampa)
    public abstract String[] getNombresStats();

    // Mensaje que suelta según sus estadísticas (ej. suspira de hambre)
    public abstract String getMensaje();

    // Hace que pase un poco de tiempo: las estadísticas cambian solas
    public abstract void pasarTiempo();

    //Cosas que todos usan igual

    // Mantiene las estadísticas comunes entre 0 y 100. Las subclases agregan las suya y sacan promedios de la salud
    protected void limitarAtributos() {
        if (hambre < 0) {
            hambre = 0;
        }
        if (hambre > 100) {
            hambre = 100;
        }
        if (energia < 0) {
            energia = 0;
        }
        if (energia > 100) {
            energia = 100;
        }
        if (felicidad < 0) {
            felicidad = 0;
        }
        if (felicidad > 100) {
            felicidad = 100;
        }
    }

    // Cambia las estadisticas con el modo trampa. Los hijos le agregan cosas.
    public void ponerStat(String estadistica, int valor) {
        switch (estadistica) {
            case "hambre":
                hambre = valor;
                break;
            case "energia":
                energia = valor;
                break;
            case "felicidad":
                felicidad = valor;
                break;
        }
        limitarAtributos();
    }

    // Convierte un número en un texto de nivel (para que se entienda mas)
    protected String nivel(int valor) {
        if (valor < 30) {
            return "nivel bajo";
        }
        if (valor < 70) {
            return "nivel medio";
        }
        return "nivel alto";
    }

    // Aviso general de la slaud: bien, necesita atención o mal
    public String getAnimo() {
        if (salud >= 60) {
            return "Se siente bien";
        }
        if (salud >= 30) {
            return "Necesita atención";
        }
        return "Está mal";
    }

    // Muere cuando el promedio de sus estadísticas llega a 0
    @Override
    public boolean morir() {
        return salud <= 0;
    }

    // toString con lo que todos los tamagotchis tienen. Las subclases agregan lo suyo
    @Override
    public String toString() {
        String durmiendo = "no";
        if (dormido) {
            durmiendo = "sí";
        }

        String textoHambre = "llena";
        if (hambre < 30) {
            textoHambre = "mucha hambre";
        } else if (hambre < 60) {
            textoHambre = "poca hambre";
        } else if (hambre < 90) {
            textoHambre = "no tiene hambre";
        }

        return nombre + " (" + getTipo() + ")\n"
                + "Durmiendo: " + durmiendo + "\n"
                + "Salud: " + nivel(salud) + "\n"
                + "Hambre: " + textoHambre + "\n"
                + "Energía: " + nivel(energia) + "\n"
                + "Felicidad: " + nivel(felicidad);
    }
}
