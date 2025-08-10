package ec.edu.espol.proyecto2tresenraya.modelo;

import java.io.Serializable;

public class SesionJuego implements Serializable{

    private static char simboloJugador = 'X'; // Valor por defecto
    private static String quienInicia = "Jugador 1"; // Valor por defecto

    // --- Getters ---
    public static char getSimboloJugador() {
        return simboloJugador;
    }

    public static String getQuienInicia() {
        return quienInicia;
    }

    // --- Setters con validación ---
    public static void setSimboloJugador(char simbolo) {
        simbolo = Character.toUpperCase(simbolo);
        if (simbolo == 'X' || simbolo == 'O') {
            SesionJuego.simboloJugador = simbolo;
        } else {
            throw new IllegalArgumentException("El símbolo debe ser 'X' o 'O'");
        }
    }

    public static void setQuienInicia(String quien) {
        if (quien != null && !quien.trim().isEmpty()) {
            SesionJuego.quienInicia = quien.trim();
        } else {
            throw new IllegalArgumentException("El valor de quien inicia no puede estar vacío");
        }
    }

    // --- Método para reiniciar la sesión ---
    public static void reiniciar() {
        simboloJugador = 'X';
        quienInicia = "Jugador 1";
    }
}
