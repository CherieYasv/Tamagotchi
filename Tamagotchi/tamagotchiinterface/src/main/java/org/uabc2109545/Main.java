package org.uabc2109545;

import org.uabc2109545.Controlador.Controlador;
import org.uabc2109545.Vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        Controlador control = new Controlador(vista);
        control.iniciar();
    }
}
