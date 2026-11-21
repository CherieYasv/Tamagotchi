package org.uabc2109545.Modelo;

// Serpiente de cascabel además de lo basico tiene la clase padre, esta tiene:
// "cascabel" (las ganas de sonarlo) "calor" (ganas de sol).
public class Serpiente extends Tamagotchi {
    private int cascabel;
    private int calor;

    // Crea una serpiente con sus niveles iniciales
    public Serpiente(String nombre) {
        super(nombre, 70, 70, 70);
        this.cascabel = 70;
        this.calor = 70;
        limitarAtributos();
    }

    public int getCascabel() {
        return cascabel;
    }

    public int getCalor() {
        return calor;
    }

    @Override
    public String getTipo() {
        return "Serpiente de cascabel";
    }

    @Override
    public String[] getAlimentos() {
        return new String[]{"Ratón", "Huevo", "Lagartija"};
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Cazar", "Deslizarse", "Esconderse en una roca"};
    }

    @Override
    public String[] getAcciones() {
        return new String[]{"Sonar el cascabel", "Tomar el sol"};
    }

    @Override
    public String[] getNombresStats() {
        return new String[]{"hambre", "energia", "felicidad", "cascabel", "calor"};
    }

    // Limita todo entre 0 y 100 y calcula la salud por el promedio de las demas
    @Override
    protected void limitarAtributos() {
        super.limitarAtributos();
        if (cascabel < 0) {
            cascabel = 0;
        }
        if (cascabel > 100) {
            cascabel = 100;
        }
        if (calor < 0) {
            calor = 0;
        }
        if (calor > 100) {
            calor = 100;
        }
        salud = (hambre + energia + felicidad + cascabel + calor) / 5;
    }

    // Cambia cascabel y calor en el modo trampa, ya lo demas lo hace la clase padre
    @Override
    public void ponerStat(String stat, int valor) {
        if (stat.equals("cascabel")) {
            cascabel = valor;
            limitarAtributos();
        } else if (stat.equals("calor")) {
            calor = valor;
            limitarAtributos();
        } else {
            super.ponerStat(stat, valor);
        }
    }

    // Comidas: 1 ratón, 2 huevo, 3 lagartija
    @Override
    public String comer(int alimento) {
        String texto = "Ese alimento no existe.";
        switch (alimento) {
            case 1:
                hambre = hambre + 45;
                energia = energia + 30;
                felicidad = felicidad + 10;
                dormido = false;
                texto = nombre + " se traga el raton entero.";
                break;
            case 2:
                hambre = hambre + 25;
                energia = energia + 15;
                felicidad = felicidad + 10;
                dormido = false;
                texto = nombre + " se come el huevo completo.";
                break;
            case 3:
                hambre = hambre + 35;
                energia = energia + 20;
                felicidad = felicidad + 20;
                dormido = false;
                texto = nombre + " lanzas la lagartija y se la come en el aire.";
                break;
        }
        limitarAtributos();
        return texto;
    }

    // Juegos: 1 cazar, 2 deslizarse, 3 esconderse en una roca
    @Override
    public String jugar(int juego) {
        String texto = "Ese juego no existe.";
        switch (juego) {
            case 1:
                hambre = hambre - 5;
                energia = energia - 10;
                felicidad = felicidad + 15;
                dormido = false;
                texto = nombre + " salio al patio y cazo lo que vio.";
                break;
            case 2:
                hambre = hambre - 10;
                energia = energia - 20;
                felicidad = felicidad + 25;
                dormido = false;
                texto = nombre + " se deslizo por la arena.";
                break;
            case 3:
                hambre = hambre - 3;
                energia = energia - 5;
                felicidad = felicidad + 10;
                dormido = false;
                texto = nombre + " tomo una roca y se escondio ahi.";
                break;
        }
        limitarAtributos();
        return texto;
    }

    //Dormir sube la energía y baja un poco el hambre
    @Override
    public String dormir(int horasDeSueno) {
        dormido = true;
        energia = energia + horasDeSueno * 15;
        hambre = hambre - horasDeSueno * 3;
        limitarAtributos();
        return nombre + " Zzz";
    }

    // Acción propia: sonar el cascabel
    public String sonarCascabel() {
        dormido = false;
        cascabel = cascabel + 30;
        felicidad = felicidad + 5;
        energia = energia - 5;
        limitarAtributos();
        return nombre + " hizo sonar su cascabel";
    }

    // Acción unica de la serpiente: tomar el sol
    public String tomarSol() {
        dormido = false;
        calor = calor + 30;
        energia = energia + 5;
        felicidad = felicidad + 5;
        limitarAtributos();
        return nombre + " se estiro y tomo el sol.";
    }

    // Con el tiempo bajan todos los niveles (en un reptil el hambre baja mas lento)
    @Override
    public void pasarTiempo() {
        hambre = hambre - 2;
        felicidad = felicidad - 2;
        if (dormido) {
            energia = energia + 5;
            if (energia >= 100) {
                dormido = false;
            }
        } else {
            energia = energia - 2;
        }
        cascabel = cascabel - 2;
        calor = calor - 3;
        limitarAtributos();
    }

    // Mensaje según la primera estadística que esté mal
    @Override
    public String getMensaje() {
        if (dormido) {
            return nombre + " duerme Zzz";
        }
        if (hambre < 30) {
            return nombre + " sizea y abre la boca, tiene hambre";
        }
        if (energia < 30) {
            return nombre + " se queda quieto, no tiene fuerzas.";
        }
        if (felicidad < 30) {
            return nombre + " se esconde bajo una roca, está triste.";
        }
        if (cascabel < 30) {
            return nombre + " mueve la cola levemente, quiere sonar su cascabel.";
        }
        if (calor < 30) {
            return nombre + " tiembla de frío, necesita sol.";
        }
        return nombre + " sisea levemente, está contento.";
    }

    // toString que suma lo de la clase padre con lo de la clase hija
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Ganas de sonar el cascabel: " + nivel(cascabel) + "\n"
                + "Ganas de tomar el sol: " + nivel(calor);
    }
}
