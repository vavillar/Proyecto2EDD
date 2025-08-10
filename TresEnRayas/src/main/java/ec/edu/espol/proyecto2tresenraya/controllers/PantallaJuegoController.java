/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.controllers;

import ec.edu.espol.proyecto2tresenraya.ia.MinMax;
import ec.edu.espol.proyecto2tresenraya.modelo.SesionJuego;
import ec.edu.espol.proyecto2tresenraya.modelo.Tablero;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Home
 */
public class PantallaJuegoController implements Initializable {

    @FXML
    private Label lblTurno;
    @FXML
    private Label lblResultado;
    @FXML
    private Button btnReinicio;
    @FXML
    private Button btn00;
    @FXML
    private Button btn01;
    @FXML
    private Button btn02;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn20;
    @FXML
    private Button btn21;
    @FXML
    private Button btn22;

    // Lógica del juego
    private Tablero tablero;
    private char simboloJugador;
    private char simboloIA;
    private String quienInicia;
    private char turnoActual;
    private boolean contraIA;

    // Método para recibir la configuración desde SeleccionSimboloJugadorController
    public void configurarJuego(char simboloJugador, String quienInicia) {
        this.simboloJugador = simboloJugador;
        this.simboloIA = (simboloJugador == 'X') ? 'O' : 'X';
        this.quienInicia = quienInicia;
        this.contraIA = "IA".equalsIgnoreCase(quienInicia) || "Jugador 1".equalsIgnoreCase(quienInicia) && simboloIA != simboloJugador;
        iniciarJuego();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablero = new Tablero();

        simboloJugador = SesionJuego.getSimboloJugador();
        simboloIA = (simboloJugador == 'X') ? 'O' : 'X';
        quienInicia = SesionJuego.getQuienInicia();

        // Por ahora siempre contra IA
        contraIA = true;

        configurarBotones();
        iniciarJuego();
    }


    private void iniciarJuego() {
        tablero.limpiar();
        lblResultado.setVisible(false);

        if ("IA".equalsIgnoreCase(quienInicia)) {
            turnoActual = simboloIA;
            lblTurno.setText("IA");

            // Esperar un poco antes de que la IA mueva
            PauseTransition pausa = new PauseTransition(Duration.seconds(5));
            pausa.setOnFinished(e -> moverIA());
            pausa.play();

        } else {
            turnoActual = simboloJugador;
            lblTurno.setText("Jugador");
        }
    }


    private void configurarBotones() {
        Button[][] botones = {
            {btn00, btn01, btn02},
            {btn10, btn11, btn12},
            {btn20, btn21, btn22}
        };

        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                final int f = fila;
                final int c = col;
                botones[fila][col].setOnAction(e -> manejarMovimiento(f, c, botones[f][c]));
            }
        }
    }

    private void manejarMovimiento(int fila, int col, Button boton) {
        if (tablero.getSimbolo(fila, col) != ' ' || hayGanador()) return;

        if (turnoActual == simboloJugador) {
            tablero.colocarFicha(fila, col, simboloJugador);
            boton.setText(String.valueOf(simboloJugador));

            if (!verificarFin()) {
                turnoActual = simboloIA;
                lblTurno.setText("IA");

                // Esperar antes de que la IA haga su jugada
                PauseTransition pausa = new PauseTransition(Duration.seconds(1));
                pausa.setOnFinished(e -> moverIA());
                pausa.play();
            }
        }
    }

    private void cambiarTurno() {
        if (turnoActual == simboloJugador) {
            // Si es contra IA, ahora le toca a la IA
            if (contraIA) {
                turnoActual = simboloIA;
                lblTurno.setText("IA");
                moverIA();
            } else {
                // Jugador vs Jugador
                turnoActual = (turnoActual == 'X') ? 'O' : 'X';
                lblTurno.setText("Jugador " + (turnoActual == simboloJugador ? "1" : "2"));
            }
        } else {
            // Turno de IA o del otro jugador humano
            if (contraIA) {
                turnoActual = simboloJugador;
                lblTurno.setText("Jugador");
            } else {
                turnoActual = (turnoActual == 'X') ? 'O' : 'X';
                lblTurno.setText("Jugador " + (turnoActual == simboloJugador ? "1" : "2"));
            }
        }
    }

    private void moverIA() {
        if (!hayGanador() && !tablero.estaCompleto()) {
            int[] mejorMovimiento = MinMax.obtenerMejorMovimiento(tablero, simboloIA, simboloJugador);
            tablero.colocarFicha(mejorMovimiento[0], mejorMovimiento[1], simboloIA);

            Button[][] botones = {
                {btn00, btn01, btn02},
                {btn10, btn11, btn12},
                {btn20, btn21, btn22}
            };
            botones[mejorMovimiento[0]][mejorMovimiento[1]].setText(String.valueOf(simboloIA));

            if (!verificarFin()) {
                turnoActual = simboloJugador;
                lblTurno.setText("Jugador");
            }
        }
    }

    private boolean verificarFin() {
        char ganador = tablero.verificarGanador();
        if (ganador != ' ') {
            lblResultado.setVisible(true);
            lblResultado.setText((ganador == simboloJugador ? "Jugador" : "IA") + " ha ganado");
            return true;
        }
        if (tablero.estaCompleto()) {
            lblResultado.setVisible(true);
            lblResultado.setText("Empate");
            return true;
        }
        return false;
    }

    private boolean hayGanador() {
        return tablero.verificarGanador() != ' ' || tablero.estaCompleto();
    }

    @FXML
    private void reiniciarJuego() {
        tablero.limpiar();
        Button[][] botones = {
            {btn00, btn01, btn02},
            {btn10, btn11, btn12},
            {btn20, btn21, btn22}
        };
        for (Button[] fila : botones) {
            for (Button b : fila) {
                b.setText("");
            }
        }
        iniciarJuego();
    }
}