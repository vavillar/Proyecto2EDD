/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.modelo;


import java.util.Scanner;
import ec.edu.espol.proyecto2tresenraya.ia.MinMax;

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
        System.out.print("Nombre del Jugador 1 (tú): ");
        jugador1 = new Jugador(scanner.nextLine(), 'X');

        // Jugador 2 será la computadora (IA)
        jugador2 = new Jugador("Computadora", 'O');

        Jugador actual = jugador1;
        char ganador = ' ';

        // Bucle principal del juego
        while (!tablero.estaCompleto() && ganador == ' ') {
            tablero.mostrar();
            System.out.println("Turno de " + actual.getNombre() + " (" + actual.getFicha() + ")");

            if (actual == jugador1) {
                // Turno del jugador humano
                int fila = pedirCoordenada("Fila");
                int col = pedirCoordenada("Columna");

                if (!tablero.colocarFicha(fila, col, actual.getFicha())) {
                    System.out.println("Movimiento inválido. Intenta de nuevo.");
                    continue;
                }
            } else {
                // Turno de la computadora (IA)
                System.out.println("La computadora está pensando...");
                int[] mejorMovimiento = MinMax.obtenerMejorMovimiento(tablero, jugador2.getFicha(), jugador1.getFicha());

                // Validación de seguridad (por si no encuentra jugada válida)
                if (mejorMovimiento[0] != -1 && mejorMovimiento[1] != -1) {
                    tablero.colocarFicha(mejorMovimiento[0], mejorMovimiento[1], jugador2.getFicha());
                    System.out.println("Computadora jugó en [" + mejorMovimiento[0] + ", " + mejorMovimiento[1] + "]");
                } else {
                    System.out.println("¡Error: la computadora no encontró un movimiento!");
                    break;
                }
            }

            // Verificar si alguien ganó
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

    // Pide coordenada válida al usuario
    private int pedirCoordenada(String tipo) {
        System.out.print(tipo + " (0-2): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. " + tipo + " (0-2): ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}