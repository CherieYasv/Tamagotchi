package org.uabc2109545.Controlador;

import org.uabc2109545.Modelo.Coleccion;
import org.uabc2109545.Modelo.Dragon;
import org.uabc2109545.Modelo.Pulpo;
import org.uabc2109545.Modelo.Serpiente;
import org.uabc2109545.Modelo.Tamagotchi;
import org.uabc2109545.Vista.VistaConsola;

public class Controlador {
    private VistaConsola vista;
    private Coleccion coleccion = new Coleccion();
    private Tamagotchi activo;     // <-- El tamagotchi que se esta usando en este momento
    private boolean salir = false;

    //Este es para JavaFX. JavaFX no ocupa vista en terminal.
    public Controlador() {
    }

    //Este es para la vista en terminal.
    public Controlador(VistaConsola vista) {
        this.vista = vista;
    }

    public Coleccion getColeccion() {
        return coleccion;
    }

    public Tamagotchi getActivo() {
        return activo;
    }

    // Aqui se cmbia el tamagotchi que se está usando
    public void setActivo(int posicionTamagotchi) {
        if (posicionTamagotchi >= 0 && posicionTamagotchi < coleccion.tamano()) {
            activo = coleccion.obtener(posicionTamagotchi);
        }
    }

    // Crea un tamagotchi y lo deja activo en el menu
    //1. Dragon
    //2. Serpiente
    //3. Pulpo
    public boolean crearTamagotchi(int tipo, String nombre) {
        String nombreFinal = nombre.trim();
        if (nombreFinal.isEmpty()) {
            nombreFinal = "Daisy";
        }

        Tamagotchi nuevo;
        switch (tipo) {
            case 1:
                nuevo = new Dragon(nombreFinal);
                break;
            case 2:
                nuevo = new Serpiente(nombreFinal);
                break;
            case 3:
                nuevo = new Pulpo(nombreFinal);
                break;
            default:
                return false;
        }
        coleccion.agregar(nuevo);
        activo = nuevo;
        return true;
    }

    // Darle de comer al tamal de gotchi activo
    public String comer(int alimento) {
        return activo.comer(alimento);
    }

    // Jugar con el tamal de gotchi activo
    public String jugar(int juego) {
        return activo.jugar(juego);
    }

    // Dormir con el tamal de gotchi activo
    public String dormir(int horas) {
        return activo.dormir(horas);
    }

    // AQUI SE USAN LOS LANZADORES QUE MENCIONO LA PROFESORA EN CLASE.
    //El comando "instanceof" es un operador que verifica que la variable de la izquierda sea del tipo de la derecha.
    //No es lo mismo usar equals(), porque equals evalua el contenido y todo lo que contiene la clase.
    //"instanceof" solo evalua el tipo de clase.
    public String accionEspecial(int cual) {
        String texto = "";
        if (activo instanceof Dragon) {
            Dragon dragon = (Dragon) activo;
            if (cual == 1) {
                texto = dragon.volar();
            } else {
                texto = dragon.rascarPanza();
            }
        } else if (activo instanceof Serpiente) {
            Serpiente serpiente = (Serpiente) activo;
            if (cual == 1) {
                texto = serpiente.sonarCascabel();
            } else {
                texto = serpiente.tomarSol();
            }
        } else if (activo instanceof Pulpo) {
            Pulpo pulpo = (Pulpo) activo;
            if (cual == 1) {
                texto = pulpo.lanzarTinta();
            } else {
                texto = pulpo.camuflarse();
            }
        }
        return texto;
    }

    // Pasa el tiempo para todos por igual. Manda un mensaje que alguien murio
    public String pasarTiempo() {
        coleccion.pasarTiempo();
        return revisarMuertes();
    }

    // Quita de pantalla a los que murieron y si ya ninguno esta, regresa la pantalla 0 tamagotchis
    public String revisarMuertes() {
        String aviso = coleccion.quitarMuertos();

        if (!coleccion.contiene(activo)) {
            if (coleccion.estaVacia()) {
                activo = null;
            } else {
                activo = coleccion.obtener(0);
            }
        }
        return aviso;
    }

    // Modo trampa para cambiar estadisticas de un tamal en especifico
    public String trampa(int posicionTamagotchi, String estadisticas, int valor) {
        coleccion.obtener(posicionTamagotchi).ponerStat(estadisticas, valor);
        String aviso = revisarMuertes();
        if (aviso.isEmpty()) {
            aviso = "Listo, estadística cambiada.";
        }
        return aviso;
    }

