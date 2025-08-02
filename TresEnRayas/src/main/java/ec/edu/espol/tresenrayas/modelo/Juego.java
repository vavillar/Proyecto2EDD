/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.tresenrayas.modelo;

/**
 *
 * @author Victor
 */
import java.util.Scanner;

public class Juego {
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Scanner scanner;

    public Juego() {
        tablero = new Tablero();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
    System.out.print("Nombre del Jugador 1: ");
    jugador1 = new Jugador(scanner.nextLine(), 'X');

    System.out.print("Nombre del Jugador 2: ");
    jugador2 = new Jugador(scanner.nextLine(), 'O');

    Jugador actual = jugador1;
    char ganador = ' ';

    /* Bucle que mantiene el juego mientras que el tablero no esté completo 
    y el ganador no se haya sido anunciado*/
    while (!tablero.estaCompleto() && ganador == ' ') {
        tablero.mostrar();
        System.out.println("Turno de " + actual.getNombre() + " (" + actual.getFicha() + ")");
        int fila = pedirCoordenada("Fila");
        int col = pedirCoordenada("Columna");

        if (!tablero.colocarFicha(fila, col, actual.getFicha())) {
            System.out.println("Movimiento inválido. Intenta de nuevo.");
            continue;
        }

        ganador = tablero.verificarGanador();

        if (ganador == ' ') {
            actual = (actual == jugador1) ? jugador2 : jugador1;
        }
    }

    tablero.mostrar();
    if (ganador != ' ') {
        System.out.println("¡Ganó " + (ganador == jugador1.getFicha() ? jugador1.getNombre() : jugador2.getNombre()) + "!");
    } else {
        System.out.println("Empate.");
    }
}

    private int pedirCoordenada(String tipo) { //Método que pide coordenada por consola
        System.out.print(tipo + " (0-2): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. " + tipo + " (0-2): ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}