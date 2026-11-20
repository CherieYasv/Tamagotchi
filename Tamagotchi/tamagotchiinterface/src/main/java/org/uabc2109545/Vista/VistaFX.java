package org.uabc2109545.Vista;

import java.io.InputStream;

import org.uabc2109545.Controlador.Controlador;
import org.uabc2109545.Modelo.Dragon;
import org.uabc2109545.Modelo.Serpiente;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

// Vista JavaFX muy simple: botones, un globo de texto y un espacio para la imagen.
public class VistaFX extends Application {
    private Controlador controlador = new Controlador();
    private int accionActual = 0;   // 0 = nada; 2 comer, 3 jugar, 4 dormir, 5 y 6 acciones especiales

    private TextField campoNombre = new TextField();
    private FlowPane listaMascotas = new FlowPane(8, 8);
    private Label burbuja = new Label("Escribe un nombre y elige un tamagotchi para empezar");
    private Label textoImagen = new Label();
    private ImageView vistaImagen = new ImageView();
    private Label estado = new Label();
    private VBox panelAcciones = new VBox(8);
    private Button[] botonesComida = new Button[3];
    private Button[] botonesJuego = new Button[3];
    private Button botonDormir = new Button("Dormir");
    private Button botonEspecial1 = new Button();
    private Button botonEspecial2 = new Button();

    // Los 7 estados que tienen imagen (el nombre del archivo es tipo + estado, ej. dragonNormal.png)
    private String[] estados = {"Normal", "Triste", "Comiendo", "Jugando", "Durmiendo", "Especial1", "Especial2"};

    // Qué imagen hay que agregar en cada estado (mismo orden que "estados")
    private String[] ayudaDragon = {"un dragón sentado", "un dragón triste", "un dragón comiendo",
            "un dragón jugando", "un dragón durmiendo", "un dragón volando", "un dragón con la panza rascada"};
    private String[] ayudaSerpiente = {"una serpiente enroscada", "una serpiente triste", "una serpiente comiendo",
            "una serpiente jugando", "una serpiente durmiendo", "una serpiente sonando su cascabel", "una serpiente tomando el sol"};
    private String[] ayudaPulpo = {"un pulpo flotando", "un pulpo triste", "un pulpo comiendo",
            "un pulpo jugando", "un pulpo durmiendo", "un pulpo lanzando tinta", "un pulpo camuflado"};