    // Arranca el programa en la terminal
    public void iniciar() {
        while (!salir) {
            int op = vista.mostrarMenuInicial();
            if (op == 1) {
                crearDesdeVista();
                menuJuego();   // regresa aquí si salen o si mueren todos
            } else if (op == 2) {
                salir = true;
            } else {
                vista.mostrarDatos("Opción no válida.");
            }
        }
        vista.mostrarDatos("Saliendo del juego...");
    }

    // Pregunta el tipo y el nombre y crea el tamagotchi
    private void crearDesdeVista() {
        String[] tipos = {"Dragón", "Serpiente de cascabel", "Pulpo"};
        int tipo = vista.elegirDeLista("Que tamagotchi quieres?", tipos);
        if (tipo == 0) {
            return;
        }
        if (tipo < 1 || tipo > tipos.length) {
            vista.mostrarDatos("Opcion no valida.");
            return;
        }
        String nombre = vista.leerCadenas("Ponle un nombre:");
        crearTamagotchi(tipo, nombre);
        vista.mostrarDatos("-> ¡" + activo.getNombre() + " ha nacido");
    }

    // Menú que se repite mientras haya un minimo un tamagotchi activo
    private void menuJuego() {
        while (activo != null && !salir) {
            vista.mostrarEstado(activo);
            int op = vista.mostrarMenuAcciones(activo, coleccion);
            boolean pasaTiempo = false;   // true si la acción gasta un turno

            switch (op) {
                case 1:
                    int alimento = vista.elegirDeLista("Que le das de comer?", activo.getAlimentos());
                    if (alimento >= 1 && alimento <= 3) {
                        vista.mostrarDatos(">> " + comer(alimento));
                        pasaTiempo = true;
                    }
                    break;
                case 2:
                    int juego = vista.elegirDeLista("A que juegan?", activo.getJuegos());
                    if (juego >= 1 && juego <= 3) {
                        vista.mostrarDatos(">> " + jugar(juego));
                        pasaTiempo = true;
                    }
                    break;
                case 3:
                    int horas = vista.leerNumeros("Cuantas horas duerme? (1 a 8)");
                    if (horas >= 1 && horas <= 8) {
                        vista.mostrarDatos("--> " + dormir(horas));
                        pasaTiempo = true;
                    } else {
                        vista.mostrarDatos("Horas no válidas.");
                    }
                    break;
                case 4:
                case 5:
                    vista.mostrarDatos(">> " + accionEspecial(op - 3));
                    pasaTiempo = true;
                    break;
                case 6:
                    vista.mostrarDatos(">> " + activo.getNombre() + " espera tranquilamente.");
                    pasaTiempo = true;
                    break;
                case 7:
                    int posicionTamagotchi = vista.elegirDeLista("Cual tamagotchi quieres usar?", nombresMascotas());
                    if (posicionTamagotchi >= 1 && posicionTamagotchi <= coleccion.tamano()) {
                        setActivo(posicionTamagotchi - 1);
                    }
                    break;
                case 8:
                    crearDesdeVista();
                    break;
                case 9:
                    modoTrampa();
                    break;
                case 10:
                    vista.mostrarDatos(coleccion.listaJuguetes());
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    vista.mostrarDatos("Opción no válida.");
            }

            if (pasaTiempo) {
                String aviso = pasarTiempo();
                if (!aviso.isEmpty()) {
                    vista.mostrarDatos(">> " + aviso);
                }
            }
        }
    }

    // Modo trampa en la terminal: elegir tamagotchi, estadística y valor
    private void modoTrampa() {
        int m = vista.elegirDeLista("MODO TRAMPA ---> a cual tamagotchi?", nombresMascotas());
        if (m < 1 || m > coleccion.tamano()) {
            return;
        }
        String[] estadisticas = coleccion.obtener(m - 1).getNombresStats();
        int s = vista.elegirDeLista("Que estadistica quieres cambiar? (nota: en enojo 100 = furioso, en las demás 100 = bien)", estadisticas);
        if (s < 1 || s > estadisticas.length) {
            return;
        }
        int valor = vista.leerNumeros("Nuevo valor (0 a 100):");
        vista.mostrarDatos("---> " + trampa(m - 1, estadisticas[s - 1], valor));
    }

    // Lista con "Nombre (Tipo)" de cada tamagotchi que aparece en pantalla
    private String[] nombresMascotas() {
        String[] lista = new String[coleccion.tamano()];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = coleccion.obtener(i).getNombre() + " (" + coleccion.obtener(i).getTipo() + ")";
        }
        return lista;
    }
}
