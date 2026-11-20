package org.uabc2109545.Modelo;

// Pulpo: además de lo básico tiene "tinta" (ganas de lanzarla) y "camuflaje" (ganas de camuflarse).
public class Pulpo extends Tamagotchi {
    private int tinta;
    private int camuflaje;

    // Crea un pulpo con sus niveles iniciales
    public Pulpo(String nombre) {
        super(nombre, 70, 75, 75);
        this.tinta = 70;
        this.camuflaje = 70;
        limitarAtributos();
    }

    public int getTinta() {
        return tinta;
    }

    public int getCamuflaje() {
        return camuflaje;
    }

    @Override
    public String getTipo() {
        return "Pulpo";
    }

    @Override
    public String[] getAlimentos() {
        return new String[]{"Cangrejo", "Camarón", "Alga"};
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Meterse en un frasco", "Explorar su pecera", "Atrapar burbujas"};
    }

    @Override
    public String[] getAcciones() {
        return new String[]{"Lanzar tinta", "Camuflarse"};
    }

    @Override
    public String[] getNombresStats() {
        return new String[]{"hambre", "energia", "felicidad", "tinta", "camuflaje"};
    }

    // Limita todo entre 0 y 100 y calcula la salud (promedio)
    @Override
    protected void limitarAtributos() {
        super.limitarAtributos();
        if (tinta < 0) {
            tinta = 0;
        }
        if (tinta > 100) {
            tinta = 100;
        }
        if (camuflaje < 0) {
            camuflaje = 0;
        }
        if (camuflaje > 100) {
            camuflaje = 100;
        }
        salud = (hambre + energia + felicidad + tinta + camuflaje) / 5;
    }

    // Cambia tinta y camuflaje en el modo trampa, ya lo demás lo hace la clase padre
    @Override
    public void ponerStat(String estadistica, int valor) {
        if (estadistica.equals("tinta")) {
            tinta = valor;
            limitarAtributos();
        } else if (estadistica.equals("camuflaje")) {
            camuflaje = valor;
            limitarAtributos();
        } else {
            super.ponerStat(estadistica, valor);
        }
    }

    //comidas: 1 cangrejo, 2 camarón, 3 alga
    @Override
    public String comer(int alimento) {
        String texto = "Ese alimento no existe.";
        switch (alimento) {
            case 1:
                hambre = hambre + 35;
                energia = energia + 25;
                felicidad = felicidad + 15;
                dormido = false;
                texto = nombre + " atrapo el cangrejo con sus tentáculos.";
                break;
            case 2:
                hambre = hambre + 25;
                energia = energia + 15;
                felicidad = felicidad + 20;
                dormido = false;
                texto = nombre + " se comió el camarón.";
                break;
            case 3:
                hambre = hambre + 15;
                energia = energia + 10;
                felicidad = felicidad + 5;
                dormido = false;
                texto = nombre + " muerde el alga.";
                break;
        }
        limitarAtributos();
        return texto;
    }

    //juegos: 1 frasco, 2 pecera, 3 burbujas
    @Override
    public String jugar(int juego) {
        String texto = "Ese juego no existe.";
        switch (juego) {
            case 1:
                hambre = hambre - 8;
                energia = energia - 15;
                felicidad = felicidad + 25;
                dormido = false;
                texto = nombre + " se mete en el frasco de manera juguetona.";
                break;
            case 2:
                hambre = hambre - 5;
                energia = energia - 10;
                felicidad = felicidad + 15;
                dormido = false;
                texto = nombre + " explora su pecera.";
                break;
            case 3:
                hambre = hambre - 10;
                energia = energia - 20;
                felicidad = felicidad + 30;
                dormido = false;
                texto = nombre + " atrapó burbujas.";
                break;
        }
        limitarAtributos();
        return texto;
    }

    //dormir: sube la energía y baja un poco el hambre
    @Override
    public String dormir(int horasDeSueno) {
        dormido = true;
        energia = energia + horasDeSueno * 15;
        hambre = hambre - horasDeSueno * 3;
        limitarAtributos();
        return nombre + " se acomoda y duerme Zzz...";
    }

    // Accion unica del pulpo: lanzar tinta
    public String lanzarTinta() {
        dormido = false;
        tinta = tinta + 30;
        felicidad = felicidad + 5;
        energia = energia - 5;
        limitarAtributos();
        return nombre + " lanza tinta negra.";
    }

    // Acción unica del pulpo: camuflajearse
    public String camuflarse() {
        dormido = false;
        camuflaje = camuflaje + 30;
        felicidad = felicidad + 5;
        energia = energia - 5;
        limitarAtributos();
        return nombre + " cambió de color y desaparece.";
    }

    // Con el tiempo bajan todos los niveles 
    @Override
    public void pasarTiempo() {
        hambre = hambre - 3;
        felicidad = felicidad - 2;
        if (dormido) {
            energia = energia + 5;
            if (energia >= 100) {
                dormido = false;
            }
        } else {
            energia = energia - 3;
        }
        tinta = tinta - 2;
        camuflaje = camuflaje - 2;
        limitarAtributos();
    }

    // Mensaje según la primera estadística que esté mal
    @Override
    public String getMensaje() {
        if (dormido) {
            return nombre + " Zzz";
        }
        if (hambre < 30) {
            return nombre + " Se angustia un poco, tiene hambre.";
        }
        if (energia < 30) {
            return nombre + " esta un poco desanimado, esta muy cansado.";
        }
        if (felicidad < 30) {
            return nombre + " diambula levemente, esta triste.";
        }
        if (tinta < 30) {
            return nombre + " mira a todos lados de manera triste, quiere lanzar tinta.";
        }
        if (camuflaje < 30) {
            return nombre + " mira los colores con tristeza, quiere camuflajearse.";
        }
        return nombre + " cambia de colores muy contento.";
    }

    // Agrega tinta y camuflaje al estado
    @Override
    public String getEstado() {
        return super.getEstado() + "\n"
                + "Ganas de lanzar tinta: " + nivel(tinta) + "\n"
                + "Ganas de camuflarse: " + nivel(camuflaje);
    }
}