    // Arma la ventana
    @Override
    public void start(Stage ventana) {
        // ----- Arriba: crear tamagotchi y elegir con cuál jugar -----
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

        // ----- Centro: globo de texto, imagen y estado -----
        burbuja.setWrapText(true);
        burbuja.setMaxWidth(340);
        burbuja.setTextAlignment(TextAlignment.CENTER);
        // globo cuadrado (sin esquinas redondeadas)
        burbuja.setStyle("-fx-background-color: white; -fx-border-color: black; " + "-fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 10;");

        vistaImagen.setFitWidth(300);
        vistaImagen.setFitHeight(300);
        vistaImagen.setPreserveRatio(true);
        textoImagen.setWrapText(true);
        textoImagen.setMaxWidth(260);
        textoImagen.setTextAlignment(TextAlignment.CENTER);
        StackPane zonaImagen = new StackPane(textoImagen, vistaImagen);
        zonaImagen.setMinSize(300, 300);
        zonaImagen.setMaxSize(300, 300);
        zonaImagen.setStyle("-fx-border-color: gray; -fx-border-style: dashed;");

        estado.setStyle("-fx-font-family: monospace;");
        VBox centro = new VBox(6, burbuja, zonaImagen, estado);
        centro.setAlignment(Pos.TOP_CENTER);
        centro.setPadding(new Insets(10));

        // ----- Abajo: acciones -----
        HBox filaComer = new HBox(8, new Label("Comer:"));
        HBox filaJugar = new HBox(8, new Label("Jugar:"));
        for (int i = 0; i < 3; i++) {
            final int num = i + 1;
            botonesComida[i] = new Button();
            botonesComida[i].setOnAction(e -> hacer(controlador.comer(num), 2));
            botonesJuego[i] = new Button();
            botonesJuego[i].setOnAction(e -> hacer(controlador.jugar(num), 3));
            filaComer.getChildren().add(botonesComida[i]);
            filaJugar.getChildren().add(botonesJuego[i]);
        }
        botonDormir.setOnAction(e -> hacer(controlador.dormir(3), 4));
        botonEspecial1.setOnAction(e -> hacer(controlador.accionEspecial(1), 5));
        botonEspecial2.setOnAction(e -> hacer(controlador.accionEspecial(2), 6));
        HBox filaOtras = new HBox(8, botonDormir, botonEspecial1, botonEspecial2);
        panelAcciones.getChildren().addAll(filaComer, filaJugar, filaOtras);
        panelAcciones.setPadding(new Insets(10));

        // ----- Ventana -----
        BorderPane raiz = new BorderPane();
        raiz.setTop(arriba);
        raiz.setCenter(centro);
        raiz.setBottom(panelAcciones);
        ventana.setTitle("Tamagotchis");
        ventana.setScene(new Scene(raiz, 560, 780));
        ventana.show();
        refrescar();

        // Cada 3 segundos pasa el tiempo y el tamagotchi suelta su mensaje
        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            String aviso = controlador.pasarTiempo();
            accionActual = 0;
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

    // Crea un tamagotchi nuevo con el nombre escrito
    private void crear(int tipo) {
        controlador.crearTamagotchi(tipo, campoNombre.getText());
        campoNombre.clear();
        accionActual = 0;
        burbuja.setText("¡Hola! Soy " + controlador.getActivo().getNombre());
        refrescar();
    }

    // Cambia de tamagotchi
    private void elegir(int pos) {
        controlador.setActivo(pos);
        accionActual = 0;
        burbuja.setText(controlador.getActivo().getMensaje());
        refrescar();
    }

    // Muestra la reacción en el globo y la imagen de la acción
    private void hacer(String reaccion, int imagen) {
        accionActual = imagen;
        burbuja.setText(reaccion);
        refrescar();
    }

    // Vuelve a dibujar botones, estado e imagen con los datos actuales
    private void refrescar() {
        // botones con los tamagotchis disponibles
        listaMascotas.getChildren().clear();
        for (int i = 0; i < controlador.getMascotas().size(); i++) {
            final int pos = i;
            Tamagotchi m = controlador.getMascotas().get(i);
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
            vistaImagen.setImage(null);
            textoImagen.setText("Crea un tamagotchi arriba para empezar");
            return;
        }

        estado.setText(t.getEstado() + "\nÁnimo: " + t.getAnimo());
        String[] comidas = t.getAlimentos();
        String[] juegos = t.getJuegos();
        for (int i = 0; i < 3; i++) {
            botonesComida[i].setText(comidas[i]);
            botonesJuego[i].setText(juegos[i]);
        }
        botonEspecial1.setText(t.getAcciones()[0]);
        botonEspecial2.setText(t.getAcciones()[1]);
        actualizarImagen(t);
    }

    // Pone la imagen del estado; si el archivo no existe, muestra qué imagen agregar
    private void actualizarImagen(Tamagotchi t) {
        int pos = 0;                          // 0 = normal
        if (t.isDormido()) {
            pos = 4;                          // durmiendo
        } else if (accionActual > 0) {
            pos = accionActual;               // acaba de hacer algo
        } else if (t.getSalud() < 30) {
            pos = 1;                          // triste
        }

        String prefijo;
        String[] ayuda;
        if (t instanceof Dragon) {
            prefijo = "dragon";
            ayuda = ayudaDragon;
        } else if (t instanceof Serpiente) {
            prefijo = "serpiente";
            ayuda = ayudaSerpiente;
        } else {
            prefijo = "pulpo";
            ayuda = ayudaPulpo;
        }

        String archivo = prefijo + estados[pos];
        InputStream foto = getClass().getResourceAsStream("/imagenes/" + archivo + ".png");
        if (foto != null) {
            vistaImagen.setImage(new Image(foto));
            textoImagen.setText("");
        } else {
            vistaImagen.setImage(null);
            textoImagen.setText("Agrega una imagen de " + ayuda[pos]
                    + "\n\nArchivo: imagenes/" + archivo + ".png");
        }
    }
}
