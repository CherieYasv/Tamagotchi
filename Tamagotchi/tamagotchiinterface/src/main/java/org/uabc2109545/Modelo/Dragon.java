package org.uabc2109545.Modelo;

// Dragón. Lo nuevo que tiene es "vuelo" y "enojo" (100 es furioso).
public class Dragon extends Tamagotchi {
    private int vuelo;
    private int enojo;

    // Crea un dragón con sus niveles iniciales
    public Dragon(String nombre) {
        super(nombre, 70, 80, 80);
        this.vuelo = 70;
        this.enojo = 20;
        limitarAtributos();
    }

    public int getVuelo() {
        return vuelo;
    }

    public int getEnojo() {
        return enojo;
    }

    @Override
    public String getTipo() {
        return "Dragón";
    }

    @Override
    public String[] getAlimentos() {
        return new String[]{"Carne asada", "Fruta", "Dulce"};
    }

    @Override
    public String[] getJuegos() {
        return new String[]{"Jugar", "Volar", "Perseguir su cola"};
    }

    @Override
    public String[] getAcciones() {
        return new String[]{"Volar", "Rascar la panza"};
    }

    @Override
    public String[] getNombresStats() {
        return new String[]{"hambre", "energia", "felicidad", "vuelo", "enojo"};
    }

    // Limita todo entre 0 y 100 y calcula la salud (el enojo cuenta al revés)
    @Override
    protected void limitarAtributos() {
        super.limitarAtributos();
        if (vuelo < 0) {
            vuelo = 0;
        }
        if (vuelo > 100) {
            vuelo = 100;
        }
        if (enojo < 0) {
            enojo = 0;
        }
        if (enojo > 100) {
            enojo = 100;
        }
        salud = (hambre + energia + felicidad + vuelo + (100 - enojo)) / 5;
    }

    // Cambia vuelo y enojo en el modo trampa. Ya lo demas lo hace el modo trampa
    @Override
    public void ponerStat(String stat, int valor) {
        if (stat.equals("vuelo")) {
            vuelo = valor;
            limitarAtributos();
        } else if (stat.equals("enojo")) {
            enojo = valor;
            limitarAtributos();
        } else {
            super.ponerStat(stat, valor);
        }
    }

    // Comida segun el alimento.
    @Override
    public String comer(int alimento) {
        String texto = "Ese alimento no existe.";
        switch (alimento) {
            case 1:
                hambre = hambre + 40;
                energia = energia + 20;
                felicidad = felicidad + 15;
                dormido = false;
                texto = nombre + " devoró la carne asada.";
                break;
            case 2:
                hambre = hambre + 20;
                energia = energia + 10;
                felicidad = felicidad + 5;
                dormido = false;
                texto = nombre + " mordio la fruta.";
                break;
            case 3:
                hambre = hambre + 10;
                energia = energia + 15;
                felicidad = felicidad + 25;
                dormido = false;
                texto = nombre + " se comió el dulce y se puso feliz.";
                break;
        }
        limitarAtributos();
        return texto;
    }

    // Juegos: 1 jugar, 2 volar, 3 perseguir su cola
    @Override
    public String jugar(int juego) {
        String texto = "Ese juego no existe.";
        switch (juego) {
            case 1:
                hambre = hambre - 10;
                energia = energia - 20;
                felicidad = felicidad + 20;
                dormido = false;
                texto = nombre + " estuvo jugando un rato.";
                break;
            case 2:
                hambre = hambre - 15;
                energia = energia - 30;
                felicidad = felicidad + 30;
                dormido = false;
                texto = nombre + " se fue a volar.";
                break;
            case 3:
                hambre = hambre - 5;
                energia = energia - 10;
                felicidad = felicidad + 10;
                dormido = false;
                texto = nombre + " da vueltas persiguiendo su cola.";
                break;
        }
        limitarAtributos();
        return texto;
    }

    // Duerme: sube la energía y baja un poco el hambre
    @Override
    public String dormir(int horasDeSueno) {
        dormido = true;
        energia = energia + horasDeSueno * 15;
        hambre = hambre - horasDeSueno * 3;
        limitarAtributos();
        return nombre + "Zzz";
    }

    // Acción de la subclase dragon, volar.
    public String volar() {
        dormido = false;
        vuelo = vuelo + 30;
        energia = energia - 15;
        felicidad = felicidad + 10;
        hambre = hambre - 5;
        enojo = enojo - 5;
        limitarAtributos();
        return nombre + " vuela por el cielo.";
    }

    // Accion de la subclase dragon, rascarse la panza
    public String rascarPanza() {
        dormido = false;
        enojo = enojo - 30;
        felicidad = felicidad + 10;
        limitarAtributos();
        return nombre + " baila de felicidad por su panza llena.";
    }

    // Con el tiempo baja el hambre, la energía, la felicidad y el vuelo y sube el enojo
    @Override
    public void pasarTiempo() {
        hambre = hambre - 4;
        felicidad = felicidad - 2;
        if (dormido) {
            energia = energia + 5;
            if (energia >= 100) {
                dormido = false;
            }
        } else {
            energia = energia - 3;
        }
        vuelo = vuelo - 2;
        enojo = enojo + 3;
        limitarAtributos();
    }

    // Mensaje según la primera estadística que esté mal
    @Override
    public String getMensaje() {
        if (dormido) {
            return nombre + " ronca Zzz";
        }
        if (hambre < 30) {
            return nombre + " esta triste mientras su estomago ruge.";
        }
        if (energia < 30) {
            return nombre + " bosteza y apenas se sostiene. Tiene mucho sueño.";
        }
        if (felicidad < 30) {
            return nombre + " se acuesta y suelta triste humo de su nariz.";
        }
        if (vuelo < 30) {
            return nombre + " mira al cielo con tristeza. Quiere volar.";
        }
        if (enojo > 70) {
            return nombre + " gruñe y echa humo negro por la nariz, esta enojado.";
        }
        return nombre + " mueve la cola contento, todo está bien.";
    }

    // Agrega vuelo y enojo al estado
    @Override
    public String getEstado() {
        return super.getEstado() + "\n"
                + "Ganas de volar: " + nivel(vuelo) + "\n"
                + "Enojo: " + nivel(enojo);
    }
}
