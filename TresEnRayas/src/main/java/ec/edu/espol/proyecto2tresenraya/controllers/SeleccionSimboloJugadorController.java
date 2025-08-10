package ec.edu.espol.proyecto2tresenraya.controllers;

import ec.edu.espol.proyecto2tresenraya.App;
import ec.edu.espol.proyecto2tresenraya.modelo.SesionJuego;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class SeleccionSimboloJugadorController implements Initializable {

    @FXML
    private Label lblNumeroJugador; // Puedes usarlo para indicar Jugador 1 / Jugador 2 si lo necesitas
    @FXML
    private Button btncirculo;
    @FXML
    private Button btncruz;
    @FXML
    private ChoiceBox<String> cbiniciaJuego; 
    @FXML
    private Button btnJugar;

    // Variables internas para las elecciones del jugador
    private char simboloJugador = 'X'; // Valor por defecto
    private String quienInicia = "Jugador 1"; // Valor por defecto

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar opciones en el ChoiceBox
        cbiniciaJuego.getItems().addAll("Jugador 1", "IA");
        cbiniciaJuego.setValue("Jugador 1");

        // Escuchar selección de símbolo
        btncirculo.setOnAction(e -> simboloJugador = 'O');
        btncruz.setOnAction(e -> simboloJugador = 'X');

        // Escuchar selección de inicio
        cbiniciaJuego.setOnAction(e -> quienInicia = cbiniciaJuego.getValue());

        // Acción para el botón JUGAR
        btnJugar.setOnAction(e -> iniciarJuego());
    }

    private void iniciarJuego() {
        try {
            // Guardar configuración en SesionJuego (disponible para PantallaJuegoController)
            SesionJuego.setSimboloJugador(simboloJugador);
            SesionJuego.setQuienInicia(quienInicia);

            // Cambiar a la pantalla de juego usando el método centralizado en App
            App.mostrarPantallaJuego();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
