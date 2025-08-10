package ec.edu.espol.proyecto2tresenraya;

import ec.edu.espol.proyecto2tresenraya.ia.MinMax;
import ec.edu.espol.proyecto2tresenraya.modelo.Juego;
import ec.edu.espol.proyecto2tresenraya.modelo.Tablero;
import java.util.Scanner;

/**
 *
 * @author Victor
 */
public class TresEnRayas{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tablero tablero = new Tablero();

        System.out.println("=== TRES EN RAYA (Humano vs IA) ===");

        // Elegir ficha
        char fichaHumano;
        while (true) {
            System.out.print("Elige tu ficha (X/O): ");
            fichaHumano = sc.next().toUpperCase().charAt(0);
            if (fichaHumano == 'X' || fichaHumano == 'O') break;
            System.out.println("Ficha inválida. Debe ser X o O.");
        }
        char fichaIA = (fichaHumano == 'X') ? 'O' : 'X';

        // Elegir quién empieza
        System.out.print("¿Quién empieza? (1 = Humano, 2 = IA): ");
        int opcion = sc.nextInt();
        boolean turnoHumano = (opcion == 1);

        tablero.mostrar();

        // Bucle de juego
        while (true) {
            if (turnoHumano) {
                // ---------------- TURNO HUMANO ----------------
                System.out.println("\nTu turno (" + fichaHumano + ")");
                int fila, col;
                while (true) {
                    System.out.print("Fila (0-2): ");
                    fila = sc.nextInt();
                    System.out.print("Columna (0-2): ");
                    col = sc.nextInt();
                    if (tablero.colocarFicha(fila, col, fichaHumano)) break;
                    System.out.println("Posición inválida, intenta de nuevo.");
                }
            } else {
                //TURNO IA 
                System.out.println("\nTurno de la IA (" + fichaIA + ") pensando...");
                int[] mejorJugada = MinMax.obtenerMejorMovimiento(tablero, fichaIA, fichaHumano);
                tablero.colocarFicha(mejorJugada[0], mejorJugada[1], fichaIA);
            }

            tablero.mostrar();

            // Verificar ganador
            char ganador = tablero.verificarGanador();
            if (ganador == fichaHumano) {
                System.out.println("¡Ganaste!");
                break;
            } else if (ganador == fichaIA) {
                System.out.println("La IA gana");
                break;
            } else if (tablero.estaCompleto()) {
                System.out.println("Empate");
                break;
            }

            // Cambiar turno
            turnoHumano = !turnoHumano;
        }

        sc.close();
    }
}