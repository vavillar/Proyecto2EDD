/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.modelo;

import java.io.Serializable;

/**
 *
 * @author Deeje
 */
public class EstadoPartida implements Serializable{
    private final char[][] celdas;
    private final char turnoActual;
    private final char simboloJugador;
    private final String quienInicia;
    private final boolean contraIA;

    public EstadoPartida(Tablero tablero, char turnoActual, char simboloJugador, String quienInicia, boolean contraIA) {
        this.celdas = tablero.getCeldas();
        this.turnoActual = turnoActual;
        this.simboloJugador = simboloJugador;
        this.quienInicia = quienInicia;
        this.contraIA = contraIA;
    }

    public char[][] getCeldas() {
        return celdas;
    }

    public char getTurnoActual() {
        return turnoActual;
    }

    public char getSimboloJugador() {
        return simboloJugador;
    }

    public String getQuienInicia() {
        return quienInicia;
    }

    public boolean isContraIA() {
        return contraIA;
    }
    
    
    
    
}
