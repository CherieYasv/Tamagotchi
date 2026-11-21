package org.uabc2109545.Vista;

import org.uabc2109545.Controlador.Controlador;
import org.uabc2109545.Modelo.Coleccion;
import org.uabc2109545.Modelo.Tamagotchi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

// Vista JavaFX
public class VistaFX extends Application {
    private Controlador controlador = new Controlador();

    private TextField campoNombre = new TextField();
    private FlowPane listaMascotas = new FlowPane(8, 8);
    private Label burbuja = new Label("Escribe un nombre y elige un tamagotchi para empezar");
    private Label estado = new Label();
    private VBox panelAcciones = new VBox(8);
    private Button[] botonesComida = new Button[3];
    private Button[] botonesJuego = new Button[3];
    private Button botonDormir = new Button("Dormir");
    private Button botonEspecial1 = new Button();
    private Button botonEspecial2 = new Button();

    // Arma la ventana
    @Override
    public void start(Stage ventana) {
        //Botones y cosas de arriba, como los botones para crear un tamagotchi de x animal
        Button botonDragon = new Button("Dragón");
        botonDragon.setOnAction(e -> crear(1));
        Button botonSerpiente = new Button("Serpiente");
        botonSerpiente.setOnAction(e -> crear(2));
        Button botonPulpo = new Button("Pulpo");
        botonPulpo.setOnAction(e -> crear(3));
        campoNombre.setPromptText("Nombre");

        HBox filaCrear = new HBox(8, new Label("Nuevo:"), campoNombre, botonDragon, botonSerpiente, botonPulpo);
        filaCrear.setAlignment(Pos.CENTER_LEFT);
        VBox arriba = new VBox(8, filaCrear, new Label("Tus tamagotchis (elige con cuál jugar):"), listaMascotas);
        arriba.setPadding(new Insets(10));

        //Mensaje del centro con el estado de los tamagotchis
        burbuja.setWrapText(true);
        burbuja.setMaxWidth(380);
        burbuja.setMinHeight(90);
        burbuja.setAlignment(Pos.CENTER);
        burbuja.setTextAlignment(TextAlignment.CENTER);
        burbuja.setStyle("-fx-background-color: white; -fx-border-color: black; " + "-fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 10; -fx-font-size: 15;");

        estado.setStyle("-fx-font-family: monospace;");
        VBox centro = new VBox(15, burbuja, estado);
        centro.setAlignment(Pos.TOP_CENTER);
        centro.setPadding(new Insets(20));

        //Seccion de los botones de abajo (como los de comer, jugar, etc)
        HBox filaComer = new HBox(8, new Label("Comer:"));
        HBox filaJugar = new HBox(8, new Label("Jugar:"));
        for (int i = 0; i < 3; i++) {
            final int num = i + 1;
            botonesComida[i] = new Button();
            botonesComida[i].setOnAction(e -> hacer(controlador.comer(num)));
            botonesJuego[i] = new Button();
            botonesJuego[i].setOnAction(e -> hacer(controlador.jugar(num)));
            filaComer.getChildren().add(botonesComida[i]);
            filaJugar.getChildren().add(botonesJuego[i]);
        }
        botonDormir.setOnAction(e -> hacer(controlador.dormir(3)));
        botonEspecial1.setOnAction(e -> hacer(controlador.accionEspecial(1)));
        botonEspecial2.setOnAction(e -> hacer(controlador.accionEspecial(2)));
        HBox filaOtras = new HBox(8, botonDormir, botonEspecial1, botonEspecial2);
        panelAcciones.getChildren().addAll(filaComer, filaJugar, filaOtras);
        panelAcciones.setPadding(new Insets(10));

        //Seccion de la ventana
        BorderPane raiz = new BorderPane();
        raiz.setTop(arriba);
        raiz.setCenter(centro);
        raiz.setBottom(panelAcciones);
        ventana.setTitle("Tamagotchis");
        ventana.setScene(new Scene(raiz, 560, 520));
        ventana.show();
        refrescar();

        // Cada 3 segundos pasa el tiempo y el tamagotchi suelta su mensaje
        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            String aviso = controlador.pasarTiempo();
            if (!aviso.isEmpty()) {
                burbuja.setText(aviso);
            } else if (controlador.getActivo() != null) {
                burbuja.setText(controlador.getActivo().getMensaje());
            }
            refrescar();
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }

    // Crea un tamagotchi nuevo con el usuario escriba
    private void crear(int tipo) {
        controlador.crearTamagotchi(tipo, campoNombre.getText());
        campoNombre.clear();
        burbuja.setText("¡Hola! Soy " + controlador.getActivo().getNombre());
        refrescar();
    }

    // Cambia de tamagotchi
    private void elegir(int pos) {
        controlador.setActivo(pos);
        burbuja.setText(controlador.getActivo().getMensaje());
        refrescar();
    }

    // Muestra en el mensaje central la reacción del tamagotchi
    private void hacer(String reaccion) {
        burbuja.setText(reaccion);
        refrescar();
    }

    // Vuelve a dibujar botones y estado con los datos actuales
    private void refrescar() {
        // botones con los tamagotchis disponibles
        Coleccion coleccion = controlador.getColeccion();
        listaMascotas.getChildren().clear();
        for (int i = 0; i < coleccion.tamano(); i++) {
            final int pos = i;
            Tamagotchi m = coleccion.obtener(i);
            Button boton = new Button(m.getNombre() + " (" + m.getTipo() + ")");
            if (m == controlador.getActivo()) {
                boton.setStyle("-fx-font-weight: bold; -fx-border-color: black;");
            }
            boton.setOnAction(e -> elegir(pos));
            listaMascotas.getChildren().add(boton);
        }

        Tamagotchi t = controlador.getActivo();
        panelAcciones.setDisable(t == null);
        if (t == null) {
            estado.setText("No hay ningún tamagotchi.");
            return;
        }

        estado.setText(t.toString() + "\nÁnimo: " + t.getAnimo());
        String[] comidas = t.getAlimentos();
        String[] juegos = t.getJuegos();
        for (int i = 0; i < 3; i++) {
            botonesComida[i].setText(comidas[i]);
            botonesJuego[i].setText(juegos[i]);
        }
        botonEspecial1.setText(t.getAcciones()[0]);
        botonEspecial2.setText(t.getAcciones()[1]);
    }
}
