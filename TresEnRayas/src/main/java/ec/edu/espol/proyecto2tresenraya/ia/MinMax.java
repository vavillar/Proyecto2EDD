/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.ia;



import ec.edu.espol.proyecto2tresenraya.modelo.Tablero;

// Esta clase representa la IA del juego Tres en Raya.
// Utiliza el algoritmo Minimax para decidir el mejor movimiento.
public class MinMax {

    // Método principal para obtener la mejor jugada que puede hacer la computadora.
    // Retorna un arreglo [fila, columna] con la posición recomendada.
    public static int[] obtenerMejorMovimiento(Tablero tablero, char fichaIA, char fichaHumano) {
        int mejorValor = -1000;
        int[] mejorMovimiento = {-1, -1};

        // Recorremos todas las casillas del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Solo probamos en casillas vacías
                if (tablero.getSimbolo(i, j) == ' ') {
                    // Clonamos el tablero para simular la jugada
                    Tablero copia = tablero.clonar();
                    copia.colocarFicha(i, j, fichaIA);

                    // Llamamos a minimax con profundidad 2 y turno del humano
                    int valor = minimax(copia, 2, false, fichaIA, fichaHumano);

                    // Si es la mejor jugada hasta ahora, la guardamos
                    if (valor > mejorValor) {
                        mejorValor = valor;
                        mejorMovimiento[0] = i;
                        mejorMovimiento[1] = j;
                    }
                }
            }
        }

        return mejorMovimiento;
    }

    // Algoritmo Minimax recursivo
    // profundidad: hasta dónde simular hacia adelante (en este caso 2 turnos)
    // maximizando: true si es turno de la IA, false si es del humano
    private static int minimax(Tablero tablero, int profundidad, boolean maximizando, char fichaIA, char fichaHumano) {
        // Si hay un ganador o empate, devolvemos una puntuación
        char ganador = tablero.verificarGanador();
        if (ganador == fichaIA) return 10;
        if (ganador == fichaHumano) return -10;
        if (tablero.estaCompleto() || profundidad == 0) return evaluarUtilidad(tablero, fichaIA, fichaHumano);

        if (maximizando) {
            int mejorValor = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero.getSimbolo(i, j) == ' ') {
                        Tablero copia = tablero.clonar();
                        copia.colocarFicha(i, j, fichaIA);
                        int valor = minimax(copia, profundidad - 1, false, fichaIA, fichaHumano);
                        if (valor > mejorValor) mejorValor = valor;
                    }
                }
            }
            return mejorValor;
        } else {
            int peorValor = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero.getSimbolo(i, j) == ' ') {
                        Tablero copia = tablero.clonar();
                        copia.colocarFicha(i, j, fichaHumano);
                        int valor = minimax(copia, profundidad - 1, true, fichaIA, fichaHumano);
                        if (valor < peorValor) peorValor = valor;
                    }
                }
            }
            return peorValor;
        }
    }

    // Evalúa qué tan bueno es el tablero para la IA comparando las líneas disponibles
    private static int evaluarUtilidad(Tablero tablero, char fichaIA, char fichaHumano) {
        int lineasIA = contarLineasDisponibles(tablero, fichaIA, fichaHumano);
        int lineasHumano = contarLineasDisponibles(tablero, fichaHumano, fichaIA);
        return lineasIA - lineasHumano;
    }

    // Cuenta las filas, columnas y diagonales donde aún puede ganar el jugador indicado
    private static int contarLineasDisponibles(Tablero tablero, char jugador, char oponente) {
        int total = 0;

        // Filas
        for (int i = 0; i < 3; i++) {
            boolean disponible = true;
            for (int j = 0; j < 3; j++) {
                if (tablero.getSimbolo(i, j) == oponente) {
                    disponible = false;
                }
            }
            if (disponible) total++;
        }

        // Columnas
        for (int j = 0; j < 3; j++) {
            boolean disponible = true;
            for (int i = 0; i < 3; i++) {
                if (tablero.getSimbolo(i, j) == oponente) {
                    disponible = false;
                }
            }
            if (disponible) total++;
        }

        // Diagonal principal
        boolean diagonal1 = true;
        for (int i = 0; i < 3; i++) {
            if (tablero.getSimbolo(i, i) == oponente) {
                diagonal1 = false;
            }
        }
        if (diagonal1) total++;

        // Diagonal secundaria
        boolean diagonal2 = true;
        for (int i = 0; i < 3; i++) {
            if (tablero.getSimbolo(i, 2 - i) == oponente) {
                diagonal2 = false;
            }
        }
        if (diagonal2) total++;

        return total;
    }
}
